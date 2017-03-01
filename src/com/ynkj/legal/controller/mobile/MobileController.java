package com.ynkj.legal.controller.mobile;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ynkj.legal.common.BaseController;
import com.ynkj.legal.model.users.Users;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.ynkj.common.interceptor.Auth;
import com.ynkj.common.session.SessionUtils;
import com.ynkj.common.session.UserInfoSession;
import com.ynkj.common.util.MethodUtil;
import com.ynkj.common.util.Pager;
/**
 * 手机端
 * @author ftx
 *
 */
@Controller
//@RequestMapping("/mobile")
@RequestMapping("/login2")
public class MobileController extends BaseController {

	/**
	 * 手机端登录界面跳转
	 * @param 
	 * @return
	 */
	@Auth(verifyLogin=false)//无需登录
	//@RequestMapping("/login") 
	@RequestMapping()
	public ModelAndView login(String redirectURL,HttpServletRequest request,HttpServletResponse response){
		Map<String, Object> context = getRootMap();
		context.put("redirectURL", redirectURL);
		return new ModelAndView("mobile/login",context);
	}
	
	
	/**
	 * 手机端帐号验证
	 * @param user
	 * @param request
	 * @param response
	 * @return
	 */
	@Auth(verifyLogin=false,verifyURL=false)
	@RequestMapping("/checkUsers")
	public void checkUsers(Users user,HttpServletRequest request,HttpServletResponse response){
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
			sendFailureMessage(response, "3");
		}
		
	}
	
	
}
