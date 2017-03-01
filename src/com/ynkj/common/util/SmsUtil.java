package com.ynkj.common.util;

import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.core.task.TaskExecutor;

 
/**
 * 发送手机短信的接口.
 * 
 */
public class SmsUtil {
	private final static Log logger = LogFactory.getLog(SmsUtil.class);
	private TaskExecutor taskExecutor;
	private String codeContentRegister;//注册
	private String codeContentForgetPwd;//忘记密码
	private String codeContentModifyPhone;//修改手机号
	private String codeContentPushOrder;//推送订单
	private String codeContentHasPaied;//已支付订单
	private String codeContentToSign;//已发货订单
	private String codeContentHasSign;//已签收订单
	private String codeContentRegSuccess;//注册成功下发用户名和密码
	private String codeContentFindPwd;//找回登录密码
	private String codeContentFindPwdSendCode;//找回登录密码,发送验证码
	
	private String sendURL = "http://120.202.19.50:9002/smsgate/http.do";
	/**
	 * 注册短信
	 * 
	 * @param phone
	 * @param code
	 */
	public void sendCodeForRegister(String phone, String code) {
		 commonSendSMS(phone, codeContentRegister.replace("{code}", code));
	}

	/**
	 * 重置密码短信
	 * 
	 * @param phone
	 * @param code
	 */
	public void sendCodeForResetPassword(String phone, String code) {
		commonSendSMS(phone, codeContentForgetPwd.replace("{code}", code));
	}

	/**
	 * 修改手机号码
	 * 
	 * @param phone
	 * @param code
	 */
	public void sendCodeForModifyPhone(String phone, String code) {
		commonSendSMS(phone, codeContentModifyPhone.replace("{code}", code));
	}
	
	/**
	 * 推送订单短信
	 * 
	 * @param phone
	 * @param code
	 */
	public void sendCodeForPushOrder(String phone,String lowName,String upName, String amount,String orderId) {
		commonSendSMS(phone, codeContentPushOrder.replace("{lowName}", lowName).replace("{upName}", upName).replace("{amount}", amount).replace("{orderId}", orderId));
	}

	/**
	 * 已支付订单短信
	 * 
	 * @param phone
	 * @param code
	 */
	public void sendCodeForHasPaied(String phone,String upName,String lowName, String orderId , String amount) {
		commonSendSMS(phone, codeContentHasPaied.replace("{upName}", upName).replace("{lowName}", lowName).replace("{orderId}", orderId).replace("{amount}", amount));
	}
	
	/**
	 * 已发货订单短信
	 * @param phone
	 * @param code
	 */
	public void sendCodeForToSign(String phone,String lowName,String upName, String orderId) {
		commonSendSMS(phone, codeContentToSign.replace("{lowName}", lowName).replace("{upName}", upName).replace("{orderId}", orderId));
	}
	
	/**
	 * 已签收订单短信
	 * @param phone
	 * @param code
	 */
	public void sendCodeForHasSign(String phone,String orderId) {
		commonSendSMS(phone, codeContentHasSign.replace("{orderId}", orderId));
	}
	
	/**
	 * 注册成功短信通知
	 * @param phone
	 * @param agentName
	 * @param userName
	 */
	public void setCodeContentRegSuccess(String phone,String agentName,String sp,String userName,String password) {
		commonSendSMS(phone, codeContentRegSuccess.replace("{agentName}", agentName).replace("{sp}",sp).replace("{userName}",userName).replace("{password}",password));
	}

	/**
	 * 找回登录密码，短信通知
	 * @param phone
	 * @param agentName
	 * @param userName
	 */
	public void setCodeContentFindPwd(String phone,String agentName,String password) {
		commonSendSMS(phone, codeContentFindPwd.replace("{agentName}", agentName).replace("{password}",password));
	}

	public void setCodeForMentioning(String telphone, String orderId, String money, String code) {
		commonSendSMS(telphone, "【储居汇】提醒您，您的订单{orderId}已支付成功,金额为{money},提货校验码为{code},可立即前往门店提货,详询拔打..."
				.replace("{orderId}", orderId).replace("{money}", money).replace("{code}", code));
	}

	/**
	 * 找回登录密码，发送验证码
	 * @param phone
	 * @param agentName
	 * @param userName
	 */
	public void setCodeContentFindPwdSendCode(String phone,String agentName,String code) {
		commonSendSMS(phone, codeContentFindPwdSendCode.replace("{agentName}", agentName).replace("{code}",code));
	}

	/** 
	 * 发送短信 
	 * <pre>
	 * MODIFY BY JFAN 2013-01-15
	 * 把实现方式改为调用短信平台实现
	 * <pre>
	 * @param phone 逗号分割的手机号
	 * @param content 短信内容
	 */
	public void commonSendSMS(String phone, String content) {
		taskExecutor.execute(new SendSmsTask(phone,content));
	}
	
	class SendSmsTask implements Runnable {      
        private String phone;
        private String content;
        public SendSmsTask(String phone,String content) {        
            this.phone = phone;
            this.content = content;
        }      
        public void run() {        
        	// 登录名
    		String user = "x008";
    		// 密码
    		String pwd = "xf123456";
    		// post内容
    		String postContent = "";
    		// 返回结果
    		String result = "";
    		try {
    			postContent = "batchsendsm&user=" + user + "&pwd=" + pwd +"&phone="+phone+"&msg="+content;
    			//返回短信Id
    			URL url = new URL(sendURL);
    			result = HttpUtil.httpPostRequest(url, postContent);
    		} catch (Exception e) {
    			e.printStackTrace();
    		}
    		logger.info(result+"---短信内容:" + content + " --- phone:" + phone);
        }  
    } 
	
	public static void main(String[] args) {
		new SmsUtil().commonSendSMS("15927416531","【法务助手】验证码：0926");
	}
	
	// 验证手机号码是否合法
	public boolean validateMoblie(String phone) {
		return matchingText("^1[3,5,8](\\d){9}$", phone);
	}

	/**
	  * 创建指定数量的随机字符串
	  * @param numberFlag 是否是数字
	  * @param length
	  * @return
	  */
	 public static String createRandom(boolean numberFlag, int length){
	  String retStr = "";
	  String strTable = numberFlag ? "1234567890" : "1234567890abcdefghijkmnpqrstuvwxyz";
	  int len = strTable.length();
	  boolean bDone = true;
	  do {
	   retStr = "";
	   int count = 0;
	   for (int i = 0; i < length; i++) {
	    double dblR = Math.random() * len;
	    int intR = (int) Math.floor(dblR);
	    char c = strTable.charAt(intR);
	    if (('0' <= c) && (c <= '9')) {
	     count++;
	    }
	    retStr += strTable.charAt(intR);
	   }
	   if (count >= 2) {
	    bDone = false;
	   }
	  } while (bDone);
	 
	  return retStr;
	 }
	
	
	private boolean matchingText(String expression, String text) {
		Pattern p = Pattern.compile(expression); // 正则表达式
		Matcher m = p.matcher(text); // 操作的字符串
		boolean b = m.matches();
		return b;
	}

	public void setCodeContentRegister(String codeContentRegister) {
		this.codeContentRegister = codeContentRegister;
	}

	public void setCodeContentForgetPwd(String codeContentForgetPwd) {
		this.codeContentForgetPwd = codeContentForgetPwd;
	}

	public void setCodeContentModifyPhone(String codeContentModifyPhone) {
		this.codeContentModifyPhone = codeContentModifyPhone;
	}

 

	public void setCodeContentPushOrder(String codeContentPushOrder) {
		this.codeContentPushOrder = codeContentPushOrder;
	}

	public void setCodeContentHasPaied(String codeContentHasPaied) {
		this.codeContentHasPaied = codeContentHasPaied;
	}

	public void setCodeContentToSign(String codeContentToSign) {
		this.codeContentToSign = codeContentToSign;
	}

	public void setCodeContentHasSign(String codeContentHasSign) {
		this.codeContentHasSign = codeContentHasSign;
	}

	public void setCodeContentRegSuccess(String codeContentRegSuccess) {
		this.codeContentRegSuccess = codeContentRegSuccess;
	}

	public void setCodeContentFindPwd(String codeContentFindPwd) {
		this.codeContentFindPwd = codeContentFindPwd;
	}

	public void setCodeContentFindPwdSendCode(String codeContentFindPwdSendCode) {
		this.codeContentFindPwdSendCode = codeContentFindPwdSendCode;
	}
	public TaskExecutor getTaskExecutor() {
		return taskExecutor;
	}
	public void setTaskExecutor(TaskExecutor taskExecutor) {
		this.taskExecutor = taskExecutor;
	}	
	
}
