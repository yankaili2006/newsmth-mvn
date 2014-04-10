/**  
 * @Packagecom.bean
 * @author yankaili@gmail.com  
 * @date Jun 17, 2011 10:39:15 AM 
 */
package com.newsmths.bean;

/**
 * @author Administrator 10:39:15 AM
 */
public class ArticleBean {

	private String boardName;
	private int boardId;
	private int id;
	private int gid;
	private String title;
	private String url;
	
	// 发信人
	private String author;
	// 签名档
	private String sign;
	// 完整内容
	private String content;
	// 回复的某人
	private String atauthor;
	// 回复默认的某些信息
	private String atmsg;
	// 发表的信息
	private String msg;
	// 通过某个网址访问水木
	private String channel;
	// 发表时间
	private String time;
	// 发表者的IP地址
	private String ip;
	// 纯正文
	private String raw;
	public String getRaw() {
		return raw;
	}

	public void setRaw(String raw) {
		this.raw = raw;
	}

	/**
	 * @return the content
	 */
	public String getContent() {
		return content;
	}

	/**
	 * @param content
	 *            the content to set
	 */
	public void setContent(String content) {
		this.content = content;
	}

	/**
	 * @return the time
	 */
	public String getTime() {
		return time;
	}

	/**
	 * @param time
	 *            the time to set
	 */
	public void setTime(String time) {
		this.time = time;
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

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getGid() {
		return gid;
	}

	public void setGid(int gid) {
		this.gid = gid;
	}


	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getSign() {
		return sign;
	}

	public void setSign(String sign) {
		this.sign = sign;
	}

	public String getAtauthor() {
		return atauthor;
	}

	public void setAtauthor(String atauthor) {
		this.atauthor = atauthor;
	}

	public String getAtmsg() {
		return atmsg;
	}

	public void setAtmsg(String atmsg) {
		this.atmsg = atmsg;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public String getChannel() {
		return channel;
	}

	public void setChannel(String channel) {
		this.channel = channel;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

}
