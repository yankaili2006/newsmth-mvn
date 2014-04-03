package com.newsmths.ide;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Properties;

import org.apache.log4j.Logger;

import com.newsmths.bean.ArticleBean;
import com.newsmths.bean.NoticeBean;
import com.newsmths.bean.TopicBean;
import com.newsmths.ide.task.MailTask;
import com.newsmths.util.AccountUtil;
import com.newsmths.util.DBUtil;
import com.newsmths.util.MailUtil;
import com.newsmths.util.PropHelper;

public class Mailer {

	private static Logger log = Logger.getLogger(MailTask.class);

	public boolean mail(String email) {

		/* construct email content */
		DBUtil util = new DBUtil();
		ArrayList<NoticeBean> list = null;
		StringBuffer content = new StringBuffer();
		
		list = util.getNoticeListPage(email, 20);

		if (list.size() <= 0)
			return false;

		for (int j = 0; j < list.size(); j++) {
			NoticeBean bean = (NoticeBean) list.get(j);
			content.append("<h1>");
			content.append(bean.getTitle());
			content.append("</h1>");
			content.append("<p>" + bean.getContent() + "</p>");
		}

		/* send mail */
		Date nowTime = new Date();
		SimpleDateFormat time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String sTime = time.format(nowTime);

		MailUtil mailer = new MailUtil();

		PropHelper helper = new PropHelper();
		Properties prop = helper.getProp();
		String mailType = prop.getProperty("MAIL");
		if ("javasendmail".equals(mailType)) {
			return mailer.SendJavaMail(email, sTime, content.toString());
		} else if ("linuxmail".equals(mailType)) {
			try {
				String cmd = "echo '" + content.toString() + "' | mail -s "
						+ sTime + " " + email;
				Process process = Runtime.getRuntime().exec(cmd);
				log.error(cmd);
			} catch (IOException e) {
				e.printStackTrace();
				log.error("", e);
				return false;
			}
		}

		util.UpdateNotice(list);
		return true;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String mail = "coola58@163.com";

		Mailer dispatcher = new Mailer();
		dispatcher.mail(mail);
	}

}
