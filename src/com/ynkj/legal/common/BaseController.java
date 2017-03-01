package com.ynkj.legal.common;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import com.alibaba.fastjson.JSONObject;
import com.ynkj.common.util.DateTimeUtil;
import com.ynkj.common.util.HtmlUtil;
import com.ynkj.common.util.Pager;
import com.ynkj.common.util.SerialNo;
import com.ynkj.common.util.SmsUtil;
import com.ynkj.legal.model.users.UserActionHistory;
import com.ynkj.legal.service.users.NotificationService;
import com.ynkj.legal.service.users.UsersService;

public class BaseController {
	public final static String SUCCESS = Constant.SUCCESS;

	public final static String MSG = Constant.MSG;

	public final static String CODENUM = Constant.CODENUM;

	public final static String DATA = Constant.DATA;

	public final static String LOGOUT_FLAG = Constant.LOGOUT_FLAG;
	
	@Resource
	public NotificationService notificationService;
	@Resource
	public UsersService usersService;
	@Resource
	protected SmsUtil smsUtil;
	
	/**
	 * 获取IP地址
	 * 
	 * @param request
	 * @return
	 */
	public String getIpAddr(HttpServletRequest request) {
		String ip = request.getHeader("x-forwarded-for");
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
		}
		return ip;
	}

	/**
	 * 所有ActionMap 统一从这里获取
	 * 
	 * @return
	 */
	public Map<String, Object> getRootMap() {
		Map<String, Object> rootMap = new HashMap<String, Object>();
		// 添加url到 Map中
		// rootMap.putAll(URLUtils.getUrlMap());
		return rootMap;
	}

	public Map<String, Object> getRootMap(Map<String, Object> map) {
		if (map == null) {
			map = new HashMap<String, Object>();
		}
		return map;
	}

	public ModelAndView forword(String viewName, Map context) {
		return new ModelAndView(viewName, context);
	}

	public ModelAndView redirect(String viewName, Map context) {
		return new ModelAndView(new RedirectView(viewName), context);
	}

	public ModelAndView error(String errMsg) {
		return new ModelAndView("error");
	}

	/**
	 * 
	 * 提示成功信息
	 * 
	 * @param message
	 * 
	 */
	public void sendSuccessMessage(HttpServletResponse response, String message) {
		sendSuccessMessage(response, message, null);
	}
	
	public void sendSuccessMessage(HttpServletResponse response, String message, Object data) {
		Map<String, Object> result = new HashMap<String, Object>();
		result.put(Constant.SUCCESS, true);
		result.put(Constant.MSG, message);
		result.put("data", data);
		result.put(Constant.CODENUM, response.SC_OK);
		HtmlUtil.writerJson(response, result);
	}

	/**
	 * 
	 * 提示失败信息
	 * 
	 * @param message
	 * 
	 */
	public void sendFailureMessage(HttpServletResponse response, String message) {
		Map<String, Object> result = new HashMap<String, Object>();
		result.put(Constant.SUCCESS, false);
		result.put(Constant.MSG, message);
		result.put(Constant.CODENUM, response.SC_NOT_ACCEPTABLE);
		HtmlUtil.writerJson(response, result);
	}

	//
	// 获取
	public JSONObject getAppParams(HttpServletRequest request) {
		String params = request.getParameter("params");
		if (StringUtils.isNotEmpty(params)) {
			params = params.replace("‘", "'");
			params = params.replace("“", "\"");
			return JSONObject.parseObject(params);
		}
		return new JSONObject();
	}
	
	public Pager getAppPager(HttpServletRequest request){
		JSONObject params = getAppParams(request);
		Pager p = new Pager();
		if(StringUtils.isNotEmpty(params.getString("pageId"))){
			p.setPageId(params.getIntValue("pageId"));
		}
		if(StringUtils.isNotEmpty(params.getString("pageSize"))){
			p.setPageSize(params.getIntValue("pageSize"));
		}
		return p;
	}
	
	public boolean isAppAccess(HttpServletRequest request){
		String appAccess = request.getParameter("appAccess");
		if(StringUtils.isNotEmpty(appAccess) && "1".equals(appAccess)){
			return true;
		}
		return false;
	}
	
	public void addNotification(int actionType,int modelType,String sourceId
			,String senderUid,String recipientUid){
		
		notificationService.addNotification(actionType, modelType, sourceId, senderUid, recipientUid);
		
	}
	
	public void addUserActionHistory(String uid,int associateType,int associateAction,String associateId,int anonymous ){
		
		UserActionHistory userActionHistory = new UserActionHistory();
		userActionHistory.setHistoryId(SerialNo.getUNID());
		userActionHistory.setAddTime(DateTimeUtil.getNowDateTime());
		userActionHistory.setAnonymous(anonymous);
		userActionHistory.setUid(uid);
		userActionHistory.setAssociateType(associateType);
		userActionHistory.setAssociateAction(associateAction);
		userActionHistory.setAssociateId(associateId);
		usersService.saveUserHistoryAction(userActionHistory);
	}
}
