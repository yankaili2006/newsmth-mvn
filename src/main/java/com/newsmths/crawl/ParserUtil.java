package com.newsmths.crawl;

import java.util.ArrayList;

import org.htmlparser.Parser;
import org.htmlparser.filters.AndFilter;
import org.htmlparser.filters.HasAttributeFilter;
import org.htmlparser.filters.TagNameFilter;
import org.htmlparser.util.NodeList;
import org.htmlparser.util.ParserException;

public class ParserUtil {

	private void parse(String url) throws ParserException {
		String DW_HOME_PAGE_URL = url;
		ArrayList<String> pTitleList = new ArrayList<String>();
		// 创建 html parser 对象，并指定要访问网页的 URL 和编码格式
		Parser htmlParser = new Parser(DW_HOME_PAGE_URL);
		htmlParser.setEncoding("UTF-8");
		String postTitle = "";
		// 获取指定的 div 节点，即 <div> 标签，并且该标签包含有属性 id 值为“tab1”
		NodeList divOfTab1 = htmlParser.extractAllNodesThatMatch(new AndFilter(
				new TagNameFilter("a"), new HasAttributeFilter("class",
						"fn-left w210 rrd-dimgray fn-text-overflow")));
		
		System.out.println(divOfTab1.elementAt(0).toHtml());
		if (divOfTab1 != null && divOfTab1.size() > 0) {
			
System.out.println(divOfTab1.elementAt(0).getChildren().size());
			// 获取指定 div 标签的子节点中的 <li> 节点
			NodeList itemLiList = divOfTab1.extractAllNodesThatMatch(new AndFilter(
					new TagNameFilter("li"), new HasAttributeFilter("id",
							"loan-list-header")));
			
			System.out.println(itemLiList.size());
			if (itemLiList != null && itemLiList.size() > 0) {
				for (int i = 0; i < itemLiList.size(); ++i) {
					System.out.println(itemLiList.toHtml());
				}
			}
		}

		// 在 <li> 节点的子节点中获取 Link 节点
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		ParserUtil util = new ParserUtil();
		try {
			util.parse("http://www.renrendai.com/lend/loanList.action");
		} catch (ParserException e) {
			e.printStackTrace();
			log.error("", e);
		}

	}

}
