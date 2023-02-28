package book_management;

import java.io.IOException;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/DeleteServlet")
public class DeleteServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    public DeleteServlet() {super();}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String loginUser = (String)request.getUserPrincipal().getName();
		int id = Integer.parseInt(request.getParameter("id"));
		
		if (id == 0) {
			try(BookDAO bdao = new BookDAO()){
				bdao.deleteAll(loginUser);
			} catch (Exception e) {
				throw new ServletException(e);
			}
		} else {
			BookBean bb = new BookBean();
			bb.setId(id);
			
			try(BookDAO bdao = new BookDAO()){
				bdao.delete(bb);
			} catch (Exception e) {
				throw new ServletException(e);
			}
		}
		
		RequestDispatcher rd = request.getRequestDispatcher("/SearchServlet");
		rd.forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
