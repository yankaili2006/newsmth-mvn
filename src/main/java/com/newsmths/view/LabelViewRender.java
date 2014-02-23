package com.newsmths.view;

import java.util.ArrayList;

import com.newsmths.bean.UserLabelBean;
import com.newsmths.util.DBUtil;

public class LabelViewRender {
	
	public String render() {
		StringBuffer sBuf = new StringBuffer();
		String email = "coola58@163.com";

		DBUtil util = new DBUtil();
		ArrayList<UserLabelBean> list = util.getUserLabelList(email);
		
		for (int i = 0; i < list.size(); i++) {
			UserLabelBean bean = list.get(i);
			sBuf.append("<a href='TagServlet?key=" + bean.getLabel() + "'>"
					+ bean.getLabel() + "</a>&nbsp;&nbsp;&nbsp;&nbsp;");
		}
		
		return sBuf.toString();
	}
}
