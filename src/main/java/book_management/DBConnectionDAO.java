package book_management;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class DBConnectionDAO implements AutoCloseable{
	private Connection connection = null;
	
	public DBConnectionDAO() {}
	
	// データベース接続を取得する。取得済みの場合は既存接続を利用する。未接続の場合は新たにコンテナから取得する.
	public Connection getConnection() throws Exception {
		try {
			if (connection == null || connection.isClosed()) {
				InitialContext initCtx = new InitialContext();
				DataSource ds = (DataSource)initCtx.lookup("java:comp/env/jdbc/localDB");
				connection = ds.getConnection();
			}
		} catch (NamingException | SQLException e) {
			e.printStackTrace();
			connection = null;
			throw e;
		}
		return connection;
	}
	
	// プリペアードステートメントを返す
	public PreparedStatement getPreparedStatement(String sql) throws Exception {
		return getConnection().prepareStatement(sql);
	}
	
	// トランザクションのコミットを行う
	public void commit() throws SQLException {
		connection.commit();
	}
	
	// トランザクションのロールバックを行う
	public void rollback() throws SQLException {
		connection.rollback();
	}
	
	// データベース接続を閉じる
	public void close() {
		try {
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			connection = null;
		}
	}
}
