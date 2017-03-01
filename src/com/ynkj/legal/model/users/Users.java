package com.ynkj.legal.model.users;

import java.util.List;

import com.ynkj.legal.model.question.Topic;

public class Users {
	
	private String userId;
	private String userName;
	private String mobile;
	private String email;
	private String password;
	private String avatarFile;
	private Integer sex;
	private String birthday;
	private String province;
	private String city;
	private String cityName;
	private String oneWord;
	private String individualResume;
	private String companyName;
	private String school;
	private String profess;
	private String jobId;
	private String jobName;
	private String regTime;
	private String regIp;
	private String lastLogin;
	private String lastIp;
	private Integer fansCount;
	private Integer inviteCount;  //被邀请数量
	private Integer questionCount;
	private Integer answerCount;
	private Integer caseCount;
	private Integer invitationAvailable; //邀请数量
	private Integer forbidden;
	private Integer agreeCount;
	private Integer recommendFlag; //推荐标记
	private Integer deleteFlag;
	private String professTag;
	private String adeptTag;
	private String inviteFlag;//邀请标记
	private String professTagName;//职称
	private String adeptTagName;//擅长字符串
	
	private int lawsuitCount; //官司数量
	private int winCount;  //胜诉
	
	private int followFlag;
	
	private int certifyStatus;
	private String certifyName;
	private String certifyFile;
	private String certifyCompany;
	private String certifyVerify;
	private int smsStatus;
	
	private List<Topic> listTopic;


	public int getSmsStatus() {
		return smsStatus;
	}



	public void setSmsStatus(int smsStatus) {
		this.smsStatus = smsStatus;
	}



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



	public Integer getCaseCount() {
		return caseCount;
	}



	public void setCaseCount(Integer caseCount) {
		this.caseCount = caseCount;
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



	public int getLawsuitCount() {
		return lawsuitCount;
	}



	public void setLawsuitCount(int lawsuitCount) {
		this.lawsuitCount = lawsuitCount;
	}



	public int getWinCount() {
		return winCount;
	}



	public void setWinCount(int winCount) {
		this.winCount = winCount;
	}



	public int getFollowFlag() {
		return followFlag;
	}



	public void setFollowFlag(int followFlag) {
		this.followFlag = followFlag;
	}



	public int getCertifyStatus() {
		return certifyStatus;
	}



	public void setCertifyStatus(int certifyStatus) {
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
	public List<Topic> getListTopic() {
		return listTopic;
	}
	public void setListTopic(List<Topic> listTopic) {
		this.listTopic = listTopic;
	}
	public String getCityName() {
		return cityName;
	}
	public void setCityName(String cityName) {
		this.cityName = cityName;
	}
	public String getJobName() {
		return jobName;
	}
	public void setJobName(String jobName) {
		this.jobName = jobName;
	}
	public String getInviteFlag() {
		return inviteFlag;
	}
	public void setInviteFlag(String inviteFlag) {
		this.inviteFlag = inviteFlag;
	}

}
