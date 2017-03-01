package com.ynkj.legal.model.question;

public class QuestionComments {
	
	private String commentsId;
	private String questionId;
	private String uid;
	private String replyUid;
	private String message;
	private String addTime;
	private Integer verifyStatus;
	private String auditTime;
	private Integer deleteFlag;
	
	
	public String getCommentsId() {
		return commentsId;
	}
	public void setCommentsId(String commentsId) {
		this.commentsId = commentsId;
	}
	
	public String getQuestionId() {
		return questionId;
	}
	public void setQuestionId(String questionId) {
		this.questionId = questionId;
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
