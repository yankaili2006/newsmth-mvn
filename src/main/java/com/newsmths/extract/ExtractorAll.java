package com.newsmths.extract;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;

import sun.misc.BASE64Encoder;

import com.newsmths.bean.ArticleBean;
import com.newsmths.bean.TopicBean;
import com.newsmths.crawl.FetchURL;
import com.newsmths.ide.CrawlNewsmth;
import com.newsmths.util.AccountUtil;
import com.newsmths.util.DBUtil;
import com.newsmths.util.PropHelper;

public class ExtractorAll {

	public FetchURL fetcher = new FetchURL();

	private static Logger log = Logger.getLogger(ExtractorAll.class);

	/*
	 * Crawl one page
	 */
	public void extractTopicListByPage(String boardName, int boardId, int page) {

		long startTime = System.currentTimeMillis();

		FetchURL fetcher = new FetchURL();
		String url = "http://www.newsmth.net/bbsdoc.php?board=" + boardName
				+ "&ftype=6";
		if (page > 0) {
			url = url + "&page=" + page;
		}

		log.debug("url = [" + url + "]");

		String html = fetcher.getPage(url);

		String relativeTopicPath = "bbstcon.php?board=" + boardName + "&gid=";
		// 1.0 获取话题列表
		String catalogPattern = "c\\.o\\((\\d+),\\d+,.*?,(\\d+),'(.*?)',\\d+,\\d+,\\d+\\);";
		Pattern pt = Pattern.compile(catalogPattern, Pattern.CASE_INSENSITIVE);
		Matcher mt = pt.matcher(html);
		while (mt.find()) {
			/* 话题ID */
			int gid = Integer.valueOf(mt.group(1).trim());
			/* 话题标题 */
			String title = mt.group(3).trim();
			/*
			 * 话题URL
			 * http://www.newsmth.net/bbstcon.php?board=SecondComputer&gid=
			 * 1887612
			 */
			url = CrawlNewsmth.SITE + relativeTopicPath + gid;

			log.debug(gid + "," + title + "," + url);

			TopicBean bean = new TopicBean();
			bean.setBoardName(boardName);
			bean.setBoardId(boardId);
			bean.setPage(page);
			bean.setGid(gid);
			bean.setTitle(title);
			bean.setUrl(url);

			DBUtil util = new DBUtil();
			util.addTopicBean(bean);

			html = fetcher.getPage(url);
			extractTopic(boardName, boardId, html, gid);
			AccountUtil.TopicCnt++;

		}

		// 计算消耗的时间
		AccountUtil.timeCost = System.currentTimeMillis() - startTime;
	}

	/*
	 * Crawl pages between pageStart and pageEnd
	 */
	public void extractTopicListByPageRange(String boardName, int boardId,
			int pageStart, int pageEnd) {
		if (pageEnd < pageStart)
			return;
		for (int i = pageStart; i <= pageEnd; i++) {
			extractTopicListByPage(boardName, boardId, i);
		}
	}

	public void extractTopic(String boardName, int boardId, String html, int gid) {
		// o.o([[1639919,'solorist'],[1639922,'iwait'],[1639924,'sakulaqi'],[1639981,'sweanson'],[1640000,'imms'],[1640329,'MichaelChou']]);o.h();
		// http://www.newsmth.net/bbscon.php?bid=675&id=1639922

		String contentPattern = "o.o\\(\\[(.*?)\\]\\);";

		Pattern pt = Pattern.compile(contentPattern, Pattern.CASE_INSENSITIVE);
		Matcher mt = pt.matcher(html);

		log.debug("html:" + html);

		// Vector<String> artList = new Vector<String>();

		String relativePath = "bbscon.php?bid=" + boardId + "&id=";
		while (mt.find()) {
			String content = mt.group(1).trim();

			log.debug("content:" + content);

			String headPattern = "\\[(\\d+),'.*?'\\]";
			Pattern idpt = Pattern.compile(headPattern,
					Pattern.CASE_INSENSITIVE);
			Matcher idmt = idpt.matcher(content);

			while (idmt.find()) {
				int id = Integer.valueOf(idmt.group(1).trim());
				/* http://www.newsmth.net/bbscon.php?bid=675&id=1892036 */
				String artURL = CrawlNewsmth.SITE + relativePath + id;

				html = fetcher.getPage(artURL);
				extractArticle(artURL, boardName, boardId, html, gid, id);
			}
		}
	}

	/**
	 * @param html
	 * @return 抽取话题列表
	 */
	public void extractArticle(String url, String boardName, int boardId,
			String html, int gid, int id) {

		ArticleBean bean = new ArticleBean();
		bean.setBoardName(boardName);
		bean.setBoardId(boardId);
		bean.setId(id);
		bean.setGid(gid);
		bean.setUrl(url);

		log.debug("id: " + id);

		// 2.0 内容
		String content = "";
		String contentPattern = "prints\\('(.*?)'\\);";
		Pattern pt = Pattern.compile(contentPattern, Pattern.CASE_INSENSITIVE);
		Matcher mt = pt.matcher(html);
		while (mt.find()) {
			content = mt.group(1).trim();
			bean.setContent(content);
			log.debug("content: " + content);
		}

		// 1.0 标题和时间
		String raw = "";
		String author = "";
		String board = "";
		String title = "";
		String time = "";
		String headPattern = "发信人:(.*?), 信区:(.*?)\\\\n标  题:(.*?)\\\\n发信站: .*? \\((.*?)\\), (站内|转信)\\\\n(.*?)\\[m\\\\r\\[.*?m※ 来源:·";
		pt = Pattern.compile(headPattern, Pattern.CASE_INSENSITIVE);
		mt = pt.matcher(content);
		while (mt.find()) {
			author = mt.group(1).trim();
			board = mt.group(2).trim();
			title = mt.group(3).trim();
			time = mt.group(4).trim();
			raw = mt.group(6).trim();
		}
		bean.setAuthor(author);
		bean.setTitle(title);
		if (time != null) {
			try {
				SimpleDateFormat sdf = new SimpleDateFormat(
						"EEE MMM dd HH:mm:ss yyyy", Locale.ENGLISH);
				Date date = sdf.parse(time);
				SimpleDateFormat format1 = new SimpleDateFormat(
						"yyyy-MM-dd hh:mm:ss");
				bean.setTime(format1.format(date));

			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		bean.setRaw(raw);

		log.debug("author: " + author + ", " + board + "," + title + "," + time);
		log.debug("raw: " + raw);

		// 兼容编辑过的情况
		String sign = "";
		String channel = "";
		String ip = "";
		if (content.contains("--")) {

			if (content.matches(".*?\\[.*?m※ 修改:.*?")) {
				headPattern = "--(.*?)\\\\n\\\\r\\[.*?m※ 修改:·.*? 于 .*? 修改本文·\\[FROM: .*?\\]\\\\r\\[m\\\\n\\\\r\\[m\\\\r\\[.*?m※ 来源:·(.*?)·\\[FROM: (.*?)\\]\\\\r\\[m\\\\n";
			} else {
				headPattern = "--(.*?)\\\\n\\\\n\\\\r\\[m\\\\r\\[.*?m※ 来源:·(.*?)·\\[FROM: (.*?)\\]\\\\r\\[m\\\\n";
			}
			pt = Pattern.compile(headPattern, Pattern.CASE_INSENSITIVE);
			mt = pt.matcher(content);
			while (mt.find()) {
				sign = mt.group(1).trim();
				channel = mt.group(2).trim();
				ip = mt.group(3).trim();
			}
		} else {
			if (content.matches(".*?\\[.*?m※ 修改:.*?")) {
				headPattern = "\\\\n\\\\r\\[.*?m※ 修改:·.*? 于 .*? 修改本文·\\[FROM: .*?\\]\\\\r\\[m\\\\n\\\\r\\[m\\\\r\\[.*?m※ 来源:·(.*?)·\\[FROM: (.*?)\\]\\\\r\\[m\\\\n";
			} else {
				headPattern = "\\\\n\\\\n\\\\r\\[m\\\\r\\[.*?m※ 来源:·(.*?)·\\[FROM: (.*?)\\]\\\\r\\[m\\\\n";
			}
			pt = Pattern.compile(headPattern, Pattern.CASE_INSENSITIVE);
			mt = pt.matcher(content);
			while (mt.find()) {
				channel = mt.group(1).trim();
				ip = mt.group(2).trim();
			}
		}
		bean.setSign(sign);
		bean.setChannel(channel);
		bean.setIp(ip);
		log.debug("sign: " + sign);
		log.debug("channel: " + channel);
		log.debug("ip: " + ip);

		String safter = "";
		String atauthor = "";
		String sbefore = "";
		if (raw.matches(".*?【 在.*?的大作中提到: 】.*")) {
			String atpattern = "(.*?)【 在 (.*?) 的大作中提到: 】(.*)";
			pt = Pattern.compile(atpattern, Pattern.CASE_INSENSITIVE);
			mt = pt.matcher(raw);
			while (mt.find()) {
				sbefore = mt.group(1).trim();
				atauthor = mt.group(2).trim();
				safter = mt.group(3).trim();
			}
		}
		bean.setAtauthor(atauthor);

		log.debug("before: " + sbefore);
		log.debug("at: " + atauthor);
		log.debug("after: " + safter);
		StringBuffer safterraw = new StringBuffer();
		StringBuffer atmsg = new StringBuffer();
		if (safter.matches("\\\\n:.*")) {
			String elems[] = safter.split("\\\\n:");
			int i = 0;
			for (i = 0; i < elems.length; i++) {
				if (elems[i].length() > 0) {
					if (!elems[i].contains("\n")) {
						atmsg.append(elems[i] + "\\n");
					}
				}
				log.debug(i + "= " + elems[i]);
			}
			if (elems[i - 1].contains("\n")) {
				String ele[] = elems[i - 1].split("\\\\n");
				if (ele.length > 1) {
					safterraw.append(ele[1]);
				}
			}
			log.debug("atmsg: " + atmsg.toString());
			log.debug("safterraw: " + safterraw.toString());
		}
		bean.setAtmsg(atmsg.toString());
		bean.setMsg(sbefore.toLowerCase() + safterraw.toString());

		DBUtil util = new DBUtil();
		if (util.addArticleBean(bean)) {
			AccountUtil.ArticleCnt++;
		}
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		FetchURL request = new FetchURL();
		String url = "http://www.newsmth.net/bbsdoc.php?board=SecondComputer&ftype=6";
		url = "http://www.newsmth.net/bbsdoc.php?board=ITjob&ftype=6";
		url = "http://www.newsmth.net/bbscon.php?bid=398&id=2123748";
		url = "http://www.newsmth.net/bbscon.php?bid=676&id=154387";
		// url = "http://www.newsmth.net/bbscon.php?bid=676&id=154397";
		url = "http://www.newsmth.net/bbscon.php?bid=676&id=358";
		// url = "http://www.newsmth.net/bbscon.php?bid=676&id=154389";
		String html = request.getPage(url);

		log.debug(html);

		ExtractorAll extractor = new ExtractorAll();

		extractor.extractArticle(url, "second", 11, html, 110, 110);

		// extractor.extractTopicListByPage("ITjob", 1002, 718);

	}

}
