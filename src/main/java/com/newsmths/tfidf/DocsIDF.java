/**
 * Nov 6, 2010 
 * IDFDocument.java 
 */
package com.newsmths.tfidf;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;

import org.apache.log4j.Logger;

import com.newsmths.lucene.Search;

/**
 * @author Administrator
 * 
 */
public class DocsIDF {
	private static Logger log = Logger.getLogger(DocsIDF.class);
	/* 词, 以及出现该词的文档列表 */
	public HashMap<String, HashSet<Integer>> words;

	public DocsIDF() {
		words = new HashMap<String, HashSet<Integer>>();
	}

	// 添加word的Hashset格式的文档列表
	public void addWord(String word, HashSet<Integer> docs) {
		if (words.containsKey(word)) {
			((HashSet<Integer>) words.get(word)).addAll(docs);
		} else {
			words.put(word, docs);
		}
	}

	/*
	 * 添加TF文档的一组TermFrequence到IDF文档中
	 */
	public void addTerm(String word, Integer docId) {
		if (words.containsKey(word)) {
			((HashSet<Integer>) words.get(word)).add(docId);
		} else {
			HashSet<Integer> set = new HashSet<Integer>();
			set.add(docId);
			words.put(word, set);
		}
	}

	// 打印TF中词汇列表
	public void print() {
		Iterator iter = words.entrySet().iterator();
		StringBuilder out = new StringBuilder();
		while (iter.hasNext()) {
			Map.Entry entry = (Map.Entry) iter.next();
			String word = (String) entry.getKey();
			out.append("IDF:" + "[" + word + ":");
			Iterator it = ((HashSet<Integer>) entry.getValue()).iterator();
			while (it.hasNext()) {
				int docId = (Integer) it.next();
				out.append(docId + ",");
			}
			out.append("]\n");
		}
		out.append("\n");
		log.debug(out.toString());
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		DocsIDF doc = new DocsIDF();
		doc.print();
	}

}
