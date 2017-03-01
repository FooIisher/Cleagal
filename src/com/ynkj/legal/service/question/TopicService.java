package com.ynkj.legal.service.question;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.ynkj.common.util.Pager;
import com.ynkj.legal.common.dao.SqlDao;
import com.ynkj.legal.model.question.Topic;


@Service("topicService")
public class TopicService {
	@Resource
	private SqlDao sqlDao;
	
	@SuppressWarnings("unchecked")
	public List<Topic> listTopic(Map<String, Object> map,Pager p){
		int rowCount = sqlDao.count("topic.queryCount", map);
		p.setRowCount(rowCount);
		List<Topic> listTopic =sqlDao.list("topic.query", map);
		for (int i = 0; i < listTopic.size(); i++) {
			Topic topic = listTopic.get(i);
			Map<String, Object> childMap = new HashMap<String, Object>();
			childMap.put("parentId", topic.getTopicId());
			topic.setListTopics( sqlDao.list("topic.query", childMap));
			listTopic.set(i, topic);
		}
		return listTopic;
	}
	
	@SuppressWarnings("unchecked")
	public List<Topic> listTopicSimple(Map<String, Object> map){
		return sqlDao.list("topic.query", map);
	}
	
	@SuppressWarnings("unchecked")
	public List<Topic> listTopicIdByUserId(String userId){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("userId", userId);
		return sqlDao.list("topic.queryTopicsByUserId", map);
	}
	
	public Topic queryTopicById(String topicId){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("topicId", topicId);
		return (Topic)sqlDao.view("topic.query", map);
	}
	
	/**
	 * 查询工具所需地区
	 * @param map
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<Map<String, Object>> queryArea(Map<String, Object> map){
		return sqlDao.list("topic.queryArea", map);
	}
	
}
