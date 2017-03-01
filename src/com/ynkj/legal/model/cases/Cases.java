package com.ynkj.legal.model.cases;

public class Cases {
	
	private String caseId;
	private String caseContent;
	private String caseTitle;
	private String imgUrl;
	private Integer followCount;
	private Integer commentCount;
	private Integer viewCount;
	private String unverifiedModify;
	private Integer verifyStatus;
	private String publishUser;
	private String publishTime;
	private int status;
	private int deleteFlag ;
	
	private String userName;
	private String avatatFile;
	private String followFlag;
	private String oneWord;
	
	
 
	public Integer getViewCount() {
		return viewCount;
	}
	public void setViewCount(Integer viewCount) {
		this.viewCount = viewCount;
	}
	public String getOneWord() {
		return oneWord;
	}
	public void setOneWord(String oneWord) {
		this.oneWord = oneWord;
	}
	public String getFollowFlag() {
		return followFlag;
	}
	public void setFollowFlag(String followFlag) {
		this.followFlag = followFlag;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getAvatatFile() {
		return avatatFile;
	}
	public void setAvatatFile(String avatatFile) {
		this.avatatFile = avatatFile;
	}
	public String getCaseId() {
		return caseId;
	}
	public void setCaseId(String caseId) {
		this.caseId = caseId;
	}
	public String getCaseContent() {
		return caseContent;
	}
	public void setCaseContent(String caseContent) {
		this.caseContent = caseContent;
	}
	public String getCaseTitle() {
		return caseTitle;
	}
	public void setCaseTitle(String caseTitle) {
		this.caseTitle = caseTitle;
	}
	public String getImgUrl() {
		return imgUrl;
	}
	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}
	public Integer getFollowCount() {
		return followCount;
	}
	public void setFollowCount(Integer followCount) {
		this.followCount = followCount;
	}
	public Integer getCommentCount() {
		return commentCount;
	}
	public void setCommentCount(Integer commentCount) {
		this.commentCount = commentCount;
	}
	public String getUnverifiedModify() {
		return unverifiedModify;
	}
	public void setUnverifiedModify(String unverifiedModify) {
		this.unverifiedModify = unverifiedModify;
	}
	public Integer getVerifyStatus() {
		return verifyStatus;
	}
	public void setVerifyStatus(Integer verifyStatus) {
		this.verifyStatus = verifyStatus;
	}
	public String getPublishUser() {
		return publishUser;
	}
	public void setPublishUser(String publishUser) {
		this.publishUser = publishUser;
	}
	public String getPublishTime() {
		return publishTime;
	}
	public void setPublishTime(String publishTime) {
		this.publishTime = publishTime;
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
	
	
	

}
