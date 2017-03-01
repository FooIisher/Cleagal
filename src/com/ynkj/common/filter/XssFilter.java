package com.ynkj.common.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

/**
 * 利用Servlet的过滤器机制，将request请求代理，
 * request中参数值里的指定半角字符，强制替换成全角字符。
 * 
 * @author SCG
 * 
 */
public class XssFilter implements Filter {

	protected FilterConfig filterConfig = null;
	
	@Override
	public void init(FilterConfig config) throws ServletException {
		this.filterConfig = config;
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException,
			ServletException {
		if (request instanceof HttpServletRequest) {
			XssHttpServletRequestWrapper xssRequest = new XssHttpServletRequestWrapper((HttpServletRequest) request);
			chain.doFilter(xssRequest, response);
		}
	}

	@Override
	public void destroy() {
		this.filterConfig = null;
	}
}