package com.ynkj.common.util;

import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;

/**
 * 系统时间工具类
 * 
 */
public class DateTimeUtil {

	/** 缺省日期格式yyyy-MM-dd */
	public static final String DEFAULT_DATE_FORMAT = "yyyy-MM-dd";
	/** 缺省时间格式HH:mm:ss */
	public static final String DEFAULT_TIME_FORMAT = "HH:mm:ss";

	/** 缺省月格式MONTH */
	public static final String DEFAULT_MONTH = "MONTH";

	/** 缺省年格式YEAR */
	public static final String DEFAULT_YEAR = "YEAR";

	/** 缺省日格式DAY */
	public static final String DEFAULT_DATE = "DAY";
	/**
	 * 缺省日期格式HOUR
	 */
	public static final String DEFAULT_HOUR = "HOUR";

	/** 缺省长日期格式yyyy-MM-dd HH-mm */
	public static final String DEFAULT_DATETIME_FORMAT = "yyyy-MM-dd HH:mm";

	/** 缺省长日期格式,精确到秒yyyy-MM-dd HH:mm:ss */
	public static final String DEFAULT_DATETIME_FORMAT_SEC = "yyyy-MM-dd HH:mm:ss";

	public static final String FILE_NAME_DATE = "yyyyMMddHHmmss";

	/**
	 * 功能 ： 字符串(yyyy-MM-dd HH:mm:ss)返回时间
	 * 
	 * @param time
	 * @return
	 */
	public static Date StringToDate(String time) {
		Date date = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat(
				DateTimeUtil.DEFAULT_DATETIME_FORMAT_SEC);
		try {
			date = formatter.parse(time);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date;
	}

	/**
	 * 
	 * 功能 ： 返回时间字符串(yyyyMMddHHmmss)
	 */
	public static String getFileNameDate() {
		Date currentTime = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat(
				DateTimeUtil.FILE_NAME_DATE);
		String dateString = formatter.format(currentTime);
		return dateString;
	}

	/**
	 * 
	 * 功能 ： 返回时间字符串(HH:mm:ss)
	 */
	public static String getNowTime() {
		Date currentTime = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat(
				DateTimeUtil.DEFAULT_TIME_FORMAT);
		String dateString = formatter.format(currentTime);
		return dateString;
	}

	/**
	 * 
	 * 功能 ： 返回时间字符串(yyyy-MM-dd HH:mm:ss)
	 */
	public static String getNowDateTime() {
		Date currentTime = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat(
				DateTimeUtil.DEFAULT_DATETIME_FORMAT_SEC);
		String dateString = formatter.format(currentTime);
		return dateString;
	}

	/**
	 * 
	 * 功能 ： 返回 某年某月某日 星期几 几点几分的格式
	 */
	public static String showTime(String s) {
		String timeValue = "";
		int year = getYear(s);
		int month = getMonth(s);
		int date = getDay(s);
		int hours = getHour(s);
		int minutes = getMinute(s);
		int seconds = getSecond(s);
		String week = getWeek(s);
		timeValue += year + "年";
		timeValue += ((month < 10) ? "0" : "") + month + "月";
		timeValue += ((date < 10) ? "0" : "") + date + "日  ";
		timeValue += week + "  ";
		timeValue += (hours < 12) ? "上午" : "下午";
		timeValue += ((hours <= 12) ? hours : hours - 12);
		timeValue += ((minutes < 10) ? ":0" : ":") + minutes;
		timeValue += ((seconds < 10) ? ":0" : ":") + seconds;

		return timeValue;
	}

	/**
	 * 
	 * 功能 ： 返回日期字符串("yyyy/mm/dd hh:ss:mm")的年
	 */
	public static int getYear(String s) {
		try {
			return Integer.parseInt(s.substring(0, 4));
		} catch (Exception e) {
			return 1970;
		}
	}

	/**
	 * 
	 * 功能 ： 返回日期字符串("yyyy/mm/dd hh:ss:mm")的月
	 */
	public static int getMonth(String s) {
		try {
			return Integer.parseInt(s.substring(5, 7));
		} catch (Exception e) {
			return 1;
		}
	}

	/**
	 * 
	 * 功能 ：返回日期字符串("yyyy/mm/dd hh:ss:mm")的日
	 */
	public static int getDay(String s) {
		try {
			return Integer.parseInt(s.substring(8, 10));
		} catch (Exception e) {
			return 1;
		}
	}

	/**
	 * 
	 * 功能 ： 返回日期字符串("yyyy/mm/dd hh:ss:mm")的时
	 */
	public static int getHour(String s) {
		try {
			return Integer.parseInt(s.substring(11, 13));
		} catch (Exception e) {
			return 0;
		}
	}

	public static String getStringHour(String s) {
		try {
			return Integer.parseInt(s.substring(11, 13)) < 10 ? "0"
					+ Integer.parseInt(s.substring(11, 13)) : Integer
					.parseInt(s.substring(11, 13)) + "";
		} catch (Exception e) {
			return "";
		}
	}

	/**
	 * 
	 * 功能 ： 返回日期字符串("yyyy/mm/dd hh:ss:mm")的分
	 */
	public static int getMinute(String s) {
		try {
			return Integer.parseInt(s.substring(14, 16));
		} catch (Exception e) {
			return 0;
		}
	}

	public static String getStringMinute(String s) {
		try {
			return Integer.parseInt(s.substring(14, 16)) < 10 ? "0"
					+ Integer.parseInt(s.substring(14, 16)) : Integer
					.parseInt(s.substring(14, 16)) + "";
		} catch (Exception e) {
			return "";
		}
	}

	/**
	 * 
	 * 功能 ： 返回日期字符串("yyyy/mm/dd hh:ss:mm")的秒
	 */
	public static int getSecond(String s) {
		try {
			return Integer.parseInt(s.substring(17, 19));
		} catch (Exception e) {
			return 0;
		}
	}

	public static String getStringSecond(String s) {
		try {
			return Integer.parseInt(s.substring(17, 19)) < 10 ? "0"
					+ Integer.parseInt(s.substring(17, 19)) : Integer
					.parseInt(s.substring(17, 19)) + "";
		} catch (Exception e) {
			return "";
		}
	}

	/**
	 * 根据一个日期，返回是星期几的字符串
	 */
	public static String getWeek(String sdate) {
		// 再转换为时间
		Date date = strToDate(sdate);
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		// int hour=c.get(Calendar.DAY_OF_WEEK);
		// hour中存的就是星期几了，其范围 1~7
		// 1=星期日 7=星期六，其他类推
		return new SimpleDateFormat("EEEE").format(c.getTime());
	}

	// 返回日期型时间
	public static Date strToDate(String strDate) {
		SimpleDateFormat formatter = new SimpleDateFormat(
				DateTimeUtil.DEFAULT_DATETIME_FORMAT_SEC);
		ParsePosition pos = new ParsePosition(0);
		Date strtodate = formatter.parse(strDate, pos);
		return strtodate;
	}

	/**
	 * 返回时间字符串(yyyy-MM-dd)
	 * 
	 * @return
	 */
	public static String getStringDate() {
		Date currentTime = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat(
				DateTimeUtil.DEFAULT_DATE_FORMAT);
		String dateString = formatter.format(currentTime);
		return dateString;
	}

	/**
	 * 比较时间大小
	 * 
	 * @param startTime
	 * @param endTime
	 * @param type
	 * @return
	 */
	public static long getTimeDX(String startTime, String endTime, String type) {
		try {

			java.text.SimpleDateFormat formatter = new java.text.SimpleDateFormat(
					type);

			java.text.SimpleDateFormat endformatter = new java.text.SimpleDateFormat(
					type);

			Calendar rightNow = Calendar.getInstance();

			Calendar endrightNow = Calendar.getInstance();

			Date startTempdate = formatter.parse(startTime);

			Date endTempdate = endformatter.parse(endTime);

			// Long out1 = startTempdate.getTime() - endTempdate.getTime();

			rightNow.setTime(startTempdate);
			endrightNow.setTime(endTempdate);

			Long start = rightNow.getTime().getTime();
			Long end = endrightNow.getTime().getTime();
			Long result = end - start;

			return result;

		} catch (Exception e) {

			return 0;
		}
	}

	/**
	 * 
	 * 功能 ：两个时间间隔，对开始时间和结束时间大小无要求
	 */
	public static int getTimeInterval(String startTime, String endTime,
			String type) {

		try {

			Long inv = -1l;
			java.text.SimpleDateFormat formatter = new java.text.SimpleDateFormat(
					type);

			java.text.SimpleDateFormat endformatter = new java.text.SimpleDateFormat(
					type);

			Calendar rightNow = Calendar.getInstance();

			Calendar endrightNow = Calendar.getInstance();

			Date startTempdate = formatter.parse(startTime);

			Date endTempdate = endformatter.parse(endTime);

			// Long out1 = startTempdate.getTime() - endTempdate.getTime();

			rightNow.setTime(startTempdate);
			endrightNow.setTime(endTempdate);

			Long start = rightNow.getTime().getTime();
			Long end = endrightNow.getTime().getTime();
			Long result = end - start;

			String tmpField = type;

			if (tmpField.equals(DEFAULT_YEAR)) {

				inv = result / (1000 * 60 * 60 * 24 * 365l);

			} else if (tmpField.equals(DEFAULT_MONTH)) {

				inv = result / (1000 * 60 * 60 * 24 * 30l);

			} else if (tmpField.equals(DEFAULT_DATE)) {

				inv = result / (1000 * 60 * 60 * 24l);
			} else if (tmpField.equals(DEFAULT_DATETIME_FORMAT_SEC)) {
				return result.intValue();
			}

			return Integer.parseInt(inv + "");

		} catch (Exception e) {

			return 0;
		}
	}

	/**
	 * 
	 * 功能 ：通过季度返回月份数组
	 */
	public static Integer[] getMonthBySeason(int season) {

		try {
			Integer[] month = new Integer[3];
			switch (season) {
			case 1:
				month[0] = 1;
				month[1] = 2;
				month[2] = 3;
				break;
			case 2:
				month[0] = 4;
				month[1] = 5;
				month[2] = 6;
				break;
			case 3:
				month[0] = 7;
				month[1] = 8;
				month[2] = 9;
				break;
			case 4:
				month[0] = 10;
				month[1] = 11;
				month[2] = 12;
				break;

			default:
				break;
			}

			return month;
		} catch (Exception e) {

			return new Integer[0];
		}

	}

	/**
	 * 
	 * 功能 ：通过月份返回季度
	 * 
	 */
	public static int getSeasonByMonth(int month) {

		try {
			int season = -1;
			switch (month) {
			case 1:
				season = 1;
				break;
			case 2:
				season = 1;
				break;
			case 3:
				season = 1;
				break;
			case 4:
				season = 2;
				break;
			case 5:
				season = 2;
				break;
			case 6:
				season = 2;
				break;
			case 7:
				season = 3;
				break;
			case 8:
				season = 3;
				break;
			case 9:
				season = 3;
				break;
			case 10:
				season = 4;
				break;
			case 11:
				season = 4;
				break;
			case 12:
				season = 4;
				break;
			default:
				season = 0;
				break;
			}

			return season;

		} catch (Exception e) {

			return 0;
		}

	}

	/**
	 * 功能 ：对于给定的时间增加天数/月数/年数后的日期,按指定格式输出
	 * 
	 * @param date
	 *            时间
	 * @param type
	 *            增加的类型
	 * @param interval
	 *            增加的值
	 * @param strFormat
	 *            时间类型
	 * @return
	 */
	public static String getEndTime(String date, String type, int interval,
			String strFormat) {

		try {

			java.text.SimpleDateFormat formatter = new java.text.SimpleDateFormat(
					strFormat);

			Calendar rightNow = Calendar.getInstance();
			Date tempdate;
			tempdate = formatter.parse(date);

			rightNow.setTime(tempdate);

			int intField = 0;
			String tmpField = type.toUpperCase();

			intField = Calendar.DATE;
			if (tmpField.equals(DEFAULT_YEAR))
				intField = Calendar.YEAR;
			if (tmpField.equals(DEFAULT_MONTH))
				intField = Calendar.MONTH;
			if (tmpField.equals(DEFAULT_DATE))
				intField = Calendar.DATE;

			rightNow.add(intField, interval);
			String day = formatter.format(rightNow.getTime());

			return day;

		} catch (ParseException e) {
			return "有异常";
		}
	}

	/**
	 * 根据指定日期得到是当月的第几周
	 */
	public static int getWeekNumOfMonth(String strDate) {
		Calendar cal = null;
		cal = new GregorianCalendar();
		int yy = Integer.parseInt(strDate.substring(0, 4));
		int mm = Integer.parseInt(strDate.substring(5, 7)) - 1;
		int dd = Integer.parseInt(strDate.substring(8, 10));
		cal.set(yy, mm, dd);
		int iWeekNum = cal.get(Calendar.WEEK_OF_MONTH);
		return iWeekNum;
	}

	/**
	 * 根据指定日期得到是当年的第几周
	 * 
	 */
	public static int getWeekNumOfYear(String strDate) {
		Calendar cal = null;
		cal = new GregorianCalendar();
		int yy = Integer.parseInt(strDate.substring(0, 4));
		int mm = Integer.parseInt(strDate.substring(5, 7)) - 1;
		int dd = Integer.parseInt(strDate.substring(8, 10));
		cal.set(yy, mm, dd);
		int iWeekNum = cal.get(Calendar.WEEK_OF_YEAR);
		return iWeekNum;
	}

	/**
	 * 传入两个时间字符串比较大小 大的为endDate 小的为startDate 放入HashMap中
	 * 
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	public static HashMap<String, Object> compareDate(String startDate,
			String endDate) {
		HashMap<String, Object> map = new HashMap<String, Object>();
		if (startDate != null && !startDate.equals("") && !endDate.equals("")
				&& endDate != null) {
			long long1 = Long.valueOf(startDate.replaceAll("[-\\s:]", ""));
			long long2 = Long.valueOf(endDate.replaceAll("[-\\s:]", ""));
			if (long1 > long2) {
				map.put("startTime", endDate);
				map.put("endTime", startDate);
			} else {
				map.put("startTime", startDate);
				map.put("endTime", endDate);
			}
		} else {
			map.put("startTime", startDate);
			map.put("endTime", endDate);
		}
		return map;
	}

	/**
	 * yyyy-MM-dd HH:mm:ss 转换为 * * * * * ?
	 * 
	 * @param date
	 * @return
	 */
	public static String dateTocronExpression(String date) {
		String arr[] = date.split(" ");
		String day[] = arr[0].split("-");
		String time[] = arr[1].split(":");
		String cronExpression = time[2] + " " + time[1] + " " + time[0] + " "
				+ day[2] + " " + day[1] + " ? " + day[0];
		return cronExpression;
	}

	/****
	 * 根据给定的时间减去 X小时，得到结果
	 * 
	 * @param time
	 *            2014-10-30 14:20
	 * @param hour
	 *            -5(减5小时) 5(加5小时)
	 * @return 2014-10-30 09:20:00
	 */
	public static String timeForValue(String time, int hour) {
		GregorianCalendar gc = new GregorianCalendar();
		String arr[] = time.split(" ");
		String d[] = arr[0].split("-");
		String t[] = arr[1].split(":");
		gc.set(Integer.parseInt(d[0]), Integer.parseInt(d[1]) - 1,
				Integer.parseInt(d[2]), Integer.parseInt(t[0]),
				Integer.parseInt(t[1]), 0);
		gc.add(Calendar.HOUR, hour);
		SimpleDateFormat formatter = new SimpleDateFormat(
				DateTimeUtil.DEFAULT_DATETIME_FORMAT_SEC);
		String dateString = formatter.format(gc.getTime());
		return dateString.substring(0, dateString.length() - 3);
	}

	/****
	 * 比较两个时间大小
	 * 
	 * @param startTime
	 * @param endTime
	 * @return 1 前者比后者大 ，-1 前者比后者小
	 */
	public static int compTime(String time1, String time2) {
		long longTime1 = Long.valueOf(time1.replaceAll("[-\\s:]", ""));
		long longTime2 = Long.valueOf(time2.replaceAll("[-\\s:]", ""));
		if (longTime1 > longTime2) {
			return 1;
		} else {
			return -1;
		}
	}

	/**
	 * app时间转换
	 * 
	 * @param startTime
	 * @param endTime
	 * @param type
	 * @return
	 */
	public static String getTimeIntervalStr(String startTime, String endTime,
			String type) {
		int startDay = getDay(startTime);
		int endDay = getDay(endTime);
		int time = DateTimeUtil.getTimeInterval(startTime, endTime, type);
		int day = time / (1000 * 60 * 60 * 24);
		if (day == 0) {
			if (startDay != endDay)
				return "昨天  " + getHour(startTime) + ":"
						+ startTime.substring(14, 16);
			int hour = time / (1000 * 60 * 60);
			if (hour == 0) {
				int m = time / (1000 * 60);
				if (m == 0) {
					return "刚刚";
				} else if (m > 0) {
					return m + "分钟前";
				} else {
					return getHour(startTime) + ":"
							+ startTime.substring(14, 16);
				}
			} else if (hour > 0) {
				return hour + "小时前";
			} else {
				return getHour(startTime) + ":" + startTime.substring(14, 16);
			}
		} else if (day == 1) {
			return "昨天  " + getHour(startTime) + ":"
					+ startTime.substring(14, 16);
		} else {
			return getMonth(startTime) + "月" + getDay(startTime) + "日";
		}
	}

	/**
	 * 
	 * @param startTime
	 * @param endTime
	 * @param type
	 * @return
	 */
	public static String getChatTimeStr(String startTime, String endTime,
			String type) {
		int startDay = getDay(startTime);
		int endDay = getDay(endTime);
		int time = DateTimeUtil.getTimeInterval(startTime, endTime, type);
		int day = time / (1000 * 60 * 60 * 24);
		if (day == 0) {
			if (startDay == endDay)
				return getHour(startTime) + ":" + startTime.substring(14, 16);
			else
				return "昨天  " + getHour(startTime) + ":"
						+ startTime.substring(14, 16);
		} else if (day == 1) {
			return "昨天  " + getHour(startTime) + ":"
					+ startTime.substring(14, 16);
		} else {
			return getMonth(startTime) + "月" + getDay(startTime) + "日 "
					+ getHour(startTime) + ":" + startTime.substring(14, 16);
		}
	}

	/**
	 * yyyy-MM-dd HH:mm:ss 转换为 * * * * * 不带通配符
	 * 
	 * @param date
	 * @return
	 */
	public static String dateTocronExpression1(String date) {
		String arr[] = date.split(" ");
		String day[] = arr[0].split("-");
		String time[] = arr[1].split(":");
		String cronExpression = "0 " + time[1] + " " + time[0] + " " + day[2]
				+ " " + day[1] + " ? " + day[0];
		return cronExpression;
	}

	/**
	 * dd/MM/yyyy HH:mm 转换为 yyyy-MM-dd HH:mm:ss
	 * 
	 * @param dateTime
	 * @return
	 */
	public static String dateTimeDevConvert(String dateTime) {
		String d = dateTime.split(" ")[0];
		String H = dateTime.split(" ")[1];
		return d.split("/")[2] + "-" + d.split("/")[1] + "-" + d.split("/")[0]
				+ " " + H + ":00";
	}

	public static int getAge(Date birthDay) {
		Calendar cal = Calendar.getInstance();

		if (cal.before(birthDay)) {
			return 0;
		}
		int yearNow = cal.get(Calendar.YEAR);
		int monthNow = cal.get(Calendar.MONTH);
		int dayOfMonthNow = cal.get(Calendar.DAY_OF_MONTH);
		cal.setTime(birthDay);

		int yearBirth = cal.get(Calendar.YEAR);
		int monthBirth = cal.get(Calendar.MONTH);
		int dayOfMonthBirth = cal.get(Calendar.DAY_OF_MONTH);

		int age = yearNow - yearBirth;

		if (monthNow <= monthBirth) {
			if (monthNow == monthBirth) {
				if (dayOfMonthNow < dayOfMonthBirth)
					age--;
			} else {
				age--;
			}
		}
		System.out.println("age:" + age);
		return age;
	}

	/**
	 * 获得当前日期与本周一相差的天数
	 * 
	 * @return
	 */
	private static int getMondayPlus() {
		Calendar cd = Calendar.getInstance();
		// 获得今天是一周的第几天，星期日是第一天，星期二是第二天......
		int dayOfWeek = cd.get(Calendar.DAY_OF_WEEK);
		if (dayOfWeek == 1) {
			return -6;
		} else {
			return 2 - dayOfWeek;
		}
	}

	/**
	 * 获得当前周- 周一的日期
	 * 
	 * @return
	 */
	public static String getCurrentMonday() {
		int mondayPlus = getMondayPlus();
		GregorianCalendar currentDate = new GregorianCalendar();
		currentDate.add(GregorianCalendar.DATE, mondayPlus);
		Date monday = currentDate.getTime();
		SimpleDateFormat df = new SimpleDateFormat(
				DateTimeUtil.DEFAULT_DATE_FORMAT);
		String preMonday = df.format(monday);
		return preMonday + " 00:00:00";
	}

	/**
	 * 获得当前周- 周日 的日期
	 * 
	 * @return
	 */
	public static String getPreviousSunday() {
		int mondayPlus = getMondayPlus();
		GregorianCalendar currentDate = new GregorianCalendar();
		currentDate.add(GregorianCalendar.DATE, mondayPlus + 6);
		Date monday = currentDate.getTime();
		SimpleDateFormat df = new SimpleDateFormat(
				DateTimeUtil.DEFAULT_DATE_FORMAT);
		String preMonday = df.format(monday);
		return preMonday + " 23:59:59";
	}

	public static String getNextDay() {
		Date date = new Date();// 取时间
		Calendar calendar = new GregorianCalendar();
		calendar.setTime(date);
		calendar.add(calendar.DATE, 1);// 把日期往后增加一天.整数往后推,负数往前移动
		date = calendar.getTime(); // 这个时间就是日期往后推一天的结果
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		return formatter.format(date);
	}
	
	public static void main(String[] args) {
		System.out.println(getNextDay());
	}
}
