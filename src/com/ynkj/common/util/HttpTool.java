package com.ynkj.common.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.net.ssl.SSLHandshakeException;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.HttpEntity;
import org.apache.http.HttpEntityEnclosingRequest;
import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.apache.http.HttpVersion;
import org.apache.http.NameValuePair;
import org.apache.http.NoHttpResponseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.HttpRequestRetryHandler;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.PoolingClientConnectionManager;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.params.CoreProtocolPNames;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;
import org.apache.http.protocol.ExecutionContext;
import org.apache.http.protocol.HTTP;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;

import com.ynkj.legal.common.Constant;

public class HttpTool {
	private final static Log logger = LogFactory.getLog(HttpTool.class);
	/**
	 * 默认字符集
	 */
	private static final String defaultCharset = "UTF-8";
	
	/**
	 * 请求失败重试次数
	 */
	private int retryCount = 3;
	
	// 异常自动恢复处理, 使用HttpRequestRetryHandler接口实现请求的异常恢复
	private HttpRequestRetryHandler requestRetryHandler = new HttpRequestRetryHandler() {
		// 自定义的恢复策略
		public boolean retryRequest(IOException exception, int executionCount,
				HttpContext context) {
			// 设置恢复策略，在发生异常时候将自动重试3次
			if (executionCount >= retryCount) {
				// Do not retry if over max retry count
				return false;
			}
			if (exception instanceof NoHttpResponseException) {
				// Retry if the server dropped connection on us
				return true;
			}
			if (exception instanceof SSLHandshakeException) {
				// Do not retry on SSL handshake exception
				return false;
			}
			HttpRequest request = (HttpRequest) context
					.getAttribute(ExecutionContext.HTTP_REQUEST);
			boolean idempotent = (request instanceof HttpEntityEnclosingRequest);
			if (!idempotent) {
				// Retry if the request is considered idempotent
				return true;
			}
			return false;
		}
	};
	// 使用ResponseHandler接口处理响应，HttpClient使用ResponseHandler会自动管理连接的释放，解决了对连接的释放管理
	private ResponseHandler<String> responseHandler = new ResponseHandler<String>() {
		// 自定义响应处理
		public String handleResponse(HttpResponse response)
				throws ClientProtocolException, IOException {
			HttpEntity entity = response.getEntity();
			if (entity != null) {
				String charset = EntityUtils.getContentCharSet(entity) == null ? defaultCharset
						: EntityUtils.getContentCharSet(entity);
				return new String(EntityUtils.toByteArray(entity), charset);
			} else {
				return null;
			}
		}
	};
	
	/**
	 * Get方式提交,URL中包含查询参数, 格式：http://www.g.cn?search=p&name=s.....
	 * 
	 * @param url
	 *            提交地址
	 * @return 响应消息
	 */
	public String get(String url) {
		return get(url, null, null);
	}

	/**
	 * Get方式提交,URL中不包含查询参数, 格式：http://www.g.cn
	 * 
	 * @param url
	 *            提交地址
	 * @param params
	 *            查询参数集, 键/值对
	 * @return 响应消息
	 */
	public String get(String url, Map<String, String> params) {
		return get(url, params, null);
	}

	/**
	 * Get方式提交,URL中不包含查询参数, 格式：http://www.g.cn
	 * 
	 * @param url
	 *            提交地址
	 * @param params
	 *            查询参数集, 键/值对
	 * @param charset
	 *            参数提交编码集
	 * @return 响应消息
	 */
	public String get(String url, Map<String, String> params,
			String charset) {
		if (StringUtils.isBlank(url)) {
			return null;
		}
		List<NameValuePair> qparams = getParamsList(params);
		if (qparams != null && qparams.size() > 0) {
			charset = (charset == null ? defaultCharset : charset);
			String formatParams = URLEncodedUtils.format(qparams, charset);
			url = (url.indexOf("?")) < 0 ? (url + "?" + formatParams) : (url
					.substring(0, url.indexOf("?") + 1) + formatParams);
		}
		DefaultHttpClient httpclient = getDefaultHttpClient(charset);
		logger.info("Get请求,url:"+url);
		HttpGet hg = new HttpGet(url);
		// 发送请求，得到响应
		String responseStr = null;
		try {
			responseStr = httpclient.execute(hg, responseHandler);
		} catch (ClientProtocolException e) {
			e.printStackTrace();
			responseStr = "-11,客户端连接协议错误";
		} catch (IOException e) {
			e.printStackTrace();
			responseStr = "-12,IO操作异常";
		} finally {
			abortConnection(hg, httpclient);
		}
		return responseStr;
	}

	/**
	 * Post方式提交,URL中不包含提交参数, 格式：http://www.g.cn
	 * 
	 * @param url
	 *            提交地址
	 * @param content
	 *            提交内容
	 * @return 响应消息
	 */
	public String post(String url, String content) {
		return post(url, content, null);
	}
	
	/**
	 * Post方式提交
	 * @param url 请求地址
	 * @param content 请求内容，可为空
	 * @param charset 字符集
	 * @return
	 */
	public String post(String url, String content, String charset) {
		if (StringUtils.isBlank(url)) {
			return null;
		}

		String responseStr = null;
		
		if(charset == null){
			charset = defaultCharset;
		}

		DefaultHttpClient httpclient = getDefaultHttpClient(charset);
		
		ByteArrayEntity entity = null;
		HttpPost hp = null;

		try {
			hp = new HttpPost(url);
			if(!StringUtils.isBlank(content)){
				entity = new ByteArrayEntity(content.getBytes(charset));
				hp.setEntity(entity);
			}
			responseStr = httpclient.execute(hp, responseHandler);
		} catch (IOException e) {
			e.printStackTrace();
			responseStr = "-12,IO操作异常";
		} finally {
			abortConnection(hp, httpclient);
		}
		return responseStr;
	}
	
	/**
	 * Post方式提交,URL中不包含提交参数, 格式：http://www.g.cn
	 * 
	 * @param url
	 *            提交地址
	 * @param params
	 *            提交参数集, 键/值对
	 * @return 响应消息
	 */
	public String post(String url, Map<String, String> params) {
		return post(url, params, null);
	}
		
	/**
	 * Post方式提交,URL中不包含提交参数, 格式：http://www.g.cn
	 * 
	 * @param url
	 *            提交地址
	 * @param params
	 *            提交参数集, 键/值对
	 * @param charset
	 *            参数提交编码集
	 * @return 响应消息
	 */
	public String post(String url, Map<String, String> params,
			String charset) {
		if (StringUtils.isBlank(url)) {
			return null;
		}

		if(charset == null){
			charset = defaultCharset;
		}
		
		// 发送请求，得到响应
		String responseStr = null;

		// 创建HttpClient实例
		DefaultHttpClient httpclient = getDefaultHttpClient(charset);
		UrlEncodedFormEntity formEntity = null;
		try {
			if (StringUtils.isBlank(charset)) {
				formEntity = new UrlEncodedFormEntity(getParamsList(params));
			} else {
				formEntity = new UrlEncodedFormEntity(getParamsList(params),
						charset);
			}
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			responseStr = "-13,不支持的编码集";
		}
		HttpPost hp = new HttpPost(url);
		hp.setEntity(formEntity);

		try {
			responseStr = httpclient.execute(hp, responseHandler);
		} catch (ClientProtocolException e) {
			e.printStackTrace();
			responseStr = "-11,客户端连接协议错误";
		} catch (IOException e) {
			e.printStackTrace();
			responseStr = "-12,IO操作异常";
		} finally {
			abortConnection(hp, httpclient);
		}
		return responseStr;
	}

	/**
	 * 获取DefaultHttpClient实例
	 * 
	 * @param charset
	 *            参数编码集, 可空
	 * @return DefaultHttpClient 对象
	 */
	private DefaultHttpClient getDefaultHttpClient(final String charset) {
		DefaultHttpClient httpclient = new DefaultHttpClient();
		httpclient.getParams().setParameter(
				CoreProtocolPNames.PROTOCOL_VERSION, HttpVersion.HTTP_1_1);
		// 模拟浏览器，解决一些服务器程序只允许浏览器访问的问题
		httpclient.getParams().setParameter(CoreProtocolPNames.USER_AGENT,
				"Mozilla/4.0 (compatible; MSIE 7.0; Windows NT 5.1)");
		httpclient.getParams().setParameter(
				CoreProtocolPNames.USE_EXPECT_CONTINUE, Boolean.FALSE);
		httpclient.getParams().setParameter(
				CoreProtocolPNames.HTTP_CONTENT_CHARSET,
				charset == null ? defaultCharset : charset);
		httpclient.setHttpRequestRetryHandler(requestRetryHandler);
		return httpclient;
	}

	/**
	 * 释放HttpClient连接
	 * 
	 * @param hrb
	 *            请求对象
	 * @param httpclient
	 *            client对象
	 */
	private void abortConnection(final HttpRequestBase hrb,
			final HttpClient httpclient) {
		if (hrb != null) {
			hrb.abort();
		}
	//	if (httpclient != null) {
	//		httpclient.getConnectionManager().shutdown();
	//	}
	}
	
	/**
	 * 将传入的键/值对参数转换为NameValuePair参数集
	 * 
	 * @param paramsMap
	 *            参数集, 键/值对
	 * @return NameValuePair参数集
	 */
	private List<NameValuePair> getParamsList(
			Map<String, String> paramsMap) {
		if (paramsMap == null || paramsMap.size() == 0) {
			return null;
		}
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		for (Map.Entry<String, String> map : paramsMap.entrySet()) {
			params.add(new BasicNameValuePair(map.getKey(), map.getValue()));
		}
		return params;
	}

	public void setRetryCount(int retryCount) {
		this.retryCount = retryCount;
	}
	
	/**    
	 * invokeGetService(这里用一句话描述这个方法的作用)    
	 * @param @param url
	 * @param @param charsetName
	 * @param @return     
	 * @return String
	 * @Exception 异常对象
	*/
	public String invokeGetService(String url, String charsetName, int timeoutms){
		String resStr = "";
		HttpClient httpclient = null;
//		logger.info("request url : " + url);
		try {
			httpclient = new DefaultHttpClient();
			httpclient.getParams().setIntParameter("http.socket.timeout", timeoutms);
			
			HttpGet httpget = new HttpGet(url);
			HttpResponse response = httpclient.execute(httpget);
			HttpEntity resEntity = response.getEntity();
			if (resEntity != null) {
			    InputStream is = resEntity.getContent();
			    resStr = convertStreamToString(is, charsetName);
			}
		} catch (ClientProtocolException e) {
		    logger.error(e.getMessage(), e);
		} catch (IOException e) {
		    logger.error(e.getMessage(), e);
		} finally{   
            //HttpClient的实例不再需要时，降低连接，管理器关闭，以确保立即释放所有系统资源   
            if(httpclient != null)   
                httpclient.getConnectionManager().shutdown();   
        }
//		logger.debug("interface return result: " + resStr);
		return resStr;
	}
	
	public String convertStreamToString(InputStream is, String charsetName){   
        /*  
         * To convert the InputStream to String we use the BufferedReader.readLine()  
         * method. We iterate until the BufferedReader return null which means  
         * there's no more data to read. Each line will appended to a StringBuilder  
         * and returned as String.  
         */            
        StringBuilder sb = new StringBuilder();       
        try {   
        	BufferedReader reader = new BufferedReader(new InputStreamReader(is, charsetName)); 
        	String line = null;
            while ((line = reader.readLine()) != null) {   
                sb.append(line + "\n");   
            }   
        } catch (IOException e) {   
            logger.info(e.getMessage());   
        } finally {   
            try {   
                is.close();   
            } catch (IOException e) {   
                logger.info(e.getMessage());
            }   
        }       
        return sb.toString();   
    } 
	
	public void PostWithJSON(String url, String json) throws Exception {
        // 将JSON进行UTF-8编码,以便传输中文        
        DefaultHttpClient httpClient = new DefaultHttpClient();
        HttpPost httpPost = new HttpPost(url);
        httpPost.addHeader(HTTP.CONTENT_TYPE, "application/json");
        
        StringEntity se = new StringEntity(json);
        se.setContentType("text/json");
        se.setContentEncoding(new BasicHeader(HTTP.CONTENT_TYPE, "application/json"));
        httpPost.setEntity(se);
        httpClient.execute(httpPost);
    }
	
	/** 
	 * 适合多线程的HttpClient,用httpClient4.2.1实现 
	 * @return DefaultHttpClient 
	 */  
	public static DefaultHttpClient getHttpClient()  
	{         
	    // 设置组件参数, HTTP协议的版本,1.1/1.0/0.9   
	    HttpParams params = new BasicHttpParams();   
	    HttpProtocolParams.setVersion(params, HttpVersion.HTTP_1_1);   
	    HttpProtocolParams.setUserAgent(params, "HttpComponents/1.1");   
	    HttpProtocolParams.setUseExpectContinue(params, true);      
	  
	    //设置连接超时时间   
	    int REQUEST_TIMEOUT = 10*1000;  //设置请求超时10秒钟   
	    int SO_TIMEOUT = 10*1000;       //设置等待数据超时时间10秒钟   
	    //HttpConnectionParams.setConnectionTimeout(params, REQUEST_TIMEOUT);  
	    //HttpConnectionParams.setSoTimeout(params, SO_TIMEOUT);  
	    params.setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, REQUEST_TIMEOUT);    
	    params.setParameter(CoreConnectionPNames.SO_TIMEOUT, SO_TIMEOUT);   
	    
	    //设置访问协议   
	    SchemeRegistry schreg = new SchemeRegistry();    
	    schreg.register(new Scheme("http",80,PlainSocketFactory.getSocketFactory()));   
	    schreg.register(new Scheme("https", 443, SSLSocketFactory.getSocketFactory()));         
	      
	    //多连接的线程安全的管理器   
	    PoolingClientConnectionManager pccm = new PoolingClientConnectionManager(schreg);  
	    pccm.setDefaultMaxPerRoute(20); //每个主机的最大并行链接数   
	    pccm.setMaxTotal(100);          //客户端总并行链接最大数      
	    
	    DefaultHttpClient httpClient = new DefaultHttpClient(pccm, params);    
	    return httpClient;  
	} 
 
	
}
