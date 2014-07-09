/**
 * Mar 20, 2011 
 * Searcher.java 
 */
package com.newsmths.lucene;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Properties;

import net.paoding.analysis.analyzer.PaodingAnalyzer;

import org.apache.log4j.Logger;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.CorruptIndexException;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.Term;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.Sort;
import org.apache.lucene.search.SortField;
import org.apache.lucene.search.TermQuery;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.search.TopScoreDocCollector;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.store.SimpleFSDirectory;
import org.apache.lucene.util.Version;

import com.newsmths.bean.ArticleBean;
import com.newsmths.bean.HitBean;
import com.newsmths.extract.ExtractorAll;
import com.newsmths.ide.task.TagTask;
import com.newsmths.util.DBUtil;
import com.newsmths.util.PropHelper;

/**
 * @author Administrator 10:07:03 PM
 * 
 *         在索引目录中，搜索我们要找的文档，返回文档信息
 * 
 */
public class Search {

	private Integer total = 0;
	private final Integer MAX_RECORD_NUM = 100;
	private static Logger log = Logger.getLogger(Search.class);

	public Integer getTotal() {
		return total;
	}

	public void setTotal(Integer total) {
		this.total = total;
	}

	/*
	 * 构造函数
	 * 
	 * Searcher会与库函数重名
	 */
	public Search() {
	}

	public ArrayList<HitBean> search(String searchKey, int nPage, int PAGE_SIZE) {

		Query query = new TermQuery(new Term(IndexParam.FIELD_CONTENT,
				searchKey));
		log.info("Searching for: " + query.toString(IndexParam.FIELD_CONTENT));

		/* 读取索引文件 */
		IndexReader reader = null;
		IndexSearcher indexSearcher = null;
		try {
			Properties prop = new PropHelper().getProp();
			FSDirectory dir = SimpleFSDirectory.open(new File(prop
					.getProperty("index_path")));
			reader = IndexReader.open(dir);
			indexSearcher = new IndexSearcher(reader);
		} catch (Exception e) {
			e.printStackTrace();
			log.error("", e);
		}

		// 搜索结果
		Sort sort = new Sort(new SortField(IndexParam.FIELD_TIME,
				SortField.Type.STRING, true)); // 排序,false升序,true降序
		TopDocs topDocs = null;
		if (indexSearcher != null) {
			try {
				topDocs = indexSearcher.search(query, MAX_RECORD_NUM, sort);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		// 存放搜索结果
		ArrayList<HitBean> hits = new ArrayList<HitBean>();
		if (topDocs != null) {
			// 文档得分
			ScoreDoc[] scoreDocs = topDocs.scoreDocs;

			DBUtil util = new DBUtil();
			for (int i = 0; i < topDocs.scoreDocs.length; i++) {
				Document document = null;
				try {
					document = indexSearcher.doc(scoreDocs[i].doc);
				} catch (Exception e) {
					e.printStackTrace();
					log.error("", e);
				}

				// 组装结果
				SearchHit hit = new SearchHit();
				String id = document.getField("id").stringValue();
				hit.setId(id);
				ArticleBean abean = util.getArticleById(Integer.valueOf(id));
				hit.setAbean(abean);
				hit.setContent(document.getField(IndexParam.FIELD_CONTENT)
						.stringValue());
				hit.setFileName(document.getField(IndexParam.FIELD_NAME)
						.stringValue());
				hit.setScore(scoreDocs[i].score);

				HitBean hitBean = new HitBean();
				hitBean.setHit(hit);
				hits.add(hitBean);
			}
		}

		// 关闭读入数据流
		try {
			if (reader != null) {
				reader.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
			log.error("", e);
		}
		return hits;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Search search = new Search();
		search.search("兼职", 1, 10);
		search.search("金融", 1, 10);
		search.search("java", 1, 10);
	}

}
