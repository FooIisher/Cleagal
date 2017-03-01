package com.ynkj.legal.controller.answer;


import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
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
import com.ynkj.legal.model.answer.AnswerComments;
import com.ynkj.legal.model.answer.UserFollowAnswer;
import com.ynkj.legal.model.answer.UserPraiseAnswer;
import com.ynkj.legal.model.question.Question;
import com.ynkj.legal.service.answer.AnswerCommentsService;
import com.ynkj.legal.service.answer.AnswerService;
import com.ynkj.legal.service.answer.UserFollowAnswerService;
import com.ynkj.legal.service.answer.UserPraiseAnswerService;
import com.ynkj.legal.service.question.QuestionService;

@Controller
@RequestMapping("/answer")
public class AnswerController extends BaseController{
	
	
	@Resource
	public UserFollowAnswerService userFollowAnswerService;
	
	
	@Resource
	public UserPraiseAnswerService userPraiseAnswerService;
	
	@Resource
	public AnswerService answerService;
	
	@Resource
	public AnswerCommentsService answerCommentsService;
	
	@Resource
	public QuestionService questionService;
	
	
	/**
	 * 答案
	 * @return
	 */
	@Auth(verifyLogin=false,verifyURL=false)
	@RequestMapping("/detail{answerId}") 
	public ModelAndView answerDetail(@PathVariable String answerId,HttpServletRequest request,HttpServletResponse response){
		Map<String, Object> context = getRootMap();
		Answer answer =  answerService.getAnswerById(answerId);
		answerService.addViewCount(answerId);
		String questionId="";
		try {
			questionId = answer.getQuestionId();
		} catch (Exception e) {
		}
		return this.redirect(request.getContextPath()+"/question/detail"+questionId+".shtml", context);
	}
	
	/**
	 * 收藏答案
	 * @return
	 */
	@Auth(verifyLogin=true,verifyURL=false)
	@RequestMapping("/followAnswer")
	public void userFollowAnswer(String answerId,HttpServletRequest request,HttpServletResponse response) throws Exception{
		
		String userId = SessionUtils.getUserId(request);
		UserFollowAnswer userFollowAnswer = new UserFollowAnswer();
		userFollowAnswer.setAnswerId(answerId);
		userFollowAnswer.setUserId(userId);
		userFollowAnswerService.followAnswer(userFollowAnswer);
		this.sendSuccessMessage(response, "收藏成功");
	}
	
	/**
	 * app收藏答案
	 * @return
	 */
	@Auth(verifyLogin=true)
	@RequestMapping("/app/followAnswer")
	public void appUserFollowAnswer(HttpServletRequest request,HttpServletResponse response) throws Exception{
		JSONObject params = getAppParams(request);
		String answerId = params.getString("answerId");
		if(StringUtils.isEmpty(answerId)){
			sendFailureMessage(response, "answerId不能为空");
			return;
		}
		String userId = SessionUtils.getUserId(request);
		UserFollowAnswer userFollowAnswer = new UserFollowAnswer();
		userFollowAnswer.setAnswerId(answerId);
		userFollowAnswer.setUserId(userId);
		userFollowAnswerService.followAnswer(userFollowAnswer);
		this.sendSuccessMessage(response, "收藏成功");
	}
	
	/**
	 * 取消收藏答案
	 * @return
	 */
	@Auth(verifyLogin=true,verifyURL=false)
	@RequestMapping("/disFollowAnswer")
	public void disUserFollowAnswer(String answerId,HttpServletRequest request,HttpServletResponse response) throws Exception{
		
		String userId = SessionUtils.getUserId(request);
		UserFollowAnswer userFollowAnswer = new UserFollowAnswer();
		userFollowAnswer.setAnswerId(answerId);
		userFollowAnswer.setUserId(userId);
		userFollowAnswerService.unFollowAnswer(userFollowAnswer);
		this.sendSuccessMessage(response, "取消收藏成功");
	}
	
	/**
	 * app取消收藏答案
	 * @return
	 */
	@Auth(verifyLogin=true,verifyURL=false)
	@RequestMapping("/app/disFollowAnswer")
	public void appDisUserFollowAnswer(HttpServletRequest request,HttpServletResponse response) throws Exception{
		JSONObject params = getAppParams(request);
		String answerId = params.getString("answerId");
		if(StringUtils.isEmpty(answerId)){
			sendFailureMessage(response, "answerId不能为空");
			return;
		}
		String userId = SessionUtils.getUserId(request);
		UserFollowAnswer userFollowAnswer = new UserFollowAnswer();
		userFollowAnswer.setAnswerId(answerId);
		userFollowAnswer.setUserId(userId);
		userFollowAnswerService.unFollowAnswer(userFollowAnswer);
		this.sendSuccessMessage(response, "取消收藏成功");
	}
	
	/**
	 * 答案点赞
	 * @param answerId
	 * @param request
	 * @param response
	 */
	@Auth(verifyLogin=true,verifyURL=false)
	@RequestMapping("/praiseAnswer")
	public void praiseAnswer(String answerId,HttpServletRequest request,HttpServletResponse response){
		String userId = SessionUtils.getUserId(request);
		UserPraiseAnswer userPraiseAnswer = new UserPraiseAnswer();
		userPraiseAnswer.setUserId(userId);
		userPraiseAnswer.setAnswerId(answerId);
		userPraiseAnswerService.praiseAnswer(userPraiseAnswer);
		
		Answer answer = answerService.getAnswerById(answerId);
		String publishUser = "";
		if (answer != null) {
			publishUser =  answer.getUid();
		}
		this.addNotification(Constant.ACTION_TYPE_PRAISE, Constant.MODEL_TYPE_ANSWER,
				answerId, userId, publishUser);
		this.addUserActionHistory(userId, Constant.MODEL_TYPE_ANSWER, 
				Constant.ACTION_TYPE_PRAISE, answerId, 0);
		
		this.sendSuccessMessage(response, "点赞成功");
	}
	
	/**
	 * app答案点赞
	 * @param answerId
	 * @param request
	 * @param response
	 */
	@Auth(verifyLogin=true)
	@RequestMapping("/app/praiseAnswer")
	public void appPraiseAnswer(HttpServletRequest request,HttpServletResponse response){
		JSONObject params = getAppParams(request);
		String answerId = params.getString("answerId");
		if(StringUtils.isEmpty(answerId)){
			sendFailureMessage(response, "answerId不能为空");
			return;
		}
		String userId = SessionUtils.getUserId(request);
		UserPraiseAnswer userPraiseAnswer = new UserPraiseAnswer();
		userPraiseAnswer.setUserId(userId);
		userPraiseAnswer.setAnswerId(answerId);
		userPraiseAnswerService.praiseAnswer(userPraiseAnswer);
		
		Answer answer = answerService.getAnswerById(answerId);
		String publishUser = "";
		if (answer != null) {
			publishUser =  answer.getUid();
		}
		this.addNotification(Constant.ACTION_TYPE_PRAISE, Constant.MODEL_TYPE_ANSWER,
				answerId, userId, publishUser);
		this.addUserActionHistory(userId, Constant.MODEL_TYPE_ANSWER, 
				Constant.ACTION_TYPE_PRAISE, answerId, 0);
		
		this.sendSuccessMessage(response, "点赞成功");
	}
	
	/**
	 * 取消答案点赞
	 * @param answerId
	 * @param request
	 * @param response
	 */
	@Auth(verifyLogin=true,verifyURL=false)
	@RequestMapping("/disPraiseAnswer")
	public void deiPraiseAnswer(String answerId,HttpServletRequest request,HttpServletResponse response){
		String userId = SessionUtils.getUserId(request);
		UserPraiseAnswer userPraiseAnswer = new UserPraiseAnswer();
		userPraiseAnswer.setUserId(userId);
		userPraiseAnswer.setAnswerId(answerId);
		userPraiseAnswerService.disPraiseAnswer(userPraiseAnswer);
		this.sendSuccessMessage(response, "取消成功");
	}
	
	/**
	 * app取消答案点赞
	 * @param answerId
	 * @param request
	 * @param response
	 */
	@Auth(verifyLogin=true)
	@RequestMapping("/app/disPraiseAnswer")
	public void appDeiPraiseAnswer(HttpServletRequest request,HttpServletResponse response){
		JSONObject params = getAppParams(request);
		String answerId = params.getString("answerId");
		if(StringUtils.isEmpty(answerId)){
			sendFailureMessage(response, "answerId不能为空");
			return;
		}
		String userId = SessionUtils.getUserId(request);
		UserPraiseAnswer userPraiseAnswer = new UserPraiseAnswer();
		userPraiseAnswer.setUserId(userId);
		userPraiseAnswer.setAnswerId(answerId);
		userPraiseAnswerService.disPraiseAnswer(userPraiseAnswer);
		this.sendSuccessMessage(response, "取消成功");
	}
	/**
	 * 评论答案
	 * @return
	 */
	@Auth(verifyLogin=true,verifyURL=false)
	@RequestMapping("/commentAnswer")
	public void userCommentAnswer(AnswerComments answerComments,HttpServletRequest request,HttpServletResponse response) throws Exception{
		String userId = SessionUtils.getUserId(request);
		UserInfoSession userInfo = SessionUtils.getUser(request);
		if(userInfo.getCertifyStatus()==2){//认证律师自动通过审核
			answerComments.setVerifyStatus(1); 
		}else{
			answerComments.setVerifyStatus(0); 
		}
		answerComments.setCommentsId(SerialNo.getUNID()); 
		answerComments.setAddTime(DateTimeUtil.getNowDateTime());
		answerComments.setUid(userId);
		answerCommentsService.addAnswerComment(answerComments);
		
		
		Answer answer = answerService.getAnswerById(answerComments.getAnswerId());
		String publishUser = "";
		if (answer != null) {
			publishUser =  answer.getUid();
		}
		this.addNotification(Constant.ACTION_TYPE_COMMENT, Constant.MODEL_TYPE_ANSWER,
				answerComments.getAnswerId(), userId, publishUser);
		this.addUserActionHistory(userId, Constant.MODEL_TYPE_ANSWER, 
				Constant.ACTION_TYPE_COMMENT, answerComments.getAnswerId(), 0);
		this.sendSuccessMessage(response, "评论成功");
	}
	
	/**
	 * app评论答案
	 * @return
	 */
	@Auth(verifyLogin=true,verifyURL=false)
	@RequestMapping("/app/commentAnswer")
	public void appUserCommentAnswer(HttpServletRequest request,HttpServletResponse response){
		JSONObject params = new JSONObject();
		String answerId = params.getString("answerId");
		if(StringUtils.isEmpty(answerId)){
			sendFailureMessage(response, "answerId不能为空");
			return;
		}
		String message = params.getString("message");
		if(StringUtils.isEmpty(message)){
			sendFailureMessage(response, "message不能为空");
			return;
		}
		AnswerComments answerComments = new AnswerComments();
		answerComments.setAnswerId(answerId);
		answerComments.setMessage(message);
		String userId = SessionUtils.getUserId(request);
		UserInfoSession userInfo = SessionUtils.getUser(request);
		if(userInfo.getCertifyStatus()==2){//认证律师自动通过审核
			answerComments.setVerifyStatus(1); 
		}else{
			answerComments.setVerifyStatus(0); 
		}
		answerComments.setCommentsId(SerialNo.getUNID()); 
		answerComments.setAddTime(DateTimeUtil.getNowDateTime());
		answerComments.setUid(userId);
		answerCommentsService.addAnswerComment(answerComments);
		
		
		Answer answer = answerService.getAnswerById(answerComments.getAnswerId());
		String publishUser = "";
		if (answer != null) {
			publishUser =  answer.getUid();
		}
		this.addNotification(Constant.ACTION_TYPE_COMMENT, Constant.MODEL_TYPE_ANSWER,
				answerComments.getAnswerId(), userId, publishUser);
		this.addUserActionHistory(userId, Constant.MODEL_TYPE_ANSWER, 
				Constant.ACTION_TYPE_COMMENT, answerComments.getAnswerId(), 0);
		this.sendSuccessMessage(response, "评论成功");
	}
	
	
	@Auth(verifyLogin=false)//无需登录
	@RequestMapping("/listAnswerComment{answerId}") 
	public ModelAndView listAnswerComment(@PathVariable String answerId,HttpServletRequest request,HttpServletResponse response,Pager p){
		Map<String, Object> context = getRootMap();
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("answerId", answerId);
		map.put("verifyStatus", 1);
		context.put("commentList", answerCommentsService.listAnswerComments(map, p));
		return new ModelAndView("ajax/answer_commentList",context); 
	}

	/**
	 * 
	 * @param answer
	 * @param request
	 * @return
	 */
	@Auth(verifyLogin=true,verifyURL=false)
	@RequestMapping("/saveAnswer")
	public ModelAndView saveAnswer(Answer answer,HttpServletRequest request){
		Map<String, Object> context = getRootMap();
		answer.setUid(SessionUtils.getUserId(request));
		UserInfoSession userInfo = SessionUtils.getUser(request);
		if(userInfo.getCertifyStatus()==2){//认证律师回答自动通过审核
			answer.setStatus(1);
			answer.setVerifyStatus(1);
		}else{
			context.put("msg", "答案已经提交成功，正在审核中");
			answer.setStatus(0);
			answer.setVerifyStatus(0);
		}
		if(answerService.saveAnswer(answer) == 0){
			context.put("msg", "回答失败，请刷新后再试");
		}else{
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("questionId", answer.getQuestionId());
			Question question = questionService.getQuestionById(map);
			String publishUser = "";
			if (question != null) {
				publishUser =  question.getPublishedUid();
			}
			this.addNotification(Constant.ACTION_TYPE_ANSWER, Constant.MODEL_TYPE_ANSWER,
					answer.getAnswerId(), SessionUtils.getUserId(request), publishUser);
			this.addUserActionHistory(SessionUtils.getUserId(request), Constant.MODEL_TYPE_ANSWER, 
					Constant.ACTION_TYPE_ANSWER, answer.getAnswerId(), answer.getAnonymous());
		}
		return redirect(request.getContextPath()+"/question/detail"+answer.getQuestionId()+".shtml", context);
	}
	
	/**
	 * app保存回答
	 * @param request
	 * @param response
	 */
	@Auth(verifyLogin=true,verifyURL=false)
	@RequestMapping("/app/saveAnswer")
	public void appSaveAnswer(HttpServletRequest request,HttpServletResponse response){
		JSONObject params = getAppParams(request);
		String questionId = params.getString("questionId");
		if(StringUtils.isEmpty(questionId)){
			sendFailureMessage(response, "questionId不能为空");
			return;
		}
		String answerContent = params.getString("answerContent");
		if(StringUtils.isEmpty(answerContent)){
			sendFailureMessage(response, "answerContent不能为空");
			return;
		}
		String anonymous = params.getString("anonymous");
		if(StringUtils.isEmpty(anonymous)){
			sendFailureMessage(response, "anonymous不能为空");
			return;
		}
		Answer answer = new Answer();
		answer.setQuestionId(questionId);
		answer.setAnswerContent(answerContent);
		answer.setAnonymous(Integer.parseInt(anonymous));
		answer.setUid(SessionUtils.getUserId(request));
		UserInfoSession userInfo = SessionUtils.getUser(request);
		String msg = "答案已经提交成功，正在审核中";
		if(userInfo.getCertifyStatus()==2){//认证律师回答自动通过审核
			msg = "答案已经提交成功";
			answer.setStatus(1);
			answer.setVerifyStatus(1);
		}else{
			answer.setStatus(0);
			answer.setVerifyStatus(0);
		}
		if(answerService.saveAnswer(answer) == 0){
			sendFailureMessage(response, "回答失败");
		}else{
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("questionId", answer.getQuestionId());
			Question question = questionService.getQuestionById(map);
			String publishUser = "";
			if (question != null) {
				publishUser =  question.getPublishedUid();
			}
			this.addNotification(Constant.ACTION_TYPE_ANSWER, Constant.MODEL_TYPE_ANSWER,
					answer.getAnswerId(), SessionUtils.getUserId(request), publishUser);
			this.addUserActionHistory(SessionUtils.getUserId(request), Constant.MODEL_TYPE_ANSWER, 
					Constant.ACTION_TYPE_ANSWER, answer.getAnswerId(), answer.getAnonymous());
			sendSuccessMessage(response, msg);
		}
	}
}
