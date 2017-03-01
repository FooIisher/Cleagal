package com.ynkj.legal.service.users;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.ynkj.common.util.Blowfish;
import com.ynkj.common.util.DateTimeUtil;
import com.ynkj.common.util.MethodUtil;
import com.ynkj.common.util.Pager;
import com.ynkj.legal.common.dao.SqlDao;
import com.ynkj.legal.model.users.UserActionHistory;
import com.ynkj.legal.model.users.Users;
import com.ynkj.legal.service.question.TopicService;


@Service("usersService")
public class UsersService {
	@Resource
	private SqlDao sqlDao;
	
	@Resource
	private TopicService topicService;
	 
	
	@SuppressWarnings("unchecked")
	public List<HashMap<String, Object>> simpleSearch(Map<String, Object> map,Pager page){
		return sqlDao.pageList("users.simpleSearch", map,page.getPageOffset(),page.getPageSize());
	}
	
	@SuppressWarnings("unchecked")
	public List<Users> listUsers(Map<String, Object> map,Pager p){
		int rowCount = sqlDao.count("users.queryCount", map);
		p.setRowCount(rowCount);
		return sqlDao.pageList("users.queryUserList", map, (p.getPageId() - 1) * p.getPageSize(),p.getPageSize());
	}
	
	
	/**
	 * 手机号找用户
	 * @param mobile
	 * @return
	 */
	public Users getUserByMobile(String mobile){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("mobile", mobile);
		return (Users) sqlDao.view("users.queryUserList", map);
	}
	
	/**
	 * 修改用户
	 * @param user
	 */
	public void updateUser(Users user){
		
		sqlDao.update("users.update", user);
	}
	
	public void updateUserPwd(String userId , String password){
		Users user = new Users();
		user.setUserId(userId);
		user.setPassword(MethodUtil.MD5(password));
		this.updateUser(user);
		
		HashMap<String, Object> hashMap = new HashMap<String, Object>();
		hashMap.put("username", userId);
		Blowfish fish = new Blowfish(Blowfish.passwordKey);
		String pwd = fish.encryptString(password);
		hashMap.put("encryptedPassword", pwd);
		sqlDao.update("users.updateOfUser", hashMap);
	}
	
	public Users getUsersByMap(Map<String, Object> map){
		return (Users) sqlDao.view("users.queryUserList", map);
	}
	
	public Users getUsersById(String findUserId){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("findUserId", findUserId);
		return (Users) sqlDao.view("users.queryUserList", map);
	}
	
	public List<Users> listUsersRecommend(Map<String, Object> map,Pager pager){
		map.put("recommendFlag", 1); 
		List<Users> listUsers = sqlDao.pageList("users.queryUserList", map, pager.getPageOffset(), pager.getPageSize());
		for (int i = 0; i < listUsers.size(); i++) {
			Users users = listUsers.get(i);
			users.setListTopic(topicService.listTopicIdByUserId(users.getUserId()));
			StringBuffer str = new StringBuffer();
			for (int j = 0; j < users.getListTopic().size(); j++) {
				str.append("、"+users.getListTopic().get(j).getTopicTitle());
			}
			users.setAdeptTagName(str.toString().replaceFirst("、", ""));
			listUsers.set(i, users);
		}
		return listUsers;
	}
	
	public List<Users> listUsersCertified(Map<String, Object> map,Pager pager) {
		map.put("certifyStatus", 2); 
		List<Users> listUsers = sqlDao.pageList("users.queryUserList", map, pager.getPageOffset(), pager.getPageSize());
		return listUsers;
		
	}
	

	
	public Integer getUserByParams(String value,String params){
		 HashMap<String, Object> hashmap = new HashMap<String, Object>();
		 hashmap.put("params", params);
		 hashmap.put("value", value);
		 return sqlDao.count("users.getUsersByParams", hashmap); 
	}
	
	public synchronized void saveUsers(Users user){
		
		//openfire 密码
		Blowfish fish = new Blowfish(Blowfish.passwordKey);
		String password = fish.encryptString(user.getPassword());
		
		user.setPassword(MethodUtil.MD5(user.getPassword()));
		user.setRegTime(DateTimeUtil.getNowDateTime());
		user.setAgreeCount(0);
		user.setDeleteFlag(0);
		user.setFansCount(0);
		user.setForbidden(0);
		user.setUserId(sqlDao.count("users.getMaxUserId",null)+1+"");
		user.setSex(-1);
		user.setQuestionCount(0);
		user.setAnswerCount(0);
		user.setCaseCount(0);
		user.setInviteCount(0);
		user.setRecommendFlag(0);
		user.setInvitationAvailable(0);
		
		user.setCertifyStatus(0);
		user.setWinCount(0);
		user.setLawsuitCount(0);
		sqlDao.create("users.insert", user);
		
		
		
		
		//保存用户到openfire
		HashMap<String, Object> hashMap = new HashMap<String, Object>();
		hashMap.put("username", user.getUserId());
		hashMap.put("encryptedPassword", password);
		hashMap.put("creationDate", "0");
		hashMap.put("modificationDate", "0");
		sqlDao.create("users.saveOfUser", hashMap);
	}
	
	public Users getUsersByLogin(String mobile,String password){
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("mobile", mobile);
		map.put("password", password);
		return (Users) sqlDao.view("users.getUsersByLogin", map);
	}
	
	public void updateUserAgreeCount(String userId){
		Users users = new Users();
		users.setUserId(userId);
		sqlDao.update("users.updateUserAgreeCount", users);
	}
	
	public void updateUserFansCount(String userId){
		Users users = new Users();
		users.setUserId(userId);
		sqlDao.update("users.updateUserFansCount", users);
	}
	
	@SuppressWarnings("unchecked")
	public List<Map<String, Object>> qeuryUserActionHistory(Map<String, Object> map,Pager p){
		int rowCount = sqlDao.count("users.countUserActionHistory",map);
		p.setRowCount(rowCount);
		return sqlDao.pageList("users.queryUserActionHistory", map, p.getPageOffset(),p.getPageSize());
	}
	
	/**
	 * 用户关注数量
	 * @param map（userId 关注的数量,userFollowedId 被关注的数量）
	 * @return
	 */
	public int countUserFollow(Map<String, Object> map){
		return sqlDao.count("users.countUserFollow",map);
	}
	
	public void saveUserHistoryAction(UserActionHistory userActionHistory){
		
		sqlDao.create("userActionHistory.insert", userActionHistory);
		
	}
	public int saveFeedback(Map<String, Object> map){
		return sqlDao.create("users.saveFeedback", map);
	}
	
	@SuppressWarnings("unchecked")
	public List<Map<String, Object>> queryCityList(Map<String, Object> map){
		return sqlDao.list("users.queryCityList", map);
	}
	
	@SuppressWarnings("unchecked")
	public List<Map<String, Object>> querySmsPhoneList(Map<String, Object> map){
		return sqlDao.list("users.querySmsPhoneList", map);
	}
	
	@SuppressWarnings("unchecked")
	public List<Users> listUsers(Map<String, Object> map){
		return sqlDao.list("users.queryUserList", map);
	}
	
}
