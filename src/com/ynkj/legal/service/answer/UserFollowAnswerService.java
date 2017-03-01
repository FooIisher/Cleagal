package com.ynkj.legal.service.answer;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.ynkj.common.util.DateTimeUtil;
import com.ynkj.legal.common.dao.SqlDao;
import com.ynkj.legal.model.answer.UserFollowAnswer;


@Service("userFollowAnswerService")
public class UserFollowAnswerService {
	
	@Resource
	public AnswerService answerService;
	
	@Resource
	private SqlDao sqlDao;
	
	public void followAnswer(UserFollowAnswer userFollowAnswer){
		UserFollowAnswer exitUserFollow = (UserFollowAnswer)sqlDao.view("userFollowAnswer.query", 
				userFollowAnswer.clone());
		userFollowAnswer.setStatus(0);
		userFollowAnswer.setAddTime(DateTimeUtil.getNowDateTime());
		if (exitUserFollow == null) {
			sqlDao.create("userFollowAnswer.insert", userFollowAnswer);
		}else{
			sqlDao.update("userFollowAnswer.update", userFollowAnswer);
		}
		answerService.updateAnswerFollowCount(userFollowAnswer.getAnswerId());
	}
	
	public void unFollowAnswer(UserFollowAnswer userFollowAnswer){
		userFollowAnswer.setStatus(1);
		userFollowAnswer.setAddTime(DateTimeUtil.getNowDateTime());
		sqlDao.update("userFollowAnswer.update", userFollowAnswer);
		answerService.updateAnswerFollowCount(userFollowAnswer.getAnswerId());
	}
	
	

}
