package com.ynkj.legal.controller.cases;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.ynkj.common.interceptor.Auth;
import com.ynkj.common.session.SessionUtils;
import com.ynkj.common.util.DateTimeUtil;
import com.ynkj.common.util.Pager;
import com.ynkj.common.util.SerialNo;
import com.ynkj.common.util.Uploader;
import com.ynkj.legal.common.BaseController;
import com.ynkj.legal.common.Constant;
import com.ynkj.legal.model.cases.Cases;
import com.ynkj.legal.model.cases.CasesComments;
import com.ynkj.legal.model.cases.UserFollowCases;
import com.ynkj.legal.model.question.Topic;
import com.ynkj.legal.service.cases.CasesCommentsService;
import com.ynkj.legal.service.cases.CasesService;
import com.ynkj.legal.service.cases.UserFollowCasesService;
import com.ynkj.legal.service.question.TopicService;



/**
 * 案例
 * @author Administrator
 *
 */
@Controller
@RequestMapping("/case")
public class CaseController extends BaseController{
	
	@Resource
	public TopicService topicService;
	
	@Resource
	public CasesService casesService;
	
	@Resource
	public CasesCommentsService casesCommentsService;
	
	@Resource
	public UserFollowCasesService userFollowCasesService;
	
	
	@Auth(verifyLogin=false)//无需登录
	@RequestMapping("/index") 
	public ModelAndView index(Pager p){
		Map<String, Object> context = getRootMap();
		Pager pager = new Pager();
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("isParent", 1);
		List<Topic> listTopicCases = topicService.listTopic(map,pager);
		for (int i = 0; i < listTopicCases.size(); i++) {
			Topic topic = listTopicCases.get(i);
			String topicId = topic.getTopicId();
			Pager p1 = new Pager();
			p1.setPageSize(3);
			Map<String, Object> map1 = new HashMap<String, Object>();
			map1.put("parentId", topicId);
			topic.setListCases(casesService.listCasesByParentTopicId(map1, p1));
			listTopicCases.set(i, topic);
		}
		context.put("listTopicCases", listTopicCases);
		return new ModelAndView("case/index",context);
	}
	
	@Auth(verifyLogin=false)//无需登录
	@RequestMapping("/list{topicId}") 
	public ModelAndView list(@PathVariable String topicId,Pager p,HttpServletResponse response){
		Map<String, Object> context = getRootMap();
		Map<String, Object> map = new HashMap<String, Object>();
		Map<String, Object> map1 = new HashMap<String, Object>();
		Topic topic = topicService.queryTopicById(topicId);
		if(topic.getIsParent()==1){
			map.put("parentId", topicId);
			map1.put("parentId", topicId);
			context.put("parentId", topicId);
			context.put("topic", topic);
		}else{
			map.put("topicId", topicId);
			map1.put("parentId", topic.getParentId());
			context.put("parentId", topic.getParentId());
			context.put("topic", topicService.queryTopicById(topic.getParentId()));
		}
		List<Cases> listCases = casesService.listCasesByParentTopicId(map, p);
		context.put("topicList", topicService.listTopicSimple(map1));
		context.put("listCases", listCases);
		context.put("topicId", topicId);
		return new ModelAndView("case/list-case",context);
	}
	
	@Auth(verifyLogin=false)//无需登录
	@RequestMapping("/listajax{topicId}") 
	public ModelAndView listajax(@PathVariable String topicId,Pager p,HttpServletResponse response){
		
		Map<String, Object> context = getRootMap();
		Map<String, Object> map = new HashMap<String, Object>();
		Map<String, Object> map1 = new HashMap<String, Object>();
		Topic topic = topicService.queryTopicById(topicId);
		if(topic.getIsParent()==1){
			map.put("parentId", topicId);
			map1.put("parentId", topicId);
			context.put("parentId", topicId);
		}else{
			map.put("topicId", topicId);
			map1.put("parentId", topic.getParentId());
			context.put("parentId", topic.getParentId());
		}
		List<Cases> listCases = casesService.listCasesByParentTopicId(map, p);
		context.put("listCases", listCases);
		return new ModelAndView("case/list-case-ajax",context);
	} 
	
	@Auth(verifyLogin=false)//无需登录
	@RequestMapping("/detail{caseId}") 
	public ModelAndView detail(@PathVariable String caseId,Pager p,HttpServletRequest request,HttpServletResponse response){
		
		Map<String, Object> context = getRootMap();
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("caseId", caseId);
		String userId = SessionUtils.getUserId(request);
		map.put("userId", userId);
		Cases cases = casesService.detailCaseId(map);
		context.put("cases", cases);
		casesService.addViewCount(caseId);
		List<CasesComments> listCasesComments = casesCommentsService.listCasesComments(map, p);
		context.put("listCasesComments", listCasesComments);
		return new ModelAndView("case/detail-case",context);
	}
	
	/**
	 * 收藏案例
	 * @return
	 */
	@Auth(verifyLogin=true,verifyURL=false)
	@RequestMapping("/followCase")
	public void userFollowCase(String caseId,HttpServletRequest request,HttpServletResponse response) throws Exception{
		
		String userId = SessionUtils.getUserId(request);
		UserFollowCases userFollowCases = new UserFollowCases();
		userFollowCases.setCaseId(caseId);
		userFollowCases.setUserId(userId);
		userFollowCasesService.followCase(userFollowCases);
		this.addUserActionHistory(userId, Constant.MODEL_TYPE_CASE, 
				Constant.ACTION_TYPE_FOLLOW, caseId, 0);
		this.sendSuccessMessage(response, "关注成功");
	}
	
	
	/**
	 * 取消收藏案例
	 * @return
	 */
	@Auth(verifyLogin=true,verifyURL=false)
	@RequestMapping("/disFollowCase")
	public void DisUserFollowCase(String caseId,HttpServletRequest request,HttpServletResponse response) throws Exception{
		
		String userId = SessionUtils.getUserId(request);
		UserFollowCases userFollowCases = new UserFollowCases();
		userFollowCases.setCaseId(caseId);
		userFollowCases.setUserId(userId);
		userFollowCasesService.disFollowCase(userFollowCases);
		this.sendSuccessMessage(response, "取消关注成功");
	}
	
	
	
	/**
	 * 评论案例
	 * @return
	 */
	@Auth(verifyLogin=true,verifyURL=false)
	@RequestMapping("/commentCase")
	public void userCommentCase(CasesComments casesComments,HttpServletRequest request,HttpServletResponse response) throws Exception{
		
		String userId = SessionUtils.getUserId(request);
		casesComments.setVerifyStatus(0); 
		casesComments.setCommentsId(SerialNo.getUNID()); 
		casesComments.setAddTime(DateTimeUtil.getNowDateTime());
		casesComments.setUid(userId);
		casesCommentsService.addCasesComment(casesComments);
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("caseId", casesComments.getCaseId());
		Cases cases = casesService.detailCaseId(map);
		String publishUId = "";
		if (cases != null) {
			publishUId = cases.getPublishUser();
		}
		this.addNotification(Constant.ACTION_TYPE_COMMENT, Constant.MODEL_TYPE_CASE,
				casesComments.getCaseId(), userId, publishUId);
		this.addUserActionHistory(userId, Constant.MODEL_TYPE_CASE, 
				Constant.ACTION_TYPE_COMMENT, casesComments.getCaseId(), 0);
		
		this.sendSuccessMessage(response, "评论成功");
	}
	
	
	
	
	/**
	 * 保存案例
	 * @return
	 */
	@Auth(verifyLogin=true,verifyURL=false)
	@RequestMapping("/saveCase")
	public ModelAndView savegCase(Cases cases,String[] topicIds,HttpServletRequest request){
		String userId = SessionUtils.getUserId(request);
		cases.setVerifyStatus(0);//审核不通过
		cases.setStatus(0);//有效
		cases.setCaseId(SerialNo.getUNID());
		cases.setUnverifiedModify(cases.getCaseContent());
		cases.setPublishTime(DateTimeUtil.getNowDateTime());
		cases.setPublishUser(userId);
		casesService.addCases(cases,topicIds);
		Map<String, Object> context = getRootMap();
		this.addUserActionHistory(userId, Constant.MODEL_TYPE_CASE, 
				Constant.ACTION_TYPE_PUBLISH, cases.getCaseId(), 0);
		this.addUserActionHistory(userId, Constant.MODEL_TYPE_CASE, 
				Constant.ACTION_TYPE_PUBLISH, cases.getCaseId(), 0);
		return redirect(request.getContextPath()+"/member/"+SessionUtils.getUserId(request)+"/asks.shtml", context);
	//	return redirect(request.getContextPath()+"/member/"+userId+"/cases.shtml", context);
	}
	
	@Auth(verifyLogin = false, verifyURL = false)
	@RequestMapping("/uploadHeadImg")
	public void memberUploadImg(@RequestParam MultipartFile upload, HttpServletRequest request,
			HttpServletResponse response) {
		Map<String, Object> context = getRootMap();
		Uploader up = new Uploader(request, upload);
		String[] fileType = { ".gif", ".png", ".jpg", ".jpeg" };
		up.setAllowFiles(fileType);
		up.setMaxSize(2 * 1024); // 单位KB
		try {
			up.upload();
		} catch (Exception e) {
			e.printStackTrace();
			this.sendFailureMessage(response, "失败");
		}
		context.put("up", up);
		this.sendSuccessMessage(response, "成功",context);
	}
	
	/**
	 * 添加案例
	 * @return
	 */
	@Auth(verifyLogin=true,verifyURL=false)
	@RequestMapping("/addCase")
	public ModelAndView addCase(HttpServletRequest request){
		Map<String, Object> context = getRootMap();
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("isParent", 1);
		Pager pager = new Pager();
		context.put("topicList", topicService.listTopic(map, pager));
		return forword("case/add-case", context);
	}
	
	
}
