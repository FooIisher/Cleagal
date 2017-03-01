package com.ynkj.common.util;

import org.apache.commons.lang.StringUtils;

public class FunctionUtil {
	public static String parseHeadImg(String imgUrl){
		if(StringUtils.isEmpty(imgUrl)){
			return "static/images/logo_head.png";
		}else{
			return imgUrl;
		}
	}
}
