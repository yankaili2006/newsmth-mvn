package com.newsmths.ide.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.newsmths.bean.HitBean;
import com.newsmths.bean.LabelBean;
import com.newsmths.bean.UserBean;
import com.newsmths.bean.UserLabelBean;
import com.newsmths.tfidf.TagSearch;
import com.newsmths.util.DBUtil;

public class SubServlet extends HttpServlet {
	private static Logger log = Logger.getLogger(SubServlet.class);

	/**
	 * Constructor of the object.
	 */
	public SubServlet() {
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

		String email = request.getParameter("email");
		String keyws = request.getParameter("keyws");

		System.out.println("email = " + email + "keyws = " + keyws);

		String msg = "订阅失败！";
		if (email == null || "".equals(email)) {
			msg = "邮箱不能为空！";
		} else if (keyws == null || "".equals(keyws)) {
			msg = "关键词不能为空！";
		} else {
			DBUtil util = new DBUtil();
			UserBean uBean = new UserBean();
			uBean.setEmail(email);
			uBean.setName(email);
			uBean.setPwd("000000");

			int id = util.addUser(uBean);
			if (id <= 0) {
				msg = "创建用户失败！";
			} else {
				uBean.setId(id);

				String keys[] = keyws.split(" ");
				for (int i = 0; i < keys.length; i++) {
					String label = keys[i];
					LabelBean lBean = new LabelBean();
					lBean.setLabel(label);
					int lid = util.addLabel(lBean);
					if (id <= 0) {
						msg = "创建标签失败！";
						break;
					} else {
						lBean.setId(lid);

						UserLabelBean ulBean = new UserLabelBean();
						ulBean.setLid(lid);
						ulBean.setLabel(label);
						ulBean.setUid(uBean.getId());
						ulBean.setuEmail(uBean.getEmail());

						if (!util.addUserLabel(ulBean)) {
							msg = "已经订阅过了！";
						} else {
							msg = "订阅成功！";
						}
					}

				}
			}
		}

		System.out.println(msg);
		response.setContentType("text/xml;charset=UTF-8");
		response.setHeader("Pragma",   "no-cache");   //HTTP   1.0  
		response.setDateHeader("Expires",   0);   //prevents   caching   at   the   proxy   server  
		PrintWriter out = response.getWriter();
		out.write(msg);
		out.flush();
		out.close();
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
