package com.newsmths.ide.servlet;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.newsmths.bean.HitBean;
import com.newsmths.lucene.Search;
import com.newsmths.lucene.SearchHit;
import com.newsmths.tfidf.TagSearch;

public class SearchServlet extends HttpServlet {
	private static Logger log = Logger.getLogger(SearchServlet.class);

	/**
	 * Constructor of the object.
	 */
	public SearchServlet() {
		super();
	}

	/**
	 * Destruction of the servlet. <br>
	 */
	public void destroy() {
		super.destroy(); // Just puts "destroy" string in log
		// Put your code here
	}

	/**
	 * The doGet method of the servlet. <br>
	 * 
	 * This method is called when a form has its tag value method equals to get.
	 * 
	 * @param request
	 *            the request send by the client to the server
	 * @param response
	 *            the response send by the server to the client
	 * @throws ServletException
	 *             if an error occurred
	 * @throws IOException
	 *             if an error occurred
	 */
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		doPost(request, response);
	}

	/**
	 * The doPost method of the servlet. <br>
	 * 
	 * This method is called when a form has its tag value method equals to
	 * post.
	 * 
	 * @param request
	 *            the request send by the client to the server
	 * @param response
	 *            the response send by the server to the client
	 * @throws ServletException
	 *             if an error occurred
	 * @throws IOException
	 *             if an error occurred
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");

		response.setContentType("text/html");

		String key = request.getParameter("key");
		if (null == key || "".equals(key)) {
			key = "java";
		}
		log.debug("search key = [" + key + "]");

		// 分页页码
		String page = request.getParameter("p");
		Integer nPage = 1;
		if (null != page && !"".equals(page)) {
			nPage = Integer.valueOf(page);
		}

		// action 动作
		String a = request.getParameter("a");
		if (null == a || "".equals(a)) {
			a = "s";
		}
		int PAGE_SIZE = 10;
		ArrayList<HitBean> list = null;
		int total = 0;
		if ("s".equals(a)) { // search
			Search search = new Search();
			list = search.search(key, nPage, PAGE_SIZE);
			total = search.getTotal();
		} else {// tag
			TagSearch search = new TagSearch();
			try {
				list = search.search(key, nPage, PAGE_SIZE);
				total = search.getTotal();

			} catch (Exception e) {
				e.printStackTrace();
				log.error("", e);
			}
		}

		request.setAttribute("list", list);
		request.setAttribute("key", key);
		request.setAttribute("total", total);
		request.setAttribute("p", nPage);
		request.setAttribute("a", a);

		RequestDispatcher dispatcher = request
				.getRequestDispatcher("index.jsp");
		dispatcher.forward(request, response);
	}

	/**
	 * Initialization of the servlet. <br>
	 * 
	 * @throws ServletException
	 *             if an error occurs
	 */
	public void init() throws ServletException {
		// Put your code here
	}

}
