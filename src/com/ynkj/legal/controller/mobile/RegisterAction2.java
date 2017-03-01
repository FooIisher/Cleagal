package com.ynkj.legal.controller.mobile;

import java.util.Map;





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
import com.ynkj.legal.common.BaseController;
import com.ynkj.legal.model.users.Users;
import com.ynkj.legal.service.users.UsersService;

@Controller
@RequestMapping("/register2")
public class RegisterAction2 extends BaseController{
	
	protected UsersService usersService;
	
	//跳转注册界面
	@Auth(verifyLogin = false, verifyURL = false)
	@RequestMapping
	public ModelAndView register() throws Exception {
		Map<String, Object> context = getRootMap();
		return forword("mobile/register", context); // 跳转页面,context为带回页面的参数数据
	}
	
	
	
	//获取验证码
	@RequestMapping("/getSmsCode")
	public void getSmsCode(String phone, String type,HttpServletRequest request,HttpServletResponse response) {
		
		smsCode(phone, type, request, response);
		
	}

    //验证码输入验证
	@RequestMapping("/app/getSmsCode")
	private void smsCode(String phone, String type, HttpServletRequest request,
			HttpServletResponse response) {
		// TODO Auto-generated method stub
		if (BeanUtils.isEmpty(phone)){
			sendFailureMessage(response, "手机号为空!");
		}
		if(!SessionUtils.isMobileNumber(phone.toString())){
			sendFailureMessage(response, "手机格式错误!");
		}
		
		//判断号码是否存在
		Integer count =usersService.getUserByParams(phone, "mobile");
		if (count > 0) {
			this.sendFailureMessage(response, "手机号码已经存在!");
			return;
		}
		//生成验证码
		//String code = SmsUtil.createRandom(true, 4).toLowerCase();
		String code = "fish";
		smsUtil.sendCodeForRegister(phone,code);
		SessionUtils.setValidateCode(request, code);
		sendSuccessMessage(response, "发送成功", code);	 
	}
	
	//用户注册
	@RequestMapping("/saveMember")
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
