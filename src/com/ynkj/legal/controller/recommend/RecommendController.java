package com.ynkj.legal.controller.recommend;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;



import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.ynkj.common.interceptor.Auth;
import com.ynkj.common.session.SessionUtils;
import com.ynkj.common.util.Pager;
import com.ynkj.legal.common.BaseController;
import com.ynkj.legal.model.question.Topic;
import com.ynkj.legal.model.users.Users;
import com.ynkj.legal.service.question.TopicService;
import com.ynkj.legal.service.users.UsersService;


/**
 * 推荐
 * @author Administrator
 *
 */
@Controller
@RequestMapping("/recommend")
public class RecommendController extends BaseController{
	@Resource
	public TopicService topicService;
	@Resource
	protected UsersService usersService;
	
	@Auth(verifyLogin=false)//无需登录
	@RequestMapping("/index{topicId}") 
	public ModelAndView index(@PathVariable String topicId,Pager p){
		Map<String,Object> context =  getRootMap();
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("isParent", 1);
		Pager pager = new Pager();
		context.put("topicList", topicService.listTopic(map, pager));
		
		if (topicId == null || topicId.equals("") || topicId.equals("New")) {
			topicId = "New";
			map.put("New", topicId);
			context.put("topicId","New");
		}else if (topicId.equals("Hot")) {
			map.put("Hot", topicId);
			context.put("topicId","Hot");
		}else{
			
			Topic topic2 = topicService.queryTopicById(topicId);
			context.put("topic2", topic2);
			Topic topic1 = topicService.queryTopicById(topic2.getParentId());
			context.put("topic1", topic1);
			context.put("topicId", topic2.getParentId());
			map.put("topicId", topicId);
		}
		
		List<Users> list = usersService.listUsersRecommend(map, p);
		context.put("recommendUsers", list);
		return new ModelAndView("recommend/index",context);
	}
	
	
	/**
	 * 首页右侧推荐大牛
	 * @param request
	 * @param response
	 */
	
	
	@Auth(verifyLogin=false,verifyURL=false)//无需登录
	@RequestMapping("/indexRecomment")
	public ModelAndView indexRecomment(){
		Map<String,Object> context =  getRootMap();
		Map<String, Object> map = new HashMap<String, Object>();
		//map.put("recommendFlag", 1);
		Pager p = new Pager();
		p.setPageSize(3);
		context.put("recommentList", usersService.listUsersRecommend(map, p));
		return new ModelAndView("ajax/index_recomend_users",context);
	}
	
	/**
	 * 邀请用户列表
	 * @param request
	 * @param response
	 */
	@Auth(verifyLogin=false)//无需登录
	@RequestMapping("/inviteUsers")
	public ModelAndView inviteUsers(String questionId,String keyword,HttpServletRequest request){
		Map<String,Object> context =  getRootMap();
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("questionId", questionId);
		map.put("excludeUserId", SessionUtils.getUserId(request));
		map.put("keyword", keyword);
		map.put("userOrderBy", "answer_count DESC,recommend_flag DESC,");
		Pager p = new Pager();
		context.put("userList", usersService.listUsers(map, p));
		return new ModelAndView("ajax/invite_userList",context);
	}
	
}
