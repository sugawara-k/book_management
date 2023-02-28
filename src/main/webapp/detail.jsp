<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page import="book_management.*" %>
<%! String memo;%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>書籍詳細</title>
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
	<h2>書籍詳細</h2>
	<%
		BookDTO bdto = (BookDTO)request.getAttribute("bdto");
		BookBean bb = bdto.get(0);
	%>
	<form action="RegisterServlet" method="post">
		<%
 		if(!bb.getImageLinks().isEmpty()){
		%>
		<img alt="表紙" src="<%= bb.getImageLinks() %>">
		<% } %>
		<table>
			<tr>
				<th>タイトル</th>
				<td><input type="text" name="title" value="<%= bb.getTitle() %>" size="20">
				<%
					String errorMsg = (String)request.getAttribute("errorMsg");
					if (errorMsg != null) {
					%>
					<strong style="color:red">※<%= errorMsg %></strong>
				<% } %>
				</td>
			</tr>
			<tr>
				<th>著者</th>
				<td><input type="text" name="authors" value="<%= bb.getAuthors() %>" size="20"></td>
			</tr>
			<tr>
				<th>内容</th>
				<td><textarea name="description" cols="100" rows="5"><%= bb.getDescription() %></textarea></td>
			</tr>
			<tr>
				<th>ステータス</th>
				<td>
					<select name="status">
						<option value="0" <% if(bb.getStatus() == 0) { %> selected <% } %>>未読</option>
						<option value="1" <% if(bb.getStatus() == 1) { %> selected <% } %>>既読</option>
						<option value="2" <% if(bb.getStatus() == 2) { %> selected <% } %>>購入予定</option>
					</select>
				</td>
			</tr>
			<tr>
				<th>読了日</th>
				<td><input type="date" name="date" value="<%= bb.getDate() %>"></td>
			</tr>
			<tr>
				<th>memo</th>
					<td><textarea name="memo" cols="100" rows="5"><%
					if(bb.getMemo() != null){
						memo = bb.getMemo().toString();
					} else {
						memo = "";
					}
					%><%= memo %></textarea></td>
			</tr>
			<tr>
				<th>出版社</th>
				<td><input type="text" name="publisher" value="<%= bb.getPublisher() %>" size="20"></td>
			</tr>
			<tr>
				<th>出版日</th>
				<td><input type="text" name="publishedDate" value="<%= bb.getPublishedDate() %>" size="20"></td>
			</tr>
			<tr>
				<th>画像URL</th>
				<td><input type="text" name="imageLinks" value="<%= bb.getImageLinks() %>" size="100"></td>
			</tr>
		</table>
		
		<br>
		
		<input type="hidden" name="id" value="<%= bb.getId() %>" />
		<input type="submit" value="登録" />
	</form><br>
	<%
		if(bb.getId() > 0) {
	%>
	<form action="DeleteServlet" method="post">
		<input type="hidden" name="id" value="<%= bb.getId() %>" />
		<input type="submit" value="削除" />
	</form><br>
	<% } %>
</body>
</html>