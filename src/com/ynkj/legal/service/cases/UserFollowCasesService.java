package com.ynkj.legal.service.cases;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.ynkj.common.util.DateTimeUtil;
import com.ynkj.legal.common.dao.SqlDao;
import com.ynkj.legal.model.cases.UserFollowCases;


@Service("userFollowCasesService")
public class UserFollowCasesService {
	
	@Resource
	private SqlDao sqlDao;
	
	@Resource
	CasesService casesService;
	
	public void followCase(UserFollowCases userFollowCases){
		UserFollowCases exitUserFollowCases = (UserFollowCases)sqlDao.view("userFollowCases.query", 
				userFollowCases.clone());
		
		userFollowCases.setAddTime(DateTimeUtil.getNowDateTime());
		userFollowCases.setStatus(0);
		if (exitUserFollowCases == null) {
			sqlDao.create("userFollowCases.insert", userFollowCases);
		}else{
			sqlDao.update("userFollowCases.update", userFollowCases);
		}
		casesService.updateCaseFollowCount(userFollowCases.getCaseId());
	}
	
	public void disFollowCase(UserFollowCases userFollowCases){
		
		userFollowCases.setStatus(1);
		userFollowCases.setAddTime(DateTimeUtil.getNowDateTime());
		sqlDao.update("userFollowCases.update", userFollowCases);
		casesService.updateCaseFollowCount(userFollowCases.getCaseId());
		
	}

}
