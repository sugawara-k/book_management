<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>ログアウト</title>
</head>
<body>
<%
	session.invalidate();
%>
<header>
		<h2 style="margin-bottom:0;">書籍管理アプリケーション</h2>
		<ul style="margin-top:0; padding-top:10px; width:100%; box-sizing:border-box; background-color:#eee; list-style:none; display:flex;">
			<li title="ログイン" style="margin:0 10px;"><a href="LoginServlet"><img src="./png/003-login.png" alt="login_icons"></a></li>
			<li title="topへ" style="margin-left:auto; margin-right:20px;"><a href="top.jsp"><img src="./png/001-home.png" alt="home_icons"></a></li>
			<li title="登録書籍一覧へ" style="margin-right:5%;"><a href="SearchServlet"><img src="./png/002-books.png" alt="books_icons"></a></li>
		</ul>
	</header>
	<p style="text-align:center;"><strong>ログアウトしました</strong></p>
</body>
</html>