package com.ynkj.legal.service.users;

import java.util.HashMap;
import java.util.Map;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.ynkj.common.util.Pager;
import com.ynkj.legal.common.dao.SqlDao;
import com.ynkj.legal.model.users.Type;

@Service("typeService")
public class TypeService {
	
	@Resource
	private SqlDao sqlDao;
	
	@SuppressWarnings("unchecked")
	public List<Type> listType(Map<String, Object>map,Pager p){
		
		int rowCount = sqlDao.count("type.queryCount",map);
		p.setRowCount(rowCount);
		List<Type> listType= sqlDao.list("type.query",map);

		for(int i = 0;i < listType.size();i++){
			Type type = listType.get(i);
			Map<String, Object> childMap = new HashMap<String, Object>();
			childMap.put("parentId", type.getTypeId());
			type.setListTypes(sqlDao.list("type.query", childMap));
			listType.set(i, type);
		}
		
		return listType;
	}
	
	public Type queryTypeById(String typeId){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("typeId", typeId);
		return (Type) sqlDao.view("type.query", map);
	}

}
