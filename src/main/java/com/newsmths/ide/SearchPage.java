package com.newsmths.ide;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.newsmths.bean.HitBean;
import com.newsmths.lucene.Search;
import com.newsmths.view.PageViewRender;

public class SearchPage extends HttpServlet {
	
	private static Logger log = Logger.getLogger(SearchPage.class);
	/**
	 * Constructor of the object.
	 */
	public SearchPage() {
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

		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");

		response.setContentType("text/html");
		PrintWriter out = response.getWriter();

		String key = request.getParameter("key");
		
		log.debug("Search key = [" + key + "]");
		
		// 分页页码
		String page = request.getParameter("p");
		Integer nPage = 1;
		if (null != page && !"".equals(page)) {
			nPage = Integer.valueOf(page);
		}
		
		int PAGE_SIZE = 10;
		if (null != key && !"".equals(key) && !"null".equals(key)) {
			log.debug("search key = [" + key + "]");

			Search search = new Search();
			ArrayList<HitBean> list = search.search(key, nPage, PAGE_SIZE);
			
			PageViewRender render = new PageViewRender();
			out.print(render.render(list));
		}

		out.flush();
		out.close();
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
		doGet(request, response);
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
