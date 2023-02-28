package book_management;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ProxySelector;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/ApiSearchServlet")
public class ApiSearchServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public ApiSearchServlet() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 各種値を取得
		String title = request.getParameter("title");
		String authors = request.getParameter("authors");
		int startIndex = Integer.parseInt(request.getParameter("startIndex"));
		// 検索条件を属性値に設定
		request.setAttribute("title", title);
		request.setAttribute("authors", authors);
		request.setAttribute("startIndex", startIndex);
		
		// 複合検索設定
		String URL = "";
		if (title == "") {
			URL = "https://www.googleapis.com/books/v1/volumes?q=inauthor:" + authors + "&maxResults=10&startIndex=" + startIndex;
		} else if (authors == "") {
			URL = "https://www.googleapis.com/books/v1/volumes?q=intitle:" + title + "&maxResults=10&startIndex=" + startIndex;
		} else {
			URL = "https://www.googleapis.com/books/v1/volumes?q=intitle:" + title + "&inauthor:" + authors + "&maxResults=10&startIndex=" + startIndex;
		}
		
		String json = "";
		try {
			// HTTPクライアント生成
			var client = HttpClient.newBuilder()
			        .proxy(ProxySelector.of(new InetSocketAddress("172.16.1.6", 80)))
					.build();
			
			// リクエスト準備
			// https://www.googleapis.com/books/v1/volumes?q=intitle:吾輩は猫である&maxResults=10&startIndex=0
			var req = HttpRequest.newBuilder()
					.uri(URI.create(URL))
					.build();
			
			// レスポンス取得
			var res = client.send(req, HttpResponse.BodyHandlers.ofString());
			json = res.body();
			
		} catch (IOException | InterruptedException e) {
			e.printStackTrace();
		}
		
		// jsonの中身を取り出してBeanに格納していく
		BookDTO bdto = new BookDTO();
		ObjectMapper mapper = new ObjectMapper();
		try {
			JsonNode node = mapper.readTree(json);
			
			// 検索件数を取得し、属性値に設定
			String totalItems = node.path("totalItems").asText();
			request.setAttribute("totalItems", totalItems);
			
			int totalInt = Integer.parseInt(totalItems);
			
			int loop = 10;	// for文のループ回数
			if ((totalInt - startIndex) < 10) {
				loop = totalInt - startIndex;
			}
			
			for (int i = 0; i < loop; i++) {
				BookBean bb = new BookBean();
				bb.setTitle(node.path("items").path(i).path("volumeInfo").path("title").asText());
				bb.setAuthors(node.path("items").path(i).path("volumeInfo").path("authors").path(0).asText());
				bb.setDescription(node.path("items").path(i).path("volumeInfo").path("description").asText());
				bb.setPublisher(node.path("items").path(i).path("volumeInfo").path("publisher").asText());
				bb.setPublishedDate(node.path("items").path(i).path("volumeInfo").path("publishedDate").asText());
				bb.setImageLinks(node.path("items").path(i).path("volumeInfo").path("imageLinks").path("smallThumbnail").asText());
				bdto.add(bb);
			}
			request.setAttribute("bdto", bdto);
			} catch (IOException e) {
			e.printStackTrace();
		}
		// jspにフォワード
		RequestDispatcher rd = request.getRequestDispatcher("/searchList.jsp");
		rd.forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
