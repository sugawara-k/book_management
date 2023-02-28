package book_management;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class BookDAO extends DBConnectionDAO {
	
	public BookDTO selectALL(String loginUser) throws Exception {
		BookDTO bdto = new BookDTO();
		
		String sql = "SELECT * FROM book_list WHERE userid = '" + loginUser + "'";
		
		PreparedStatement statement = getPreparedStatement(sql);
		ResultSet rs = statement.executeQuery();
		
		while(rs.next()) {
			BookBean bb = new BookBean();
			bb.setId(rs.getInt("id"));
			bb.setTitle(rs.getString("title"));
			bb.setAuthors(rs.getString("authors"));
			bb.setDescription(rs.getString("description"));
			bb.setStatus(rs.getInt("status"));
			bb.setDate(rs.getDate("date"));
			bb.setMemo(rs.getString("memo"));
			bdto.add(bb);
		}
		return bdto;
	}

	public BookDTO selectTitle(String loginUser, String title) throws Exception {
		BookDTO bdto = new BookDTO();
		
		String sql = "SELECT * FROM book_list WHERE userid = '" + loginUser + "' AND title = '" + title + "'";
		
		PreparedStatement statement = getPreparedStatement(sql);
		ResultSet rs = statement.executeQuery();
		
		while(rs.next()) {
			BookBean bb = new BookBean();
			bb.setId(rs.getInt("id"));
			bb.setTitle(rs.getString("title"));
			bb.setAuthors(rs.getString("authors"));
			bb.setDescription(rs.getString("description"));
			bb.setStatus(rs.getInt("status"));
			bb.setDate(rs.getDate("date"));
			bb.setMemo(rs.getString("memo"));
			bdto.add(bb);
		}
		return bdto;
	}
	
	public BookDTO selectAuthors(String loginUser, String authors) throws Exception {
		BookDTO bdto = new BookDTO();
		
		String sql = "SELECT * FROM book_list WHERE userid= '" + loginUser + "' AND authors = '" + authors + "'";
		
		PreparedStatement statement = getPreparedStatement(sql);
		ResultSet rs = statement.executeQuery();
		
		while(rs.next()) {
			BookBean bb = new BookBean();
			bb.setId(rs.getInt("id"));
			bb.setTitle(rs.getString("title"));
			bb.setAuthors(rs.getString("authors"));
			bb.setDescription(rs.getString("description"));
			bb.setStatus(rs.getInt("status"));
			bb.setDate(rs.getDate("date"));
			bb.setMemo(rs.getString("memo"));
			bdto.add(bb);
		}
		return bdto;
	}
	
	public BookDTO selectStatus(String loginUser, int status) throws Exception {
		BookDTO bdto = new BookDTO();
		
		String sql = "SELECT * FROM book_list WHERE userid = '" + loginUser + "' AND status = '" + status + "'";
		
		PreparedStatement statement = getPreparedStatement(sql);
		ResultSet rs = statement.executeQuery();
		
		while(rs.next()) {
			BookBean bb = new BookBean();
			bb.setId(rs.getInt("id"));
			bb.setTitle(rs.getString("title"));
			bb.setAuthors(rs.getString("authors"));
			bb.setDescription(rs.getString("description"));
			bb.setStatus(rs.getInt("status"));
			bb.setDate(rs.getDate("date"));
			bb.setMemo(rs.getString("memo"));
			bdto.add(bb);
		}
		return bdto;
	}
	
	public BookDTO selectDetail(int id) throws Exception {
		BookDTO bdto = new BookDTO();
		
		String sql = "SELECT * FROM book_list WHERE id = '" + id + "'";
		
		PreparedStatement statement = getPreparedStatement(sql);
		ResultSet rs = statement.executeQuery();
		
		while(rs.next()) {
			BookBean bb = new BookBean();
			bb.setId(rs.getInt("id"));
			bb.setTitle(rs.getString("title"));
			bb.setAuthors(rs.getString("authors"));
			bb.setDescription(rs.getString("description"));
			bb.setStatus(rs.getInt("status"));
			bb.setDate(rs.getDate("date"));
			bb.setMemo(rs.getString("memo"));
			bb.setPublisher(rs.getString("publisher"));
			bb.setPublishedDate(rs.getString("publishedDate"));
			bb.setImageLinks(rs.getString("imageLinks"));
			bdto.add(bb);
		}
		return bdto;
	}
	
	public int insert(String loginUser, BookBean dto) throws Exception{
		String sql = "INSERT INTO book_list (title, authors, description, status, date, memo, publisher, publishedDate, imageLinks, userid) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
		
		int result = 0;
		
		try {
			PreparedStatement statement = getPreparedStatement(sql);
			statement.setString(1, dto.getTitle());
			statement.setString(2, dto.getAuthors());
			statement.setString(3, dto.getDescription());
			statement.setInt(4, dto.getStatus());
			if(dto.getDate() == null) {
				statement.setString(5, null);
			} else {
				statement.setString(5, dto.getDate().toString());
			}
			statement.setString(6, dto.getMemo());
			statement.setString(7, dto.getPublisher());
			statement.setString(8, dto.getPublishedDate());
			statement.setString(9, dto.getImageLinks());
			statement.setString(10, loginUser);
			result = statement.executeUpdate();
			super.commit();
		} catch (Exception e) {
			super.rollback();
			throw e;
		}
		return result;
	}
	
	public int update(BookBean dto) throws Exception {
		String sql = "UPDATE book_list SET title = ?, authors = ?, description = ?, status = ?, date = ?, memo = ?, publisher = ?, publishedDate = ?, imageLinks = ? WHERE id = ?";
		
		int result = 0;
		
		try {
			PreparedStatement statement = getPreparedStatement(sql);
			statement.setString(1, dto.getTitle());
			statement.setString(2, dto.getAuthors());
			statement.setString(3, dto.getDescription());
			statement.setInt(4, dto.getStatus());
			if(dto.getDate() == null) {
				statement.setString(5, null);
			} else {
				statement.setString(5, dto.getDate().toString());
			}
			statement.setString(6, dto.getMemo());
			statement.setString(7, dto.getPublisher());
			statement.setString(8, dto.getPublishedDate());
			statement.setString(9, dto.getImageLinks());
			statement.setInt(10, dto.getId());
			result = statement.executeUpdate();
			super.commit();
		} catch (Exception e) {
			super.rollback();
			throw e;
		}
		return result;
		
	}
	
	public int delete(BookBean dto) throws Exception{
		String sql = "DELETE FROM book_list where id = ?";
		
		int result = 0;
		
		try {
			PreparedStatement statement = getPreparedStatement(sql);
			statement.setInt(1, dto.getId());
			result = statement.executeUpdate();
			super.commit();
		} catch (Exception e) {
			super.rollback();
			throw e;
		}
		return result;
	}
	
	public int deleteAll(String loginUser) throws Exception{
		String sql = "DELETE FROM book_list WHERE userid = '" + loginUser + "'";
		
		int result = 0;
		
		try {
			PreparedStatement statement = getPreparedStatement(sql);
			result = statement.executeUpdate();
			super.commit();
		} catch (Exception e) {
			super.rollback();
			throw e;
		}
		return result;
	}
	
	public int signUp(String userid, String pass) throws Exception{
		String sql1 = "INSERT INTO users (userid, password) VALUES (?, ?)";
		String sql2 = "INSERT INTO roles (userid, role) VALUES (?, ?)";
		
		int result = 0;
		
		try {
			PreparedStatement statement1 = getPreparedStatement(sql1);
			statement1.setString(1, userid);
			statement1.setString(2, pass);
			PreparedStatement statement2 = getPreparedStatement(sql2);
			statement2.setString(1, userid);
			statement2.setString(2, "normal");
			result = statement1.executeUpdate();
			result = statement2.executeUpdate();
			super.commit();
		} catch (Exception e) {
			super.rollback();
			throw e;
		}
		return result;
	}

}
