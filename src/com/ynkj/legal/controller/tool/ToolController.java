package com.ynkj.legal.controller.tool;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;


import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.ynkj.common.interceptor.Auth;
import com.ynkj.common.util.Pager;
import com.ynkj.legal.common.BaseController;
import com.ynkj.legal.service.question.TopicService;


/**
 * 工具
 * @author Administrator
 *
 */
@Controller
@RequestMapping("/tool")
public class ToolController extends BaseController{
	@Resource
	public TopicService topicService;
	
	@Auth(verifyLogin=false)//无需登录
	@RequestMapping("/index") 
	public ModelAndView index(Pager p){
		Map<String, Object> context = getRootMap();
		return new ModelAndView("tool/index",context);
	}
	
	@Auth(verifyLogin=false)//无需登录
	@RequestMapping("/gstool") 
	public ModelAndView gstool(){
		Map<String, Object> context = getRootMap();
		return new ModelAndView("tool/gstool",context);
	}
	
	@Auth(verifyLogin=false)//无需登录
	@RequestMapping("/ldtool") 
	public ModelAndView ldtool(){
		Map<String, Object> context = getRootMap();
		return new ModelAndView("tool/ldtool",context);
	}
	
	@Auth(verifyLogin=false)//无需登录
	@RequestMapping("/swtool") 
	public ModelAndView swtool(){
		Map<String, Object> context = getRootMap();
		return new ModelAndView("tool/swtool",context);
		
	}
   
	
	@Auth(verifyLogin=false,verifyURL=false)//无需登录
	@RequestMapping("/ajaxQueryToolArea")
	public ModelAndView ajaxQueryToolArea(String parentId){
		Map<String,Object> context =  getRootMap();
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("parentId", parentId);
		context.put("areaList", topicService.queryArea(map));
		return new ModelAndView("ajax/index_tool_area",context);
	}
}
