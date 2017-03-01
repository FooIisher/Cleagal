package com.ynkj.legal.model.users;

public class UserActionHistory {
	
	private String historyId;
	private String uid;
	private Integer associateType;
	private String associateId;
	private Integer associateAction;
	private String addTime;
	private Integer anonymous;
	
	
	
	
 
	public String getHistoryId() {
		return historyId;
	}
	public void setHistoryId(String historyId) {
		this.historyId = historyId;
	}
	public String getUid() {
		return uid;
	}
	public void setUid(String uid) {
		this.uid = uid;
	}
 
	public String getAssociateId() {
		return associateId;
	}
	public void setAssociateId(String associateId) {
		this.associateId = associateId;
	}
	public String getAddTime() {
		return addTime;
	}
	public void setAddTime(String addTime) {
		this.addTime = addTime;
	}
	public Integer getAssociateType() {
		return associateType;
	}
	public void setAssociateType(Integer associateType) {
		this.associateType = associateType;
	}
	public Integer getAssociateAction() {
		return associateAction;
	}
	public void setAssociateAction(Integer associateAction) {
		this.associateAction = associateAction;
	}
	public Integer getAnonymous() {
		return anonymous;
	}
	public void setAnonymous(Integer anonymous) {
		this.anonymous = anonymous;
	}
 
	
	

}
