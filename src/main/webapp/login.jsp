<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>ログイン</title>
</head>
<body>
	<header>
		<h2>書籍管理アプリケーション</h2>
	</header>
	<div style="display:block; text-align:center; width:fit-content; margin:0 auto;">
		<%
			String msg = "";
			msg = (String)request.getAttribute("msg");
			if (msg != null) {
		%>
		<br><strong>新規登録が完了しました!</strong>
		<% } %>
		<form action="j_security_check" method="POST">
		<br>
			<p style="text-align:left; margin-bottom:0;">ユーザID</p>
			<input type="text" name="j_username"><br><br>
			<p style="text-align:left; margin-bottom:0;">パスワード</p>
			<input type="password" name="j_password"><br><br>
			<input type="submit" value="ログイン">
		</form>
		<br>
		<a href="signUp.jsp">新規登録</a>
	</div>
</body>
</html>