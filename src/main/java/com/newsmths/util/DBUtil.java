package com.newsmths.util;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Properties;

import org.apache.log4j.Logger;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import com.newsmths.bean.ArticleBean;
import com.newsmths.bean.LabelBean;
import com.newsmths.bean.NoticeBean;
import com.newsmths.bean.TagBean;
import com.newsmths.bean.TopicBean;
import com.newsmths.bean.UserBean;
import com.newsmths.bean.UserLabelBean;
import com.newsmths.tfidf.DocTF;
import com.newsmths.tfidf.TFIDFUtil;

public class DBUtil {
	private static Logger log = Logger.getLogger(DBUtil.class);

	public boolean init() {
		initDB();

		initData();

		return true;
	}

	/* create table and insert initialise data */
	public boolean initDB() {
		Statement stmt = null;
		Connection conn = getConnectionByJDBC();
		try {
			stmt = conn.createStatement();

			/* topic */
			String sql = "DROP TABLE IF EXISTS topic;";
			log.debug(sql);
			stmt.addBatch(sql);
			sql = "CREATE TABLE IF NOT EXISTS topic ("
					+ "  boardName varchar(512) NOT NULL,"
					+ "  boardId int(11) NOT NULL,"
					+ "  page int(32) NOT NULL," + "  gid int(11) NOT NULL,"
					+ "  title varchar(512) NOT NULL,"
					+ "  url varchar(1024) NOT NULL" + ");";
			log.debug(sql);
			stmt.addBatch(sql);

			/* article */
			sql = "DROP TABLE IF EXISTS article;";
			log.debug(sql);
			stmt.addBatch(sql);
			sql = "CREATE TABLE IF NOT EXISTS article ("
					+ "  boardName varchar(128) NOT NULL,"
					+ "  boardId int(11) NOT NULL," + "  id int(11) NOT NULL,"
					+ "  gid int(11) NOT NULL,"
					+ "  url varchar(512) NOT NULL,"
					+ "  title varchar(512) NOT NULL,"
					+ "  author varchar(128) NOT NULL,"
					+ "  sign blob NOT NULL," + "  content blob NOT NULL,"
					+ "  atauthor varchar(64) NOT NULL,"
					+ "  atmsg blob NOT NULL," + "  msg blob NOT NULL,"
					+ "  channel varchar(64) NOT NULL,"
					+ "  time varchar(32) NOT NULL,"
					+ "  ip varchar(20) NOT NULL" + ");";
			log.debug(sql);
			stmt.addBatch(sql);

			/* notice */
			sql = "DROP TABLE IF EXISTS notice;";
			log.debug(sql);
			stmt.addBatch(sql);
			sql = "  CREATE TABLE IF NOT EXISTS notice ("
					+ "  email varchar(128) NOT NULL,"
					+ "  title varchar(512) NOT NULL,"
					+ "  gid int(11) NOT NULL," + "  status int(1) NOT NULL"
					+ ");";
			log.debug(sql);
			stmt.addBatch(sql);

			/* board */
			sql = "DROP TABLE IF EXISTS board;";
			log.debug(sql);
			stmt.addBatch(sql);
			sql = "  CREATE TABLE IF NOT EXISTS board ("
					+ "  id int(11) NOT NULL,"
					+ "  name varchar(128) NOT NULL,"
					+ "  alias varchar(128) NOT NULL,"
					+ "  priority int(11) NOT NULL" + ");";
			log.debug(sql);
			stmt.addBatch(sql);

			/* user */
			sql = "DROP TABLE IF EXISTS user;";
			log.debug(sql);
			stmt.addBatch(sql);
			sql = " CREATE TABLE IF NOT EXISTS user ("
					+ " id int(11) NOT NULL," + " name varchar(32) NOT NULL,"
					+ " pwd varchar(32) NOT NULL,"
					+ " email varchar(32) NOT NULL" + ");";
			log.debug(sql);
			stmt.addBatch(sql);

			/* label */
			sql = "DROP TABLE IF EXISTS label;";
			log.debug(sql);
			stmt.addBatch(sql);
			sql = " CREATE TABLE IF NOT EXISTS label ("
					+ " id int(11) NOT NULL," + " label varchar(32) NOT NULL"
					+ ");";
			log.debug(sql);
			stmt.addBatch(sql);

			/* usearch */
			sql = "DROP TABLE IF EXISTS usearch;";
			log.debug(sql);
			stmt.addBatch(sql);
			sql = " CREATE TABLE IF NOT EXISTS usearch ("
					+ " uid int(11) NOT NULL," + " skey varchar(32) NOT NULL,"
					+ " time datetime NOT NULL" + ");";
			log.debug(sql);
			stmt.addBatch(sql);

			/* uboard */
			sql = "DROP TABLE IF EXISTS uboard;";
			log.debug(sql);
			stmt.addBatch(sql);
			sql = " CREATE TABLE IF NOT EXISTS uboard ("
					+ " uid int(11) NOT NULL," + " bid int(11) NOT NULL" + ");";
			log.debug(sql);
			stmt.addBatch(sql);

			/* ulabel */
			sql = "DROP TABLE IF EXISTS ulabel;";
			log.debug(sql);
			stmt.addBatch(sql);
			sql = " CREATE TABLE IF NOT EXISTS ulabel ("
					+ " uid int(11) NOT NULL," + " lid int(11) NOT NULL" + ");";
			log.debug(sql);
			stmt.addBatch(sql);

			/* word */
			sql = "DROP TABLE IF EXISTS word;";
			log.debug(sql);
			stmt.addBatch(sql);
			sql = " CREATE TABLE IF NOT EXISTS word ("
					+ " docId int(11) NOT NULL,"
					+ " word varchar(256) NOT NULL," + " tfidf double NOT NULL"
					+ ");";
			log.debug(sql);
			stmt.addBatch(sql);

			/* doc */
			sql = "DROP TABLE IF EXISTS doc;";
			log.debug(sql);
			stmt.addBatch(sql);
			sql = " CREATE TABLE IF NOT EXISTS doc ("
					+ " docId int(11) NOT NULL," + " tfidf double NOT NULL"
					+ ");";
			log.debug(sql);
			stmt.addBatch(sql);

			stmt.executeBatch();
			if (stmt != null) {
				stmt.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (conn != null)
					conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return true;
	}

	public boolean initData() {

		Statement stmt = null;
		Connection conn = getConnectionByJDBC();
		String sql = "";
		try {
			stmt = conn.createStatement();

			/* board */
			sql = "truncate table board;";
			log.debug(sql);
			stmt.addBatch(sql);
			sql = "insert into board values (1002, 'ITjob', 'IT类兼职', 1);";
			log.debug(sql);
			stmt.addBatch(sql);
			sql = "insert into board values (675, 'SecondComputer', '二手电脑市场', 1);";
			log.debug(sql);
			stmt.addBatch(sql);
			sql = "insert into board values (26, 'ITExpress', 'IT业界特快', 1);";
			log.debug(sql);
			stmt.addBatch(sql);
			sql = "insert into board values (198, 'Entrepreneur', '创业者论坛', 1);";
			log.debug(sql);
			stmt.addBatch(sql);
			sql = "insert into board values (225, 'EconForum', '经济论坛', 1);";
			log.debug(sql);
			stmt.addBatch(sql);
			sql = "insert into board values (835, 'SecondDigi', '二手数码产品', 1);";
			log.debug(sql);
			stmt.addBatch(sql);
			sql = "insert into board values (676, 'SecondBook', '二手图书市场', 1);";
			log.debug(sql);
			stmt.addBatch(sql);
			sql = "insert into board values (123, 'SecondMarket', '二手市场主版', 1);";
			log.debug(sql);
			stmt.addBatch(sql);
			sql = "insert into board values (383, 'WorkLife', '职业生涯', 1);";
			log.debug(sql);
			stmt.addBatch(sql);
			sql = "insert into board values (398, 'PieLove', '鹊桥·征男友女友', 1);";
			log.debug(sql);
			stmt.addBatch(sql);

			/* user */
			sql = "insert into user values (1, 'admin', 'admin', 'coola58@163.com');";
			log.debug(sql);
			stmt.addBatch(sql);

			// label
			sql = "insert into label values(1, '金融');";
			log.debug(sql);
			stmt.addBatch(sql);
			sql = "insert into label values(2, 'java');";
			log.debug(sql);
			stmt.addBatch(sql);
			sql = "insert into label values(3, '比特币');";
			log.debug(sql);
			stmt.addBatch(sql);

			// user label
			sql = "insert into ulabel values(1,1);";
			log.debug(sql);
			stmt.addBatch(sql);
			sql = "insert into ulabel values(1,2);";
			log.debug(sql);
			stmt.addBatch(sql);
			sql = "insert into ulabel values(1,3);";
			log.debug(sql);
			stmt.addBatch(sql);

			stmt.executeBatch();

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (conn != null)
					conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return true;

	}

	/* truncate tables in database */
	public boolean clear() {
		Statement stmt = null;
		Connection conn = getConnectionByJDBC();

		try {
			stmt = conn.createStatement();
			String sql = "truncate TABLE topic;";
			log.debug(sql);
			stmt.addBatch(sql);

			sql = "truncate TABLE article;";
			log.debug(sql);
			stmt.addBatch(sql);

			sql = "truncate TABLE notice;";
			log.debug(sql);
			stmt.addBatch(sql);

			stmt.executeBatch();
			if (stmt != null) {
				stmt.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (conn != null)
					conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return true;
	}

	public HashMap<Integer, String> getBoardList() {
		HashMap<Integer, String> boardMap = new HashMap<Integer, String>();

		Statement stmt = null;
		Connection conn = getConnectionByJDBC();

		try {
			String sql = "select * from board where priority > 0";
			log.debug(sql);

			stmt = conn.createStatement();

			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) {
				int id = rs.getInt("id");
				boardMap.put(id, rs.getString("name"));
			}
			if (rs != null) {
				rs.close();
			}
			if (stmt != null) {
				stmt.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (conn != null)
					conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return boardMap;
	}

	/* get connection */
	public Connection getConnectionByJDBC() {
		Connection conn = null;

		PropHelper helper = new PropHelper();
		Properties prop = helper.getProp();
		String url = prop.getProperty("db");
		String username = prop.getProperty("user");
		String password = prop.getProperty("pwd");

		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		try {
			conn = DriverManager.getConnection(url, username, password);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return conn;
	}

	/* add topic */
	public boolean addTopicBean(TopicBean bean) {
		if (bean == null) {
			System.out.println("bean is null");
			return false;
		}
		Statement stmt = null;
		Connection conn = getConnectionByJDBC();

		try {
			String sql = "select count(1) as cnt from topic where boardId = "
					+ bean.getBoardId() + " and gid = " + bean.getGid();
			// log.debug(sql);

			stmt = conn.createStatement();

			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) {
				int cnt = rs.getInt("cnt");
				if (cnt > 0) {
					return false;
				}
			}
			if (rs != null) {
				rs.close();
			}
			if (stmt != null) {
				stmt.close();
			}

			sql = "insert into topic values('" + bean.getBoardName() + "', "
					+ bean.getBoardId() + ", " + bean.getPage() + ", "
					+ bean.getGid() + ", '" + bean.getTitle() + "', '"
					+ bean.getUrl() + "')";

			log.debug(sql);
			stmt = conn.createStatement();

			stmt.execute(sql);

			if (stmt != null) {
				stmt.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (conn != null)
					conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return true;
	}

	/* add article */
	public boolean addArticleBean(ArticleBean bean) {

		if (bean == null) {
			System.out.println("bean is null");
			return false;
		}

		Statement stmt = null;
		Connection conn = getConnectionByJDBC();

		try {
			String sql = "select count(1) as cnt from article where boardId = "
					+ bean.getBoardId() + " and id = " + bean.getId();
			// log.debug(sql);
			stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) {
				int cnt = rs.getInt("cnt");
				if (cnt > 0) {
					return false;
				}
			}
			if (rs != null) {
				rs.close();
			}
			if (stmt != null) {
				stmt.close();
			}

			String content = bean.getContent();
			BASE64Encoder encoder = new BASE64Encoder();
			sql = "insert into article values('" + bean.getBoardName() + "',"
					+ bean.getBoardId() + ", " + bean.getId() + ", "
					+ bean.getGid() + ", '" + bean.getUrl() + "', '"
					+ bean.getTitle() + "', '" + bean.getAuthor() + "', '"
					+ bean.getSign() + "', '"
					+ encoder.encode(content.getBytes()) + "', '"
					+ bean.getAtauthor() + "', '"
					+ encoder.encode(bean.getAtmsg().getBytes()) + "', '"
					+ encoder.encode(bean.getMsg().getBytes()) + "', '"
					+ bean.getChannel() + "', '" + bean.getTime() + "', '"
					+ bean.getIp() + "')";
			log.debug(sql);

			stmt = conn.createStatement();
			stmt.execute(sql);

			if (stmt != null) {
				stmt.close();
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (conn != null)
					conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return true;
	}

	/* get max page number in table topic */
	public int getMaxPageNoFromDB(int boardId) {
		int maxPageNo = 0;

		Statement stmt = null;
		Connection conn = getConnectionByJDBC();

		try {
			String sql = "select max(page) as mpage from topic where boardId = "
					+ boardId;
			log.debug(sql);
			stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) {
				maxPageNo = rs.getInt("mpage");
			}
			if (rs != null) {
				rs.close();
			}
			if (stmt != null) {
				stmt.close();
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (conn != null)
					conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return maxPageNo;
	}

	/* add notice */
	public boolean addNotice(TopicBean bean, String email) {
		if (bean == null) {
			System.out.println("bean is null");
			return false;
		}

		Statement stmt = null;
		Connection conn = getConnectionByJDBC();

		try {
			int cnt = 0;
			String ssql = "select count(1) from notice where email = '" + email
					+ "' and gid = " + bean.getGid();
			log.debug(ssql);
			stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(ssql);
			while (rs.next()) {
				cnt = rs.getInt(1);
			}

			if (cnt <= 0) {
				String sql = "insert into notice values('" + email + "', '"
						+ bean.getTitle() + "', " + bean.getGid() + ", 0" + ")";
				log.debug(sql);

				stmt = conn.createStatement();
				stmt.execute(sql);
			}

			if (stmt != null) {
				stmt.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (conn != null)
					conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return true;
	}

	/* get to send email number */
	public int getToSendMailNum(String email) {
		int toSendNum = 0;

		Statement stmt = null;
		Connection conn = getConnectionByJDBC();

		try {
			String sql = "select count(1) as cnt from notice where email = '"
					+ email + "'";
			// log.debug(sql);
			stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) {
				toSendNum = rs.getInt("cnt");
			}
			if (rs != null) {
				rs.close();
			}
			if (stmt != null) {
				stmt.close();
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (conn != null)
					conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return toSendNum;
	}

	/* get notice list */
	public ArrayList<NoticeBean> getNoticeListPage(String email, int iCnt) {
		ArrayList<NoticeBean> list = new ArrayList<NoticeBean>();

		Statement stmt = null;
		Connection conn = getConnectionByJDBC();

		try {
			String sql = "select n.title as title, n.gid as gid, a.content as content from notice n, article a where n.gid = a.id and n.status = '0' and n.email = '"
					+ email + "' limit " + iCnt;
			log.debug(sql);
			stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) {
				NoticeBean bean = new NoticeBean();
				bean.setEmail(email);
				bean.setTitle(rs.getString("title"));
				bean.setGid(rs.getInt("gid"));
				String content = rs.getString("content");
				BASE64Decoder decoder = new BASE64Decoder();
				String buffer = null;
				try {
					buffer = new String(decoder.decodeBuffer(content), "UTF-8");
				} catch (IOException e) {
					e.printStackTrace();
				}

				buffer = buffer.replaceAll("\\\\n\\\\r", "<br>");
				buffer = buffer.replaceAll("\\\\n", "<br>");
				buffer = buffer.replaceAll("\\\\r", "<br>");
				bean.setContent(buffer);

				list.add(bean);
			}
			if (rs != null) {
				rs.close();
			}
			if (stmt != null) {
				stmt.close();
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (conn != null)
					conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return list;
	}

	/* get article list */
	public ArrayList<NoticeBean> getTopicListPage(int start, int iCnt) {
		ArrayList<NoticeBean> list = new ArrayList<NoticeBean>();

		Statement stmt = null;
		Connection conn = getConnectionByJDBC();

		try {
			String sql = "select t.title as title, t.gid as gid, a.content as content from topic t, article a where t.gid = a.id limit "
					+ start + "," + iCnt;
			log.debug(sql);
			stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) {
				NoticeBean bean = new NoticeBean();
				bean.setTitle(rs.getString("title"));
				bean.setGid(rs.getInt("gid"));
				String content = rs.getString("content");
				BASE64Decoder decoder = new BASE64Decoder();

				String buffer = null;
				try {
					buffer = new String(decoder.decodeBuffer(content), "UTF-8");
				} catch (IOException e) {
					e.printStackTrace();
				}

				buffer = buffer.replaceAll("\\\\n\\\\r", "<br>");
				buffer = buffer.replaceAll("\\\\n", "<br>");
				buffer = buffer.replaceAll("\\\\r", "<br>");
				bean.setContent(buffer);
				list.add(bean);
			}
			if (rs != null) {
				rs.close();
			}
			if (stmt != null) {
				stmt.close();
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (conn != null)
					conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return list;
	}

	/* get article list */
	public ArrayList<NoticeBean> getTopicListPageNotTFIDF(int start, int iCnt) {
		ArrayList<NoticeBean> list = new ArrayList<NoticeBean>();

		Statement stmt = null;
		Connection conn = getConnectionByJDBC();

		try {
			String sql = "select t.title as title, t.gid as gid, a.content as content from topic t, article a where t.gid = a.id and a.id not in(select docId from doc) limit "
					+ start + "," + iCnt;
			log.debug(sql);
			stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) {
				NoticeBean bean = new NoticeBean();
				bean.setTitle(rs.getString("title"));
				bean.setGid(rs.getInt("gid"));
				String content = rs.getString("content");
				BASE64Decoder decoder = new BASE64Decoder();

				String buffer = null;
				try {
					buffer = new String(decoder.decodeBuffer(content), "UTF-8");
				} catch (IOException e) {
					e.printStackTrace();
				}

				buffer = buffer.replaceAll("\\\\n\\\\r", "<br>");
				buffer = buffer.replaceAll("\\\\n", "<br>");
				buffer = buffer.replaceAll("\\\\r", "<br>");
				bean.setContent(buffer);
				list.add(bean);
			}
			if (rs != null) {
				rs.close();
			}
			if (stmt != null) {
				stmt.close();
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (conn != null)
					conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return list;
	}

	/* get article by article id */
	public NoticeBean getArticleById(int aid) {

		Statement stmt = null;
		Connection conn = getConnectionByJDBC();

		NoticeBean bean = null;

		try {
			String sql = "select t.title as title, t.gid as gid, a.content as content from topic t, article a where t.gid = a.id and a.id = "
					+ aid;
			log.debug(sql);
			stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) {
				bean = new NoticeBean();
				bean.setTitle(rs.getString("title"));
				bean.setGid(rs.getInt("gid"));
				String content = rs.getString("content");
				BASE64Decoder decoder = new BASE64Decoder();

				String buffer = null;
				try {
					buffer = new String(decoder.decodeBuffer(content), "UTF-8");
				} catch (IOException e) {
					e.printStackTrace();
				}

				buffer = buffer.replaceAll("\\\\n\\\\r", "<br>");
				buffer = buffer.replaceAll("\\\\n", "<br>");
				buffer = buffer.replaceAll("\\\\r", "<br>");
				bean.setContent(buffer);
			}
			if (rs != null) {
				rs.close();
			}
			if (stmt != null) {
				stmt.close();
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (conn != null)
					conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return bean;
	}

	/* update notice status */
	public boolean UpdateNotice(ArrayList<NoticeBean> list) {
		StringBuffer gids = new StringBuffer();
		for (int i = 0; i < list.size(); i++) {
			NoticeBean bean = list.get(i);
			gids.append(bean.getGid() + ",");
		}
		gids.append("0");
		Statement stmt = null;
		Connection conn = getConnectionByJDBC();

		try {
			String sql = "update notice set status = '1' where gid in("
					+ gids.toString() + ")";

			log.debug(sql);

			stmt = conn.createStatement();
			stmt.executeUpdate(sql);

			if (stmt != null) {
				stmt.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (conn != null)
					conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return true;
	}

	/* 增加词, 以及词的tfidf */
	public boolean addWord(Integer docId, String word, Double tfidf) {
		Statement stmt = null;
		Connection conn = getConnectionByJDBC();

		try {
			String sql = "select count(1) as cnt from word where docId="
					+ docId + "  and word = '" + word + "'";
			// log.debug(sql);

			stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) {
				String cnt = rs.getString(1);
				if (Long.valueOf(cnt) > 0) {
					if (stmt != null) {
						stmt.close();
					}
					if (conn != null) {
						conn.close();
					}
					return true;
				}
			}

			sql = "insert into word values(" + docId + ",'" + word + "',"
					+ tfidf + ")";
			log.debug(sql);
			stmt.execute(sql);

			if (stmt != null) {
				stmt.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (conn != null)
					conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return true;
	}

	/* 获取词的列表 */
	public ArrayList<TagBean> getWordByDocId(Integer docId) {
		ArrayList<TagBean> list = new ArrayList<TagBean>();

		Statement stmt = null;
		Connection conn = getConnectionByJDBC();

		try {
			String sql = "select docId, word, tfidf from word where docId="
					+ docId + " order by tfidf desc";
			log.debug(sql);

			stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) {
				Integer id = rs.getInt(1);
				String word = rs.getString(2);
				Double tfidf = rs.getDouble(3);

				TagBean bean = new TagBean();
				bean.setDocId(id);
				bean.setWord(word);
				bean.setTfidf(tfidf);
				list.add(bean);
			}

			if (stmt != null) {
				stmt.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (conn != null)
					conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return list;
	}

	/* 增加doc, 以及doc的tfidf */
	public boolean addDoc(Integer docId, Double tfidf) {
		Statement stmt = null;
		Connection conn = getConnectionByJDBC();

		try {
			String sql = "select count(1) as cnt from doc where docId=" + docId;
			// log.debug(sql);

			stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) {
				String cnt = rs.getString(1);
				if (Long.valueOf(cnt) > 0) {
					if (stmt != null) {
						stmt.close();
					}
					if (conn != null) {
						conn.close();
					}
					return true;
				}
			}

			sql = "insert into doc values(" + docId + "," + tfidf + ")";
			log.debug(sql);
			stmt.execute(sql);

			if (stmt != null) {
				stmt.close();
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (conn != null)
					conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return true;
	}

	// 将数据库中的blob类型转换为字符串
	private String Blob2Str(Blob blob) {
		String out = null;
		if (blob != null) {
			try {
				InputStream instream = blob.getBinaryStream();
				int flength = (int) blob.length();
				byte[] b = new byte[flength];
				byte[] nb = new byte[1024];
				int len = 0;
				int tlen = 0;
				while (flength > 0) {
					len = instream.read(nb);
					System.arraycopy(nb, 0, b, tlen, len);
					tlen = len;
					flength -= len;
				}
				out = new String(b, "utf-8");
				instream.close();
			} catch (SQLException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return out;
	}

	// 获取UserLabel
	public ArrayList<UserLabelBean> getUserLabelList(String email) {
		ArrayList<UserLabelBean> list = new ArrayList<UserLabelBean>();

		Statement stmt = null;
		Connection conn = getConnectionByJDBC();

		try {
			String sql = "select u.id, u.email, l.id, l.label from user u, label l, ulabel ul where u.id=ul.uid and l.id = ul.lid and u.email='"
					+ email + "'";
			log.debug(sql);

			stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) {
				UserLabelBean bean = new UserLabelBean();
				int uid = rs.getInt(1);
				String uemail = rs.getString(2);
				int lid = rs.getInt(3);
				String label = rs.getString(4);

				bean.setUid(uid);
				bean.setuEmail(uemail);
				bean.setLid(lid);
				bean.setLabel(label);

				list.add(bean);
			}

			if (stmt != null) {
				stmt.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (conn != null)
					conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return list;
	}

	// 添加用户
	public int addUser(UserBean bean) {
		Statement stmt = null;
		Connection conn = getConnectionByJDBC();

		try {
			int max = (int) System.currentTimeMillis();

			String sql = "select max(id) from user";

			stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) {
				max = rs.getInt(1);
			}

			int uid = 0;
			sql = "select id from user where email = '" + bean.getEmail() + "'";
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			while (rs.next()) {
				uid = rs.getInt(1);
			}
			if (uid > 0) {
				return uid;
			}

			// 增加记录
			sql = "insert into user values(" + (max + 1) + ", '"
					+ bean.getName() + "','" + bean.getPwd() + "','"
					+ bean.getEmail() + "')";
			log.debug(sql);
			stmt = conn.createStatement();
			stmt.execute(sql);

			if (stmt != null) {
				stmt.close();
			}

			return max + 1;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (conn != null)
					conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return 0;
	}

	// 添加标签
	public int addLabel(LabelBean bean) {
		Statement stmt = null;
		Connection conn = getConnectionByJDBC();

		try {
			int max = (int) System.currentTimeMillis();

			String sql = "select max(id) from label";

			stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) {
				max = rs.getInt(1);
			}

			int id = 0;
			sql = "select id from label where label = '" + bean.getLabel()
					+ "'";
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			while (rs.next()) {
				id = rs.getInt(1);
			}
			if (id > 0) {
				return id;
			}

			// 增加记录
			sql = "insert into label values(" + (max + 1) + ", '"
					+ bean.getLabel() + "')";
			log.debug(sql);
			stmt = conn.createStatement();
			stmt.execute(sql);

			if (stmt != null) {
				stmt.close();
			}
			return max + 1;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (conn != null)
					conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return 0;
	}

	// 添加用户订阅标签
	public boolean addUserLabel(UserLabelBean bean) {
		Statement stmt = null;
		Connection conn = getConnectionByJDBC();

		try {
			int cnt = 0;
			String sql = "select count(1) from ulabel where uid = "
					+ bean.getUid() + " and lid = " + bean.getLid();
			stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) {
				cnt = rs.getInt(1);
			}
			if (cnt > 0) {
				return false;
			}

			// 增加记录
			sql = "insert into ulabel values(" + bean.getUid() + ", "
					+ bean.getLid() + ")";
			log.debug(sql);
			stmt = conn.createStatement();
			stmt.execute(sql);

			if (stmt != null) {
				stmt.close();
			}
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (conn != null)
					conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return true;
	}

	// 获取用户
	public ArrayList<UserBean> getUser() {
		Statement stmt = null;
		Connection conn = getConnectionByJDBC();
		ArrayList<UserBean> list = new ArrayList<UserBean>();
		try {

			String sql = "select * from user";

			stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) {
				UserBean bean = new UserBean();
				bean.setId(rs.getInt(1));
				bean.setName(rs.getString(2));
				bean.setPwd(rs.getString(3));
				bean.setEmail(rs.getString(4));

				list.add(bean);
			}

			if (stmt != null) {
				stmt.close();
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (conn != null)
					conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return list;
	}

	public void test() {
		String sql = "select * from article limit 1, 10";
		Connection conn = getConnectionByJDBC();
		try {
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) {
				String buffer = null;
				String a = rs.getString("title");
				System.out.println("title = [" + a + "]");

				a = Blob2Str(rs.getBlob("sign"));
				System.out.println("sign = [" + a + "]");

				a = Blob2Str(rs.getBlob("content"));
				BASE64Decoder decoder = new BASE64Decoder();
				try {
					buffer = new String(decoder.decodeBuffer(a), "UTF-8");
				} catch (IOException e) {
					e.printStackTrace();
				}
				System.out.println("content = [" + buffer + "]");

				a = Blob2Str(rs.getBlob("atmsg"));
				decoder = new BASE64Decoder();
				try {
					buffer = new String(decoder.decodeBuffer(a), "UTF-8");
				} catch (IOException e) {
					e.printStackTrace();
				}
				System.out.println("atmsg = [" + buffer + "]");

				a = Blob2Str(rs.getBlob("msg"));
				decoder = new BASE64Decoder();
				try {
					buffer = new String(decoder.decodeBuffer(a), "UTF-8");
				} catch (IOException e) {
					e.printStackTrace();
				}
				System.out.println("msg = [" + buffer + "]");
				System.out
						.println("------------------------------------------------------------");
			}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		} finally {
			try {
				if (conn != null)
					conn.close();
			} catch (SQLException e) {
				System.out.println(e.getMessage());
				e.printStackTrace();
			}
		}
	}

	public static void main(String[] args) {
		DBUtil db = new DBUtil();
		db.test();
	}
}
