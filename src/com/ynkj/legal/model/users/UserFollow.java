package com.ynkj.legal.model.users;

public class UserFollow {
	
	private String userId;
	private String userFollowedId;
	private Integer status;
	private String addTime;
	
	
	public String getUserFollowedId() {
		return userFollowedId;
	}
	public void setUserFollowedId(String userFollowedId) {
		this.userFollowedId = userFollowedId;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getAddTime() {
		return addTime;
	}
	public void setAddTime(String addTime) {
		this.addTime = addTime;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	
	

}
