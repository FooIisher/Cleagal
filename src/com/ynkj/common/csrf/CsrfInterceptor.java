package com.ynkj.common.csrf;

import java.net.URLEncoder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

/**
 * 
 * @author SHI CHANGGEN
 * CSRF跨站请求防御
 */

public class CsrfInterceptor extends HandlerInterceptorAdapter {

	private static final Logger logger = LoggerFactory.getLogger(CsrfInterceptor.class);

	

	@Override
	public boolean preHandle(HttpServletRequest request,HttpServletResponse response, Object handler) throws Exception {
		if ("POST".equalsIgnoreCase(request.getMethod())) {
			String CsrfToken = CsrfTokenManager.getTokenFromRequest(request);
			if (CsrfToken == null || !CsrfToken.equals(request.getSession().getAttribute(CsrfTokenManager.CSRF_TOKEN_FOR_SESSION_ATTR_NAME))) {
				String reLoginUrl = "/login.shtml?redirectURL=" + URLEncoder.encode(getCurrentUrl(request), "utf-8");
				response.sendRedirect(reLoginUrl);
				logger.info(">>>>>>>>>CSRF已被拦截<<<<<<<<");
				return false;
			}
		}

		return true;
	}

	private String getCurrentUrl(HttpServletRequest request) {
		String currentUrl = request.getRequestURL().toString();
		if (!StringUtils.isEmpty(request.getQueryString())) {
			currentUrl += "?" + request.getQueryString();
		}

		return currentUrl;
	}
}
