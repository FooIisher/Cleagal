package com.ynkj.legal.model.cases;

public class CasesComments {
	
	private String commentsId;
	private String caseId;
	private String uid;
	private String replyUid;
	private String message;
	private String addTime;
	private Integer verifyStatus;
	private String auditTime;
	private Integer deleteFlag;
	
	private String userName;
	private String replyUserName;
	private String avatarFile;
	
	
	
	
	
	public String getAvatarFile() {
		return avatarFile;
	}
	public void setAvatarFile(String avatarFile) {
		this.avatarFile = avatarFile;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getReplyUserName() {
		return replyUserName;
	}
	public void setReplyUserName(String replyUserName) {
		this.replyUserName = replyUserName;
	}
	public String getCommentsId() {
		return commentsId;
	}
	public void setCommentsId(String commentsId) {
		this.commentsId = commentsId;
	}
	
	
	
	
	public String getCaseId() {
		return caseId;
	}
	public void setCaseId(String caseId) {
		this.caseId = caseId;
	}
	public String getUid() {
		return uid;
	}
	public void setUid(String uid) {
		this.uid = uid;
	}
	public String getReplyUid() {
		return replyUid;
	}
	public void setReplyUid(String replyUid) {
		this.replyUid = replyUid;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getAddTime() {
		return addTime;
	}
	public void setAddTime(String addTime) {
		this.addTime = addTime;
	}
	public Integer getVerifyStatus() {
		return verifyStatus;
	}
	public void setVerifyStatus(Integer verifyStatus) {
		this.verifyStatus = verifyStatus;
	}
	public String getAuditTime() {
		return auditTime;
	}
	public void setAuditTime(String auditTime) {
		this.auditTime = auditTime;
	}
	public Integer getDeleteFlag() {
		return deleteFlag;
	}
	public void setDeleteFlag(Integer deleteFlag) {
		this.deleteFlag = deleteFlag;
	}
	
	
	

}
