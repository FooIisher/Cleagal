package com.ynkj.legal.service.comm;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.ynkj.legal.common.dao.SqlDao;
import com.ynkj.legal.model.comm.Job;

@Service("jobService")
public class JobService {
	
	@Resource
	private SqlDao sqlDao;
	
	
	public List<Job> listJob(){
		
		return sqlDao.list("job.listJob", new HashMap<String, Object>());
	}

}
