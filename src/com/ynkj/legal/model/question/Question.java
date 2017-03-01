package com.ynkj.legal.model.question;


public class Question {
	
	private String questionId;
	private String questionTitle;
	private String questionContent;
	private String addTime;
	private String updateTime;
	private String publishedUid;
	private String answerCount;
	private String commentCount;
	private String followCount;
	private String viewCount;
	private String verifyStatus;
	private String lockFlag;
	private String anonymous = "0";
	private String status;
	private String deleteFlag;
	private String followFlag;
	private String unverifiedModify;

	private String answerId;
	private String answerContent;
	private String agreeCount;
	private String praiseFlag;

	private String aanonymous;//回答是否匿名	
	private String auserName;//回答者昵称
	private String aoneWord;//回答者一句话介绍
	private String aavatarFile;//回答者头像
	private String acommentCount;
	private String afollowFlag;
	private String aviewCount;
	private String acertifyStatus;//回答者认证状态
	
	private String userName;//提问者昵称
	private String oneWord;//提问者一句话介绍
	private String avatarFile;//提问者头像
	
	private String topicId;//问题标签Id
	private String topicTitle;//问题标签名称
	private String topicPic;//问题标签图片
	
	
 
	public String getAviewCount() {
		return aviewCount;
	}
	public void setAviewCount(String aviewCount) {
		this.aviewCount = aviewCount;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getOneWord() {
		return oneWord;
	}
	public void setOneWord(String oneWord) {
		this.oneWord = oneWord;
	}
	public String getPraiseFlag() {
		return praiseFlag;
	}
	public void setPraiseFlag(String praiseFlag) {
		this.praiseFlag = praiseFlag;
	}
	public String getAnswerId() {
		return answerId;
	}
	public void setAnswerId(String answerId) {
		this.answerId = answerId;
	}
	public String getAnswerContent() {
		return answerContent;
	}
	public void setAnswerContent(String answerContent) {
		this.answerContent = answerContent;
	}
	public String getAgreeCount() {
		return agreeCount;
	}
	public void setAgreeCount(String agreeCount) {
		this.agreeCount = agreeCount;
	}
	public String getQuestionId() {
		return questionId;
	}
	public void setQuestionId(String questionId) {
		this.questionId = questionId;
	}
	public String getQuestionTitle() {
		return questionTitle;
	}
	public void setQuestionTitle(String questionTitle) {
		this.questionTitle = questionTitle;
	}
	public String getQuestionContent() {
		return questionContent;
	}
	public void setQuestionContent(String questionContent) {
		this.questionContent = questionContent;
	}
	public String getAddTime() {
		return addTime;
	}
	public void setAddTime(String addTime) {
		this.addTime = addTime;
	}
	public String getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}
	public String getPublishedUid() {
		return publishedUid;
	}
	public void setPublishedUid(String publishedUid) {
		this.publishedUid = publishedUid;
	}
	public String getAnswerCount() {
		return answerCount;
	}
	public void setAnswerCount(String answerCount) {
		this.answerCount = answerCount;
	}
	public String getCommentCount() {
		return commentCount;
	}
	public void setCommentCount(String commentCount) {
		this.commentCount = commentCount;
	}
	
	
	 
	public String getFollowCount() {
		return followCount;
	}
	public void setFollowCount(String followCount) {
		this.followCount = followCount;
	}
	public String getViewCount() {
		return viewCount;
	}
	public void setViewCount(String viewCount) {
		this.viewCount = viewCount;
	}
	public String getVerifyStatus() {
		return verifyStatus;
	}
	public void setVerifyStatus(String verifyStatus) {
		this.verifyStatus = verifyStatus;
	}
	public String getLockFlag() {
		return lockFlag;
	}
	public void setLockFlag(String lockFlag) {
		this.lockFlag = lockFlag;
	}
	public String getAnonymous() {
		return anonymous;
	}
	public void setAnonymous(String anonymous) {
		this.anonymous = anonymous;
	}
	 
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getDeleteFlag() {
		return deleteFlag;
	}
	public void setDeleteFlag(String deleteFlag) {
		this.deleteFlag = deleteFlag;
	}
	public String getFollowFlag() {
		return followFlag;
	}
	public void setFollowFlag(String followFlag) {
		this.followFlag = followFlag;
	}
	public String getUnverifiedModify() {
		return unverifiedModify;
	}
	public void setUnverifiedModify(String unverifiedModify) {
		this.unverifiedModify = unverifiedModify;
	}
	public String getAvatarFile() {
		return avatarFile;
	}
	public void setAvatarFile(String avatarFile) {
		this.avatarFile = avatarFile;
	}
	public String getAuserName() {
		return auserName;
	}
	public void setAuserName(String auserName) {
		this.auserName = auserName;
	}
	public String getAoneWord() {
		return aoneWord;
	}
	public void setAoneWord(String aoneWord) {
		this.aoneWord = aoneWord;
	}
	public String getAavatarFile() {
		return aavatarFile;
	}
	public void setAavatarFile(String aavatarFile) {
		this.aavatarFile = aavatarFile;
	}
	public String getAanonymous() {
		return aanonymous;
	}
	public void setAanonymous(String aanonymous) {
		this.aanonymous = aanonymous;
	}
	public String getAcommentCount() {
		return acommentCount;
	}
	public void setAcommentCount(String acommentCount) {
		this.acommentCount = acommentCount;
	}
	public String getAfollowFlag() {
		return afollowFlag;
	}
	public void setAfollowFlag(String afollowFlag) {
		this.afollowFlag = afollowFlag;
	}
	public String getTopicId() {
		return topicId;
	}
	public void setTopicId(String topicId) {
		this.topicId = topicId;
	}
	public String getTopicTitle() {
		return topicTitle;
	}
	public void setTopicTitle(String topicTitle) {
		this.topicTitle = topicTitle;
	}
	public String getTopicPic() {
		return topicPic;
	}
	public void setTopicPic(String topicPic) {
		this.topicPic = topicPic;
	}
	public String getAcertifyStatus() {
		return acertifyStatus;
	}
	public void setAcertifyStatus(String acertifyStatus) {
		this.acertifyStatus = acertifyStatus;
	}
	

}
