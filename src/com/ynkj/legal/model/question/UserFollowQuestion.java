package com.ynkj.legal.model.question;

public class UserFollowQuestion implements Cloneable{
	
	private String userId;
	private String questionId;
	private int status;
	private String addTime;
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getQuestionId() {
		return questionId;
	}
	public void setQuestionId(String questionId) {
		this.questionId = questionId;
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
		 UserFollowQuestion o = null;  
	        try {  
	            o = (UserFollowQuestion) super.clone();  
	        } catch (CloneNotSupportedException e) {  
	            e.printStackTrace();  
	        }  
	        return o;  
	  }  
	

}
