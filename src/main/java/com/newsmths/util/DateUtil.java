package com.newsmths.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil {
	/*
	 * Input: Fri Feb 7 10:50:36 2014 EE MMM d HH:mm:ss yyyy
	 */

	public String getTime(String str) {
		String oft = "yyyy-MM-dd HH:mm:ss";
		SimpleDateFormat sdf = new SimpleDateFormat(oft);
		sdf = new SimpleDateFormat(oft);
		return sdf.format(new Date(str));
	}

	public static void main(String[] args) {
		DateUtil util = new DateUtil();
		System.out.println(util.getTime("Fri Feb 7 10:50:36 2014"));
		System.out.println(util.getTime("Sun Jan 26 10:32:40 2014"));
	}
}
