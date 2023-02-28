<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page import="book_management.*" %>
<%! int startIndex; int totalItems; %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>検索結果</title>
</head>
<body>
	<header style="position:sticky; top:0; left:0; width:100%; background-color:#eee;">
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
			<li title="ログイン" style="margin:0 10px;"><a href="login.jsp"><img src="./png/003-login.png" alt="login_icons"></a></li>
			<% } else { %>
			<li style="margin:auto 20px auto 0;">ログインユーザ：<%= loginUser %></li>
			<li title="ログアウト" style="margin:0 10px;"><a href="logout.jsp"><img src="./png/004-logout.png" alt="logout_icons"></a></li>
			<% } %>
			<li title="topへ" style="margin-left:auto; margin-right:20px;"><a href="top.jsp"><img src="./png/001-home.png" alt="home_icons"></a></li>
			<li title="登録書籍一覧へ" style="margin-right:5%;"><a href="SearchServlet"><img src="./png/002-books.png" alt="books_icons"></a></li>
		</ul>
	</header>
	<%
	startIndex = (int)request.getAttribute("startIndex");
	totalItems = Integer.parseInt((String)request.getAttribute("totalItems"));
	if(totalItems == 0) {
	%>
	<h2>検索結果がありません</h2>
	<% } else { %>
		<%
		if(totalItems - startIndex < 10) {
		%>
		<h2><%= totalItems %>件中<%= startIndex + 1 %>～<%= startIndex + (totalItems - startIndex) %>件目を表示します</h2>
		<% } else { %>
		<h2><%= totalItems %>件中<%= startIndex + 1 %>～<%= startIndex + 10 %>件目を表示します</h2>
		<% } %>
	<%
	BookDTO bdto = new BookDTO();
	bdto = (BookDTO)request.getAttribute("bdto");
	%>
	<table border="1">
		<tr>
			<th>表紙</th>
			<th style="text-align:left">タイトル</th>
			<th style="text-align:left">著者</th>
			<th>内容</th>
			<th>出版社</th>
			<th>出版日</th>
		</tr>
		<%
			for (int i = 0; i < bdto.size(); i++){
				BookBean bb = bdto.get(i);
		%>
		<tr>
			<td><img src="<%= bb.getImageLinks() %>" alt="表紙を取得できません"></td>
			<td><%= bb.getTitle() %></td>
			<td><%= bb.getAuthors() %></td>
			<td width=50%><%= bb.getDescription() %></td>
			<td><%= bb.getPublisher() %></td>
			<td><%= bb.getPublishedDate() %></td>
			<td>
				<form action="DetailServlet" method="post">
				<input type="hidden" name="id" value="-1" />
				<input type="hidden" name="title" value="<%= bb.getTitle() %>" />
				<input type="hidden" name="authors" value="<%= bb.getAuthors() %>" />
				<input type="hidden" name="description" value="<%= bb.getDescription() %>" />
				<input type="hidden" name="publisher" value="<%= bb.getPublisher() %>" />
				<input type="hidden" name="publishedDate" value="<%= bb.getPublishedDate() %>" />
				<input type="hidden" name="imageLinks" value="<%= bb.getImageLinks() %>" />
				<input type="submit" value="登録" />
				</form>
			</td>
			<%
				}
			%>
		</tr>
	</table>
	<br>
	<footer style="position:sticky; bottom:0; left:0; display:flex; justify-content:center; align-items:center; gap:0 20px; width:100%; height:50px; background-color:#eee;">
	<%
	if (startIndex >= 10) {
	%>
	<form action="ApiSearchServlet" method="post">
	<input type="hidden" name="title" value="<%= request.getAttribute("title") %>"/>
	<input type="hidden" name="authors" value="<%= request.getAttribute("authors") %>" />
	<input type="hidden" name="startIndex" value="<%= startIndex - 10 %>" />
	<input type="submit" value="戻る" />
	</form>
	<% } %>
	<%
	if(totalItems > 0) {
		if(totalItems - startIndex < 10) {
		%>
		<%= startIndex + 1 %>～<%= startIndex + (totalItems - startIndex) %>/<%= totalItems %>
		<% } else { %>
		<%= startIndex + 1 %>～<%= startIndex + 10 %>/<%= totalItems %>
		<% }} %>
	<%
	if ((totalItems - startIndex) > 10) {
	%>
	<form action="ApiSearchServlet" method="post">
	<input type="hidden" name="title" value="<%= request.getAttribute("title") %>" />
	<input type="hidden" name="authors" value="<%= request.getAttribute("authors") %>" />
	<input type="hidden" name="startIndex" value="<%= startIndex + 10 %>" />
	<input type="submit" value="次へ" />
	</form>
	<% }} %>
	</footer>

</body>
</html>