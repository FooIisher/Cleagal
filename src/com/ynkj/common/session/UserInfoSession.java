package com.ynkj.common.session;

import java.io.Serializable;
import java.util.List;

import com.ynkj.legal.model.question.Topic;

public class UserInfoSession implements Serializable {
	
	private static final long serialVersionUID = -4925639519382691692L;
	private String userId;;
	private String userName;
	private String mobile;
	private String email;
	private String password;
	private String avatarFile;
	private Integer sex;
	private String birthday;
	private String province;
	private String city;
	private String oneWord;
	private String individualResume;
	private String companyName;
	private String school;
	private String profess;
	private String jobId;
	private String regTime;
	private String regIp;
	private String lastLogin;
	private String lastIp;
	private Integer fansCount;
	private Integer inviteCount;
	private Integer questionCount;
	private Integer answerCount;
	private Integer invitationAvailable;
	private Integer forbidden;
	private Integer agreeCount;
	private Integer recommendFlag;
	private Integer deleteFlag;
	private String professTag;
	private String adeptTag;
	
	private String professTagName;
	private String adeptTagName;
	
	private Integer lawsuitCount;
	private Integer winCount;
	
	private Integer followFlag;
	
	private Integer certifyStatus;
	private String certifyName;
	private String certifyFile;
	private String certifyCompany;
	private String certifyVerify;
	
	private String truePassword;
	
	

	public String getCertifyVerify() {
		return certifyVerify;
	}

	public void setCertifyVerify(String certifyVerify) {
		this.certifyVerify = certifyVerify;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getAvatarFile() {
		return avatarFile;
	}

	public void setAvatarFile(String avatarFile) {
		this.avatarFile = avatarFile;
	}

	public Integer getSex() {
		return sex;
	}

	public void setSex(Integer sex) {
		this.sex = sex;
	}

	public String getBirthday() {
		return birthday;
	}

	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getOneWord() {
		return oneWord;
	}

	public void setOneWord(String oneWord) {
		this.oneWord = oneWord;
	}

	public String getIndividualResume() {
		return individualResume;
	}

	public void setIndividualResume(String individualResume) {
		this.individualResume = individualResume;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getSchool() {
		return school;
	}

	public void setSchool(String school) {
		this.school = school;
	}

	public String getProfess() {
		return profess;
	}

	public void setProfess(String profess) {
		this.profess = profess;
	}

	public String getJobId() {
		return jobId;
	}

	public void setJobId(String jobId) {
		this.jobId = jobId;
	}

	public String getRegTime() {
		return regTime;
	}

	public void setRegTime(String regTime) {
		this.regTime = regTime;
	}

	public String getRegIp() {
		return regIp;
	}

	public void setRegIp(String regIp) {
		this.regIp = regIp;
	}

	public String getLastLogin() {
		return lastLogin;
	}

	public void setLastLogin(String lastLogin) {
		this.lastLogin = lastLogin;
	}

	public String getLastIp() {
		return lastIp;
	}

	public void setLastIp(String lastIp) {
		this.lastIp = lastIp;
	}

	public Integer getFansCount() {
		return fansCount;
	}

	public void setFansCount(Integer fansCount) {
		this.fansCount = fansCount;
	}

	public Integer getInviteCount() {
		return inviteCount;
	}

	public void setInviteCount(Integer inviteCount) {
		this.inviteCount = inviteCount;
	}

	public Integer getQuestionCount() {
		return questionCount;
	}

	public void setQuestionCount(Integer questionCount) {
		this.questionCount = questionCount;
	}

	public Integer getAnswerCount() {
		return answerCount;
	}

	public void setAnswerCount(Integer answerCount) {
		this.answerCount = answerCount;
	}

	public Integer getInvitationAvailable() {
		return invitationAvailable;
	}

	public void setInvitationAvailable(Integer invitationAvailable) {
		this.invitationAvailable = invitationAvailable;
	}

	public Integer getForbidden() {
		return forbidden;
	}

	public void setForbidden(Integer forbidden) {
		this.forbidden = forbidden;
	}

	public Integer getAgreeCount() {
		return agreeCount;
	}

	public void setAgreeCount(Integer agreeCount) {
		this.agreeCount = agreeCount;
	}

	public Integer getRecommendFlag() {
		return recommendFlag;
	}

	public void setRecommendFlag(Integer recommendFlag) {
		this.recommendFlag = recommendFlag;
	}

	public Integer getDeleteFlag() {
		return deleteFlag;
	}

	public void setDeleteFlag(Integer deleteFlag) {
		this.deleteFlag = deleteFlag;
	}

	public String getProfessTag() {
		return professTag;
	}

	public void setProfessTag(String professTag) {
		this.professTag = professTag;
	}

	public String getAdeptTag() {
		return adeptTag;
	}

	public void setAdeptTag(String adeptTag) {
		this.adeptTag = adeptTag;
	}

	public String getProfessTagName() {
		return professTagName;
	}

	public void setProfessTagName(String professTagName) {
		this.professTagName = professTagName;
	}

	public String getAdeptTagName() {
		return adeptTagName;
	}

	public void setAdeptTagName(String adeptTagName) {
		this.adeptTagName = adeptTagName;
	}

	public Integer getLawsuitCount() {
		return lawsuitCount;
	}

	public void setLawsuitCount(Integer lawsuitCount) {
		this.lawsuitCount = lawsuitCount;
	}

	public Integer getWinCount() {
		return winCount;
	}

	public void setWinCount(Integer winCount) {
		this.winCount = winCount;
	}

	public Integer getFollowFlag() {
		return followFlag;
	}

	public void setFollowFlag(Integer followFlag) {
		this.followFlag = followFlag;
	}

	public Integer getCertifyStatus() {
		return certifyStatus;
	}

	public void setCertifyStatus(Integer certifyStatus) {
		this.certifyStatus = certifyStatus;
	}

	public String getCertifyName() {
		return certifyName;
	}

	public void setCertifyName(String certifyName) {
		this.certifyName = certifyName;
	}

	public String getCertifyFile() {
		return certifyFile;
	}

	public void setCertifyFile(String certifyFile) {
		this.certifyFile = certifyFile;
	}

	public String getCertifyCompany() {
		return certifyCompany;
	}

	public void setCertifyCompany(String certifyCompany) {
		this.certifyCompany = certifyCompany;
	}

	public String getTruePassword() {
		return truePassword;
	}

	public void setTruePassword(String truePassword) {
		this.truePassword = truePassword;
	}
	
	
	
 
 
}