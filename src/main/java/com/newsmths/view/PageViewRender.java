package com.newsmths.view;

import java.util.ArrayList;

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
				ArrayList<TagBean> tags = hitBean.getTags();
				sBuf.append("<h2>" + hit.getFileName() + "</h2><h4>相关度 :"
						+ hit.getScore() + "</h4>");
				sBuf.append("<h4>");
				// 打印标签
				if (tags != null && tags.size() > 0) {
					for (int j = 0; j < tags.size() && j < 10; j++) {
						TagBean tag = tags.get(j);
						sBuf.append("<a href='TagServlet?key=" + tag.getWord()
								+ "'>" + tag.getWord() + "</a>" + ":"
								+ tag.getTfidf() + "&nbsp;&nbsp;&nbsp;&nbsp;");
					}
				}
				sBuf.append("</h4>");
				sBuf.append("<div> " + hit.getContent() + "</div>");
			}
		} else {
			sBuf.append("什么也没找到，换个关键词试试");
		}
		
		return sBuf.toString();
	}
}
