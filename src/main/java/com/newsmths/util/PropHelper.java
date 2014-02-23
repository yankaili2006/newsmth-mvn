package com.newsmths.util;

import java.io.IOException;
import java.util.Enumeration;
import java.util.Properties;

public class PropHelper {

	public Properties getProp() {
		Properties prop = null;
		if (prop == null) {
			try {
				prop = new Properties();
				prop.load(PropHelper.class.getClassLoader()
						.getResourceAsStream("conf.properties"));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return prop;
	}

	public static void main(String args[]) throws IOException {
		Properties pro = new Properties();
		pro.load(PropHelper.class.getClassLoader().getResourceAsStream(
				"conf.properties"));
		System.out.println(pro.getProperty("db"));
		Enumeration<?> en = pro.propertyNames();
		while (en.hasMoreElements()) {
			String key = (String) en.nextElement();
			String Property = pro.getProperty(key);
			System.out.println(key + "=" + Property);

		}
	}
}
