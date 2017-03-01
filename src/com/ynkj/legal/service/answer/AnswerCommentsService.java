package com.ynkj.legal.service.answer;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.ynkj.common.util.Pager;
import com.ynkj.legal.common.dao.SqlDao;
import com.ynkj.legal.model.answer.AnswerComments;


@Service("answerCommentsService")
public class AnswerCommentsService {
	
	@Resource
	public AnswerService answerService;
	@Resource
	private SqlDao sqlDao;
	
	
	public void addAnswerComment(AnswerComments answerComments){
		sqlDao.create("answerComments.insert", answerComments);
		if(answerComments.getVerifyStatus()==1)
			answerService.updateAnswerCommentCount(answerComments.getAnswerId());
	}
	
	@SuppressWarnings("unchecked")
	public List<AnswerComments> listAnswerComments(Map<String, Object> map,Pager p){
		int rowCount = sqlDao.count("answerComments.countAnswerComments",map);
		p.setRowCount(rowCount);
		return sqlDao.pageList("answerComments.queryAnswerComments", map, p.getPageOffset(), p.getPageSize());
	}
	
}
