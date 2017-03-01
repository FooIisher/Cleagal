package com.ynkj.legal.controller.homepage;

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
import com.ynkj.common.util.DateTimeUtil;
import com.ynkj.common.util.Pager;
import com.ynkj.legal.common.BaseController;
import com.ynkj.legal.model.question.Question;
import com.ynkj.legal.model.question.Topic;
import com.ynkj.legal.service.answer.AnswerService;
import com.ynkj.legal.service.cases.CasesService;
import com.ynkj.legal.service.question.QuestionService;
import com.ynkj.legal.service.question.TopicService;
import com.ynkj.legal.service.users.UsersService;



/**
 * 首页
 * @author Administrator
 *
 */
@Controller
@RequestMapping("/homepage")
public class HomepageController extends BaseController{
	@Resource
	public TopicService topicService;
	
	@Resource
	public UsersService usersService;
	
	@Resource
	public AnswerService answerService;
	
	@Resource
	public QuestionService questionService;
	
	@Resource
	public CasesService casesService;
	
	@Auth(verifyLogin=false)//无需登录
	@RequestMapping("/index{topicId}") 
	public ModelAndView index(@PathVariable String topicId,HttpServletRequest request,Pager p){
		Map<String,Object> context =  getRootMap();
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("isParent", 1);
		Pager pager = new Pager();
		context.put("topicList", topicService.listTopic(map, pager));
		String userId = SessionUtils.getUserId(request);
		map.put("userId", userId);
		if (topicId == null || topicId.equals("") || topicId.equals("New")) {
			map.put("orderBy", "time");
			context.put("topicId","New");
		}else if (topicId.equals("Hot")) {
			map.put("orderBy", "answerCount");
			context.put("topicId","Hot");
		}else{
			Topic topic2 = topicService.queryTopicById(topicId);
			context.put("topic2", topic2);
			Topic topic1 = topicService.queryTopicById(topic2.getParentId());
			context.put("topic1", topic1);
			context.put("topicId", topic2.getParentId());
			map.put("topicId", topicId);
		}
		
		map.put("status", 1);
		List<Question> listQuestion = questionService.listQuestion(map, p);
		for (int i = 0; i < listQuestion.size(); i++) {
			listQuestion.get(i).setAddTime(DateTimeUtil.getTimeIntervalStr(listQuestion.get(i).getAddTime(), DateTimeUtil.getNowDateTime(), DateTimeUtil.DEFAULT_DATETIME_FORMAT_SEC));
			if(StringUtils.isNotEmpty(listQuestion.get(i).getAnswerContent())){
				listQuestion.get(i).setAnswerContent(Jsoup.parse(listQuestion.get(i).getAnswerContent()).text());
			}
		}
		context.put("listQuestion", listQuestion);
		return new ModelAndView("homepage/index",context);
	}

	/**
	 * app标签查询
	 * @param request
	 * @param response
	 */
	@Auth(verifyLogin=false)//无需登录
	@RequestMapping("/app/topic") 
	public void appTopic(HttpServletRequest request,HttpServletResponse response){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("isParent", 1);
		Pager pager = new Pager();
		List<Topic> list = topicService.listTopic(map, pager);
		sendSuccessMessage(response, "获取数据成功", list);
	}
	
	/**
	 * app问答查询
	 * @param request
	 * @param response
	 */
	@Auth(verifyLogin=false)//无需登录
	@RequestMapping("/app/index") 
	public void appIndex(HttpServletRequest request,HttpServletResponse response){
		Map<String,Object> context =  getRootMap();
		Pager p = getAppPager(request);
		JSONObject params = getAppParams(request);
		String topicId = params.getString("topicId");
		String orderStr = params.getString("orderStr");
		Map<String, Object> map = new HashMap<String, Object>();
		String userId = SessionUtils.getUserId(request);
		map.put("userId", userId);
		if (orderStr == null || orderStr.equals("") || orderStr.equals("New")) {
			map.put("orderBy", "time");
		}else if (orderStr.equals("Hot")) {
			map.put("orderBy", "answerCount");
		}
		if(StringUtils.isNotEmpty(topicId)){
			map.put("topicIds", "'"+topicId.replaceAll(",", "','")+"'");
		}
		map.put("status", 1);
		List<Question> listQuestion = questionService.listQuestion(map, p);
		for (int i = 0; i < listQuestion.size(); i++) {
			listQuestion.get(i).setAddTime(DateTimeUtil.getTimeIntervalStr(listQuestion.get(i).getAddTime(), DateTimeUtil.getNowDateTime(), DateTimeUtil.DEFAULT_DATETIME_FORMAT_SEC));
			if(StringUtils.isNotEmpty(listQuestion.get(i).getAnswerContent())){
				listQuestion.get(i).setAnswerContent(Jsoup.parse(listQuestion.get(i).getAnswerContent()).text());
			}
		}
		context.put("listQuestion", listQuestion);
		sendSuccessMessage(response, "获取数据成功", context);
	}
	
	@Auth(verifyLogin=false)//无需登录
	@RequestMapping("/ajaxQueryQuestionIndex") 
	public ModelAndView ajaxQueryQuestionIndex(String topicId,HttpServletRequest request,Pager p){
		System.out.println("+++++++++++++++++++++++++++");
		Map<String,Object> context =  getRootMap();
		Map<String, Object> map = new HashMap<String, Object>();
		String userId = SessionUtils.getUserId(request);
		map.put("isParent", 1);
		map.put("status", 1);
		map.put("userId", userId);
		if (topicId == null || topicId.equals("") || topicId.equals("New")) {
			map.put("orderBy", "time");
		}else if (topicId.equals("Hot")) {
			map.put("orderBy", "answerCount");
		}else{
			map.put("topicId", topicId);
		}
		List<Question> listQuestion = questionService.listQuestion(map, p);
		for (int i = 0; i < listQuestion.size(); i++) {
			listQuestion.get(i).setAddTime(DateTimeUtil.getTimeIntervalStr(listQuestion.get(i).getAddTime(), DateTimeUtil.getNowDateTime(), DateTimeUtil.DEFAULT_DATETIME_FORMAT_SEC));
			if(StringUtils.isNotEmpty(listQuestion.get(i).getAnswerContent())){
				listQuestion.get(i).setAnswerContent(Jsoup.parse(listQuestion.get(i).getAnswerContent()).text());
			}
		}
		context.put("listQuestion", listQuestion);
		return new ModelAndView("ajax/index_question",context);
	}
	
	
	@Auth(verifyLogin=false,verifyURL=false)//无需登录
	@RequestMapping("/ajaxQueryQuestionTopic")
	public ModelAndView ajaxQueryQuestionTopic(String parentId){
		Map<String,Object> context =  getRootMap();
		Map<String, Object> map = new HashMap<String, Object>();
		if(StringUtils.isNotEmpty(parentId)){
			map.put("parentId", parentId);
		}else{
			map.put("isParent", 1);
		}
		context.put("topicList", topicService.listTopicSimple(map));
		return new ModelAndView("ajax/right_question_topic",context);
	}
	
}
