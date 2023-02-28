<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>新規登録</title>
</head>
<body>
	<header>
		<h2>書籍管理アプリケーション</h2>
	</header>
	<div style="text-align:center;">
	<br><strong>新規登録するユーザIDとパスワードを入力してください</strong>
	</div>
	<div style="display:block; text-align:center; width:fit-content; margin:0 auto;">
		<form action="SignUpServlet" method="POST">
		<br>
			<p style="text-align:left; margin-bottom:0;">ユーザID</p>
			<input type="text" name="j_username"><br><br>
			<p style="text-align:left; margin-bottom:0;">パスワード</p>
			<input type="password" name="j_password"><br><br>
			<input type="submit" value="新規登録">
		</form>
	</div>
</body>
</html>