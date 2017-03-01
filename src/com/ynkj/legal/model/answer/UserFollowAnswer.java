package com.ynkj.legal.model.answer;

import com.ynkj.legal.model.question.UserFollowQuestion;

public class UserFollowAnswer implements Cloneable{
	
	private String userId;
	private String answerId;
	private int status;
	private String addTime;
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getAnswerId() {
		return answerId;
	}
	public void setAnswerId(String answerId) {
		this.answerId = answerId;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public String getAddTime() {
		return addTime;
	}
	public void setAddTime(String addTime) {
		this.addTime = addTime;
	}
	
	public Object clone() {  
		UserFollowAnswer o = null;  
	        try {  
	            o = (UserFollowAnswer) super.clone();  
	        } catch (CloneNotSupportedException e) {  
	            e.printStackTrace();  
	        }  
	        return o;  
	  } 

}
