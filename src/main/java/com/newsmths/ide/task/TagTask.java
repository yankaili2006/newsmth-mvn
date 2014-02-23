package com.newsmths.ide.task;

import java.util.ArrayList;
import java.util.TimerTask;

import org.apache.log4j.Logger;

import com.newsmths.bean.NoticeBean;
import com.newsmths.tfidf.TFIDFUtil;
import com.newsmths.util.DBUtil;
import com.newsmths.util.PropHelper;

public class TagTask extends TimerTask {

	private static boolean isRunning = false;
	private static Logger log = Logger.getLogger(TagTask.class);

	public void run() {
		
		if (!isRunning) {
			isRunning = true;
			log.info("开始执行标签任务..."); // 开始任务

			TFIDFUtil tfidf = new TFIDFUtil();
			tfidf.loadFromBDB();

			DBUtil util = new DBUtil();
			ArrayList<NoticeBean> topics = util.getTopicListPageNotTFIDF(1, 100);
			for (int i = 0; i < topics.size(); i++) {
				tfidf.addDoc(topics.get(i).getGid(), topics.get(i).getContent());
			}
			
			tfidf.calculateTFIDF();

			try {
				tfidf.add2BDB();
				tfidf.add2DB();
			} catch (Exception e) {
				e.printStackTrace();
			}
			log.info("执行标签任务完成..."); // 任务完成
			
			isRunning = false;
		} else {
			log.info("上一次任务执行还未结束..."); // 上一次任务执行还未结束
		}
	}
}
