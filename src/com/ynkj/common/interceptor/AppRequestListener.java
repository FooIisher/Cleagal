package com.ynkj.common.interceptor;

import java.io.UnsupportedEncodingException;
import java.util.HashSet;
import java.util.Set;

import javax.servlet.ServletRequestEvent;
import javax.servlet.ServletRequestListener;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSessionAttributeListener;
import javax.servlet.http.HttpSessionBindingEvent;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.servlet.HandlerMapping;

/**
 * Request行为监听
 * @author SHI CHANGGEN
 *
 */
public class AppRequestListener implements ServletRequestListener, HttpSessionAttributeListener {

    private final Log log = LogFactory.getLog(AppRequestListener.class);
    

    private static Set<String> EXCLUDE_SESSION_NAME = new HashSet<String>();

    static {
        EXCLUDE_SESSION_NAME.add("__sitemesh__robot");
    }

    @Override
	public void requestDestroyed(ServletRequestEvent requestEvent) {

    }

    @Override
	public void requestInitialized(ServletRequestEvent requestEvent) {
        // CtxSessionBag.clear();
        HttpServletRequest request = (HttpServletRequest) requestEvent.getServletRequest();
        try {
            request.setCharacterEncoding("UTF-8");
        } catch (UnsupportedEncodingException e) {
            log.error("::", e);
        }

       
    }
    
    

    @Override
    public void attributeAdded(HttpSessionBindingEvent event) {
    	if ("UserSession".equals(event.getName())) {  
    		//SessionBagUtil.setSessionBag((SessionBagImpl) event.getValue());  
        } 
    	
        dealSessionErr(event, "add");
    }

    @Override
    public void attributeRemoved(HttpSessionBindingEvent event) {
        dealSessionErr(event, "remove");
    }

    @Override
    public void attributeReplaced(HttpSessionBindingEvent event) {
    	if ("UserSession".equals(event.getName())) {  
    		//SessionBagUtil.setSessionBag((SessionBagImpl) event.getValue());  
        } 
        dealSessionErr(event, "replace");
    }

    private void dealSessionErr(HttpSessionBindingEvent event, String type) {
        
    }
    
    /**
     * 获取客户端ip
     * @param request
     * @return
     */
//    private String getRequestIp(HttpServletRequest request) {
//        String ipAddress = null;
//        ipAddress = request.getHeader("x-forwarded-for");
//        if(ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
//         ipAddress = request.getHeader("Proxy-Client-IP");
//        }
//        if(ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
//            ipAddress = request.getHeader("WL-Proxy-Client-IP");
//        }
//        if(ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
//        	ipAddress = request.getRemoteAddr();
//        	if(ipAddress.equals("127.0.0.1")){
//	        	//根据网卡取本机配置的IP
//	            InetAddress inet=null;
//		        try {
//		        	inet = InetAddress.getLocalHost();
//		        } catch (UnknownHostException e) {
//		        	log.error("获取客户端ip失败" + e);
//		        	return "";
//		        }
//		        ipAddress= inet.getHostAddress();
//	        }
//        }
//        //对于通过多个代理的情况，第一个IP为客户端真实IP,多个IP按照','分割
//        if(ipAddress!=null && ipAddress.length()>15){ //"***.***.***.***".length() = 15
//            if(ipAddress.indexOf(",")>0){
//                ipAddress = ipAddress.substring(0,ipAddress.indexOf(","));
//            }
//        }
//        return ipAddress;
//   }

    
}
