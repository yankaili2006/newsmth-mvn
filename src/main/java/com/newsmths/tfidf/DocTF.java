package com.newsmths.tfidf;

import java.io.IOException;
import java.io.StringReader;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.apache.log4j.Logger;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;

import net.paoding.analysis.analyzer.PaodingAnalyzer;

/*
 * TF文档类，保存一个文件中所有的IF信息
 */
public class DocTF {
	private static Logger log = Logger.getLogger(DocTF.class);

	// 文档ID
	public Integer id;
	// 文档次数
	public Integer wordnum = 0;
	// 词，以及词频
	public HashMap<String, Integer> terms;
	// 词，以及词的tfidf
	public HashMap<String, Double> tags;
	// 文档IFIDF值
	public Double tfidf = 0.0;

	// 初始化为空
	public DocTF(Integer dId) {
		terms = new HashMap<String, Integer>();
		tags = new HashMap<String, Double>();
		id = dId;
	}

	// 带数据的初始化
	public DocTF(Integer dId, HashMap<String, Integer> term,
			HashMap<String, Double> tag) {
		terms = term;
		tags = tag;
		id = dId;

		// 调整词数目
		if (term != null) {
			wordnum += term.size();
		}
	}

	/*
	 * 构造函数
	 * 
	 * String text:单词字符串
	 */
	public void generate(String content) {
		PaodingAnalyzer analyzer = new PaodingAnalyzer();

		// 循环打印所有分词及其位置 while (stream.incrementToken()) {

		StringReader reader = new StringReader(content);
		TokenStream ts = null;
		try {
			ts = analyzer.tokenStream("text", reader);
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		// 添加工具类 注意：以下这些与之前lucene2.x版本不同的地方
		CharTermAttribute offAtt = (CharTermAttribute) ts
				.addAttribute(CharTermAttribute.class);
		// 循环打印出分词的结果，及分词出现的位置
		if (ts != null) {
			try {
				boolean hasnext = ts.incrementToken();
				while (hasnext) {
					String word = offAtt.toString();
					wordnum++;
					if (terms.containsKey(word)) {
						int freq = terms.get(word);
						terms.put(word, freq);
					} else {
						terms.put(word, 1);
					}
					hasnext = ts.incrementToken();
				}

			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/*
	 * 打印所有单词以及出现次数 按照出现次数
	 */
	public void print() {
		Iterator iter = terms.entrySet().iterator();
		StringBuilder out = new StringBuilder();
		out.append("TF:" + id + " [");
		while (iter.hasNext()) {
			Map.Entry entry = (Map.Entry) iter.next();
			String word = (String) entry.getKey();
			int freq = (Integer) entry.getValue();

			out.append(word + ":" + freq + ",");
		}
		out.append("]\n");
		log.debug(out.toString());
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		DocTF doc = new DocTF(1);
		doc.generate("北航实验室招聘嵌入式软硬件实习生");
		doc.print();
	}
}
