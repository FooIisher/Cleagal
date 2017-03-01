package com.ynkj.common.util;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.json.JSONException;

import com.alibaba.fastjson.JSONObject;

/**
 * <br>
 * <b>功能：</b>详细的功能描述<br>
 * <b>作者：</b>www.jeecg.org<br>
 * <b>日期：</b> Dec 14, 2013 <br>
 * <b>更新者：</b><br>
 * <b>日期：</b> <br>
 * <b>更新内容：</b><br>
 */
public class HtmlUtil {
	
	/**
	 * 
	 * <br>
	 * <b>功能：</b>输出json格式<br>
	 * <b>作者：</b>www.jeecg.org<br>
	 * <b>日期：</b> Dec 14, 2013 <br>
	 * @param response
	 * @param jsonStr
	 * @throws Exception
	 */
	public static void writerJson(HttpServletResponse response,String jsonStr) {
			writer(response,jsonStr);
	}
	
	public static void writerJson(HttpServletResponse response,Object object){
		response.setContentType("application/json");
		writer(response, JSONObject.toJSONString(object));
	}
	public static void writerJsonforUpload(HttpServletResponse response,Object object){
		response.setContentType("application/json");
		writer(response, JSONObject.toJSONString(object));
	}
	
	/**
	 * 
	 * <br>
	 * <b>功能：</b>输出HTML代码<br>
	 * <b>作者：</b>www.jeecg.org<br>
	 * <b>日期：</b> Dec 14, 2013 <br>
	 * @param response
	 * @param htmlStr
	 * @throws Exception
	 */
	public static void writerHtml(HttpServletResponse response,String htmlStr) {
		writerHtml(response, htmlStr, null);
	}
	
	public static void writerHtml(HttpServletResponse response,String htmlStr, String encoding) {
		response.setContentType("text/html");
		writer(response,htmlStr,encoding);
	}
	
	private static void writer(HttpServletResponse response,String str){
		writer(response, str, null);
	}
	
	private static void writer(HttpServletResponse response,String str, String encoding){
		if(StringUtils.isEmpty(encoding)){
			encoding = "UTF-8";
		}
		try {
			StringBuffer result = new StringBuffer();
			//设置页面不缓存
			response.setHeader("Pragma", "No-cache");
			response.setHeader("Cache-Control", "no-cache");
			response.setCharacterEncoding(encoding);
			PrintWriter out= null;
			out = response.getWriter();
			out.print(str);
			out.flush();
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	} 
}
