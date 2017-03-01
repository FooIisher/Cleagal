package com.ynkj.legal.common;

public abstract class Constant {
	
	public final static String SUCCESS = "success";

	public final static String MSG = "msg";

	public final static String CODENUM = "codenum";

	public final static String COLORGGXH = "1001";

	public final static String DATA = "data";

	public final static String LOGOUT_FLAG = "logoutFlag";
	
	/**
	 *ACTION_TYPE 审核问题
	 */
	public final static int ACTION_TYPE_CHECK = 0;
	
	/**
	 *ACTION_TYPE follow 关注
	 */
	public final static int ACTION_TYPE_FOLLOW = 1;
	
	/**
	 *ACTION_TYPE comment 评论
	 */
	public final static int ACTION_TYPE_COMMENT = 2;
	
	/**
	 *ACTION_TYPE answer 回答
	 */
	public final static int ACTION_TYPE_ANSWER = 3;
	
	/**
	 *ACTION_TYPE praise 点赞
	 */
	public final static int ACTION_TYPE_PRAISE  = 4;
	
	/**
	 *ACTION_TYPE invite 访问
	 */
	public final static int ACTION_TYPE_INVITE  = 5;
	
	/**
	 *ACTION_TYPE 发表 
	 */
	public final static int ACTION_TYPE_PUBLISH  = 6;
	
	/**
	 *MODEL_TYPE_QUESTION 问题
	 */
	public final static int MODEL_TYPE_QUESTION = 1;
	
	/**
	 *MODEL_TYPE_ANSWER 回答
	 */
	public final static int MODEL_TYPE_ANSWER = 2;
	
	/**
	 *MODEL_TYPE_CASE 案例
	 */
	public final static int MODEL_TYPE_CASE = 3;
	
	/**
	 * MODEL_TYPE_PERSON 用户
	 */
	public final static int MODEL_TYPE_PERSON = 4;

}
