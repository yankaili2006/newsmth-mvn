package com.newsmths.view;

import java.util.ArrayList;

import com.newsmths.bean.ArticleBean;
import com.newsmths.bean.HitBean;
import com.newsmths.bean.TagBean;
import com.newsmths.lucene.SearchHit;

public class PageViewRender {

	public String render(ArrayList<HitBean> list) {
		StringBuffer sBuf = new StringBuffer();

		if (list != null && list.size() > 0) {
			sBuf.append("<hr></hr>");

			for (int i = 0; i < list.size(); i++) {
				HitBean hitBean = list.get(i);
				SearchHit hit = hitBean.getHit();
				ArticleBean abean = hit.getAbean();
				ArrayList<TagBean> tags = hitBean.getTags();
				if (abean != null) {
					sBuf.append("<h2>" + abean.getTitle() + "</h2><h6>"
							+ abean.getAuthor() + "&nbsp;&nbsp;&nbsp;&nbsp;"
							+ abean.getTime() + "&nbsp;&nbsp;&nbsp;&nbsp;"
							+ hit.getScore() + "&nbsp;&nbsp;&nbsp;&nbsp;"
							+ abean.getIp() + "&nbsp;&nbsp;&nbsp;&nbsp;"
							+ abean.getId() + "&nbsp;&nbsp;&nbsp;&nbsp;"
							+ abean.getBoardName());
					sBuf.append("</h6>");
					// 打印标签
					if (tags != null && tags.size() > 0) {
						sBuf.append("<h6>");
						for (int j = 0; j < tags.size() && j < 10; j++) {
							TagBean tag = tags.get(j);
							sBuf.append("<a href='TagServlet?key="
									+ tag.getWord() + "'>" + tag.getWord()
									+ "</a>" + ":" + tag.getTfidf()
									+ "&nbsp;&nbsp;&nbsp;&nbsp;");
						}
						sBuf.append("</h6>");

					}
					sBuf.append("<p>" + abean.getRaw() + "</p>");
				}
			}
		} else {
			sBuf.append("什么也没找到，换个关键词试试");
		}

		return sBuf.toString();
	}
}
