package com.ynkj.legal.model.answer;

import java.util.List;
import java.util.Map;

public class Answer {
	private String answerId;//回答id
	private String questionId;//问题主键
	private String answerContent;//回答内容
	private String addTime;//添加时间
	private int agreeCount;//赞成数量
	private String uid;//用户id
	private int commentCount;//评论数量
	private int anonymous = 0;//是否匿名
	private String unverifiedModify;//未审核内容
	private int verifyStatus;//审核状态 0 未审核 1审核通过 2审核未通过
	private int status;//状态0无效1有效
	private int deleteFlag;//删除标记
	
	private String avatarFile;	//头像
	private String questionTitle;//问题
	private String userName; //用户名
	private int followFlag; //关注标记
	private int praiseFlag; //赞标记 1:已赞;0:未赞
	private String oneWord; //一句话介绍
	private Integer certifyStatus;//认证状态
	private List<Map<String, Object>> agreeList;//赞同人列表
	
	
	public Integer getCertifyStatus() {
		return certifyStatus;
	}
	public void setCertifyStatus(Integer certifyStatus) {
		this.certifyStatus = certifyStatus;
	}
	public String getQuestionTitle() {
		return questionTitle;
	}
	public void setQuestionTitle(String questionTitle) {
		this.questionTitle = questionTitle;
	}
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
 
	public int getFollowFlag() {
		return followFlag;
	}
	public void setFollowFlag(int followFlag) {
		this.followFlag = followFlag;
	}
	public int getPraiseFlag() {
		return praiseFlag;
	}
	public void setPraiseFlag(int praiseFlag) {
		this.praiseFlag = praiseFlag;
	}
	public String getAnswerId() {
		return answerId;
	}
	public void setAnswerId(String answerId) {
		this.answerId = answerId;
	}
	public String getQuestionId() {
		return questionId;
	}
	public void setQuestionId(String questionId) {
		this.questionId = questionId;
	}
	public String getAnswerContent() {
		return answerContent;
	}
	public void setAnswerContent(String answerContent) {
		this.answerContent = answerContent;
	}
	public String getAddTime() {
		return addTime;
	}
	public void setAddTime(String addTime) {
		this.addTime = addTime;
	}
	public int getAgreeCount() {
		return agreeCount;
	}
	public void setAgreeCount(int agreeCount) {
		this.agreeCount = agreeCount;
	}
	public String getUid() {
		return uid;
	}
	public void setUid(String uid) {
		this.uid = uid;
	}
	public int getCommentCount() {
		return commentCount;
	}
	public void setCommentCount(int commentCount) {
		this.commentCount = commentCount;
	}
	public int getAnonymous() {
		return anonymous;
	}
	public void setAnonymous(int anonymous) {
		this.anonymous = anonymous;
	}
	public String getUnverifiedModify() {
		return unverifiedModify;
	}
	public void setUnverifiedModify(String unverifiedModify) {
		this.unverifiedModify = unverifiedModify;
	}
	public int getVerifyStatus() {
		return verifyStatus;
	}
	public void setVerifyStatus(int verifyStatus) {
		this.verifyStatus = verifyStatus;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public int getDeleteFlag() {
		return deleteFlag;
	}
	public void setDeleteFlag(int deleteFlag) {
		this.deleteFlag = deleteFlag;
	}
	public String getOneWord() {
		return oneWord;
	}
	public void setOneWord(String oneWord) {
		this.oneWord = oneWord;
	}
	public List<Map<String, Object>> getAgreeList() {
		return agreeList;
	}
	public void setAgreeList(List<Map<String, Object>> agreeList) {
		this.agreeList = agreeList;
	}
	
}
