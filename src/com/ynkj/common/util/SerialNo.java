package com.ynkj.common.util;

import java.text.NumberFormat;
import java.util.Date;

public class SerialNo {

	public SerialNo() {
	}

	public static synchronized String getUNID() {
		String currentTime = DateFunc.getCurrentDateString("yyMMddHHmmssSSS");
		if (compareTime == null || compareTime.compareTo(currentTime) != 0) {
			compareTime = currentTime;
			sequence = 1L;
		} else {
			sequence++;
		}
		return (new StringBuilder(String.valueOf(currentTime))).append(numberFormat.format(sequence)).toString();
	}
	
	//生成订单ID
	public static synchronized String getOrderID() {
		//String currentTime = DateFunc.getCurrentDateString("yyyyMMddHHmmssSSS");
		String currentTime = DateFunc.getCurrentDateString("yyMMddHHmmssSSS");
		if (compareTime == null || compareTime.compareTo(currentTime) != 0) {
			compareTime = currentTime;
			sequence = 1L;
		} else {
			sequence++;
		}
		return (new StringBuilder(String.valueOf(currentTime))).append(smallFormat.format(sequence)).toString();
	}
	
	public static synchronized String getUNID2() {
		String currentTime = DateFunc.getCurrentDateString("MMddHHmmssSSS");
		if (compareTime == null || compareTime.compareTo(currentTime) != 0) {
			compareTime = currentTime;
			sequence = 1L;
		} else {
			sequence++;
		}
		return (new StringBuilder(String.valueOf(currentTime))).append(
				numberFormat.format(sequence)).toString();
	}
	
	public static synchronized String getDateID() {
		String currentTime = DateFunc.getCurrentDateString("yyyyMMdd");
		if (compareTime == null || compareTime.compareTo(currentTime) != 0) {
			compareTime = currentTime;
			sequence = 1L;
		} else {
			sequence++;
		}
		return (new StringBuilder(String.valueOf(currentTime))).append(
				numberFormat.format(sequence)).toString();
	}
	
	/**
	 * 字符串长度为30
	 * @return
	 */
	public static synchronized String getLongUNID() {
		String currentTime = DateFunc.getCurrentDateString("yyyyMMddHHmmssSSS");
		if (longTime == null || longTime.compareTo(currentTime) != 0) {
			longTime = currentTime;
			longSequence = 1L;
		} else {
			longSequence++;
		}
		return (new StringBuilder(String.valueOf(currentTime))).append(
				longFormat.format(longSequence)).toString();
	}

	public static String getSerialforDB() {
		return DateFunc.getCurrentDateString("yyMMddHHmmssSSS");
	}

	public static String getShortSerial() {
		return DateFunc.getCurrentDateString("mmssSSS");
	}

	public static synchronized String getSmallUNID() {
		String currentTime = DateFunc.getCurrentDateString("yyMMddHHmmss");
		if (smallTime == null || smallTime.compareTo(currentTime) != 0) {
			smallTime = currentTime;
			smallSequence = 1L;
		} else {
			smallSequence++;
		}
		return (new StringBuilder(String.valueOf(currentTime))).append(
				smallFormat.format(smallSequence)).toString();
	}

	private static long sequence;
	private static String compareTime;
	private static NumberFormat numberFormat;
	private static long smallSequence;
	private static String smallTime;
	private static NumberFormat smallFormat;
	
	private static long longSequence;
	private static String longTime;
	private static NumberFormat longFormat;


	static {
		numberFormat = NumberFormat.getInstance();
		numberFormat.setGroupingUsed(false);
		numberFormat.setMinimumIntegerDigits(5);
		numberFormat.setMaximumIntegerDigits(5);
		smallFormat = NumberFormat.getInstance();
		smallFormat.setGroupingUsed(false);
		smallFormat.setMinimumIntegerDigits(4);
		smallFormat.setMaximumIntegerDigits(4);
		longFormat = NumberFormat.getInstance();
		// 是否允许分组 1123456=》1,123,456
		longFormat.setGroupingUsed(false);
		// 允许小整数位数
		longFormat.setMinimumIntegerDigits(13);
		// 允许大整数位数
		longFormat.setMaximumIntegerDigits(13);
	}
	
	public static void main(String[] args) {
		String pk = SerialNo.getLongUNID();
		System.out.println(SerialNo.getSmallUNID());
		System.out.println(pk.length());
		System.out.println(new  Date().getTime());
	}
}
