<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page import="book_management.*"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>トップ画面</title>
</head>
<body>
	<header>
		<h2 style="margin-bottom:0;">書籍管理アプリケーション</h2>
		<%
			String loginUser;
			if(request.getUserPrincipal() != null) {
				loginUser = (String)request.getUserPrincipal().getName();
			} else {
				loginUser = null;
			}
		%>
		<ul style="margin-top:0; padding-top:10px; width:100%; box-sizing:border-box; background-color:#eee; list-style:none; display:flex;">
			<% if(loginUser == null) { %>
			<li title="ログイン" style="margin:0 10px;"><a href="LoginServlet"><img src="./png/003-login.png" alt="login_icons"></a></li>
			<% } else { %>
			<li style="margin:auto 20px auto 0;">ログインユーザ：<%= loginUser %></li>
			<li title="ログアウト" style="margin:0 10px;"><a href="logout.jsp"><img src="./png/004-logout.png" alt="logout_icons"></a></li>
			<% } %>
			<li title="topへ" style="margin-left:auto; margin-right:20px;"><a href="top.jsp"><img src="./png/001-home.png" alt="home_icons"></a></li>
			<li title="登録書籍一覧へ" style="margin-right:5%;"><a href="SearchServlet"><img src="./png/002-books.png" alt="books_icons"></a></li>
		</ul>
	</header>
	<% if(loginUser != null) { %>
	<hr>
	<p>登録した書籍を表示できます</p>
	<form action="SearchServlet" method="POST">
		<input type="submit" name="btn" value="一覧表示" />
	</form>
	<br><hr>
	<h3>登録書籍検索</h3>
	<p>条件を設定して登録した書籍を検索できます</p><br>
	<form action="SearchServlet" method="post">
		<strong>検索条件：タイトル</strong>　<input type="text" name="title" />
		<input type="submit" name="btn" value="タイトル検索" />
	</form>
	<br>
	<form action="SearchServlet" method="post">
		<strong>検索条件：著者</strong>　<input type="text" name="authors" />
		<input type="submit" name="btn" value="著者検索" />
	</form>
	<br>
	<form action="SearchServlet" method="post">
		<strong>検索条件：ステータス</strong>　<select name="status">
			<option value="0" selected>未読</option>
			<option value="1">既読</option>
			<option value="2">購入予定</option>
		</select>
		<input type="submit" name="btn" value="ステータス検索" />
	</form>
	<br>
	<% } %>
	<hr>
	<h3>Google Books APIs 検索</h3>
	<p>Google Books APIs を利用して書籍が検索できます</p>
	<form action="ApiSearchServlet" method="post">
		<strong>タイトル</strong>　<input type="text" name="title" />
		<br><br>
		<strong>著者名</strong>　<input type="text" name="authors" />
		<br><br>
		<input type="hidden" name="startIndex" value="0" />
		<input type="submit" value="検索" />
	</form>
	<br><hr>
</body>
</html>