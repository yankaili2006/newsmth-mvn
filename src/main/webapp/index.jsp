<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ page
	import="com.newsmths.lucene.SearchHit,com.newsmths.bean.*,java.net.*"%>
<%@ page
	import="com.newsmths.util.PropHelper,com.newsmths.view.PageViewRender"%>
<%@ page
	import="com.newsmths.view.LabelViewRender"%>

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

* {
  -webkit-box-sizing: border-box;
     -moz-box-sizing: border-box;
          box-sizing: border-box;
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
<div id = "main">
	<div id="center" style="width:1000px;">
		<form name="f1" action="SearchServlet" method="get">
			<table bgcolor="#FFFFFF" style="font-size: 9pt;" width="1000px;">
				<tr height="60" valign="middle">
					<td width="300px"><input name="key" size="30" maxlength="100"
						value="<%=key%>">
					</td>
					<td width="150px"><input type="submit" value="搜索"></td>
					<td width="200px"></td>
				</tr>
			</table>
		</form>
		<form name="f2" action="TagServlet" method="get">
			<table bgcolor="#FFFFFF" style="font-size: 9pt;" width="800px">
				<tr height="60" valign="middle">
					<td width="300px"><input name="key" size="30" maxlength="100"
						value="<%=key%>">
					</td>
					<td width="150px"><input type="submit" value="进入标签的世界">
					</td>
					<td width="200px"><%
						//LabelViewRender lrender = new LabelViewRender();
						//out.print(lrender.render());
					%></td>
					<td width="150px"><a href="<%=request.getContextPath()%>/sub.jsp">我要定制</a>
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
	<%
		if (null != list && list.size() > 0){
	%>
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
					$("#pagetxt").load("TagPage?key=<%=key%>&p=" + page);
				}
			});
		});
	</script>
	<%
		}
	%>
	</div>
</body>
</html>
