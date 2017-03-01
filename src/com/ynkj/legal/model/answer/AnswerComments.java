package com.ynkj.legal.model.answer;

public class AnswerComments {
	
	private String commentsId;
	private String answerId;
	private String uid;
	private String replyUid;
	private String message;
	private String addTime;
	private Integer verifyStatus;
	private String auditTime;
	private Integer deleteFlag;
	
	//用户属性
	private String userName;//昵称
	private String avatarFile;//头像
	private String certifyStatus;//认证状态
	
	//答案属性
	private String answerContent;//答案内容
	
	
	
	public String getAnswerContent() {
		return answerContent;
	}
	public void setAnswerContent(String answerContent) {
		this.answerContent = answerContent;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getAvatarFile() {
		return avatarFile;
	}
	public void setAvatarFile(String avatarFile) {
		this.avatarFile = avatarFile;
	}
	public String getCertifyStatus() {
		return certifyStatus;
	}
	public void setCertifyStatus(String certifyStatus) {
		this.certifyStatus = certifyStatus;
	}
	public String getCommentsId() {
		return commentsId;
	}
	public void setCommentsId(String commentsId) {
		this.commentsId = commentsId;
	}
	public String getAnswerId() {
		return answerId;
	}
	public void setAnswerId(String answerId) {
		this.answerId = answerId;
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
