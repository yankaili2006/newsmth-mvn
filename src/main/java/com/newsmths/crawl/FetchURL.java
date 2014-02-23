/**  
 * @Packagecom.request
 * @author yankaili@gmail.com  
 * @date Jun 16, 2011 7:10:24 PM 
 */
package com.newsmths.crawl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import org.apache.log4j.Logger;

import com.newsmths.bdb.WordVec;
import com.newsmths.extract.ExtractorAll;

/**
 * @author Administrator 7:10:24 PM
 */
public class FetchURL {

	private static Logger log = Logger.getLogger(FetchURL.class);

	/**
	 * 
	 */
	public FetchURL() {
		
	}

	/**
	 * @param urlString
	 * @return URL对应的HTML字符串
	 */
	public String getPage(String urlString) {

		URL url;
		try {
			url = new URL(urlString);
		} catch (MalformedURLException e) {
			e.printStackTrace();
			return null;
		}
		// 创建HTTP连接
		HttpURLConnection connection;
		try {
			connection = (HttpURLConnection) url.openConnection();
			connection.setRequestProperty("User-Agent",
					"Mozilla/4.0 (compatible; MSIE 6.0; Windows 2000)");
			connection.connect();
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}

		InputStream in = null;
		int tryTimes = 5;
		boolean connectOK = false;
		while (tryTimes >= 0 && !connectOK) {
			try {
				in = connection.getInputStream();
				connectOK = true;
			} catch (IOException e1) {
				e1.printStackTrace();
			} finally {
				tryTimes--;
			}
		}
		if (null == in) {
			log.error("getInputStream is null");
			return null;
		}

		StringBuffer buffer = new StringBuffer();
		try {
			BufferedReader breader = new BufferedReader(new InputStreamReader(
					in, "GBK"));
			String str = null;
			while ((str = breader.readLine()) != null) {
				buffer.append(str + "\n");
			}
			if (breader != null) {
				breader.close();
			}
			if (in != null) {
				in.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}

		return buffer.toString();
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		FetchURL request = new FetchURL();
		String url = "http://www.newsmth.net/bbsdoc.php?board=SecondComputer&ftype=6";
		log.info(request.getPage(url));
	}

}
