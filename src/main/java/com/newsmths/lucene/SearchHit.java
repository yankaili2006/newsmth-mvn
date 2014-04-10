/**
 * Mar 20, 2011 
 * SearchHit.java 
 */
package com.newsmths.lucene;

import com.newsmths.bean.ArticleBean;

/**
 * @author Administrator
 *10:42:24 PM
 */
public class SearchHit {

	private String id;
	private String content;
	private String name;
	private float score;
	
	private ArticleBean abean;
	
	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public ArticleBean getAbean() {
		return abean;
	}


	public void setAbean(ArticleBean abean) {
		this.abean = abean;
	}


	/**
	 * @return the content
	 */
	public String getContent() {
		return content;
	}


	/**
	 * @param content the content to set
	 */
	public void setContent(String content) {
		this.content = content;
	}


	/**
	 * @return the fileName
	 */
	public String getFileName() {
		return name;
	}


	/**
	 * @param fileName the fileName to set
	 */
	public void setFileName(String fileName) {
		this.name = fileName;
	}


	/**
	 * @return the score
	 */
	public float getScore() {
		return score;
	}


	/**
	 * @param score the score to set
	 */
	public void setScore(float score) {
		this.score = score;
	}


	public String getId() {
		return id;
	}


	public void setId(String id) {
		this.id = id;
	}



}
