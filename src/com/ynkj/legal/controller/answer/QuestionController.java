package com.ynkj.legal.controller.answer;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.jsoup.Jsoup;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONObject;
import com.ynkj.common.interceptor.Auth;
import com.ynkj.common.session.SessionUtils;
import com.ynkj.common.session.UserInfoSession;
import com.ynkj.common.util.DateTimeUtil;
import com.ynkj.common.util.Pager;
import com.ynkj.common.util.SerialNo;
import com.ynkj.legal.common.BaseController;
import com.ynkj.legal.common.Constant;
import com.ynkj.legal.model.answer.Answer;
import com.ynkj.legal.model.question.Question;
import com.ynkj.legal.model.question.QuestionComments;
import com.ynkj.legal.model.question.QuestionInvite;
import com.ynkj.legal.model.question.UserFollowQuestion;
import com.ynkj.legal.model.users.Users;
import com.ynkj.legal.service.answer.AnswerService;
import com.ynkj.legal.service.question.QuestionCommentsService;
import com.ynkj.legal.service.question.QuestionInviteService;
import com.ynkj.legal.service.question.QuestionService;
import com.ynkj.legal.service.question.UserFollowQuestionService;
import com.ynkj.legal.service.users.UsersService;


@Controller
@RequestMapping("/question")
public class QuestionController extends BaseController{
	
	@Resource
	public AnswerService answerService;
	@Resource
	public QuestionService questionService;
	@Resource
	public QuestionCommentsService questionCommentsService;
	@Resource
	public UserFollowQuestionService userFollowQuestionService;
	@Resource
	public QuestionInviteService questionInviteService;
	@Resource
	protected UsersService usersService;
	
	@Auth(verifyLogin=false)//无需登录
	@RequestMapping("/detail{questionId}") 
	public ModelAndView questionDetail(@PathVariable String questionId,HttpServletRequest request,HttpServletResponse response,Pager p){
		Map<String, Object> context = getRootMap();
		context.put("msg", request.getParameter("msg"));
		Map<String, Object> map = new HashMap<String, Object>();
		String userId = SessionUtils.getUserId(request);
		map.put("userId", userId);
		map.put("questionId", questionId);
		Question question = questionService.getQuestionById(map);
		questionService.addViewCount(questionId);
		context.put("question", question);
		context.put("questionTopic", questionService.queryQuestionTopic(map));
		map.put("New", "true");
		List<Answer> listAnswer = answerService.listHomePageAnswer(map,  p);
		for (int i = 0; i < listAnswer.size(); i++) {
			Map<String, Object> answerMap = new HashMap<String, Object>();
			answerMap.put("answerId", listAnswer.get(i).getAnswerId());
			Pager answerP = new Pager();
			answerP.setPageSize(3);
			listAnswer.get(i).setAgreeList(answerService.queryUserPraiseAnswer(answerMap, answerP));
			listAnswer.get(i).setAddTime(DateTimeUtil.getTimeIntervalStr(listAnswer.get(i).getAddTime(), DateTimeUtil.getNowDateTime(), DateTimeUtil.DEFAULT_DATETIME_FORMAT_SEC));
		}
		context.put("listAnswer", listAnswer);
		//已邀请人数
		context.put("inviteCount", questionInviteService.getInviteCount(map));
		return new ModelAndView("homepage/question-detail",context); 
	}
	
	/**
	 * app问题详情
	 * @param request
	 * @param response
	 */
	@Auth(verifyLogin=false)//无需登录
	@RequestMapping("/app/detail") 
	public void appQuestionDetail(HttpServletRequest request,HttpServletResponse response){
		Map<String, Object> context = getRootMap();
		JSONObject params = getAppParams(request);
		Pager p = getAppPager(request);
		String questionId = params.getString("questionId");
		String answerId = params.getString("answerId");
		Map<String, Object> map = new HashMap<String, Object>();
		String userId = SessionUtils.getUserId(request);
		map.put("userId", userId);
		map.put("questionId", questionId);
		if(p.getPageId()==1){
			Question question = questionService.getQuestionById(map);
			questionService.addViewCount(questionId);
			context.put("question", question);
			context.put("questionTopic", questionService.queryQuestionTopic(map));
			//已邀请人数
			context.put("inviteCount", questionInviteService.getInviteCount(map));
			if(StringUtils.isNotEmpty(answerId)){
				answerService.addViewCount(answerId);
			}
		}
		
		map.put("New", "true");
		List<Answer> listAnswer = answerService.listHomePageAnswer(map,  p);
		for (int i = 0; i < listAnswer.size(); i++) {
			Map<String, Object> answerMap = new HashMap<String, Object>();
			answerMap.put("answerId", listAnswer.get(i).getAnswerId());
			Pager answerP = new Pager();
			answerP.setPageSize(3);
			listAnswer.get(i).setAgreeList(answerService.queryUserPraiseAnswer(answerMap, answerP));
			listAnswer.get(i).setAddTime(DateTimeUtil.getTimeIntervalStr(listAnswer.get(i).getAddTime(), DateTimeUtil.getNowDateTime(), DateTimeUtil.DEFAULT_DATETIME_FORMAT_SEC));
			if(StringUtils.isNotEmpty(listAnswer.get(i).getAnswerContent())){
				listAnswer.get(i).setAnswerContent(Jsoup.parse(listAnswer.get(i).getAnswerContent()).text());
			}
		}
		context.put("listAnswer", listAnswer);
		sendSuccessMessage(response, "获取数据成功", context);
	}

	
	/**
	 * 邀请用户
	 * @param userId
	 * @param request
	 * @param response
	 */
	@Auth(verifyLogin=true,verifyURL=false)
	@RequestMapping("/inviteUser")
	public void inviteUser(String userId,String questionId,HttpServletRequest request,
			HttpServletResponse response){
		QuestionInvite questionInvite = new QuestionInvite();
		questionInvite.setQuestionInviteId(SerialNo.getUNID());
		questionInvite.setAddTime(DateTimeUtil.getNowDateTime());
		questionInvite.setQuestionId(questionId);
		questionInvite.setRecipientsUid(userId);
		String sessionUserId = SessionUtils.getUserId(request);
		questionInvite.setSenderUid(sessionUserId);
		questionInviteService.addQuestionInvite(questionInvite);
	}
	
	
	
	/**
	 * 收藏问题
	 * @return
	 */
	@Auth(verifyLogin=true,verifyURL=false)
	@RequestMapping("/followQuestion")
	public void userFollowQuestion(String questionId,HttpServletRequest request,HttpServletResponse response) throws Exception{
		
		String userId = SessionUtils.getUserId(request);
		UserFollowQuestion userFollowQuestion = new UserFollowQuestion();
		userFollowQuestion.setQuestionId(questionId);
		userFollowQuestion.setUserId(userId);
		userFollowQuestionService.followQuestion(userFollowQuestion);
		
		this.addUserActionHistory(userId, Constant.MODEL_TYPE_QUESTION, 
				Constant.ACTION_TYPE_FOLLOW, userFollowQuestion.getQuestionId(), 0);
		
		this.sendSuccessMessage(response, "关注成功");
	}
	
	/**
	 * 取消收藏问题
	 * @return
	 */
	@Auth(verifyLogin=true,verifyURL=false)
	@RequestMapping("/disFollowQuestion")
	public void disUserFollowQuestion(String questionId,HttpServletRequest request,HttpServletResponse response) throws Exception{
		
		String userId = SessionUtils.getUserId(request);
		UserFollowQuestion userFollowQuestion = new UserFollowQuestion();
		userFollowQuestion.setQuestionId(questionId);
		userFollowQuestion.setUserId(userId);
		userFollowQuestionService.disFollowQuestion(userFollowQuestion);
		this.sendSuccessMessage(response, "取消关注成功");
	}
	
	
	/**
	 * 评论问题
	 * @return
	 */
	@Auth(verifyLogin=true,verifyURL=false)
	@RequestMapping("/commentQuestion")
	public void userCommentQuestion(QuestionComments questionComments,HttpServletRequest request,HttpServletResponse response) throws Exception{
		
		String userId = SessionUtils.getUserId(request);
		questionComments.setVerifyStatus(0); 
		questionComments.setCommentsId(SerialNo.getUNID()); 
		questionComments.setAddTime(DateTimeUtil.getNowDateTime());
		questionComments.setUid(userId);
		questionCommentsService.addQuestionComment(questionComments);
		this.sendSuccessMessage(response, "评论成功");
		
		Map<String, Object> map =  new HashMap<String, Object>();
		map.put("questionId", questionComments.getQuestionId());
		Question question= questionService.getQuestionById(map); 
		String publishUid = "";
		if (question != null) {
			publishUid = question.getPublishedUid();
		}
		this.addNotification(Constant.ACTION_TYPE_COMMENT, Constant.MODEL_TYPE_QUESTION,
				questionComments.getQuestionId(), userId, publishUid);
		this.addUserActionHistory(userId, Constant.MODEL_TYPE_QUESTION, 
				Constant.ACTION_TYPE_COMMENT, questionComments.getQuestionId(), 0);
	}
	
	/**
	 * 保存问题
	 * @param question
	 * @param topicIds
	 * @param request
	 * @return
	 */
	@Auth(verifyLogin=true,verifyURL=false)
	@RequestMapping("/saveQuestion")
	public ModelAndView saveQuestion(Question question, String[] topicIds,HttpServletRequest request){
		Map<String, Object> context = getRootMap();
		question.setPublishedUid(SessionUtils.getUserId(request));
		String questionId = SerialNo.getSmallUNID();
		question.setQuestionId(questionId);
		UserInfoSession userInfo = SessionUtils.getUser(request);
		if(userInfo.getCertifyStatus()==2){//认证律师提问自动通过审核
			question.setVerifyStatus("1");
			question.setStatus("1");
			//发送短信通知
			String content = "【法务助手】您好,法务助手有新的1条问题了，请您尽快回答。";
			String phones = "";
			List<Map<String, Object>> phoneList = usersService.querySmsPhoneList(null);
			for (int i = 0; i < phoneList.size(); i++) {
				phones += phoneList.get(i).get("phone")+",";
			}
			smsUtil.commonSendSMS(phones, content);
		}else{
			question.setVerifyStatus("0");
			question.setStatus("0");
			//发送站内通知，提醒审核
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("smsStatus", 1);
			List<Users> userList = usersService.listUsers(map);
			for (int i = 0; i < userList.size(); i++) {
				addNotification(Constant.ACTION_TYPE_CHECK, Constant.MODEL_TYPE_QUESTION, questionId, null, userList.get(i).getUserId());
			}
		}
		question.setLockFlag("0");
		questionService.saveQuestion(question,topicIds);
		String anonymous = question.getAnonymous();
		this.addUserActionHistory(SessionUtils.getUserId(request), Constant.MODEL_TYPE_QUESTION, 
				Constant.ACTION_TYPE_PUBLISH, question.getQuestionId(), Integer.parseInt(anonymous));
		return redirect(request.getContextPath()+"/member/"+SessionUtils.getUserId(request)+"/asks.shtml", context);
	}

	/**
	 * App保存问题
	 * @param request
	 * @param response
	 * @return
	 */
	@Auth(verifyLogin=true)
	@RequestMapping("/app/saveQuestion")
	public void appSaveQuestion(HttpServletRequest request,HttpServletResponse response){
		JSONObject params = getAppParams(request);
		String questionTitle = params.getString("questionTitle");
		String questionContent = params.getString("questionContent");
		int anonymous = params.getIntValue("anonymous");
		String topicId = params.getString("topicIds");
		if(StringUtils.isEmpty(questionTitle)){
			sendFailureMessage(response, "questionTitle字段不能为空");
			return;
		}
		if(StringUtils.isEmpty(topicId)){
			sendFailureMessage(response, "topicIds字段不能为空");
			return;
		}
		if(StringUtils.isEmpty(topicId)){
			sendFailureMessage(response, "topicIds字段不能为空");
			return;
		}
		String[] topicIds = topicId.split(",");
		Question question = new Question();
		question.setQuestionTitle(questionTitle);
		question.setQuestionContent(questionContent);
		question.setAnonymous(anonymous+"");
		question.setPublishedUid(SessionUtils.getUserId(request));
		String questionId = SerialNo.getSmallUNID();
		question.setQuestionId(questionId);
		UserInfoSession userInfo = SessionUtils.getUser(request);
		String msg = "发布成功";
		if(userInfo.getCertifyStatus()==2){//认证律师提问自动通过审核
			question.setVerifyStatus("1");
			question.setStatus("1");
			//发送短信通知
			String content = "【法务助手】您好,法务助手有新的1条问题了，请您尽快回答。";
			String phones = "";
			List<Map<String, Object>> phoneList = usersService.querySmsPhoneList(null);
			for (int i = 0; i < phoneList.size(); i++) {
				phones += phoneList.get(i).get("phone")+",";
			}
			smsUtil.commonSendSMS(phones, content);
		}else{
			msg = "发布成功，请等待系统审核";
			question.setVerifyStatus("0");
			question.setStatus("0");
			//发送站内通知，提醒审核
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("smsStatus", 1);
			List<Users> userList = usersService.listUsers(map);
			for (int i = 0; i < userList.size(); i++) {
				addNotification(Constant.ACTION_TYPE_CHECK, Constant.MODEL_TYPE_QUESTION, questionId, null, userList.get(i).getUserId());
			}
		}
		question.setLockFlag("0");
		questionService.saveQuestion(question,topicIds);
		this.addUserActionHistory(SessionUtils.getUserId(request), Constant.MODEL_TYPE_QUESTION, 
				Constant.ACTION_TYPE_PUBLISH, question.getQuestionId(), anonymous);
		sendSuccessMessage(response, msg);
	}
}
