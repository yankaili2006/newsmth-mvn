/**  
 * @Packagecom.main
 * @author yankaili@gmail.com  
 * @date Jun 16, 2011 8:00:19 PM 
 */
package com.newsmths.ide;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;

import com.newsmths.crawl.FetchURL;
import com.newsmths.extract.ExtractorAll;
import com.newsmths.util.AccountUtil;
import com.newsmths.util.DBUtil;

/**
 * @author Administrator 8:00:19 PM 抓取整站
 */
public class CrawlNewsmth {

	public static final String SITE = "http://www.newsmth.net/";
	private static Logger log = Logger.getLogger(CrawlNewsmth.class);
	
	public void init() {
		AccountUtil.init();

	}

	public String crawl() {
		log.info("start crawling.....");
		
		DBUtil util = new DBUtil();
		HashMap<Integer, String> boardMap = util.getBoardList();
		Iterator iter = boardMap.entrySet().iterator();
		while (iter.hasNext()) {
			Entry entry = (Entry) iter.next();
			String boardName = (String) entry.getValue();
			int boardId = (Integer)entry.getKey();

			int pageNoFromDB = util.getMaxPageNoFromDB(boardId);
			int pageNoFromWeb = getMaxPageNoFromWeb(boardName);

			if (pageNoFromDB == 0) {
				pageNoFromDB = pageNoFromWeb;
			}
			log.debug(pageNoFromDB + ", " + pageNoFromWeb);

			ExtractorAll extractor = new ExtractorAll();
			extractor.extractTopicListByPageRange(boardName, boardId,
					pageNoFromDB, pageNoFromWeb);

		}
		
		String outHtml = "<!DOCTYPE html>\n" + "<html>\n"
				+ "<head><title>A Test Servlet</title></head>\n" + "<body>\n"
				+ "<h1>Crawl Result: </h1>\n" + "<p>Time Cost: "
				+ AccountUtil.timeCost + " ms</p>\n" + "<p>Topic Number: "
				+ AccountUtil.TopicCnt + "</p>\n" + "<p>Article Number: "
				+ AccountUtil.ArticleCnt + "</p>\n" + "</body></html>";

		log.info("finish crawl.....");

		return outHtml;
	}

	/* get max page number from website */
	public int getMaxPageNoFromWeb(String boardName) {
		int lastestPageNo = 0;
		String url = "http://www.newsmth.net/bbsdoc.php?board=" + boardName
				+ "&ftype=6"; // "http://www.newsmth.net/bbsdoc.php?board=SecondComputer&ftype=6&page=33";

		FetchURL fetch = new FetchURL();
		String html = fetch.getPage(url);

		String pagePattern = "'" + boardName + "',\\d+,\\d+,\\d+,\\d+,(\\d+),";
		Pattern pt = Pattern.compile(pagePattern, Pattern.CASE_INSENSITIVE);
		Matcher mt = pt.matcher(html);

		if (mt.find()) {
			String page = mt.group(1);
			lastestPageNo = Integer.valueOf(page);
		}
		return lastestPageNo;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		CrawlNewsmth crawler = new CrawlNewsmth();
		System.out.println(crawler.crawl());
	}
}
