<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page import="book_management.*" %>
<jsp:useBean id ="msg" scope="request" class="java.lang.String" />
<%! String status; String date; %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>登録書籍</title>
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
	<h2><%= msg %></h2>
	<%
	BookDTO bdto = new BookDTO();
	bdto = (BookDTO)request.getAttribute("bdto");
	if(bdto.size() == 0) {
	%>
	<h2>登録された書籍がありません</h2>
	<% } else{ %>
	<table border="1">
		<tr>
			<th style="text-align:left">タイトル</th>
			<th style="text-align:left">著者</th>
			<th>内容</th>
			<th>ステータス</th>
			<th>読了日</th>
			<th>memo</th>
		</tr>
		<%
			for (int i = 0; i < bdto.size(); i++){
				BookBean bb = bdto.get(i);
		%>
		<tr>
			<td width=10%><%= bb.getTitle() %></td>
			<td width=5%><%= bb.getAuthors() %></td>
			<td width=30%><%= bb.getDescription() %></td>
			<td width=10% style="text-align:center"><%
			switch(bb.getStatus()){
			case 0: status = "未読"; break;
			case 1: status = "既読"; break;
			case 2: status = "購入予定"; break;
			default: break;
			}
			%>
			<%= status %>
			</td>
			<td width=10%><%
			if (bb.getDate() != null){
				date = bb.getDate().toString();
			} else {
				date = "";
			}
			%>
			<%= date %>
			</td>
			<td width=20%><%= bb.getMemo() %></td>
			<td width=5%><a href="DetailServlet?id=<%= bb.getId() %> ">詳細</a></td>
			<%
				}
			%>
		</tr>
	</table>
	<% } %>
	<br>
	<footer style="position:sticky; bottom:0; left:0; display:flex; justify-content:center; align-items:center; gap:0 20px; width:100%; height:50px; background-color:#eee;">
	<form style="margin-left:20px;" action="DetailServlet" method="GET">
		<input type="hidden" name="id" value="0" />
		<input type="submit" value="新規登録" />
	</form>
	<form action="DeleteServlet" method="POST">
		<input type="hidden" name="id" value="0" />
		<input type="submit" value="一括削除" />
	</form>
	</footer>
</body>
</html>