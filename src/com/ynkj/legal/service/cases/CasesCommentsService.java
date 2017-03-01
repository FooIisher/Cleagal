package com.ynkj.legal.service.cases;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.ynkj.common.util.Pager;
import com.ynkj.legal.common.dao.SqlDao;
import com.ynkj.legal.model.cases.CasesComments;


@Service("casesCommentsService")
public class CasesCommentsService {
	
	@Resource
	public CasesService casesService;

	@Resource
	private SqlDao sqlDao;
	
	
	public void addCasesComment(CasesComments casesComments){
		sqlDao.create("casesComments.insert", casesComments);
		casesService.udpateCaseCommentCount(casesComments.getCaseId());
	}
	
	
	public List<CasesComments> listCasesComments(Map<String, Object> map,Pager p){
		
		return sqlDao.pageList("casesComments.listCasesComments", map, p.getPageOffset(), p.getPageSize());
	}
	
	
}
