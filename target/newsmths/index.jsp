<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ page
	import="com.newsmths.lucene.SearchHit,com.newsmths.bean.*,java.net.*"%>
<%@ page import="com.newsmths.view.PageViewRender"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>

<title>订阅水木网</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<!-- Bootstrap -->
<link href="css/bootstrap.min.css" rel="stylesheet" media="screen">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<style type="text/css">

/* Sticky footer styles
      -------------------------------------------------- */
html,body {
	height: 100%;
	/* The html and body elements cannot have any padding or margin. */
}

/* Set the fixed height of the footer here */
#push,#footer {
	height: 60px;
}

#footer {
	background-color: #f5f5f5;
}

/* Lastly, apply responsive CSS fixes as necessary */
@media ( max-width : 767px) {
	#footer {
		margin-left: -20px;
		margin-right: -20px;
		padding-left: 20px;
		padding-right: 20px;
	}
}

.container .credit {
	margin: 20px 0;
}
</style>
</head>
<body>
	<div class="navbar">
		<div class="navbar-inner">
			<div class="container">
				<a class="btn btn-navbar" data-toggle="collapse"
					data-target=".nav-collapse"> <span class="icon-bar"></span> <span
					class="icon-bar"></span> <span class="icon-bar"></span>
				</a>

				<!-- Be sure to leave the brand out there if you want it shown -->
				<a class="brand" href="index.jsp">订阅水木网</a>
				<form class="navbar-form pull-right" action="SubServlet"
					method="post">
					<input type="text" class="span2" name="email"
						placeholder="abc@163.com"> <span>&nbsp;&nbsp;</span> <input
						type="text" class="span2" name="keyws" placeholder="key1 key2">
					<span>&nbsp;&nbsp;</span>
					<button type="submit" class="btn">订阅</button>
				</form>
			</div>
		</div>
	</div>
	<%
		ArrayList<HitBean> list = (ArrayList<HitBean>) request
				.getAttribute("list");
		String key = (String) request.getAttribute("key");
		if (key == null || "".equals(key) || "null".equals(key)) {
			key = "";
		}

		int PAGE_SIZE = 10, total = 0, cnt = 0, p = 0;
		Object o = request.getAttribute("total");
		if (null != o) {
			total = (Integer) o;
			cnt = (total - 1) / PAGE_SIZE + 1;
		}
		o = request.getAttribute("p");
		if (null != o) {
			p = (Integer) o;
		}

		String a = "s"; // action type s: search, t:tag 
		o = request.getAttribute("a");
		if (null != o && !"".equals(o)) {
			a = String.valueOf(o);
		}

		String t = "SearchServlet"; //TagServlet
	%>
	<div class="container">
		<div class="row">
			<form class="navbar-form pull-left" action="SearchServlet"
				method="post" name="search">
				<div class="span3"></div>
				<div class="span3">
					<input type="text" class="span3" name="key" value="<%=key%>">
					<input type="hidden" name="a" id="a" value="<%=a%>">
				</div>
				<div class="span2">
					<button type="submit" class="btn"
						onclick="document.getElementById('a').value='s';">搜索</button>
				</div>
				<div class="span4">
					<button type="submit" class="btn"
						onclick="document.getElementById('a').value='t';">进入标签的世界</button>
				</div>
			</form>
		</div>
		<%
			PageViewRender render = new PageViewRender();
			out.print(render.render(list));
		%>
		<div class="pagination">
			<ul>
				<%
					if (null != list && list.size() > 0) {
						int MAX_P2SHOW = 10;
						if (cnt <= MAX_P2SHOW) {
							if (1 == p) {
								out.print("<li class=\"disabled\"><a href=\"#\">&laquo;</a></li>");
							} else {
								out.print("<li><a href=\"" + t + "?key=" + key + "&p="
										+ (p - 1) + "&a=" + a + "\">&laquo;</a></li>");
							}

							for (int i = 1; i <= cnt; i++) {
								if (i == p) {
									out.print("<li class=\"active\"><a href=\"" + t
											+ "?key=" + key + "&p=" + i + "&a=" + a
											+ "\">" + i + "</a></li>");
								} else {
									out.print("<li><a href=\"" + t + "?key=" + key
											+ "&p=" + i + "&a=" + a + "\">" + i
											+ "</a></li>");
								}
							}

							if (cnt == p) {
								out.print("<li class=\"disabled\"><a href=\"#\">&raquo;</a></li>");
							} else {
								out.print("<li><a href=\"" + t + "?key=" + key + "&p="
										+ (p + 1) + "&a=" + a + "\">&raquo;</a></li>");
							}
						} else {
							int st = p, ed = p, i = 1;

							while (i < MAX_P2SHOW) {
								if (st > 1) {
									st--;
									i++;
								}
								if (ed < cnt && i < MAX_P2SHOW) {
									ed++;
									i++;
								}
							}

							if (st - 1 >= 1) {
								out.print("li><a href=\"" + t + "?key=" + key + "&p="
										+ (st - 1) + "&a=" + a + "\">&laquo;</a></li>");
							} else {
								out.print("li><a class=\"disabled\" href=\"#\">&laquo;</a></li>");
							}

							for (int j = st; j <= cnt; j++) {
								if (p == j) {

									out.print("<li class=\"active\"><a href=\"" + t
											+ "?key=" + key + "&p=" + j + "&a=" + a
											+ "\">" + j + "</a></li>");
								} else {
									out.print("<li><a href=\"" + t + "?key=" + key
											+ "&p=" + j + "&a=" + a + "\">" + j
											+ "</a></li>");
								}
							}

							if (ed + 1 <= cnt) {
								out.print("li><a href=\"" + t + "?key=" + key + "&p="
										+ (ed + 1) + "&a=" + a + "\">&raquo;</a></li>");
							} else {
								out.print("li class=\"disabled\"><a href=\"#\">&raquo;</a></li>");
							}
						}
					}
				%>
			</ul>
		</div>
	</div>
	<div id="footer">
		<div class="container">
			<p class="muted credit">
				Created and Hosted by <a href="http://yankaili2006.github.io"
					target="_blank">Yankai Li</a>, using Bootstrap. About me: <a
					href="http://www.github.com/yankaili2006" target="_blank">GitHub</a>,
				<a href="http://weibo.com/u/1636216002" target="_blank">新浪微博</a>.
			</p>
		</div>
	</div>
	<script src="js/jquery.js"></script>
	<script src="js/bootstrap.min.js"></script>
</body>
</html>