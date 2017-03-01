package com.ynkj.common.filter;

import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

import org.apache.commons.lang.StringEscapeUtils;

import com.ynkj.common.util.SpringContextUtil;
import com.ynkj.common.util.Utils;


/**
 * 覆盖getParameter、getParameterValues和getParameterMap方法</br>
 * 将参数值里的指定半角字符，强制替换成全角字符。
 * 
 * @author SCG
 * 
 */
public class XssHttpServletRequestWrapper extends HttpServletRequestWrapper {

	Map filteredParameter;

	public XssHttpServletRequestWrapper(HttpServletRequest request) {
		super(request);
	}
	
	@Override
	public String getQueryString() {
		// TODO Auto-generated method stub
		return  StringEscapeUtils.escapeSql(StringEscapeUtils.escapeJavaScript(StringEscapeUtils.escapeHtml(super.getQueryString())));
	}

	@Override
	public String getParameter(String name) {
		
		String value = super.getParameter(name);
//		if (!isXssExemptUrl()) {
			if (value != null) {
				System.out.println("getParameter-before:"+value);
				value = xssEncode(name, value);
				System.out.println("getParameter-after:"+value);
			}
			return value;
//		} else {
//			return value;
//		}
	}

	private boolean isXssExemptUrl() {
		
		boolean isXssExemptUrl = false;
		
		String requestUrl = getRequestURI();
		XssConfigParam config=(XssConfigParam)SpringContextUtil.getBean("xssConfigParam");
		String inshopXssExemptUrl = config.getInshopXssExemptUrl();
		if(requestUrl == null || "".equals(requestUrl)) {
			isXssExemptUrl = false;
		} else {
			
			if (inshopXssExemptUrl==null || "".equals(inshopXssExemptUrl)) {
				isXssExemptUrl = false;
			} else {
				String[] exemptUrlArray = inshopXssExemptUrl.split(";");
				for (String item: exemptUrlArray) {
					
					if (requestUrl.indexOf(item) >= 0) {
						isXssExemptUrl = true;
						break;
					}
				}
			}
		}
		
		return isXssExemptUrl;
	}
	
	@Override
	public String[] getParameterValues(String parameter) {

		String[] values = super.getParameterValues(parameter);
		if (values != null) {
			int count = values.length;
			String[] encodedValues = new String[count];
			for (int i = 0; i < count; i++) {
				System.out.println("getParameterValues-before:"+values[i]);
				encodedValues[i] = xssEncode(parameter, values[i]);
				System.out.println("getParameterValues-after:"+encodedValues[i]);
			}
			return encodedValues;
		}
		return values;
	}

	@Override
	public Map getParameterMap() {
		if (this.filteredParameter == null) {
			Map newMap = new HashMap();
			Map paraMap = super.getParameterMap();
			Iterator<Entry<String, String[]>> keSet = paraMap.entrySet().iterator();
			while (keSet.hasNext()) {
				Entry me = keSet.next();
				String key = me.getKey().toString();
				Object val = me.getValue();
				String[] values;
				if (val != null) {
					if (val instanceof String[]) {
						values = (String[]) val;
						for (int k = 0; k < values.length; k++) {
							System.out.println("getParameterMap-before:"+values[k]);
							values[k] = xssEncode(key, values[k]);
							System.out.println("getParameterMap-after:"+values[k]);
						}
					} else {
						values = new String[1];
						values[0] = xssEncode(key, val.toString());
					}
					newMap.put(key, values);
				}
			}
			this.filteredParameter = newMap;
			Collections.unmodifiableMap(newMap);
			return newMap;
		} else {
			Collections.unmodifiableMap(filteredParameter);
			return filteredParameter;
		}
	}

	/**
	 * 将容易引起xss漏洞的半角字符直接替换成全角字符
	 * 
	 * @param name
	 *            参数
	 * @param value
	 *            值
	 * @return
	 */
	private String xssEncode(String name, String value) {
		if (value == null || value.isEmpty()) {
			return value;
		}
		
//		XssConfigParam config=(XssConfigParam)SpringContextUtil.getBean("xssConfigParam");
//		boolean is_close_xss = false;
//		// 读取配置分离文件inshop_xss
//		Integer inshop_xss = config.getInshopXss();
//		if (inshop_xss == XssConfigParam.INSHOPXSS_OPEN.intValue()) {
//			// 默认开启
//			is_close_xss = true;
//		}
//		if (is_close_xss) {

			// 带pass的因为有密码，暂时放开
			if (name != null && name.toLowerCase().indexOf("pass") > -1) {
				return value;
			}
			
			if (name != null && name.toLowerCase().indexOf("remarkfile") > -1) {
				return value;
			}
			
			if (name != null && name.toLowerCase().indexOf("content") > -1) {
				return value;
			}

			StringBuilder sb = new StringBuilder(value.length() + 16);
			for (int i = 0; i < value.length(); i++) {
				char c = value.charAt(i);
				switch (c) {
				case '>':
					sb.append('＞');// 全角大于号
					break;
				case '<':
					sb.append('＜');// 全角小于号
					break;
				case '\'':
					sb.append('‘');// 全角单引号
					break;
				case '\"':
					sb.append('“');// 全角双引号
					break;
//					case '&':
//					sb.append('＆');// 全角＆号，不能转，有returnUrl中会有用到
//					break;
				case '\\':
					sb.append('＼');// 全角斜线
					break;
//				case '#':
//					sb.append('＃');// 全角井号，不能转，有锚点中会有用到
//					break;
				default:
					sb.append(c);
					break;
				}
			}
			return Utils.decodeStr(sb.toString());

//		} else {
//			return value;
//		}
	}

}
