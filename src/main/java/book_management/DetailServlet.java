package book_management;

import java.io.IOException;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/DetailServlet")
public class DetailServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    public DetailServlet() {super();}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// リクエストパラメータから選択したidを取得する
		int id = Integer.parseInt(request.getParameter("id"));
		
		// id = 1:詳細表示　0:新規登録としてidを0,他はnullで詳細表示　-1:API検索のデータを渡して詳細表示
		BookDTO bdto = new BookDTO();
		if (id >= 1) {
			try(BookDAO bdao = new BookDAO()){
				bdto = bdao.selectDetail(id);
			} catch (Exception e) {
				throw new ServletException(e);
			}
		} else if (id == 0) {
			BookBean bb = new BookBean();
			bb.setId(0);
			bb.setTitle("");
			bb.setAuthors("");
			bb.setDescription("");
			bb.setPublisher("");
			bb.setPublishedDate("");
			bb.setImageLinks("");
			bdto.add(bb);
		} else if (id == -1) {
			BookBean bb = new BookBean();
			bb.setId(0);
			bb.setTitle(request.getParameter("title"));
			bb.setAuthors(request.getParameter("authors"));
			bb.setDescription(request.getParameter("description"));
			bb.setPublisher(request.getParameter("publisher"));
			bb.setPublishedDate(request.getParameter("publishedDate"));
			bb.setImageLinks(request.getParameter("imageLinks"));
			bdto.add(bb);
		}
		
		request.setAttribute("bdto", bdto);
		
		RequestDispatcher rd = request.getRequestDispatcher("/detail.jsp");
		rd.forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
