package com.newsmths.util;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashSet;

import org.apache.log4j.Logger;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.newsmths.bdb.BDBHelper;
import com.newsmths.bean.NoticeBean;
import com.newsmths.tfidf.TFIDFUtil;

public class W2vUtil {

	private static Logger log = Logger.getLogger(W2vUtil.class);

	public void genWordFile() {
		// 初始化BDB
		BDBHelper mbdb = new BDBHelper();
		mbdb.init();
		String strWords = null;
		try {
			strWords = mbdb.get("words");
		} catch (Exception e) {
			e.printStackTrace();
			log.error("", e);
		}

		Gson gson = new Gson();
		ArrayList<String> words = null;
		if (strWords != null && !"".equals(strWords)) {
			// 获取标签相关的文档列表
			words = (ArrayList<String>) gson.fromJson(strWords,
					new TypeToken<ArrayList<String>>() {
					}.getType());

		}

		if (null != words && words.size() > 0) {

			StringBuilder sBlder = new StringBuilder();
			for (int i = 0; i < words.size(); i++) {
				sBlder.append(words.get(i) + "\n");
			}

			System.out.println(sBlder.toString() + "\n" + words.size());

			try {
				// FileOutputStream out = new FileOutputStream("words.txt");
				// OutputStreamWriter out = new OutputStreamWriter(new
				// FileOutputStream("words.txt"), "UTF-8", true);
				// out.write(sBlder.toString());
				// out.flush();
				// out.close();
				PrintWriter pw = new PrintWriter(new OutputStreamWriter(
						new FileOutputStream("words.txt"), "UTF-8"), true);
				pw.println(sBlder.toString());
			} catch (FileNotFoundException e) {
				e.printStackTrace();
				log.error("", e);
			} catch (IOException e) {
				e.printStackTrace();
				log.error("", e);
			}

		}

	}

	// 从Word2Vec获取词向量
	public String getW2v(String key) {
		String cmd = "";
		if (null == key || "".equals(key)) {
			return null;
		}
		cmd = "/home/liyankai/github/wordvec-demo/distance-sh /home/liyankai/github/wordvec-demo/out.bin "
				+ key;

		StringBuffer sBuff = new StringBuffer();
		try {

			Process process = Runtime.getRuntime().exec(cmd);
			InputStreamReader ir = new InputStreamReader(
					process.getInputStream());
			BufferedReader input = new BufferedReader(ir);

			String line = null;
			while ((line = input.readLine()) != null) {
				sBuff.append(line);
			}
		} catch (IOException e) {
			e.printStackTrace();
			log.error("", e);
		}
		return sBuff.toString();
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		W2vUtil util = new W2vUtil();
		util.genWordFile();
	}

}
