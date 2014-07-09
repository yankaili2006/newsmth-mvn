package com.newsmths.ide.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Timer;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.newsmths.ide.task.CrawlTask;
import com.newsmths.ide.task.IndexTask;
import com.newsmths.ide.task.MailTask;
import com.newsmths.ide.task.TagTask;
import com.newsmths.lucene.Index;
import com.newsmths.util.PropHelper;

public class InitServlet extends HttpServlet {

	/**
	 * Constructor of the object.
	 */
	public InitServlet() {
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

		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		if (out != null) {
			out.println("<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\">");
			out.println("<HTML>");
			out.println("  <HEAD><TITLE>A Servlet</TITLE></HEAD>");
			out.println("  <BODY>");
			out.print("    This is ");
			out.print(this.getClass());
			out.println(", using the GET method");
			out.println("  </BODY>");
			out.println("</HTML>");
			out.flush();
			out.close();
		}
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

		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		if (out != null) {
			out.println("<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\">");
			out.println("<HTML>");
			out.println("  <HEAD><TITLE>A Servlet</TITLE></HEAD>");
			out.println("  <BODY>");
			out.print("    This is ");
			out.print(this.getClass());
			out.println(", using the POST method");
			out.println("  </BODY>");
			out.println("</HTML>");
			out.flush();
			out.close();
		}
	}

	/**
	 * Initialization of the servlet. <br>
	 * 
	 * @throws ServletException
	 *             if an error occurs
	 */
	public void init() throws ServletException {

		final long NO_DELAY = 0;
		final long MILLIS_IN_HOUR = 3600 * 1000;
		final long DELAY_TENMIN = 60 * 1000;

		Timer timerCrawler = new Timer("抓取", true);
		timerCrawler.schedule(new CrawlTask(), NO_DELAY, MILLIS_IN_HOUR);

		Timer timerIndexer = new Timer("索引", true);
		timerIndexer.schedule(new IndexTask(), DELAY_TENMIN, MILLIS_IN_HOUR);

		// Timer timerTager = new Timer("标签", true);
		// timerTager.schedule(new TagTask(), NO_DELAY, MILLIS_IN_HOUR/6);

		// Timer timerMailer = new Timer("发邮件", true);
		// timerMailer.schedule(new MailTask(), DELAY_TENMIN, MILLIS_IN_HOUR/6);

	}

}
