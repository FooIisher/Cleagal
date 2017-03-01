package com.ynkj.common.interceptor;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.ynkj.common.session.SessionUtils;
import com.ynkj.common.session.UserInfoSession;
import com.ynkj.common.util.Utils;
import com.ynkj.legal.model.users.Users;
import com.ynkj.legal.service.users.UsersService;

/**
 * 权限拦截器
 * @author  SHI CHANGGEN
 *
 */
public class AuthInterceptor extends HandlerInterceptorAdapter {
	private final static Logger log = Logger.getLogger(AuthInterceptor.class);
	@Resource
	protected UsersService usersService;
	
	@Override
	public boolean preHandle(HttpServletRequest request,HttpServletResponse response, Object handler) throws Exception {
		HandlerMethod method = (HandlerMethod)handler;
		Auth  auth = method.getMethod().getAnnotation(Auth.class);
		//判断是否移动端访问
		String appAccess = request.getParameter("appAccess");
		//app访问都要刷新session，避免切换用户，登入、登出session混乱
		if(StringUtils.isNotEmpty(appAccess) && "1".equals(appAccess)){
			UserInfoSession userSession = new UserInfoSession();
			String uid = request.getParameter("uid");
			if(StringUtils.isEmpty(uid)){
				SessionUtils.removeUser(request);
			}else{
				Users user = usersService.getUsersById(uid);
				if(user == null){
					SessionUtils.removeUser(request);					
				}else{
					org.springframework.beans.BeanUtils.copyProperties(user, userSession);
					SessionUtils.setAttr(request, SessionUtils.SESSION_USER, userSession);
				}
			}
		}
		//验证登陆超时问题  auth = null，默认验证 
		if( auth == null || auth.verifyLogin()){
			UserInfoSession userSession = SessionUtils.getUser(request);
			String baseUri = request.getContextPath();
			String path = request.getServletPath();
			String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+baseUri;
			
			if(userSession  == null || userSession.getUserId() == null){
				log.debug("用户登录超时或未登录");
				//如果是ajax请求响应头会有，x-requested-with
				if (request.getHeader("x-requested-with") != null && request.getHeader("x-requested-with").equalsIgnoreCase("XMLHttpRequest")){
					log.debug("Ajax URL链接超时!");
					response.setStatus(HttpServletResponse.SC_GATEWAY_TIMEOUT);
	                response.setHeader("sessionstatus", "timeout");//在响应头设置session状态  
	                return false;  
	            }else{
	            	response.setStatus(HttpServletResponse.SC_GATEWAY_TIMEOUT);
	            	String redirectUrl = baseUri+"/login.shtml?redirectURL="+Utils.encodeStr(basePath+path);
					response.sendRedirect(redirectUrl);
					return false;
	            }
			} 
			
		}
		
		return super.preHandle(request, response, handler);
	}

	
}
