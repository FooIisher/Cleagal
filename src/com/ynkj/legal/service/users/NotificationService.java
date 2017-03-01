package com.ynkj.legal.service.users;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.ynkj.common.util.DateTimeUtil;
import com.ynkj.common.util.Pager;
import com.ynkj.common.util.SerialNo;
import com.ynkj.legal.common.dao.SqlDao;
import com.ynkj.legal.model.users.Notification;

@Service("notificationService")
public class NotificationService {
	
	@Resource
	private SqlDao sqlDao;
	
	/**
	 * 
	 * @param actionType 操作
	 * @param modelType 推向
	 * @param sourceId 关联id
	 * @param senderUid 操作用户
	 * @param recipientUid 对象用户
	 */
	public void addNotification(int actionType,int modelType,String sourceId
			,String senderUid,String recipientUid){
		Notification notification = new Notification();
		notification.setActionType(actionType);
		notification.setModelType(modelType);
		notification.setSourceId(sourceId);
		notification.setSenderUid(senderUid);
		notification.setRecipientUid(recipientUid);
		notification.setReadFlag(0);//未读
		notification.setAddTime(DateTimeUtil.getNowDateTime());
		notification.setNotificationId(SerialNo.getUNID());
		sqlDao.create("notification.insert", notification);
	}
	
	/**
	 * sadasd
	 * @param map
	 * @return
	 */
	public List<Notification> listNotification(Map<String, Object> map,Pager p){
		
		return (List<Notification>)sqlDao.pageList("notification.getUserNotification", map, p.getPageOffset(),  p.getPageSize());
		
	}
	
	public int getNotReadCount(String userId){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("userId", userId);
		return sqlDao.count("notification.queryCount", map);
	}
	
	public void updateReadedFlagTo1(String userId){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("userId", userId);
		sqlDao.update("notification.updateReadedFlagTo1", map);
		
	}
	

}
