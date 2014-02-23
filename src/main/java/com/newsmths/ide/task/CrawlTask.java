package com.newsmths.ide.task;

import java.util.TimerTask;

import org.apache.log4j.Logger;


import com.newsmths.ide.CrawlNewsmth;
import com.newsmths.util.PropHelper;

public class CrawlTask extends TimerTask {

	private static boolean isRunning = false;
	private static Logger log = Logger.getLogger(CrawlTask.class);

	public void run() {

		if (!isRunning) {
			isRunning = true;
			log.info("开始执行抓取任务..."); // 开始任务
			CrawlNewsmth newsmth = new CrawlNewsmth();
			
			log.info(newsmth.crawl());

			log.info("执行抓取任务完成..."); // 任务完成
			isRunning = false;
		} else {
			log.info("上一次任务执行还未结束..."); // 上一次任务执行还未结束
		}
	}
}
