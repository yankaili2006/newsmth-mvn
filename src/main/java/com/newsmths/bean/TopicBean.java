/**  
 * @Packagecom.bean
 * @author yankaili@gmail.com  
 * @date Jun 17, 2011 10:38:16 AM 
 */
package com.newsmths.bean;

/**
 * @author Administrator 10:38:16 AM
 */
public class TopicBean {

	private String boardName; /* 版面 */
	private int boardId;   /* 版面ID */
	private int page;     /* 所在页码 */
	private int gid;      /* 话题ID */
	private String title; /* 话题标题 */
	private String url;   /* url */

	/**
	 * 
	 */
	public TopicBean() {
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
	}

	/**
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * @param title
	 *            the title to set
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * @return the url
	 */
	public String getUrl() {
		return url;
	}

	/**
	 * @param url
	 *            the url to set
	 */
	public void setUrl(String url) {
		this.url = url;
	}

	public int getGid() {
		return gid;
	}

	public void setGid(int gid) {
		this.gid = gid;
	}

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public String getBoardName() {
		return boardName;
	}

	public void setBoardName(String boardName) {
		this.boardName = boardName;
	}

	public int getBoardId() {
		return boardId;
	}

	public void setBoardId(int boardId) {
		this.boardId = boardId;
	}

}
