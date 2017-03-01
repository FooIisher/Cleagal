package com.ynkj.legal.model.question;

public class QuestionInvite {
	
	private String questionInviteId;
	private String questionId;
	private String senderUid;
	private String recipientsUid;
	private String addTime;
	private String availableTime;
	
	private String questionTitle;
	private String senderUser;
	private String followCount;
	private String answerCount;
	
	
	
	public String getQuestionTitle() {
		return questionTitle;
	}
	public void setQuestionTitle(String questionTitle) {
		this.questionTitle = questionTitle;
	}
	public String getSenderUser() {
		return senderUser;
	}
	public void setSenderUser(String senderUser) {
		this.senderUser = senderUser;
	}
	public String getFollowCount() {
		return followCount;
	}
	public void setFollowCount(String followCount) {
		this.followCount = followCount;
	}
	public String getAnswerCount() {
		return answerCount;
	}
	public void setAnswerCount(String answerCount) {
		this.answerCount = answerCount;
	}
	public String getQuestionInviteId() {
		return questionInviteId;
	}
	public void setQuestionInviteId(String questionInviteId) {
		this.questionInviteId = questionInviteId;
	}
	public String getQuestionId() {
		return questionId;
	}
	public void setQuestionId(String questionId) {
		this.questionId = questionId;
	}
	public String getSenderUid() {
		return senderUid;
	}
	public void setSenderUid(String senderUid) {
		this.senderUid = senderUid;
	}
	public String getRecipientsUid() {
		return recipientsUid;
	}
	public void setRecipientsUid(String recipientsUid) {
		this.recipientsUid = recipientsUid;
	}
	public String getAddTime() {
		return addTime;
	}
	public void setAddTime(String addTime) {
		this.addTime = addTime;
	}
	public String getAvailableTime() {
		return availableTime;
	}
	public void setAvailableTime(String availableTime) {
		this.availableTime = availableTime;
	}
	
	
	
	
	

}
