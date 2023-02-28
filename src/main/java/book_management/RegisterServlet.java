package book_management;

import java.io.IOException;
import java.sql.Date;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/RegisterServlet")
public class RegisterServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    public RegisterServlet() {super();}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String loginUser = (String)request.getUserPrincipal().getName();
		// リクエストパラメータを受け取り、DTOに格納する準備をする
		int id = Integer.parseInt(request.getParameter("id"));
		String title = request.getParameter("title");
		String authors = request.getParameter("authors");
		String description = request.getParameter("description");
		int status = Integer.parseInt(request.getParameter("status"));
		Date date;
		if(request.getParameter("date") == "") {
			date = null;
		} else {
			date = Date.valueOf(request.getParameter("date"));
		}
		String memo = request.getParameter("memo");
		String publisher = request.getParameter("publisher");
		String publishedDate = request.getParameter("publishedDate");
		String imageLinks = request.getParameter("imageLinks");
		
		// DTOへ格納する
		BookBean bb = new BookBean();
		bb.setId(id);
		bb.setTitle(title);
		bb.setAuthors(authors);
		bb.setDescription(description);
		bb.setStatus(status);
		bb.setDate(date);
		bb.setMemo(memo);
		bb.setPublisher(publisher);
		bb.setPublishedDate(publishedDate);
		bb.setImageLinks(imageLinks);
		
		// エラーチェック タイトルが空白or登録済みでエラーとする
		String errorMsg = "";		// エラーメッセージ格納用
		
		// 重複チェック用にデータベース検索
		BookDTO checkbdto = new BookDTO();		// テキストボックスに入力したタイトルを検索して格納
		BookBean checkbean = new BookBean();	// checkbdtoに格納したデータへのアクセス用
		try(BookDAO checkbdao = new BookDAO()) {
			checkbdto = checkbdao.selectTitle(loginUser, title);
			if (title != "") {
				checkbean = checkbdto.get(0);
			}
		} catch (Exception e) {
			throw new ServletException(e);
		}
		
		// 空白チェック
		if (title == "") {
			errorMsg = "タイトルを入力してください";
			BookDTO bdto = new BookDTO();
			bdto.add(bb);
			request.setAttribute("bdto", bdto);
			request.setAttribute("errorMsg", errorMsg);
			RequestDispatcher rd = request.getRequestDispatcher("/detail.jsp");
			rd.forward(request, response);
			return;
		// 重複チェック > 新規登録時にデータベースに登録済のタイトルを登録しようとする場合にエラーとする
		} else if (id == 0 && checkbdto.size() != 0) {		// 新規登録&登録済みのタイトルである
			errorMsg = "登録済みのタイトルです";
			BookDTO bdto = new BookDTO();
			bdto.add(bb);
			request.setAttribute("bdto", bdto);
			request.setAttribute("errorMsg", errorMsg);
			RequestDispatcher rd = request.getRequestDispatcher("/detail.jsp");
			rd.forward(request, response);
			return;
		// 重複チェック > 更新時に登録済みの別のタイトルに変更する場合にエラーとする
		} else if (checkbean.getTitle().equals(title) && id != checkbean.getId()) {		// タイトルが同じだが、Idが異なる
			errorMsg = "登録済みのタイトルです";
			BookDTO bdto = new BookDTO();
			bdto.add(bb);
			request.setAttribute("bdto", bdto);
			request.setAttribute("errorMsg", errorMsg);
			RequestDispatcher rd = request.getRequestDispatcher("/detail.jsp");
			rd.forward(request, response);
			return;
		}
		
		// 取得したidによって更新or新規登録処理
		try(BookDAO bdao = new BookDAO()) {
			if(id == 0 || id == -1) {
				bdao.insert(loginUser, bb);
			} else {
				bdao.update(bb);
			}
		} catch (Exception e) {
			throw new ServletException(e);
		}
		
		// 更新または登録後の一覧を表示
		RequestDispatcher rd = request.getRequestDispatcher("/SearchServlet");
		rd.forward(request, response);
	 }

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
