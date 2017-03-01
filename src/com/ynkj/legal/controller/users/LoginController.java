package com.ynkj.legal.controller.users;

import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONObject;
import com.ynkj.common.interceptor.Auth;
import com.ynkj.common.session.SessionUtils;
import com.ynkj.common.session.UserInfoSession;
import com.ynkj.common.util.BeanUtils;
import com.ynkj.common.util.MethodUtil;
import com.ynkj.legal.common.BaseController;
import com.ynkj.legal.model.users.Users;
import com.ynkj.legal.service.users.UsersService;

@Controller
@RequestMapping("/login")
public class LoginController extends BaseController{
	
	
	@Resource
	protected UsersService usersService;
	
	
	/**
	 * 找回密码
	 * @param url
	 * @param classifyId
	 * @return
	 */
	@Auth(verifyLogin=false,verifyURL=false)
	@RequestMapping("/findpwd")
	public ModelAndView findpwd(HttpServletRequest request,HttpServletResponse response) throws Exception{
	
		Map<String,Object>  context = getRootMap();
		return forword("comm/find_password", context);
	 
	}
	
	
	/**
	 * 重置密码
	 * @return
	 */
	@Auth(verifyLogin=false,verifyURL=false)
	@RequestMapping("/resetpwd")
	public void resetpwd(String mobile,String newPassword,HttpServletRequest request,HttpServletResponse response) throws Exception{
		
		String validcode = request.getParameter("validCode");
		if(StringUtils.isEmpty(validcode)){
			this.sendFailureMessage(response, "验证码不能为空!");
			return;
		}
		
		if(validcode == null || !validcode.trim().toUpperCase().equals(SessionUtils.getValidateCode(request))){
			this.sendFailureMessage(response, "验证码输入错误!");
			return;			
		}
		
		Users users = usersService.getUserByMobile(mobile);
		if (users != null) {
			usersService.updateUserPwd(users.getUserId(), newPassword);
			sendSuccessMessage(response,"重置密码成功");
		}else{
			sendFailureMessage(response, "重置密码失败");
		}
	 
	 
	}
	
	/**
	 * app重置密码
	 * @param request
	 * @param response
	 */
	@Auth(verifyLogin=false,verifyURL=false)
	@RequestMapping("/appResetpwd")
	public void appResetpwd(HttpServletRequest request,HttpServletResponse response){
		JSONObject params = getAppParams(request);
		String telphone = params.getString("telphone");
		String newPassword = params.getString("newPassword");
		if(StringUtils.isEmpty(telphone)){
			this.sendFailureMessage(response, "telphone字段不能为空!");
			return;
		}
		if(StringUtils.isEmpty(newPassword)){
			this.sendFailureMessage(response, "newPassword字段不能为空!");
			return;
		}
		
		Users users = usersService.getUserByMobile(telphone);
		if (users != null) {
			usersService.updateUserPwd(users.getUserId(), newPassword);
			sendSuccessMessage(response,"重置密码成功");
		}else{
			sendFailureMessage(response, "重置密码失败");
		}
	}
	
	/**
	 * 登录页面
	 * @return
	 */
	@Auth(verifyLogin=false,verifyURL=false)
	@RequestMapping
	public ModelAndView login(String redirectURL,HttpServletRequest request,HttpServletResponse response) throws Exception{
	
		Map<String,Object>  context = getRootMap();
		context.put("redirectURL", redirectURL);
		return forword("comm/login", context);
	 
	}
	
	
	/**
	 * 登录页面
	 * @param url
	 * @param classifyId
	 * @return
	 */
	@Auth(verifyLogin=false,verifyURL=false)
	@RequestMapping("/checkUsers")
	public void checkUsers(Users user,HttpServletRequest request,HttpServletResponse response) throws Exception{
		Integer validCodeKey = 0;
		if (SessionUtils.getAttr(request, "validCodeKey") == null) {
			validCodeKey = 0;
		}else{
			validCodeKey = Integer.parseInt(SessionUtils.getAttr(request, "validCodeKey").toString()) ;
			
		}
		if (validCodeKey  !=null  && validCodeKey >= 3) {
			String validcode = request.getParameter("validCode");
			if(StringUtils.isEmpty(validcode)){
				this.sendFailureMessage(response, "201");
				return;
			}
			 
			if(validcode == null || !validcode.trim().toLowerCase().equals(SessionUtils.getValidateCode(request))){
				this.sendFailureMessage(response, "202");
				return;			
			}
		}
		Users userssession = usersService.getUsersByLogin(user.getMobile(),
				MethodUtil.MD5(user.getPassword()));
		if (userssession != null) {
			UserInfoSession mSession = new UserInfoSession();
			org.springframework.beans.BeanUtils.copyProperties(userssession, mSession);
			mSession.setTruePassword(user.getPassword());
			SessionUtils.setAttr(request, SessionUtils.SESSION_USER, mSession);
			SessionUtils.setAttr(request,"validCodeKey", "0");
			String message = "用户: " + userssession.getUserName() + "登录成功";
			sendSuccessMessage(response, message, userssession.getUserId());
		}else{
			if (validCodeKey == null) {
				validCodeKey = 1;
				SessionUtils.setAttr(request,"validCodeKey", validCodeKey+"");
			}else{
				SessionUtils.setAttr(request,"validCodeKey", (++validCodeKey)+""); 
			}
			sendFailureMessage(response, validCodeKey+"");
		}
	}
	
	/**
	 * app登录接口
	 * @param request
	 * @param response
	 */
	@Auth(verifyLogin=false,verifyURL=false)
	@RequestMapping("/appLogin")
	public void appLogin(HttpServletRequest request,HttpServletResponse response){
		JSONObject params = getAppParams(request);
		String telphone = params.getString("telphone");
		String password = params.getString("password");
		if (BeanUtils.isEmpty(telphone)) {
			sendFailureMessage(response, "telphone字段为空");
			return;
		}
		if (BeanUtils.isEmpty(password)) {
			sendFailureMessage(response, "password字段为空");
			return;
		}
		Users userssession = usersService.getUsersByLogin(telphone, MethodUtil.MD5(password));
		if (userssession != null) {
			sendSuccessMessage(response, "登录成功", userssession);
		}else{
			sendFailureMessage(response, "登录失败");
		}
	}
	
	/**
	 * 退出登录
	 * @param request
	 * @param response
	 */
	@RequestMapping("/loginout")
	public void loginout(HttpServletRequest request,HttpServletResponse response){
		SessionUtils.removeUser(request);
		sendSuccessMessage(response, "退出登录成功");
	}
	
	/**
	 * 判断用户是否登录
	 * @param request
	 * @param response
	 */
	@Auth(verifyLogin=false,verifyURL=false)
	@RequestMapping("/isLogin")
	public void isLogin(HttpServletRequest request,HttpServletResponse response){
		if(SessionUtils.getUser(request)==null){
			sendFailureMessage(response, "未登录");
		}else{
			sendSuccessMessage(response, "已经登录");
		}
		
	}
	

}
