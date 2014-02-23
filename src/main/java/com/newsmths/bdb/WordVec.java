package com.newsmths.bdb;

import java.util.ArrayList;
import java.util.HashSet;

import org.apache.log4j.Logger;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.newsmths.bean.NoticeBean;
import com.newsmths.extract.ExtractorAll;
import com.newsmths.util.DBUtil;
import com.newsmths.util.PropHelper;

public class WordVec {
	private static Logger log = Logger.getLogger(WordVec.class);

	public void printWords() {
		BDBHelper bdb = new BDBHelper();
		bdb.init();

		Gson gson = new Gson();
		try {
			String wordStr = bdb.get("words");
			if (wordStr != null) {
				ArrayList<String> wordList = gson.fromJson(wordStr,
						new TypeToken<ArrayList<String>>() {
						}.getType());

				if (wordList != null && wordList.size() > 0) {
					log.info("wordList.size:" + wordList.size());
					for (int i = 0; i < wordList.size(); i++) {
						log.info(wordList.get(i) + " ");
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public static void main(String[] args) {

		WordVec w2v = new WordVec();
		w2v.printWords();
	}

}
