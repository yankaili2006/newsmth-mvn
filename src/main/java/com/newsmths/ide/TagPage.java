package com.newsmths.ide;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.newsmths.bean.HitBean;
import com.newsmths.tfidf.TagSearch;
import com.newsmths.view.PageViewRender;

public class TagPage extends HttpServlet {

	/**
	 * Constructor of the object.
	 */
	public TagPage() {
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
	 * @param request the request send by the client to the server
	 * @param response the response send by the server to the client
	 * @throws ServletException if an error occurred
	 * @throws IOException if an error occurred
	 */
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		String key = request.getParameter("key");

		// 分页页码
		String page = request.getParameter("p");
		Integer nPage = 1;
		if (null != page && !"".equals(page)) {
			nPage = Integer.valueOf(page);
		}
		
		int PAGE_SIZE = 10;
		if (null != key && !"".equals(key) && !"null".equals(key)) {

			TagSearch search = new TagSearch();
			ArrayList<HitBean> list = null;
			try {
				list = search.search(key, nPage, PAGE_SIZE);
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			PageViewRender render = new PageViewRender();
			out.print(render.render(list));
		}

		out.flush();
		out.close();
	}

	/**
	 * The doPost method of the servlet. <br>
	 *
	 * This method is called when a form has its tag value method equals to post.
	 * 
	 * @param request the request send by the client to the server
	 * @param response the response send by the server to the client
	 * @throws ServletException if an error occurred
	 * @throws IOException if an error occurred
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		doGet(request, response);
	}

	/**
	 * Initialization of the servlet. <br>
	 *
	 * @throws ServletException if an error occurs
	 */
	public void init() throws ServletException {
		// Put your code here
	}

}
