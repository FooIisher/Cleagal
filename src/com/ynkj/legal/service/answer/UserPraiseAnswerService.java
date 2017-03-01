package com.ynkj.legal.service.answer;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.ynkj.common.util.DateTimeUtil;
import com.ynkj.legal.common.dao.SqlDao;
import com.ynkj.legal.model.answer.UserPraiseAnswer;
import com.ynkj.legal.service.users.UsersService;


@Service("userPraiseAnswerService")
public class UserPraiseAnswerService {
	
	@Resource
	private SqlDao sqlDao;
	@Resource
	public AnswerService answerService;
	@Resource
	public UsersService usersService;
	
	public void praiseAnswer(UserPraiseAnswer userPraiseAnswer){
		
		userPraiseAnswer.setAddTime(DateTimeUtil.getNowDateTime());
		sqlDao.create("userPraiseAnswer.insert", userPraiseAnswer);
		usersService.updateUserAgreeCount(userPraiseAnswer.getUserId());
		answerService.updateAnswerPraiseCount(userPraiseAnswer.getAnswerId());
		
	}
	
	public void disPraiseAnswer(UserPraiseAnswer userPraiseAnswer){
		sqlDao.delete("userPraiseAnswer.delete", userPraiseAnswer);
		usersService.updateUserAgreeCount(userPraiseAnswer.getUserId());
		answerService.updateAnswerPraiseCount(userPraiseAnswer.getAnswerId());
	}

}
