package com.ynkj.legal.service.answer;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.ynkj.common.util.DateTimeUtil;
import com.ynkj.common.util.Pager;
import com.ynkj.common.util.SerialNo;
import com.ynkj.legal.common.dao.SqlDao;
import com.ynkj.legal.model.answer.Answer;

@Service("answerService")
public class AnswerService {
	
	@Resource
	private SqlDao sqlDao;
	
	
	public void addViewCount(String answerId){
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("answerId", answerId);
		sqlDao.update("answer.addViewCount", map);
	}
	
	public Answer getAnswerById(String answerId){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("answerId", answerId);
		return (Answer)sqlDao.view("answer.getHomepageAnswer", map);
	}
	
	@SuppressWarnings("unchecked")
	public List<Answer> listHomePageAnswer(Map<String, Object> map,Pager page){
		int rowCount = sqlDao.count("answer.countHomepageAnswer",map);
		page.setRowCount(rowCount);
		List<Answer> list = sqlDao.pageList("answer.getHomepageAnswer", map, page.getPageOffset(), page.getPageSize());
		return list;
	}
	
	public void updateAnswerFollowCount(String answerId){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("answerId", answerId);
		sqlDao.update("answer.updateAnswerFollowCount", map);
	}
	
	public void updateAnswerCommentCount(String answerId){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("answerId", answerId);
		sqlDao.update("answer.updateAnswerCommentCount", map);
	}
	
	public void updateAnswerPraiseCount(String answerId){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("answerId", answerId);
		sqlDao.update("answer.updateAnswerPraiseCount", map);
	}

	@SuppressWarnings("unchecked")
	public List<Map<String, Object>> queryUserPraiseAnswer(Map<String, Object> map,Pager p){
		int rowCount = sqlDao.count("answer.countUserPraiseAnswer",map);
		p.setRowCount(rowCount);
		return sqlDao.pageList("answer.queryUserPraiseAnswer", map, p.getPageOffset(),p.getPageSize());
	}
	
	public int saveAnswer(Answer answer){
		answer.setAnswerId(SerialNo.getSmallUNID());
		answer.setAddTime(DateTimeUtil.getNowDateTime());
		return sqlDao.create("answer.saveAnswer", answer);
	}
}
