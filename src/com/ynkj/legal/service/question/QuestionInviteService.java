package com.ynkj.legal.service.question;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.ynkj.common.util.Pager;
import com.ynkj.legal.common.dao.SqlDao;
import com.ynkj.legal.model.question.QuestionInvite;

@Service("questionInviteService")
public class QuestionInviteService {
	
	@Resource
	private SqlDao sqlDao;
	
	public void addQuestionInvite(QuestionInvite questionInvite){
		sqlDao.create("questionInvite.insert", questionInvite);
		//questionService.updateQuestionCommentCount(questionComments.getQuestionId());
	}
	
	public List<QuestionInvite> listQuestionInvite(Map<String, Object> map,Pager page){
		
		return sqlDao.pageList("questionInvite.queryQuestionInvite", map,
				page.getPageOffset(), page.getPageSize());
	}
	
	public int getInviteCount(Map<String, Object> map){
		return sqlDao.count("questionInvite.getInviteCount",map);
	}
}
