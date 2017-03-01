package com.ynkj.common.util;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang.StringUtils;

@SuppressWarnings({"unchecked", "unused"})
public class Utils {

	public static boolean arrayIsEmpty(String[] strs) {
		if (strs == null || strs.length == 0) {
			return true;
		}
		return false;
	}

	/**
	 * 循环删除最后的某个字符，至不是以该字符结尾
	 * 
	 * @param value
	 * @return
	 */
	public static String removeEnd(String value, char c) {

		if (StringUtils.isBlank(value)) {
			return "";
		}
		String ret = value;
		int i = 0;
		while (StringUtils.isNotBlank(ret)
				&& (StringUtils.lastIndexOf(ret, c) == (ret.length() - 1))) {
			ret = StringUtils.removeEnd(ret, String.valueOf(c));
		}
		return ret;
	}

	public static String removeStart(String value, char c) {
		if (StringUtils.isBlank(value)) {
			return "";
		}

		String ret = value;
		int i = 0;
		while (StringUtils.isNotBlank(ret)
				&& (StringUtils.indexOf(ret, String.valueOf(c)) == 0)) {
			ret = StringUtils.removeStart(ret, String.valueOf(c));
		}
		return ret;
	}

	public static String removeFirstAndEnd(String value, char c) {
		String ret = removeEnd(value, c);
		return removeStart(ret, c);
	}

	/**
	 * 使得数组不为空
	 * 
	 * @param data
	 * @return
	 */
	public static String[] formatArray(String[] data) {
		if (data == null) {
			return new String[0];
		}
		return data;
	}

	/**
	 * 格式化double类型值，使得其末尾保留两位小数
	 * 
	 * @param value
	 * @return
	 */
	public static String formatDouble(double value) {
		DecimalFormat df = new DecimalFormat("######0.00");
		return df.format(value);
	}

	/**
	 * 数据库表记录主键值
	 */
	public static String getTablePK() {
		return RandomStringUtils.randomAlphanumeric(10);
	}

	/**
	 * 生成制定位随机数字
	 */
	public static String randomNumeric(int i) {
		return RandomStringUtils.randomNumeric(i);
	}

	/**
	 * 生成制定位随机字母和数字
	 */
	public static String randomAlphanumeric(int i) {
		return RandomStringUtils.randomAlphanumeric(i);
	}

	/**
	 * 将字符串数字转化为int型数字
	 * 
	 * @param str被转化字符串
	 * @param defValue转化失败后的默认值
	 * @return int
	 */
	public static int parseInt(String str, int defValue) {
		try {
			return Integer.parseInt(str);
		} catch (Exception e) {
			return defValue;
		}
	}

	/**
	 * 将字符串数字转化为double型数字
	 * 
	 * @param str被转化字符串
	 * @param defValue转化失败后的默认值
	 * @return double
	 */
	public static double parseDouble(String str, double defValue) {
		try {
			return Double.parseDouble(str);
		} catch (Exception e) {
			return defValue;
		}
	}

	/**
	 * 检测字符串是否为空
	 */
	public static boolean strIsNull(String str) {
		return ((str == null) || "".equals(str));
	}

	/**
	 * 去空格，如为null则转化为空字符串
	 */
	public static String trim(String str) {
		if (str == null) {
			return "";
		}
		return str.trim();
	}

	public static String trim(Object obj) {
		if (obj == null) {
			return "";
		} else {
			return trim(obj.toString());
		}
	}

	/**
	 * 将字符串数组转化成中间用逗号分割的字符串 "'a','b','c'"
	 */
	public static String getRecordIds(String[] recordIds) {
		if (recordIds == null || recordIds.length == 0)
			return "";
		if (recordIds.length == 1)
			return recordIds[0];
		StringBuffer ids = new StringBuffer();
		for (int i = 0; i < recordIds.length; i++) {
			if (i == recordIds.length - 1) {
				ids.append("'" + recordIds[i] + "'");
			} else {
				ids.append("'" + recordIds[i] + "'" + ",");
			}
		}
		return ids.toString();
	}

	/**
	 * 将字符串数组转化成中间用逗号分割的字符串 "'a','b','c'"
	 */
	public static String getRecordIdsStr(String[] recordIds) {
		if (recordIds == null || recordIds.length == 0)
			return "";
		if (recordIds.length == 1)
			return "'" + recordIds[0] + "'";
		StringBuffer ids = new StringBuffer();
		for (int i = 0; i < recordIds.length; i++) {
			if (i == recordIds.length - 1) {
				ids.append("'" + recordIds[i] + "'");
			} else {
				ids.append("'" + recordIds[i] + "'" + ",");
			}
		}
		return ids.toString();
	}

	/**
	 * 将字符串数组转化成中间用逗号分割的字符串 "a,b,c"
	 */
	public static String getStrs(String[] strs) {
		if (strs == null || strs.length == 0)
			return "";
		if (strs.length == 1)
			return strs[0];
		StringBuffer ids = new StringBuffer();
		for (int i = 0; i < strs.length; i++) {
			if (i == strs.length - 1) {
				ids.append(strs[i]);
			} else {
				ids.append(strs[i] + ",");
			}
		}
		return ids.toString();
	}

	/**
	 * 处理Url与其参数的组合
	 * 
	 * @param url页面Url
	 * @param param被加入到该Url后的参数
	 * @return 一个完整的Url,包括参数
	 */
	public static String dealUrl(String url, String param) {
		String orgUrl = url;
		url = removeEnd(url, '#');
		url = removeEnd(url, '?');
		if (StringUtils.isBlank(url)) {
			return "";
		}

		if (url.lastIndexOf('/') == (url.length() - 1)) {
			url += "index.html";
		}

		if (StringUtils.isBlank(param)) {
			return orgUrl;
		}

		param = removeStart(param, '&');
		param = removeStart(param, '?');
		if (StringUtils.isBlank(param)
				|| (param.indexOf("=") == -1)
				|| (param.indexOf("=") > 0 && (param.indexOf("=") == (param
						.length() - 1)))) {
			return url;
		}
		if (url.indexOf("?") > 0) {
			url += "&" + param;
		} else {
			url += "?" + param;
		}
		return url;
	}

	public static String deleteUrlParam(String url, String key) {
		if (url == null || url.length() < 1)
			return "";
		url = url.trim();
		if (key == null || key.length() < 1)
			return url;
		key = key.trim();

		return "";
	}

	/**
	 * 得到指定符号前或后的字符
	 */
	private static String getPreOrSufStr(String str, int action,String splitSign) {
		if (str == null || str.equals(""))
			return "";
		int index = -1;
		if ((index = str.indexOf(splitSign)) != -1) {
			if (action == 0)
				return str.substring(index + 1).trim();
			return str.substring(0, index).trim();
		}
		return str;
	}

	/**
	 * 得到指定符号前的字符
	 */
	public static String getPreStr(String str,String splitSign) {
		return getPreOrSufStr(str, 1,splitSign);
	}

	/**
	 * 得到指定符号后的字符
	 */
	public static String getSufStr(String str,String splitSign) {
		return getPreOrSufStr(str, 0,splitSign);
	}

	/**
	 * 在不足len位的数字前面自动补零
	 */
	public static String getLimitLenStr(String str, int len) {
		if (str == null) {
			return "";
		}
		while (str.length() < len) {
			str = "0" + str;
		}
		return str;
	}

	/**
	 * 字符串GBK到UTF-8码的转化
	 * 
	 * @param inStr
	 *            GBK编码的字符串
	 * @return UTF-8编码的字符串
	 */
	public static String wapGbkToUtf(String inStr) {
		char temChr;
		int ascInt;
		int i;
		String result = new String("");
		if (inStr == null) {
			inStr = "";
		}
		for (i = 0; i < inStr.length(); i++) {
			temChr = inStr.charAt(i);
			ascInt = temChr + 0;
			if (ascInt > 255) {
				result = result + "&#x" + Integer.toHexString(ascInt) + ";";
			} else {
				result = result + temChr;
			}
		}
		return result;
	}

	/**
	 * URL转码
	 */
	public static String encodeStr(String str, String en) {
		try {
			return URLEncoder.encode(str, en);
		} catch (UnsupportedEncodingException e) {
			return "";
		}
	}

	/**
	 * URL转码
	 */
	public static String encodeStr(String str) {
		try {
			if (StringUtils.isBlank(str)) {
				str = "";
			}

			return URLEncoder.encode(str, "utf-8");
		} catch (UnsupportedEncodingException e) {
			return "";
		}
	}

	/**
	 * URL解码
	 */
	public static String decodeStr(String str) {
		return decodeStr(str, "utf-8");
	}

	/**
	 * URL解码
	 */
	public static String decodeStr(String str, String en) {
		try {
			return URLDecoder.decode(str, en);
		} catch (UnsupportedEncodingException e) {
			return "";
		}
	}

	/**
	 * EMAIL验证
	 */
	public static boolean isMail(String str) {
		String check = "^([a-z0-9A-Z]+[-|\\._]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";
		Pattern regex = Pattern.compile(check);
		Matcher matcher = regex.matcher(str);
		boolean isMatched = matcher.matches();
		if (isMatched) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 验证手机号
	 */
	public static boolean isTelNo(String telno) {
		String expression = "(^13\\d{9}$|^15[8-9]{1}\\d{8}$)";
		return (Utils.isValidate(telno, expression));
	}

	/**
	 * 特殊字符替换
	 */
	public static String replaceStrHtml(String inStr) {
		String result = inStr;
		if (result != null && "".equals(result)) {
			result = result.replaceAll("\r\n", "<br>");
			result = result.replaceAll(" ", "&nbsp;");
		}
		return result;
	}

	public static boolean isGradeCoe(String value, String expression) {

		Pattern pattern = Pattern.compile(expression);
		Matcher matcher = pattern.matcher(value);

		return matcher.find();
	}

	/**
	 * 取字符的前几位 value,n
	 */
	public static String getSubString(String value, int i) {
		if (StringUtils.isBlank(value)) {
			return "";
		}
		if (value.length() <= i) {
			return value;
		} else {
			return value.substring(0, i) + "...";
		}
	}

	// XuePeng.Duan
	// 2008-01-25

	/**
	 * 出错的详细信息转化为字符串
	 * 
	 * @param e
	 * @return 错误调用栈详情
	 * 
	 */
	public static String stringifyException(Throwable e) {
		StringWriter stm = new StringWriter();
		PrintWriter wrt = new PrintWriter(stm);
		e.printStackTrace(wrt);
		wrt.close();
		return stm.toString();
	}

	/**
	 * 休息指定的时间
	 * 
	 * @param millisecond
	 *            参数是毫秒
	 */
	public static void sleep(long millisecond) {
		try {
			Thread.sleep(millisecond);
		} catch (InterruptedException e) {
		}
	}

	public static ClassLoader getStandardClassLoader() {
		return Thread.currentThread().getContextClassLoader();
	}

	public static ClassLoader getFallbackClassLoader() {
		return Utils.class.getClassLoader();
	}

	/**
	 * 得到配置文件的URL
	 * 
	 * @param name
	 * @return 配置文件URL
	 * 
	 */
	public static URL getConfURL(String name) {
		try {
			return getStandardClassLoader().getResource(name);
		} catch (Exception e) {
			try {
				return getFallbackClassLoader().getResource(name);
			} catch (Exception ex) {
				return null;
			}
		}
	}

	public static String getClickCacheKey(String phonenum, String advId) {
		return phonenum + "_" + advId;
	}

	public static boolean validateCommonDig(String value) {
		if (StringUtils.isNotBlank(value)) {
			try {
				Double.parseDouble(Utils.trim(value));
				return true;
			} catch (Exception ex) {

			}
		}
		return false;
	}

	/**
	 * 验证正则表达式
	 * 
	 * @param value
	 * @param expression
	 * @return
	 */
	public static boolean isValidate(String value, String expression) {

		Pattern pattern = Pattern.compile(expression);
		Matcher matcher = pattern.matcher(value);

		return matcher.find();
	}

	/**
	 * 
	 * @param str
	 * @return
	 */
	public static List getInform(String[] str) {

		Map map = null;
		String[] recordId = new String[str.length];
		String[] email = new String[str.length];
		String[] ctype = new String[str.length];
		String[] tel = new String[str.length];
		String[] webmaster = new String[str.length];
		String[] webuser = new String[str.length];
		String[] webLogin = new String[str.length];
		int k = 0;
		for (int i = 0; i < str.length; i++) {
			java.util.StringTokenizer tokenTO = new StringTokenizer(str[i], ",");
			int j = 0;
			while (tokenTO.hasMoreTokens()) {
				try {
					if (j == 0) {
						recordId[k] = tokenTO.nextToken().toString();// 客户端ID号
					} else if (j == 1) {
						email[k] = tokenTO.nextToken().toString();// Email地址
					} else if (j == 2) {
						ctype[k] = tokenTO.nextToken().toString();// 网站类型
					} else if (j == 3) {
						tel[k] = tokenTO.nextToken().toString();// 手机号
					} else if (j == 4) {
						webmaster[k] = tokenTO.nextToken().toString();// 网站主名称
					} else if (j == 5) {
						webuser[k] = tokenTO.nextToken().toString();// 网站主用户ID
					} else {
						webLogin[k] = tokenTO.nextToken().toString();// 网站主登陆ID
					}
					j++;
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			k++;
		}
		List list = new ArrayList();
		list.add(recordId);
		list.add(email);
		list.add(ctype);
		list.add(tel);
		list.add(webmaster);
		list.add(webuser);
		list.add(webLogin);
		// map.put("recordId",(String[])recordId);
		// map.put("email",(String[])email);
		// map.put("ctype",(String[])ctype);
		// map.put("tel",(String[])tel);

		return list;
	}

	/**
	 * 比较数组中的数据是否有相等的数据
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isEquals(String[] inptstr, String param) {

		if (null == inptstr)
			return false;
		if (StringUtils.isBlank(param) || param.equals("http://"))
			param = "";
		for (int i = 0; i < inptstr.length; i++) {
			for (int j = 0; j < inptstr.length; j++) {
				if ((inptstr[i].equals(inptstr[j]) && i != j)
						|| (inptstr[j].toLowerCase()).equals(param.trim())
						|| StringUtils.isBlank(inptstr[i])
						|| inptstr[i].equals("http://")) {
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * 取得一个随机数,取值范围是0 <= value < scope
	 * 
	 * @param scope
	 *            最大值
	 */
	public static int getRandom(int scope) {
		Random random = new Random();
		return random.nextInt(scope);
	}

	/**
	 * 准装返回List
	 * 
	 * @param args
	 */
	public static List<?> returnList(String name, String[] vales) {
		List list = new ArrayList();
		HashMap map = null;
		for (int i = 0; i < vales.length; i++) {
			map = new HashMap();
			map.put(name, vales[i]);
			list.add(map);
			map = null;
		}
		return list;
	}

	/**
	 * @Description:截取字符串并以"..."结尾
	 * @param inputText
	 *            输入内容
	 * @param length
	 *            截取字节数
	 * @return 截取后的字符串
	 */
	public static String trimStr(String inputText, int length) {
		// inputText = "[转贴] 独立Wap发展应以内容为王 ";// 输入字符
		int len = 0;
		if (length < 0) {
			length = 24;
		}
		char[] charArray = inputText.toCharArray();
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < charArray.length; i++) {
			char cn = charArray[i];
			byte[] bytes = (String.valueOf(cn)).getBytes();
			len = len + bytes.length;
			if (len > length) {
				sb.append("...");
				break;
			}
			sb.append(cn);

		}
		return sb.toString();
	}
	
	public static double division(double numerator,double denominator,int digit){
		double result = 0;
		result = Math.round((numerator/denominator)*Math.pow(10, digit))/Math.pow(10, digit);
		return result;
	}
	
	public static String getStringForJson(String str) {
		if(str!=null){
			str = str.replace("\r", "\\r");
			str = str.replace("\n", "\\n");
			str = str.replace("\'", "\\\'");
			str = str.replace("\"", "\\\"");
			return str;
		}else{
			return "";
		}
	}
	
	/**
	 * 二维数组，list 套 list的 笛卡尔积合并运算
	 * @param al0
	 * @return
	 */
	public static ArrayList Dikaerji0(ArrayList al0) {  
		  
        ArrayList a0 = (ArrayList) al0.get(0);// l1  
        ArrayList result = new ArrayList();// 组合的结果  
        for (int i = 1; i < al0.size(); i++) {  
            ArrayList a1 = (ArrayList) al0.get(i);  
            ArrayList temp = new ArrayList();  
            // 每次先计算两个集合的笛卡尔积，然后用其结果再与下一个计算  
            for (int j = 0; j < a0.size(); j++) {  
                for (int k = 0; k < a1.size(); k++) {  
                    ArrayList cut = new ArrayList();  
  
                    if (a0.get(j) instanceof ArrayList) {  
                        cut.addAll((ArrayList) a0.get(j));  
                    } else {  
                        cut.add(a0.get(j));  
                    }  
                    if (a1.get(k) instanceof ArrayList) {  
                        cut.addAll((ArrayList) a1.get(k));  
                    } else {  
                        cut.add(a1.get(k));  
                    }  
                    temp.add(cut);  
                }  
            }  
            a0 = temp;  
            if (i == al0.size() - 1) {  
                result = temp;  
            }  
        }  
        return result;  
    }  

	// 测试、
	public static void main(String[] args) {
		System.out.println(Utils.encodeStr("湖北移动","GBK"));

//		System.out.println("=====4月份  " + (49 + 4 - 28 + 19) % 9);
//		System.out.println("=====5月份  " + (49 + 5 - 28 + 19) % 9);
//		System.out.println("=====6月份  " + (49 + 6 - 28 + 19) % 9);
//		System.out.println("=====7月份  " + (49 + 7 - 28 + 19) % 9);
//		System.out.println("=====8月份  " + (49 + 8 - 28 + 19) % 9);
//		System.out.println("=====9月份  " + (49 + 9 - 28 + 19) % 9);
//		System.out.println("=====10月份  " + (49 + 10 - 28 + 19) % 9);
//		
//		System.out.println(trimStr("即进即送现金100即进即送现金100即进即送现金100即进即送现金100",24));
		
	}
}
