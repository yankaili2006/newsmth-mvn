/**
 * Mar 20, 2011 
 * Index.java 
 */
package com.newsmths.lucene;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Properties;

import net.paoding.analysis.analyzer.PaodingAnalyzer;

import org.apache.log4j.Logger;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.index.CorruptIndexException;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.IndexWriterConfig.OpenMode;
import org.apache.lucene.index.Term;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.store.LockObtainFailedException;
import org.apache.lucene.store.SimpleFSDirectory;
import org.apache.lucene.util.Version;

import com.newsmths.bean.ArticleIndexBean;
import com.newsmths.bean.NoticeBean;
import com.newsmths.bean.TopicBean;
import com.newsmths.ide.task.IndexTask;
import com.newsmths.util.DBUtil;
import com.newsmths.util.PropHelper;

/**
 * @author Administrator 9:44:36 PM
 * 
 *         建立索引
 */
public class Index {

	private static Logger log = Logger.getLogger(Index.class);

	/*
	 * 从文件建立索引
	 */
	public void addDocFromFile(File file) {
		String content = FileReader.readText(file);
		// 添加内容到索引
		Document doc = new Document();
		doc.add(new Field(IndexParam.FIELD_CONTENT, content, Field.Store.YES,
				Field.Index.ANALYZED, Field.TermVector.WITH_POSITIONS_OFFSETS));
		doc.add(new Field(IndexParam.FIELD_NAME, file.getAbsolutePath() + "\\"
				+ file.getName(), Field.Store.YES, Field.Index.ANALYZED,
				Field.TermVector.WITH_POSITIONS_OFFSETS));

		// 存取索引的目录
		FSDirectory dir = null;
		IndexReader reader = null;
		IndexSearcher indexSearcher = null;
		try {
			Properties prop = new PropHelper().getProp();
			dir = SimpleFSDirectory.open(new File(prop
					.getProperty("index_path")));
			reader = IndexReader.open(dir);
			indexSearcher = new IndexSearcher(reader);
		} catch (Exception e) {
			e.printStackTrace();
			log.error("", e);
		}

		// 写索引到文件系统
		IndexWriter writer = null;
		try {
			IndexWriterConfig confIndex = new IndexWriterConfig(
					Version.LUCENE_32, new PaodingAnalyzer());
			confIndex.setOpenMode(OpenMode.CREATE_OR_APPEND);
			if (IndexWriter.isLocked(dir)) {
				IndexWriter.unlock(dir);
			}
			writer = new IndexWriter(dir, confIndex);

			if (writer != null) {
				writer.addDocument(doc);
				writer.close();
			}
		} catch (CorruptIndexException e) {
			e.printStackTrace();
			log.error("", e);
		} catch (LockObtainFailedException e) {
			e.printStackTrace();
			log.error("", e);
		} catch (IOException e) {
			e.printStackTrace();
			log.error("", e);
		}
	}

	/*
	 * 对数据库表记录建立索引
	 */
	public void addDocFromDB() {
		// 创建索引文件目录
		String path = new PropHelper().getProp().getProperty("index_path");
		try {
			File dirFile = new File(path);
			if (!(dirFile.exists()) && !(dirFile.isDirectory())) {
				if (!dirFile.mkdirs()) {
					log.info("创建目录失败，path = [" + path + "]");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.error("", e);
		}

		// 存取索引的目录
		FSDirectory dir = null;
		try {
			dir = SimpleFSDirectory.open(new File(path));
		} catch (Exception e) {
			e.printStackTrace();
			log.error("", e);
		}

		// 写索引配置
		IndexWriter writer = null;
		try {
			IndexWriterConfig indexCfg = new IndexWriterConfig(
					Version.LUCENE_32, new PaodingAnalyzer());
			indexCfg.setOpenMode(OpenMode.CREATE_OR_APPEND);
			if (IndexWriter.isLocked(dir)) {
				IndexWriter.unlock(dir);
			}
			writer = new IndexWriter(dir, indexCfg);
		} catch (Exception e) {
			e.printStackTrace();
		}

		// 索引数据库中所有的topic
		ArrayList topics = null;
		do {
			DBUtil util = new DBUtil();
			topics = util.getTopNArticleListNotIndexed(100);

			for (int i = 0; i < topics.size(); i++) {
				ArticleIndexBean bean = (ArticleIndexBean) topics.get(i);
				// 添加内容到索引
				Document doc = new Document();
				doc.add(new Field(IndexParam.FIELD_CONTENT, bean.getContent(),
						Field.Store.YES, Field.Index.ANALYZED,
						Field.TermVector.WITH_POSITIONS_OFFSETS));
				doc.add(new Field(IndexParam.FIELD_NAME, bean.getTitle(),
						Field.Store.YES, Field.Index.ANALYZED,
						Field.TermVector.WITH_POSITIONS_OFFSETS));
				doc.add(new Field(IndexParam.FIELD_ID, String.valueOf(bean
						.getGid()), Field.Store.YES, Field.Index.NOT_ANALYZED,
						Field.TermVector.YES));
				doc.add(new Field(IndexParam.FIELD_TIME, bean.getTime(),
						Field.Store.YES, Field.Index.NOT_ANALYZED,
						Field.TermVector.YES));

				try {
					// 使用update可以避免重复
					writer.updateDocument(
							new Term(IndexParam.FIELD_ID, String.valueOf(bean
									.getGid())), doc);
				} catch (Exception e) {
					e.printStackTrace();
					log.error("", e);
				}
			}
		} while (topics.size() >= 100);

		try {
			if (writer != null) {
				writer.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.error("", e);
		}
	}

	/*
	 * 递归某个目录建立索引
	 */
	public void indexDir(String dirParent) {
		File dir = new File(dirParent);
		if (dir.isDirectory()) {
			String list[] = dir.list();
			for (int i = 0; i < list.length; i++) {
				File file = new File(dirParent + "\\" + list[i]);
				if (file.isDirectory()) {
					indexDir(dirParent + "\\" + list[i]);
				} else {
					// 开始时间
					long startTime = System.currentTimeMillis();

					// 获取文件名
					String name = file.getName();
					String path = dirParent + "\\";
					path = path.replace("\\", "\\\\");
					String sourceFile = path + name;

					// 判断文件格式
					String lowerName = name.toLowerCase();
					if (lowerName != null && (lowerName.endsWith(".txt"))) {

						// 对文档建立索引
						addDocFromFile(file);

						// 结束时间
						long endTime = System.currentTimeMillis();
						long costTime = endTime - startTime;

					} else {
						continue;
					}
				}
			}
		}
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Index indexer = new Index();
		indexer.addDocFromDB();
	}

}
