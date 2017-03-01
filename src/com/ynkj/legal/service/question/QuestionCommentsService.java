package com.ynkj.legal.service.question;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.ynkj.legal.common.dao.SqlDao;
import com.ynkj.legal.model.question.QuestionComments;


@Service("questionCommentsService")
public class QuestionCommentsService {
	
	@Resource
	public QuestionService questionService;
	@Resource
	private SqlDao sqlDao;
	
	
	public void addQuestionComment(QuestionComments questionComments){
		sqlDao.create("questionComments.insert", questionComments);
		questionService.updateQuestionCommentCount(questionComments.getQuestionId());
	}
	
	
}
