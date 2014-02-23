<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<title>Control Panel</title>

		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
		<meta http-equiv="description" content="This is my page">
		<style>
body {
	font-family: Segoe UI;
	font-size: 15pt;
	color: 000000;
	margin-left: 35 px;
	margin-top: 15 px;
	background-position: top left;
	background-repeat: repeat-y;
}
</style>

	</head>

	<body>
		<h1>
			1. Database
		</h1>
		<ul>
			<li>
				To create or rebuild tables and generate initialising data, run
				<a href="./OpdbServlet?oper=init" onclick="return confirm('coreate or rebuild database?');">init database</a> (!!! cautious)
			<li>
				To truncate tables, run
				<a href="./OpdbServlet?oper=clear" onclick="return confirm('clear database?');">clear database</a> (!!! cautious)
			
		</ul>
		<h1>
			2. Functions
		</h1>
		<ul>
			<li>
				<a href="./CrawlServlet">Crawl newsmth.net</a>
			<li>
				<a href="./MailServle">Send a mail</a>
			<li>
				<a href="./SearchServlet?key=java">Search</a>
		</ul>
	</body>
</html>
