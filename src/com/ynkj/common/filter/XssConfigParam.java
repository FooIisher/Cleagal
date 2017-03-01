package com.ynkj.common.filter;

public class XssConfigParam {

	// xss防御开启
	public static Integer INSHOPXSS_OPEN = 1;
	// xss防御关闭
	public static Integer INSHOPXSS_CLOSE = 0;
	
	// xss防御-例外URL串(这里的url将不会被filter命中)
	public String inshopXssExemptUrl;
	// xss防御开关
	private Integer inshopXss;

	public Integer getInshopXss() {
		return inshopXss;
	}

	public void setInshopXss(Integer inshopXss) {
		this.inshopXss = inshopXss;
	}

	public String getInshopXssExemptUrl() {
		return inshopXssExemptUrl;
	}

	public void setInshopXssExemptUrl(String inshopXssExemptUrl) {
		this.inshopXssExemptUrl = inshopXssExemptUrl;
	}
}
