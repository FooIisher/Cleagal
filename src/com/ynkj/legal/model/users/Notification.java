package com.ynkj.legal.model.users;

public class Notification {
	
	private String notificationId;
	private String senderUid;
	private String recipientUid;
	private Integer actionType;
	private Integer modelType;
	private String sourceId;
	private String addTime;
	private Integer readFlag;
	private String notifyContent;
	private String userName;
	public String getNotificationId() {
		return notificationId;
	}
	public void setNotificationId(String notificationId) {
		this.notificationId = notificationId;
	}
	public String getSenderUid() {
		return senderUid;
	}
	public void setSenderUid(String senderUid) {
		this.senderUid = senderUid;
	}
	public String getRecipientUid() {
		return recipientUid;
	}
	public void setRecipientUid(String recipientUid) {
		this.recipientUid = recipientUid;
	}
	public Integer getActionType() {
		return actionType;
	}
	public void setActionType(Integer actionType) {
		this.actionType = actionType;
	}
	public Integer getModelType() {
		return modelType;
	}
	public void setModelType(Integer modelType) {
		this.modelType = modelType;
	}
	public String getSourceId() {
		return sourceId;
	}
	public void setSourceId(String sourceId) {
		this.sourceId = sourceId;
	}
	public String getAddTime() {
		return addTime;
	}
	public void setAddTime(String addTime) {
		this.addTime = addTime;
	}
	public Integer getReadFlag() {
		return readFlag;
	}
	public void setReadFlag(Integer readFlag) {
		this.readFlag = readFlag;
	}
	public String getNotifyContent() {
		return notifyContent;
	}
	public void setNotifyContent(String notifyContent) {
		this.notifyContent = notifyContent;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	
	
	
	
	
	

}
