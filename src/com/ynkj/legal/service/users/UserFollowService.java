package com.ynkj.legal.service.users;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;



import com.ynkj.common.util.DateTimeUtil;
import com.ynkj.legal.common.dao.SqlDao;
import com.ynkj.legal.model.users.UserFollow;


@Service("userFollowService")
public class UserFollowService {
	
	
	@Resource
	private SqlDao sqlDao;
	
	@Resource
	private UsersService usersService;
	

	
	public void followUser(UserFollow userFollow){
		UserFollow exitUserFollow = (UserFollow)sqlDao
		.view("userFollow.query",userFollow);
		userFollow.setAddTime(DateTimeUtil.getNowDateTime());
		userFollow.setStatus(0);
		if (exitUserFollow == null) {
			sqlDao.create("userFollow.insert", userFollow);
		}else{
			sqlDao.update("userFollow.update", userFollow);
		}
		usersService.updateUserFansCount(userFollow.getUserFollowedId());
	}
	
	public void disFollowUser(UserFollow userFollow){
		
		userFollow.setStatus(1);
		userFollow.setAddTime(DateTimeUtil.getNowDateTime());
		sqlDao.update("userFollow.update", userFollow);
		usersService.updateUserFansCount(userFollow.getUserFollowedId());
	}

}
