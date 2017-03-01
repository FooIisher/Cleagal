package com.ynkj.legal.controller.users;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import sun.misc.BASE64Decoder;

import com.alibaba.fastjson.JSONObject;
import com.ynkj.common.interceptor.Auth;
import com.ynkj.common.session.SessionUtils;
import com.ynkj.common.session.UserInfoSession;
import com.ynkj.common.util.DateTimeUtil;
import com.ynkj.common.util.HtmlUtil;
import com.ynkj.common.util.MethodUtil;
import com.ynkj.common.util.Pager;
import com.ynkj.common.util.SerialNo;
import com.ynkj.common.util.Uploader;
import com.ynkj.legal.common.BaseController;
import com.ynkj.legal.common.Constant;
import com.ynkj.legal.model.answer.Answer;
import com.ynkj.legal.model.answer.AnswerComments;
import com.ynkj.legal.model.cases.Cases;
import com.ynkj.legal.model.comm.Job;
import com.ynkj.legal.model.question.Question;
import com.ynkj.legal.model.question.QuestionInvite;
import com.ynkj.legal.model.users.Notification;
import com.ynkj.legal.model.users.UserFollow;
import com.ynkj.legal.model.users.Users;
import com.ynkj.legal.service.answer.AnswerCommentsService;
import com.ynkj.legal.service.answer.AnswerService;
import com.ynkj.legal.service.cases.CasesService;
import com.ynkj.legal.service.comm.JobService;
import com.ynkj.legal.service.question.QuestionInviteService;
import com.ynkj.legal.service.question.QuestionService;
import com.ynkj.legal.service.question.TopicService;
import com.ynkj.legal.service.users.UserFollowService;
import com.ynkj.legal.service.users.UsersService;

@Controller
@RequestMapping("/member")
public class UsersController extends BaseController{
	
	@Resource
	protected UsersService usersService;
	@Resource
	protected UserFollowService userFollowService;
	@Resource
	public QuestionService questionService;
	@Resource
	public AnswerService answerService;
	@Resource
	public AnswerCommentsService answerCommentsService;
	@Resource
	public CasesService casesService;
	
	@Resource
	public JobService jobService;
	@Resource
	public QuestionInviteService questionInviteService;
	
	@Resource
	public TopicService topicService;
	
 
	
	@Auth(verifyLogin = false, verifyURL = false)
	@RequestMapping("/{userId}")
	public ModelAndView memberCenter(@PathVariable String userId,HttpServletRequest request,HttpServletResponse response,Pager p){
		
		Map<String, Object> context = getRootMap();
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("userId", userId);
		context.put("userId", userId);
		context.put("dynamicList", usersService.qeuryUserActionHistory(map, p));
		context.put("user", usersService.getUsersById(userId));
		return forword("user/user-dynamic", context);
	}
	
	/**
	 * app获取个人资料
	 * @param request
	 * @param response
	 */
	@Auth(verifyLogin = true, verifyURL = false)
	@RequestMapping("/app/memberCenter")
	public void appMemberCenter(HttpServletRequest request,HttpServletResponse response){
		JSONObject params = getAppParams(request);
		String userId = params.getString("userId");
		if(StringUtils.isEmpty(userId)){
			sendFailureMessage(response, "userId不能为空");
			return;
		}
		Map<String, Object> map = new HashMap<String, Object>();
		Map<String, Object> context = getRootMap();
		map.put("userId", userId);
		context.put("user", usersService.getUsersById(userId));
		context.put("jobList", jobService.listJob());
		map.put("level", 1);
		context.put("provinceList", usersService.queryCityList(map));
		map.put("level", 2);
		context.put("cityList", usersService.queryCityList(map));
		sendSuccessMessage(response, "获取数据成功", context);
	}
	
	/**
	 * app修改个人资料
	 * @param request
	 * @param response
	 */
	@Auth(verifyLogin = true, verifyURL = false)
	@RequestMapping("/app/editUser")
	public void appEditUser(HttpServletRequest request,HttpServletResponse response){
		JSONObject params = getAppParams(request);
		String userId = SessionUtils.getUserId(request);
		Users user = usersService.getUsersById(userId);
		String userName = params.getString("userName");
		int sex = params.getIntValue("sex");
		String oneWord = params.getString("oneWord");
		String individualResume = params.getString("individualResume");
		String jobId = params.getString("jobId");
		String province = params.getString("province");
		String city = params.getString("city");
		user.setUserName(userName);
		user.setSex(sex);
		user.setOneWord(oneWord);
		user.setIndividualResume(individualResume);
		user.setJobId(jobId);
		user.setProvince(province);
		user.setCity(city);
		usersService.updateUser(user);
		sendSuccessMessage(response, "修改成功", user);
	}
	
	/**
	 * app修改个人头像
	 * @param request
	 * @param response
	 */
	@Auth(verifyLogin = true, verifyURL = false)
	@RequestMapping("/app/editAvatarFile")
	public void appEditAvatarFile(HttpServletRequest request,HttpServletResponse response){
		JSONObject params = getAppParams(request);
		String base64 = params.getString("base64");
		String userId = SessionUtils.getUserId(request);
		Users user = usersService.getUsersById(userId);
		String toDir = request.getSession().getServletContext().getRealPath("/upload/users");
		File destDir = new File(toDir);
		if (!destDir.exists())
			destDir.mkdir();
		try {
			byte[] buffer = new BASE64Decoder().decodeBuffer(base64);
			String uuid = DateTimeUtil.getFileNameDate();
			FileOutputStream fos = new FileOutputStream(new File(destDir, uuid + ".jpg"));
			fos.write(buffer);
			fos.flush();
			fos.close();
			user.setAvatarFile("upload/users/"+uuid + ".jpg");
			usersService.updateUser(user);
			sendSuccessMessage(response, "修改成功", user.getAvatarFile());
		} catch (IOException e) {
			sendFailureMessage(response, "上传失败");
			e.printStackTrace();
		}
	}
	
	/**
	 * 邀请我回答的问题
	 * @param request
	 * @param response
	 * @param p
	 * @return
	 */
	@Auth(verifyLogin=true,verifyURL=false)
	@RequestMapping("/invited")
	public ModelAndView invited(HttpServletRequest request,HttpServletResponse response,Pager p){
		Map<String, Object> context = getRootMap();
		String userId = SessionUtils.getUserId(request);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("userId", userId);
		List<QuestionInvite> listQuestionInvite = questionInviteService.listQuestionInvite(map, p);
		context.put("listQuestionInvite", listQuestionInvite);
		return forword("user/question-invited",context);
	}
	
	/**
	 * 用户提问
	 * @param userId
	 * @param request
	 * @param response
	 * @param p
	 * @return
	 */
	@Auth(verifyLogin = false, verifyURL = false)
	@RequestMapping("/{userId}/asks")
	public ModelAndView userQuestion(@PathVariable String userId,String orderBy,HttpServletRequest request,HttpServletResponse response,
			Pager p ){
		Map<String, Object> context = getRootMap();
		Map<String, Object> map = new HashMap<String, Object>();
		if (orderBy != null && !orderBy.equals("") && orderBy.equals("answerCount")) {
			orderBy = "answerCount";
			map.put("orderBy", "answerCount");
		}else{
			orderBy = "time";
			map.put("orderBy", "time");
		}
		context.put("orderBy", orderBy);
		map.put("publishedUid", userId);
		String sessionUserId = SessionUtils.getUserId(request);
		map.put("userId", sessionUserId);
		List<Question> listQuestion = questionService.listQuestion(map, p);
		map.put("findUserId", userId);
		Users user = usersService.getUsersByMap(map);
		context.put("user", user);
		context.put("listQuestion", listQuestion);
		return forword("user/user-question", context);
	}
	
	/**
	 * APP我的提问
	 * @param userId
	 * @param request
	 * @param response
	 * @param p
	 * @return
	 */
	@Auth(verifyLogin = false, verifyURL = false)
	@RequestMapping("/app/asks")
	public void userQuestion(HttpServletRequest request,HttpServletResponse response){
		JSONObject params = getAppParams(request);
		Pager p = getAppPager(request);
		String userId = params.getString("userId");
		String orderBy = params.getString("orderBy");
		if(StringUtils.isEmpty(userId)){
			sendFailureMessage(response, "userId不能为空");
			return;
		}
		Map<String, Object> context = getRootMap();
		Map<String, Object> map = new HashMap<String, Object>();
		if (orderBy != null && !orderBy.equals("") && orderBy.equals("answerCount")) {
			orderBy = "answerCount";
			map.put("orderBy", "answerCount");
		}else{
			orderBy = "time";
			map.put("orderBy", "time");
		}
		context.put("orderBy", orderBy);
		map.put("publishedUid", userId);
		String sessionUserId = SessionUtils.getUserId(request);
		map.put("userId", sessionUserId);
		List<Question> listQuestion = questionService.listQuestion(map, p);
		map.put("findUserId", userId);
		Users user = usersService.getUsersByMap(map);
		context.put("user", user);
		context.put("listQuestion", listQuestion);
		sendSuccessMessage(response, "获取数据成功",context);
	}
	
	/**
	 * 编辑用户
	 * @param request
	 * @param response
	 * @param p
	 * @return
	 */
	@Auth(verifyLogin = true, verifyURL = false)
	@RequestMapping("/edit")
	public ModelAndView edit(HttpServletRequest request,HttpServletResponse response,
			Pager p ){
		Map<String, Object> context = getRootMap();
		String sessionUserId = SessionUtils.getUserId(request);
		Users user = usersService.getUsersById(sessionUserId);
		context.put("user", user);
		List<Job> listJob = jobService.listJob();
		context.put("listJob", listJob);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("level", 1);
		context.put("provinceList", usersService.queryCityList(map));
		map.put("level", 2);
		context.put("cityList", usersService.queryCityList(map));
		return forword("user/user-edit", context);
	}
	
	
	
	@Auth(verifyLogin = true, verifyURL = false)
	@RequestMapping("/edit/users")
	public void editUsers(Users user,int renzhenFlag,HttpServletRequest request,HttpServletResponse response,
			Pager p ){
		StringBuffer htmlStr = new StringBuffer();
		htmlStr.append("<script type=\"text/javascript\">");
		htmlStr.append("parent.callback(");
		try {
			String sessionUserId = SessionUtils.getUserId(request);
			if (renzhenFlag == 1 && user.getJobId()!= null && user.getJobId().equals("lawyer")
					&& (user.getCertifyStatus() == 0 || user.getCertifyStatus() == 3)) {//未认证 和 审核未通过
				user.setCertifyStatus(1) ;//待审核
			}else{
				user.setCertifyCompany(null);
				user.setCertifyFile(null);
				user.setCertifyName(null);
			}
			user.setUserId(sessionUserId);
			usersService.updateUser(user);
			htmlStr.append("'success'");
			Users sessionuser = usersService.getUsersById(sessionUserId);
			UserInfoSession mSession = new UserInfoSession();
			org.springframework.beans.BeanUtils.copyProperties(sessionuser, mSession);
			UserInfoSession oldSession = SessionUtils.getUser(request);
			mSession.setTruePassword(oldSession.getTruePassword()); //记录真实密码
			SessionUtils.setAttr(request, SessionUtils.SESSION_USER, mSession);
		} catch (Exception e) {
			e.printStackTrace();
			htmlStr.append("'falure'");
		}
		htmlStr.append(")");
		htmlStr.append("</script>");
		System.out.println(htmlStr.toString());
		HtmlUtil.writerHtml(response, htmlStr.toString());
		
	}
	
	
	
	@Auth(verifyLogin = true, verifyURL = false)
	@RequestMapping("/certify")
	public ModelAndView certify(HttpServletRequest request,HttpServletResponse response,
			Pager p ){
		
		Map<String, Object> context = getRootMap();
		String sessionUserId = SessionUtils.getUserId(request);
		Users user = usersService.getUsersById(sessionUserId);
		context.put("user", user);
		
		return forword("user/user-certify", context);
	}
	
	@Auth(verifyLogin = true, verifyURL = false)
	@RequestMapping("/certify/edit")
	public void certifyEdit(Users user,HttpServletRequest request,HttpServletResponse response,
			Pager p ){
		StringBuffer htmlStr = new StringBuffer();
		htmlStr.append("<script type=\"text/javascript\">");
		htmlStr.append("parent.callback(");
		try {
			String sessionUserId = SessionUtils.getUserId(request);
			user.setCertifyStatus(1) ;//待审核
			user.setUserId(sessionUserId);
			usersService.updateUser(user);
			htmlStr.append("'success'");
			Users sessionuser = usersService.getUsersById(sessionUserId);
			UserInfoSession mSession = new UserInfoSession();
			org.springframework.beans.BeanUtils.copyProperties(sessionuser, mSession);
			UserInfoSession oldSession = SessionUtils.getUser(request);
			mSession.setTruePassword(oldSession.getTruePassword()); //记录真实密码
			SessionUtils.setAttr(request, SessionUtils.SESSION_USER, mSession);
		} catch (Exception e) {
			e.printStackTrace();
			htmlStr.append("'falure'");
		}
		htmlStr.append(")");
		htmlStr.append("</script>");
		System.out.println(htmlStr.toString());
		HtmlUtil.writerHtml(response, htmlStr.toString());
		
	}
	
	/**
	 * 密码修改页面
	 * @param request
	 * @param response
	 * @param p
	 * @return
	 */
	@Auth(verifyLogin = true, verifyURL = false)
	@RequestMapping("/password")
	public ModelAndView password(HttpServletRequest request,HttpServletResponse response ){
		Map<String, Object> context = getRootMap();
//		String sessionUserId = SessionUtils.getUserId(request);
//		Users user = usersService.getUsersById(sessionUserId);
//		context.put("user", user);
		return forword("user/user-updatepwd", context);
	}
	
	/**
	 * 修改密码
	 * @param oldpassword
	 * @param newpassword
	 * @param request
	 * @param response
	 */
	@Auth(verifyLogin = true, verifyURL = false)
	@RequestMapping("/updatePassword")
	public void updatePassword(String oldpassword,String newpassword,HttpServletRequest request,HttpServletResponse response ){
		try {
			String sessionUserId = SessionUtils.getUserId(request);
			Users user = usersService.getUsersById(sessionUserId);
			if (! MethodUtil.MD5(oldpassword).equals(user.getPassword())) {
				this.sendFailureMessage(response, "当前密码错误！");
				return;
			}
			usersService.updateUserPwd(sessionUserId, newpassword)	;
			UserInfoSession oldSession = SessionUtils.getUser(request);
			oldSession.setTruePassword(newpassword); //记录真实密码
			SessionUtils.setAttr(request, SessionUtils.SESSION_USER, oldSession);
		} catch (Exception e) {
		}
		this.sendSuccessMessage(response, "修改成功");
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
	 * 用户回答
	 * @param userId
	 * @param request
	 * @param response
	 * @param p
	 */
	@Auth(verifyLogin = false, verifyURL = false)
	@RequestMapping("/{userId}/answers")
	public ModelAndView userAnswer(@PathVariable String userId,String orderBy,HttpServletRequest request,HttpServletResponse response,
			Pager p ){
		Map<String, Object> context = getRootMap();
		Map<String, Object> map = new HashMap<String, Object>();
		if (orderBy != null && !orderBy.equals("") && orderBy.equals("agreeCount")) {
			orderBy = "agreeCount";
			map.put("Hot", "yes");
		}else{
			orderBy = "new";
			map.put("New", "yes");
		}
		context.put("orderBy", orderBy);
		map.put("publishedUid", userId);
		String sessionUserId = SessionUtils.getUserId(request);
		map.put("userId", sessionUserId);
		
		List<Answer> listAnswer = answerService.listHomePageAnswer(map, p);
		context.put("listAnswer", listAnswer);
		map.put("findUserId", userId);
		Users user = usersService.getUsersByMap(map);
		context.put("user", user);
		return forword("user/user-answer", context);
	}
	
	/**
	 * APP我的回答
	 * @param userId
	 * @param request
	 * @param response
	 * @param p
	 */
	@Auth(verifyLogin = false, verifyURL = false)
	@RequestMapping("/app/answers")
	public void appUserAnswer(HttpServletRequest request,HttpServletResponse response){
		JSONObject params = getAppParams(request);
		Pager p = getAppPager(request);
		String orderBy = params.getString("orderBy");
		String userId = params.getString("userId");
		if(StringUtils.isEmpty(userId)){
			sendFailureMessage(response, "userId不能为空");
			return;
		}
		Map<String, Object> context = getRootMap();
		Map<String, Object> map = new HashMap<String, Object>();
		if (orderBy != null && !orderBy.equals("") && orderBy.equals("agreeCount")) {
			orderBy = "agreeCount";
			map.put("Hot", "yes");
		}else{
			orderBy = "new";
			map.put("New", "yes");
		}
		context.put("orderBy", orderBy);
		map.put("publishedUid", userId);
		String sessionUserId = SessionUtils.getUserId(request);
		map.put("userId", sessionUserId);
		
		List<Answer> listAnswer = answerService.listHomePageAnswer(map, p);
		context.put("listAnswer", listAnswer);
		map.put("findUserId", userId);
		Users user = usersService.getUsersByMap(map);
		context.put("user", user);
		sendSuccessMessage(response, "获取数据成功", context);
	}
	
	/**
	 * 用户案例
	 * @param userId
	 * @param request
	 * @param response
	 * @param p
	 */
	@Auth(verifyLogin = false, verifyURL = false)
	@RequestMapping("/{userId}/cases")
	public ModelAndView userCases(@PathVariable String userId,HttpServletRequest request,HttpServletResponse response,
			Pager p ){
		Map<String, Object> context = getRootMap();
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("publishedUid", userId);
		String sessionUserId = SessionUtils.getUserId(request);
		map.put("userId", sessionUserId);
		List<Cases> listCase = casesService.listCases(map, p);
		context.put("listCase", listCase);
		map.put("findUserId", userId);
		Users user = usersService.getUsersByMap(map);
		context.put("user", user);
		return forword("user/user-case", context);
	}
	
	/**
	 * 用户收藏回答
	 * @param userId
	 * @param request
	 * @param response
	 * @param p
	 */
	@Auth(verifyLogin = false, verifyURL = false)
	@RequestMapping("/{userId}/collectAnswer")
	public ModelAndView userCollectAnswer(@PathVariable String userId,HttpServletRequest request,HttpServletResponse response,
			Pager p ){
		Map<String, Object> context = getRootMap();
		Map<String, Object> map = new HashMap<String, Object>();
		String sessionUserId = SessionUtils.getUserId(request);
		map.put("followUserId", userId);
		map.put("userId", sessionUserId);
		List<Answer> listAnswer = answerService.listHomePageAnswer(map, p);
		context.put("listAnswer", listAnswer);
		map.clear();
		map.put("findUserId", userId);
		Users user = usersService.getUsersByMap(map);
		context.put("user", user);
		return forword("user/follow-answer", context);
	}
	
	/**
	 * app我的收藏
	 * @param userId
	 * @param request
	 * @param response
	 * @param p
	 */
	@Auth(verifyLogin = false, verifyURL = false)
	@RequestMapping("/app/collectAnswer")
	public void appUserCollectAnswer(HttpServletRequest request,HttpServletResponse response){
		JSONObject params = getAppParams(request);
		Pager p = getAppPager(request);
		String userId = params.getString("userId");
		if(StringUtils.isEmpty(userId)){
			sendFailureMessage(response, "userId不能为空");
			return;
		}
		Map<String, Object> context = getRootMap();
		Map<String, Object> map = new HashMap<String, Object>();
		String sessionUserId = SessionUtils.getUserId(request);
		map.put("followUserId", userId);
		map.put("userId", sessionUserId);
		List<Answer> listAnswer = answerService.listHomePageAnswer(map, p);
		context.put("listAnswer", listAnswer);
		map.clear();
		map.put("findUserId", userId);
		Users user = usersService.getUsersByMap(map);
		context.put("user", user);
		sendSuccessMessage(response, "获取数据成功", context);
	}
	
	
	/**
	 * 用户提问
	 * @param userId
	 * @param request
	 * @param response
	 * @param p
	 * @return
	 */
	@Auth(verifyLogin = false, verifyURL = false)
	@RequestMapping("/{userId}/followQuestions")
	public ModelAndView userFollowQuestion(@PathVariable String userId,HttpServletRequest request,HttpServletResponse response,
			Pager p ){
		Map<String, Object> context = getRootMap();
		Map<String, Object> map = new HashMap<String, Object>();
 
		String sessionUserId = SessionUtils.getUserId(request);
		map.put("userId", sessionUserId);
		map.put("followUserId", userId);
		List<Question> listQuestion = questionService.listQuestion(map, p);
		context.put("listQuestion", listQuestion);
		
		return forword("user/user-follow-question", context);
	}
	
	/**
	 * 关注的问题
	 * @param userId
	 * @param request
	 * @param response
	 * @param p
	 * @return
	 */
	@Auth(verifyLogin = false, verifyURL = false)
	@RequestMapping("/ajaxMyFollowQuestions")
	public ModelAndView ajaxMyFollowQuestion(HttpServletRequest request,String userId){
		Map<String, Object> context = getRootMap();
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("followUserId", userId);
		Pager p = new Pager();
		List<Question> listQuestion = questionService.listQuestion(map, p);
		context.put("listQuestion", listQuestion);
		context.put("questionCount", questionService.countFollowQuestion(map));
		map.clear();
		map.put("userId", userId);
		context.put("followCount", usersService.countUserFollow(map));
		map.clear();
		map.put("userFollowedId", userId);
		context.put("followedCount", usersService.countUserFollow(map));
		context.put("userId", userId);
		return forword("ajax/right_follow_question", context);
	}
	
	/**
	 * 关注了
	 * @param userId
	 * @param request
	 * @param response
	 * @param p
	 * @return
	 */
	@Auth(verifyLogin = false, verifyURL = false)
	@RequestMapping("/{userId}/followees")
	public ModelAndView userFollowees(@PathVariable String userId,HttpServletRequest request,HttpServletResponse response,
			Pager p ){
		Map<String, Object> context = getRootMap();
		Map<String, Object> map = new HashMap<String, Object>();
 
		String sessionUserId = SessionUtils.getUserId(request);
		map.put("userId", sessionUserId);
		map.put("followeeUserId", userId);
		List<Users> listUsers = usersService.listUsers(map, p);
		context.put("listUsers", listUsers);
		map.clear();
		map.put("findUserId", userId);
		Users user = usersService.getUsersByMap(map);
		context.put("user", user);
		return forword("user/followees", context);
	}
	
	/**
	 * 被关注了
	 * @param userId
	 * @param request
	 * @param response
	 * @param p
	 * @return
	 */
	@Auth(verifyLogin = false, verifyURL = false)
	@RequestMapping("/{userId}/followers")
	public ModelAndView userFollowers(@PathVariable String userId,HttpServletRequest request,HttpServletResponse response,
			Pager p ){
		Map<String, Object> context = getRootMap();
		Map<String, Object> map = new HashMap<String, Object>();
 
		String sessionUserId = SessionUtils.getUserId(request);
		map.put("userId", sessionUserId);
		map.put("followUserId", userId);
		List<Users> listUsers = usersService.listUsers(map, p);
		context.put("listUsers", listUsers);
		map.clear();
		map.put("findUserId", userId);
		Users user = usersService.getUsersByMap(map);
		context.put("user", user);
		return forword("user/followers", context);
	}
	
	/**
	 * 消息
	 * @param userId
	 * @param request
	 * @param response
	 * @param p
	 * @return
	 */
	@Auth(verifyLogin = true, verifyURL = false)
	@RequestMapping("/notifications")
	public ModelAndView notifications(HttpServletRequest request,HttpServletResponse response,
			Pager p ){
		Map<String, Object> context = getRootMap();
		String sessionUserId = SessionUtils.getUserId(request);
		notificationService.updateReadedFlagTo1(sessionUserId);
		return forword("user/notification", context);
	}
	
	/**
	 * 未读消息
	 * @param request
	 * @param response
	 */
	@Auth(verifyLogin = true, verifyURL = false)
	@RequestMapping("/notReadNotify")
	public void notificationsCount(HttpServletRequest request,HttpServletResponse response)
	{
		int count = 0;
		try {
			String sessionUserId = SessionUtils.getUserId(request);
			count = notificationService.getNotReadCount(sessionUserId);
		} catch (Exception e) {
			this.sendFailureMessage(response, "失败");
		}
		this.sendSuccessMessage(response, "成功",count);
	}
	
	/**
	 * 消息数量
	 * @param userId
	 * @param request
	 * @param response
	 * @param p
	 * @return
	 */
	@Auth(verifyLogin = true, verifyURL = false)
	@RequestMapping("/loadNotifications")
	public void loadNotifications(HttpServletRequest request,HttpServletResponse response,
			Pager p ){
		Map<String, Object> context = getRootMap();
		Map<String, Object> map = new HashMap<String, Object>();
		String sessionUserId = SessionUtils.getUserId(request);
		map.put("userId", sessionUserId);
		p.setPageSize(100);
		List<Notification> listNotification = notificationService.listNotification(map, p);
		context.put("listNotification", listNotification);
		this.sendSuccessMessage(response, "成功",context);
	}
	
	/**
	 * 关注用户
	 * @return
	 */
	@Auth(verifyLogin=true,verifyURL=false)
	@RequestMapping("/followUser")
	public void followUser(String userFollowedId,HttpServletRequest request,HttpServletResponse response) throws Exception{
		
		String userId = SessionUtils.getUserId(request);
		UserFollow userFollow = new UserFollow();
		userFollow.setUserFollowedId(userFollowedId);
		userFollow.setUserId(userId);
		userFollowService.followUser(userFollow);
		this.addNotification(Constant.ACTION_TYPE_FOLLOW, Constant.MODEL_TYPE_PERSON,
				userFollowedId, userId, userFollowedId);
		this.sendSuccessMessage(response, "关注成功");
	}
	
	/**
	 * 取消关注
	 * @return
	 */
	@Auth(verifyLogin=true,verifyURL=false)
	@RequestMapping("/disFollowUser")
	public void disFollowUser(String userFollowedId,HttpServletRequest request,HttpServletResponse response) throws Exception{
		
		String userId = SessionUtils.getUserId(request);
		UserFollow userFollow = new UserFollow();
		userFollow.setUserFollowedId(userFollowedId);
		userFollow.setUserId(userId);
		userFollowService.disFollowUser(userFollow);
		this.sendSuccessMessage(response, "取消关注成功");
	}
	
	/**
	 * 进入问题反馈页面
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@Auth(verifyLogin=true,verifyURL=false)
	@RequestMapping("/feedback")
	public ModelAndView feedback(HttpServletRequest request,HttpServletResponse response) throws Exception{
		Map<String, Object> context = getRootMap();
		return forword("user/feedback", context);
	}
	
	/**
	 * 保存问题反馈
	 * @param content
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@Auth(verifyLogin=true,verifyURL=false)
	@RequestMapping("/saveFeedback")
	public ModelAndView saveFeedback(String content,HttpServletRequest request,HttpServletResponse response) throws Exception{
		Map<String, Object> context = getRootMap();
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("feedback_id", SerialNo.getUNID());
		map.put("content", content);
		map.put("user_id", SessionUtils.getUserId(request));
		map.put("create_time", DateTimeUtil.getNowDateTime());
		usersService.saveFeedback(map);
		return forword("user/feedbackResult", context);
	}
	
	/**
	 * app我的评论
	 * @param userId
	 * @param request
	 * @param response
	 * @param p
	 */
	@Auth(verifyLogin = false, verifyURL = false)
	@RequestMapping("/app/commentAnswer")
	public void appUserCommentAnswer(HttpServletRequest request,HttpServletResponse response){
		JSONObject params = getAppParams(request);
		Pager p = getAppPager(request);
		String userId = params.getString("userId");
		if(StringUtils.isEmpty(userId)){
			sendFailureMessage(response, "userId不能为空");
			return;
		}
		Map<String, Object> context = getRootMap();
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("userId", userId);
		List<AnswerComments> listAnswerComments = answerCommentsService.listAnswerComments(map, p);
		context.put("listAnswerComments", listAnswerComments);
		map.clear();
		map.put("findUserId", userId);
		Users user = usersService.getUsersByMap(map);
		context.put("user", user);
		sendSuccessMessage(response, "获取数据成功", context);
	}
	
}
