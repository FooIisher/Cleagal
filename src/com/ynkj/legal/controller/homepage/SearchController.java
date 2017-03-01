package com.ynkj.legal.controller.homepage;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.ynkj.common.interceptor.Auth;
import com.ynkj.common.session.SessionUtils;
import com.ynkj.common.util.Pager;
import com.ynkj.legal.common.BaseController;
import com.ynkj.legal.model.cases.Cases;
import com.ynkj.legal.model.question.Question;
import com.ynkj.legal.model.users.Users;
import com.ynkj.legal.service.cases.CasesService;
import com.ynkj.legal.service.question.QuestionService;
import com.ynkj.legal.service.users.UsersService;


/**
 * 搜搜
 * @author Administrator
 *
 */
@Controller
@RequestMapping("/search")
public class SearchController extends BaseController{
	
	@Resource
	public QuestionService questionService;
	@Resource
	public CasesService casesService;
	@Resource
	protected UsersService usersService;
	
	@Auth(verifyLogin=false,verifyURL=false)//无需登录
	@RequestMapping
	public ModelAndView searchQuestion(String type,String q,HttpServletRequest request,Pager p){
		Map<String,Object> context =  getRootMap();
		Map<String, Object> map = new HashMap<String, Object>();
		String userId = SessionUtils.getUserId(request);
		map.put("userId", userId);
		map.put("keyword", q);
		context.put("q", q);
		if (type.equals("question")) {
			List<Question> listQuestion = questionService.listQuestion(map,p);
			context.put("listQuestion", listQuestion);
			return new ModelAndView("search/search-question-result",context);
		}else if ( type.equals("people")) {
			List<Users> listUsers = usersService.listUsers(map, p);
			context.put("listUsers", listUsers);
			return new ModelAndView("search/search-users-result",context);
		}else if ( type.equals("case")) {
			List<Cases> listCases = casesService.listCases(map, p);
			context.put("listCases", listCases);
			return new ModelAndView("search/search-case-result",context);
		}
		return new ModelAndView("search/search-question-result",context);
	}
	
	@Auth(verifyLogin=false,verifyURL=false)//无需登录
	@RequestMapping("/ajaxSearch") 
	public ModelAndView ajaxSearchQuestion(String type,String q,HttpServletRequest request,Pager p){
		Map<String,Object> context =  getRootMap();
		Map<String, Object> map = new HashMap<String, Object>();
		String userId = SessionUtils.getUserId(request);
		map.put("userId", userId);
		map.put("keyword", q);
		context.put("q", q);
		if (type.equals("question")) {
			List<Question> listQuestion = questionService.listQuestion(map,p);
			context.put("listQuestion", listQuestion);
			return new ModelAndView("search/ajax-search-question",context);
		}else if ( type.equals("people")) {
			List<Users> listUsers = usersService.listUsers(map, p);
			context.put("listUsers", listUsers);
			return new ModelAndView("search/ajax-search-users",context);
		}else if ( type.equals("case")) {
			List<Cases> listCases = casesService.listCases(map, p);
			context.put("listCases", listCases);
			return new ModelAndView("search/ajax-search-case",context);
		}
		return new ModelAndView("search/ajax-search-question",context);
	}
	
	@Auth(verifyLogin=false,verifyURL=false)//无需登录
	@RequestMapping("/autoCompleteSearch") 
	public ModelAndView autoCompleteSearch(String q,HttpServletRequest request,HttpServletResponse response){
		Map<String,Object> context =  getRootMap();
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("keyword", q);
		Pager pager = new Pager();
		pager.setPageSize(3);
		pager.setPageId(1);
		List<HashMap<String, Object>> listQuestion = 
			questionService.simpleSearch(map, pager);
		context.put("listQuestion", listQuestion);
		List<HashMap<String, Object>> listCases = casesService.simpleSearch(map, pager);
		context.put("listCases", listCases);
		List<HashMap<String, Object>> listUsers = usersService.simpleSearch(map, pager);
		context.put("listUsers", listUsers);
		return new ModelAndView("ajax/all-search",context);
	}
	
 

}
