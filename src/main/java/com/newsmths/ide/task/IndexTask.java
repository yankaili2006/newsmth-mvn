package com.newsmths.ide.task;

import java.util.TimerTask;

import org.apache.log4j.Logger;

import com.newsmths.lucene.Index;
import com.newsmths.util.PropHelper;

public class IndexTask extends TimerTask {

	private static boolean isRunning = false;
	private static Logger log = Logger.getLogger(IndexTask.class);

	public void run() {
		
		if (!isRunning) {
			isRunning = true;
			log.info("开始执行索引任务..."); // 开始任务

			Index indexer = new Index();
			indexer.addDocFromDB();

			log.info("执行索引任务完成..."); // 任务完成
			isRunning = false;
		} else {
			log.info("上一次任务执行还未结束..."); // 上一次任务执行还未结束
		}
	}
}
