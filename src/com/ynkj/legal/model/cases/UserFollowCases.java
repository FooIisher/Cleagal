package com.ynkj.legal.model.cases;

import com.ynkj.legal.model.question.UserFollowQuestion;

public class UserFollowCases implements Cloneable{
	
	private String userId;
	private String caseId;
	private int status;
	private String addTime;
	
	
	public String getCaseId() {
		return caseId;
	}
	public void setCaseId(String caseId) {
		this.caseId = caseId;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
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
		 
		 	UserFollowCases o = null;  
	        try {  
	            o = (UserFollowCases) super.clone();  
	        } catch (CloneNotSupportedException e) {  
	            e.printStackTrace();  
	        }  
	        return o;  
	  } 
	

}
