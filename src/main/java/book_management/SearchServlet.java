package book_management;

import java.io.IOException;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/SearchServlet")
public class SearchServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    public SearchServlet() {super();}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String loginUser = (String)request.getUserPrincipal().getName();
		String msg = "登録書籍一覧";
		
		// 入力値(btn)を取得
		String btn = request.getParameter("btn");
		
		// 押下ボタンによる分岐処理
		if(btn == null || btn.equals("一覧表示")) {
			try (BookDAO bdao = new BookDAO()) {
				BookDTO bdto = bdao.selectALL(loginUser);
				request.setAttribute("bdto", bdto);
			} catch (Exception e) {
				throw new ServletException(e);
			}
		} else if(btn.equals("タイトル検索")) {
			try (BookDAO bdao = new BookDAO()){
				BookDTO bdto = bdao.selectTitle(loginUser, request.getParameter("title"));
				request.setAttribute("bdto", bdto);
				msg = "タイトル検索：「" + request.getParameter("title") + "」を表示します";
			} catch (Exception e) {
				throw new ServletException(e);
			}
		} else if(btn.equals("著者検索")) {
			try (BookDAO bdao = new BookDAO()) {
				BookDTO bdto = bdao.selectAuthors(loginUser, request.getParameter("authors"));
				request.setAttribute("bdto", bdto);
				msg = "著者検索：「" + request.getParameter("authors") + "」を表示します"; 
			} catch (Exception e) {
				throw new ServletException(e);
			}
		} else if(btn.equals("ステータス検索")) {
			try (BookDAO bdao = new BookDAO()) {
				String st = request.getParameter("status");
				String stmsg = "";
				BookDTO bdto = bdao.selectStatus(loginUser, Integer.parseInt(st));
				request.setAttribute("bdto", bdto);
				switch (st) {
				case "0": stmsg = "未読"; break;
				case "1": stmsg = "既読"; break;
				case "2": stmsg = "購入予定"; break;
				}
				msg = "ステータス検索：「" + stmsg + "」を表示します";
			} catch (Exception e) {
				throw new ServletException (e);
			}
		}
		
		request.setAttribute("msg", msg);
		RequestDispatcher rd = request.getRequestDispatcher("/list.jsp");
		rd.forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
