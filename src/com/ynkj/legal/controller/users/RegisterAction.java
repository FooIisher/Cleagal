package com.ynkj.legal.controller.users;

import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONObject;
import com.ynkj.common.interceptor.Auth;
import com.ynkj.common.session.SessionUtils;
import com.ynkj.common.session.UserInfoSession;
import com.ynkj.common.util.BeanUtils;
import com.ynkj.common.util.SmsUtil;
import com.ynkj.legal.common.BaseController;
import com.ynkj.legal.model.users.Users;
import com.ynkj.legal.service.users.UsersService;

@Controller
@RequestMapping("/register")
public class RegisterAction  extends BaseController{
	
	@Resource
	protected UsersService usersService;
	
	@Auth(verifyLogin = false, verifyURL = false)
	@RequestMapping
	public ModelAndView register() throws Exception {
		Map<String, Object> context = getRootMap();
		return forword("comm/register", context); // 跳转页面,context为带回页面的参数数据
	}
	
	
	@Auth(verifyLogin = false, verifyURL = false)
	@RequestMapping("/getSmsCode")
	public void getSmsCode(String telphone,String sendType,HttpServletRequest request,
			HttpServletResponse response){
		smsCode(telphone, sendType,request,response);
	}
	
	/**
	 * app接口发送验证码
	 * @param request
	 * @param response
	 */
	@Auth(verifyLogin = false, verifyURL = false)
	@RequestMapping("/app/getSmsCode")
	public void appGetSmsCode(HttpServletRequest request,HttpServletResponse response){
		JSONObject params = getAppParams(request);
		String telphone = params.getString("telphone");
		String sendType = params.getString("sendType");
		smsCode(telphone, sendType,request,response);
	}
	
	private void smsCode(String telphone,String sendType,HttpServletRequest request,HttpServletResponse response){
		if (BeanUtils.isEmpty(telphone)) {
			sendFailureMessage(response, "手机号码必须输入!");
			return;
		}

		if (!SessionUtils.isMobileNumber(telphone.toString())) {
			sendFailureMessage(response, "手机号码格式错误!");
			return;
		}

		Integer sendType1 = 1; // 1: 注册；2:找回密码
		if (BeanUtils.isNotEmpty(sendType)) {
			try {
				sendType1 = Integer.parseInt(sendType);
			} catch (Exception exc) {
			}
		}
		
		if (sendType1 == 1) {
			//	 判断手机号码是否已经在
			Integer count =usersService.getUserByParams(telphone, "mobile");
			if (count > 0) {
				this.sendFailureMessage(response, "手机号码已经存在!");
				return;
			}
		}
		if (sendType1 == 2) {
//			 判断手机号码是否已经在
			Integer count =usersService.getUserByParams(
					telphone, "mobile");
			if (count == 0) {
				this.sendFailureMessage(response, "手机号码不存在!");
				return;
			}
		}
		
		String code = SmsUtil.createRandom(true, 4).toUpperCase();
		if (sendType1 == 1) {
			smsUtil.sendCodeForRegister(telphone,code);
		}else{
			smsUtil.sendCodeForResetPassword(telphone,code);
		}
		SessionUtils.setValidateCode(request, code);
		sendSuccessMessage(response, "已发送!", code);
	}
	
	/**
	 * 注册用户接口
	 * @param request
	 * @param response
	 */
	@Auth(verifyLogin = false, verifyURL = false)
	@RequestMapping("/saveMember")
	public void saveUsers(HttpServletRequest request, HttpServletResponse response){
		JSONObject params = this.getAppParams(request);
		
		
		if (BeanUtils.isEmpty(params.get("telphone"))) {
			sendFailureMessage(response, "手机号码为空!");
			return;
		}
		if (BeanUtils.isEmpty(params.get("password"))) {
			this.sendFailureMessage(response, "密码必须输入!");
		}
		if (!SessionUtils.isMobileNumber(params.get("telphone").toString())) {
			sendFailureMessage(response, "手机号码格式错误!");
			return;
		}
	//	 判断手机号码是否已经在
		Integer count =usersService.getUserByParams(
				params.getString("telphone"), "mobile");
		if (count > 0) {
			this.sendFailureMessage(response, "手机号码已经存在!");
			return;
		}
		String truePassword = params.getString("password");
		Users users = new Users();
		users.setPassword(truePassword);
		users.setUserName(params.getString("telphone"));
		users.setMobile(params.getString("telphone"));
		usersService.saveUsers(users);
		Map<String, Object> context = getRootMap();
		context.put("userId", users.getUserId());
		this.sendSuccessMessage(response, "用户注册成功!",context);
		
	}
	
	
	@Auth(verifyLogin = false, verifyURL = false)
	@RequestMapping("/saveUsers")
	public void saveUsers(String validCode, Users user,
			HttpServletRequest request, HttpServletResponse response){
		
		
		if(validCode == null || !validCode.trim().toUpperCase().equals(SessionUtils.getValidateCode(request))){
			this.sendFailureMessage(response, "验证码输入错误!");
			return;			
		}
		
		if (!SessionUtils.isMobileNumber(user.getMobile())) {
			sendFailureMessage(response, "手机号码格式错误!");
			return;
		}

		// 判断手机号码是否已经在
		Integer count =usersService.getUserByParams(user.getMobile(), "mobile");
		if (count > 0) {
			this.sendFailureMessage(response, "手机号码已经存在!");
			return;
		}
		String truePassword = user.getPassword();
		usersService.saveUsers(user);
		Users users = usersService.getUsersByLogin(user.getMobile(), user.getPassword());
		UserInfoSession mSession = new UserInfoSession();
		org.springframework.beans.BeanUtils.copyProperties(users, mSession);
		mSession.setTruePassword(truePassword);
		SessionUtils.setAttr(request, SessionUtils.SESSION_USER, mSession);
		this.sendSuccessMessage(response, "用户注册成功!");
		
	}


}
