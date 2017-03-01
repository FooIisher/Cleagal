package com.ynkj.common.session;


import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;


/**
 * 
 * Cookie 工具类
 *
 */
public final class SessionUtils {
	
	protected static final Logger logger = Logger.getLogger(SessionUtils.class);
	
	public static final String SESSION_USER = "UserSession";
	public static final String PLATFORM_TYPE = "PlatformType";
	public static final String USER_MENU="UserMenu";
	//用户区域
	public static final String USER_AREA = "";
	
	private static final String SESSION_VALIDATECODE = "session_validatecode";//验证码
	
	
	private static final String SESSION_ACCESS_URLS = "session_access_urls"; //系统能够访问的URL
	
	
	private static final String SESSION_MENUBTN_MAP = "session_menubtn_map"; //系统菜单按钮
	
	/**
	  * 从session中获取商户ID
	  * @param request
	  * @return SysUser
	  */
	 public static String getUserId(HttpServletRequest request){
		 UserInfoSession user = getUser(request);
		 if(user != null){
			 return user.getUserId();
		 }
		return null; 
	 }
	 
	 /**
	  * 从session中获取用户区域
	  * @param request
	  * @return SysUser
	  */
	 public static String getUserArea(HttpServletRequest request){
		 String userArea = (String)request.getSession(true).getAttribute(USER_AREA);
		 return userArea; 
	 }
	 
	 /**
	  * 得到查询区域
	  * @param request
	  * @return SysUser
	  */
	 public static String getQueryArea(HttpServletRequest request){
		 String userArea = (String)request.getSession(true).getAttribute(USER_AREA);
		 if  ("000000".equals(userArea)) {
			 return null;
		 }
		 return userArea; 
	 }
	 
	 /**
	  * 从session中获取用户信息
	  * @param request
	  * @return SysUser
	  */
	 public static UserInfoSession getUser(HttpServletRequest request){
		return (UserInfoSession)request.getSession(true).getAttribute(SESSION_USER);
	 }

	/**
	 * 验证是否是手机号码
	 * @param mobiles
	 * @return
	 */
	public static boolean isMobileNumber(String mobiles){
		Pattern p = Pattern.compile("^((13[0-9])|(15[^4,\\D])|(18[0-9])|(17[0-9]))\\d{8}$");
		Matcher m = p.matcher(mobiles);
		return m.matches();
	}
	
	/**
	 * 验证是否是湖北省企业注册号
	 * @param registerId
	 * @return
	 */
	public static boolean isRegisterId(String registerId){
		Pattern p = Pattern.compile("^(42)\\d{11,13}$");
		Matcher m = p.matcher(registerId);
		return m.matches();
	}
	
	/**
	 * 验证是否是E-MAIL
	 * @param registerId
	 * @return
	 */
	public static boolean isEmail(String str){
		Pattern p = Pattern.compile("\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*");
		Matcher m = p.matcher(str);
		return m.matches();
	}
	
	/**
	  * 设置session的值
	  * @param request
	  * @param key
	  * @param value
	  */
	 public static void setAttr(HttpServletRequest request,String key,Object value){
		 request.getSession(true).setAttribute(key, value);
	 }
	 
	 
	 /**
	  * 获取session的值
	  * @param request
	  * @param key
	  * @param value
	  */
	 public static Object getAttr(HttpServletRequest request,String key){
		 return request.getSession(true).getAttribute(key);
	 }
	 
	 /**
	  * 删除Session值
	  * @param request
	  * @param key
	  */
	 public static void removeAttr(HttpServletRequest request,String key){
		 request.getSession(true).removeAttribute(key);
		 request.getSession(true).invalidate();//2014-05-14
	 }
	 
	 
	 /**
	  * 从session中移除用户信息
	  * @param request
	  * @return MerchantSession
	  */
	 public static void removeUser(HttpServletRequest request){
		removeAttr(request, SESSION_USER);
	 }
	 
	 /**
	  * 从session中移除用户信息
	  * @param request
	  * @return MerchantSession
	  */
	 public static void removePlatform(HttpServletRequest request){
		removeAttr(request, PLATFORM_TYPE);
	 }
	 
	 
	 /**
	  * 设置验证码 到session
	  * @param request
	  * @param user
	  */
	 public static void setValidateCode(HttpServletRequest request,String validateCode){
		 request.getSession(true).setAttribute(SESSION_VALIDATECODE, validateCode);
	 }
	 
	 
	 /**
	  * 从session中获取验证码
	  * @param request
	  * @return MerchantSession
	  */
	 public static String getValidateCode(HttpServletRequest request){
		return (String)request.getSession(true).getAttribute(SESSION_VALIDATECODE);
	 }
	 
	 
	 /**
	  * 从session中获删除验证码
	  * @param request
	  * @return MerchantSession
	  */
	 public static void removeValidateCode(HttpServletRequest request){
		removeAttr(request, SESSION_VALIDATECODE);
	 }
	 
	 
	 /**
	  * 判断URL是否可访问
	  * @param request
	  * @return
	  */
	 public static boolean isAccessUrl(HttpServletRequest request,String url){ 
		 List<String> accessUrls = (List)getAttr(request, SESSION_ACCESS_URLS);
		 if(accessUrls == null ||accessUrls.isEmpty() || !accessUrls.contains(url)){
			 return false;
		 }
		 return true;
	 }
	 
	 /**
	  * 设置cookie
	  * @param response
	  * @param name  cookie名字
	  * @param value cookie值
	  * @param maxAge cookie生命周期  以秒为单位
	  */
	 public static void addCookie(HttpServletResponse response,String name,String value,int maxAge){
	     Cookie cookie = new Cookie(name,value);
	     cookie.setPath("/");
	     if(maxAge>0)  cookie.setMaxAge(maxAge);
	     response.addCookie(cookie);
	 }
	 
	 /**
	  * 根据名字获取cookie
	  * @param request
	  * @param name cookie名字
	  * @return
	  */
	 public static Cookie getCookieByName(HttpServletRequest request,String name){
	     Map<String,Cookie> cookieMap = ReadCookieMap(request);
	     if(cookieMap.containsKey(name)){
	         Cookie cookie = (Cookie)cookieMap.get(name);
	         return cookie;
	     }else{
	         return null;
	     }   
	 }
	  
	  
	  
	 /**
	  * 将cookie封装到Map里面
	  * @param request
	  * @return
	  */
	 private static Map<String,Cookie> ReadCookieMap(HttpServletRequest request){  
	     Map<String,Cookie> cookieMap = new HashMap<String,Cookie>();
	     Cookie[] cookies = request.getCookies();
	     if(null!=cookies){
	         for(Cookie cookie : cookies){
	             cookieMap.put(cookie.getName(), cookie);
	         }
	     }
	     return cookieMap;
	 }
	 
	 
	 /**
	  * 设置菜单按钮
	  * @param request
	  * @param btnMap
	  */
	 public static void setMemuBtnMap(HttpServletRequest request,Map<String,List> btnMap){ //判断登录用户是否超级管理员
		 setAttr(request, SESSION_MENUBTN_MAP, btnMap);
	 }
	 
	 /**
	  * 获取菜单按钮
	  * @param request
	  * @param btnMap
	  */
	 public static List<String> getMemuBtnListVal(HttpServletRequest request,String menuUri){ //判断登录用户是否超级管理员
		 Map btnMap  = (Map)getAttr(request, SESSION_MENUBTN_MAP);
		 if(btnMap == null || btnMap.isEmpty()){
			 return null;
		 }
		 return (List<String>)btnMap.get(menuUri);
	 }
	
}