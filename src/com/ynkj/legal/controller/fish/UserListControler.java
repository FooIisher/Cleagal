package com.ynkj.legal.controller.fish;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;




import com.ynkj.common.interceptor.Auth;

import com.ynkj.common.util.Pager;
import com.ynkj.legal.common.BaseController;

import com.ynkj.legal.model.users.Type;
import com.ynkj.legal.model.users.Users;
import com.ynkj.legal.service.users.TypeService;


/**
 * @author fish
 *
 */

@Controller
@RequestMapping("/fish")
public class UserListControler extends BaseController {
	@Resource
	public TypeService typeService;
	
	@Auth(verifyLogin = false, verifyURL = false)
	@RequestMapping("/index{typeId}") 
	public ModelAndView index(@PathVariable String typeId,HttpServletRequest request,HttpServletResponse response){
		
		Map<String, Object> context = getRootMap();
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("isParent", 1);
		Pager p = new Pager();
		List<Type> typelist = typeService.listType(map, p);
		context.put("typeList", typelist);

		if(typeId == null||typeId.equals("")){
			map.put("orderBy", "time");
			
		}else{
			Type subtype = typeService.queryTypeById(typeId);
			context.put("subtype", subtype);
			Type type = typeService.queryTypeById(subtype.getParentId());
			context.put("type", type);
			map.put(subtype.getTypeKey(),subtype.getTypeValue()); 
		}
		
		List<Users> Users = usersService.listUsers(map, p);
		int num = (usersService.listUsers(map)).size();
		context.put("listUsers", Users);
		context.put("listUsersNum", num);
        
		return new ModelAndView("fish/index",context);
	}
	
	
	
	/*	
	@Auth(verifyLogin = false, verifyURL = false)
	@RequestMapping("/index") 
	public ModelAndView index(String redirectURL,HttpServletRequest request,HttpServletResponse response){
		
		Map<String, Object> context = getRootMap();
		Map<String, Object> map = new HashMap<String, Object>();
		Pager p = new Pager();
		p.setPageSize(5);
		List<Users> Users = usersService.listUsers(map, p);
		int num = (usersService.listUsers(map)).size();
		context.put("listUsers", Users);
		context.put("listUsersNum", num);
        
		return new ModelAndView("fish/index",context);
	}

	@Auth(verifyLogin = false, verifyURL = false)
	@RequestMapping("/certified")
	public ModelAndView certified(String redirectURL,HttpServletRequest request,HttpServletResponse response){
		Map<String, Object> context = getRootMap();
		Map<String, Object> map = new HashMap<String, Object>();
		Pager p = new Pager();
		p.setPageSize(5);
		map.put("certifyStatus", 2); 
		List<Users> Users = usersService.listUsers(map, p);
		int num = (usersService.listUsers(map)).size();
		context.put("listUsers", Users);
		context.put("listUsersNum", num);
		return new ModelAndView("fish/index",context);
	}
	
	@Auth(verifyLogin = false, verifyURL = false)
	@RequestMapping("/certifing")
	public ModelAndView certifing(String redirectURL,HttpServletRequest request,HttpServletResponse response){
		Map<String, Object> context = getRootMap();
		Map<String, Object> map = new HashMap<String, Object>();
		Pager p = new Pager();
		p.setPageSize(5);
		map.put("certifyStatus", 1); 
		List<Users> Users = usersService.listUsers(map, p);
		int num = (usersService.listUsers(map)).size();
		context.put("listUsers", Users);
		context.put("listUsersNum", num);
		return new ModelAndView("fish/index",context);
	}
	
	@Auth(verifyLogin = false, verifyURL = false)
	@RequestMapping("/uncertified")
	public ModelAndView uncertified(String redirectURL,HttpServletRequest request,HttpServletResponse response){
		Map<String, Object> context = getRootMap();
		Map<String, Object> map = new HashMap<String, Object>();
		Pager p = new Pager();
		p.setPageSize(5);
		map.put("certifyStatus", 3); 
		List<Users> Users = usersService.listUsers(map, p);
		int num = (usersService.listUsers(map)).size();
		context.put("listUsers", Users);
		context.put("listUsersNum", num);
		return new ModelAndView("fish/index",context);
	}
	
	@Auth(verifyLogin = false, verifyURL = false)
	@RequestMapping("/nocertified")
	public ModelAndView nocertified(String redirectURL,HttpServletRequest request,HttpServletResponse response){
		Map<String, Object> context = getRootMap();
		Map<String, Object> map = new HashMap<String, Object>();
		Pager p = new Pager();
		p.setPageSize(5);
		map.put("certifyStatus", 0); 
		List<Users> Users = usersService.listUsers(map, p);
		int num = (usersService.listUsers(map)).size();
		context.put("listUsers", Users);
		context.put("listUsersNum", num);
		return new ModelAndView("fish/index",context);
	}
	
	
	/*
	@Auth(verifyLogin = false, verifyURL = false)
	@RequestMapping("/certify={certifyStatus}")
	public ModelAndView certify(@PathVariable String certifyStatus, HttpServletRequest request,HttpServletResponse response){
		Map<String, Object> context = getRootMap();
		Map<String, Object> map = new HashMap<String, Object>();
		Pager p = new Pager();
		p.setPageSize(8);
		System.out.println("---------"+certifyStatus+"---------");
		map.put("certifyStatus", certifyStatus); 
		List<Users> Users = usersService.listUsers(map, p);
		int num = (usersService.listUsers(map)).size();
		System.out.println("---------"+num+"---------");
		context.put("listUsers", Users);
		context.put("listUsersNum", num);
		return new ModelAndView("fish/index",context);
	}
	
	
	@Auth(verifyLogin = false, verifyURL = false)
	@RequestMapping("/sex={sex}")
	public ModelAndView sex(@PathVariable String sex, HttpServletRequest request,HttpServletResponse response){
		Map<String, Object> context = getRootMap();
		Map<String, Object> map = new HashMap<String, Object>();
		Pager p = new Pager();
		p.setPageSize(8);
		map.put("sex", sex); 
		List<Users> Users = usersService.listUsers(map, p);
		int num = (usersService.listUsers(map)).size();
		context.put("listUsers", Users);
		context.put("listUsersNum", num);
		return new ModelAndView("fish/index",context);
	}
	
	@Auth(verifyLogin = false, verifyURL = false)
	@RequestMapping("")
	public ModelAndView sex(@PathVariable String type,String value, HttpServletRequest request,HttpServletResponse response){
		Map<String, Object> context = getRootMap();
		Map<String, Object> map = new HashMap<String, Object>();
		Pager p = new Pager();
		p.setPageSize(8);
		System.out.println("++++++++++++++"+type+"+++++++++++++"+value+"+++++++++++++");
		//map.put(type, value); 
		List<Users> Users = usersService.listUsers(map, p);
		int num = (usersService.listUsers(map)).size();
		context.put("listUsers", Users);
		context.put("listUsersNum", num);
		return new ModelAndView("fish/index",context);
	}
	*/
	
	@Auth(verifyLogin = false, verifyURL = false)
	@RequestMapping("/ajaxQuaryUserIndex")
	public ModelAndView indexajax(String typeId, HttpServletRequest request,HttpServletResponse response,Pager p){
	
		Map<String, Object> context = getRootMap();
		Map<String, Object> map = new HashMap<String, Object>();
		if(typeId==null||typeId.equals("")){
			map.put("orderBy", "time");
		}else{
			Type subType = typeService.queryTypeById(typeId);
			map.put(subType.getTypeKey(), subType.getTypeValue()); 
		}
		List<Users> Users = usersService.listUsers(map, p);
		context.put("listUsers", Users);
		return new ModelAndView("ajax/index_user",context);
	}
}



