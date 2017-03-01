package com.ynkj.legal.service.question;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.ynkj.common.util.DateTimeUtil;
import com.ynkj.common.util.Pager;
import com.ynkj.legal.common.dao.SqlDao;
import com.ynkj.legal.model.question.Question;


@Service("QuestionService")
public class QuestionService {
	@Resource
	private SqlDao sqlDao;
	
	
	public void addViewCount(String questionId){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("questionId", questionId);
		sqlDao.update("question.addViewCount", map);
	}
	
	@SuppressWarnings("unchecked")
	public List<HashMap<String, Object>> simpleSearch(Map<String, Object> map,Pager page){
		return sqlDao.pageList("question.simpleSearch", map,page.getPageOffset(),page.getPageSize());
	}
	
	@SuppressWarnings("unchecked")
	public List<Question> listQuestion(Map<String, Object> map,Pager page){
		int rowCount = sqlDao.count("question.countQuestion",map);
		page.setRowCount(rowCount);
		return sqlDao.pageList("question.queryQuestion", map,page.getPageOffset(),page.getPageSize());
	}
 
	/**
	 * 用户关注问题数量
	 * @param map
	 * @return
	 */
	public int countFollowQuestion(Map<String, Object> map){
		return sqlDao.count("question.countFollowQuestion",map);
	}
	
	public Question getQuestionById(Map<String, Object> map){
 
		return (Question)sqlDao.view("question.queryQuestion", map);
	}
	
	public void updateQuestionFollowCount(String questionId){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("questionId", questionId);
		sqlDao.update("question.updateQuestionFollowCount", map);
	}
	
	public void updateQuestionCommentCount(String questionId){
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("questionId", questionId);
		sqlDao.update("question.updateQuestionCommentCount", map);
		
	}
	
	@SuppressWarnings("unchecked")
	public List<Map<String, Object>> queryQuestionTopic(Map<String, Object> map){
		return sqlDao.list("question.queryQuestionTopic", map);
	}
	
	public int saveQuestion(Question question,String[] topicIds){
		question.setAddTime(DateTimeUtil.getNowDateTime());
		question.setUnverifiedModify(question.getQuestionContent());
		int rowResult = sqlDao.create("question.saveQuestion", question);
		if(rowResult > 0 && topicIds != null){
			for (int i = 0; i < topicIds.length; i++) {
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("topicId", topicIds[i]);
				map.put("questionId", question.getQuestionId());
				sqlDao.create("question.saveQuestionTopic", map);
			}
		}
		return rowResult;
	}
}
