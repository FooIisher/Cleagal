package com.ynkj.legal.service.cases;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
 
import com.ynkj.common.util.Pager;
import com.ynkj.legal.common.dao.SqlDao;
import com.ynkj.legal.model.cases.Cases;
 
@Service("casesService")
public class CasesService {
	
	@Resource
	private SqlDao sqlDao;
	
	
	
	@SuppressWarnings("unchecked")
	public List<HashMap<String, Object>> simpleSearch(Map<String, Object> map,Pager page){
		return sqlDao.pageList("cases.simpleSearch", map,page.getPageOffset(),page.getPageSize());
	}
	
	/**
	 * 查看次数
	 */
	public void addViewCount(String caseId){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("caseId", caseId);
		sqlDao.update("cases.addViewCount", map);
	}
	
	/**
	 * 添加案例
	 * @param cases
	 * @param topicIds 
	 */
	public void addCases(Cases cases,String[] topicIds){
		sqlDao.create("cases.insert",cases);
		if (topicIds != null) {
			for (int i = 0; i < topicIds.length; i++) {
				String topicId = topicIds[i];
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("caseId", cases.getCaseId());
				map.put("topicId", topicId.trim());
				sqlDao.create("cases.insertCasesTopic", map);
			}
		}
		
	}
	
	@SuppressWarnings("unchecked")
	public List<Cases> listCases(Map<String, Object> map,Pager p){
		
		return sqlDao.pageList("cases.listCases", map, 
				(p.getPageId()-1)*p.getPageSize(), p.getPageSize());
	}
	
	@SuppressWarnings("unchecked")
	public List<Cases> listCasesByParentTopicId(Map<String, Object> map,Pager pager){
		return sqlDao.pageList("cases.listCases", map, 
				(pager.getPageId()-1)*pager.getPageSize(), pager.getPageSize());
	}
	
	public Cases detailCaseId(Map<String, Object> map){
		 
		return (Cases)sqlDao.view("cases.listCases", map);
	}
	
	public void updateCaseFollowCount(String caseId){
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("caseId", caseId);
		sqlDao.update("cases.updateCaseFollowCount", map);
	}
	
	public void udpateCaseCommentCount(String caseId){
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("caseId", caseId);
		sqlDao.update("cases.updateCaseCommentCount", map);
	}
	
	

}
