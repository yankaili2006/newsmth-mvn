<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<%
	String code = (String) request.getAttribute("code");
	if (code == null || "".equals(code) || "null  ".equals(code)) {
		code = "";

	}

	String msg = (String) request.getAttribute("msg");
	if (msg == null || "".equals(msg) || "null  ".equals(msg)) {
		msg = "";

	}
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title>订阅</title>
<style type="text/css">
<!--
.STYLE1 {
	font-size: 14px
}

.STYLE2 {
	font-size: 36px;
	color: #FF0000;
	font-weight: bold;
}

.STYLE3 {
	font-size: 24px
}

.STYLE5 {
	font-size: 16px
}

.STYLE6 {
	color: #FF0000;
	font-size: 18px;
}
-->
</style>
</head>
<body>
	<script>
		function addCheck() {
			var email = document.getElementById("email").value;
			var keyws = document.getElementById("keyws").value;
			if (email == "") {
				alert("邮箱不能为空!");
				document.getElementById("email").focus();
				return false;
			}
			if (keyws == "") {
				alert("关键词不能为空!");
				document.getElementById("keyws").focus();
				return false;
			}
		}
	</script>
	<center>
		<%=code + "," + msg%>
		<form id="form1" name="form1" method="post"
			action="<%=request.getContextPath()%>/SubServlet"
			onSubmit="javascript: return addCheck()">
			     
			<table width="400" height="118" border="0" bgcolor="#33CCFF">
				<tr>
					<td colspan="2"><div align="center">
							<span class="STYLE3">订阅</span>
						</div></td>
				</tr>
				<tr>
					<td width="150"><span class="STYLE1">邮箱</span></td>
					<td width="250"><input type="text" name="email" id="email" />
					</td>
				</tr>
				<tr>
					<td width="150"><span class="STYLE5">关键词(空格分割)</span></td>
					<td width="250">         <input type="text" name="keyws"
						id="keyws" />          </td>
				</tr>
				<tr>
					<td colspan="2"><label>          
							&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type="submit" name="Submit"
							value="确定" />        </label> </td>
				</tr>
			</table>
			       
		</form>
	</center>
</body>
</html>