package com.ynkj.legal.service.question;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;



import com.ynkj.common.util.DateTimeUtil;
import com.ynkj.legal.common.dao.SqlDao;
import com.ynkj.legal.model.question.UserFollowQuestion;


@Service("userFollowQuestionService")
public class UserFollowQuestionService {
	
	
	@Resource
	private SqlDao sqlDao;
	
	@Resource
	private QuestionService questionService;
	
	public void followQuestion(UserFollowQuestion userFollowQuestion){
		UserFollowQuestion exitFollowQuestion = (UserFollowQuestion)sqlDao
		.view("userFollowQuestion.query", userFollowQuestion.clone());
		userFollowQuestion.setAddTime(DateTimeUtil.getNowDateTime());
		userFollowQuestion.setStatus(0);
		if (exitFollowQuestion == null) {
			sqlDao.create("userFollowQuestion.insert", userFollowQuestion);
		}else{
			sqlDao.update("userFollowQuestion.update", userFollowQuestion);
		}
		questionService.updateQuestionFollowCount(userFollowQuestion.getQuestionId());
	}
	
	public void disFollowQuestion(UserFollowQuestion userFollowQuestion){
		
		userFollowQuestion.setStatus(1);
		userFollowQuestion.setAddTime(DateTimeUtil.getNowDateTime());
		sqlDao.update("userFollowQuestion.update", userFollowQuestion);
		questionService.updateQuestionFollowCount(userFollowQuestion.getQuestionId());
	}

}
