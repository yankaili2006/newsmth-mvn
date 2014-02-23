<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ page
	import="com.newsmths.lucene.SearchHit,com.newsmths.bean.*,java.net.*"%>
<%@ page
	import="com.newsmths.util.PropHelper,com.newsmths.view.PageViewRender"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>

<title>Searcher</title>
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
<meta http-equiv="description" content="This is my page">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" type="text/css" href="jPaginate/css/style.css"
	media="screen" />
<style>
#center {
	height: 160px;
	width: 400px;
	vertical-align: middle;
	line-height: 200px;
}

.demo {
	width: 580px;
	padding: 10px;
	margin: 10px auto;
	border: 1px solid #fff;
	background-color: #f7f7f7;
}
</style>
<script type="text/javascript" src="jPaginate/jquery-1.3.2.js"></script>
<script src="jPaginate/jquery.paginate.js" type="text/javascript"></script>

<%
	ArrayList<HitBean> list = (ArrayList<HitBean>) request.getAttribute("list");
	String key = (String) request.getAttribute("key");
	if (key == null || "".equals(key) || "null".equals(key)) {
		key = "";
	}
	
	int PAGE_SIZE = 10, total = 0, cnt = 0, p = 0;
	Object o = request.getAttribute("total");
	if (null != o){
		total = (Integer) o;
		cnt = (total-1)/PAGE_SIZE + 1;
	}
	o = request.getAttribute("p");
	if (null != o){
	   p = (Integer) o;
	}

%>
</head>
<body>
	<div id="center">
		<form name="f1" action="SearchServlet" method="get">
			<table bgcolor="#FFFFFF" style="font-size: 9pt;">
				<tr height="60">
					<td valign="top"></td>
					<td><input name="key" size="30" maxlength="100"
						value="<%=key%>"> <input type="submit" value="搜索">
					</td>
				</tr>
			</table>
		</form>
		<form name="f2" action="TagServlet" method="get">
			<table bgcolor="#FFFFFF" style="font-size: 9pt;">
				<tr height="60">
					<td valign="top"></td>
					<td><input name="key" size="30" maxlength="100"
						value="<%=key%>"> <input type="submit" value="进入标签的世界">
					</td>
				</tr>
				
			</table>
		</form>
	</div>
	<div id="pagetxt">
		<%
			PageViewRender render = new PageViewRender();
				out.print(render.render(list));
		%>
	</div>
	<% if (null != list && list.size() > 0){ %>
	<div class="demo">
		<div id="demo2"></div>
	</div>
	<script type="text/javascript">
		$(function() {
			$("#demo2").paginate({
				count : <%=cnt%>,      //总页数
				start : <%=p%>,        //开始显示的页数
				display : <%=cnt%>,    //数字，分页条显示的页数
				border : false,
				text_color : '#888',
				background_color : '#EEE',
				text_hover_color : 'black',
				background_hover_color : '#CFCFCF',
				onChange : function(page) {
					$("#pagetxt").load("SearchPage?key=<%=key%>&p=" + page);
				}
			});
		});
	</script>
	<%} %>

</body>
</html>
