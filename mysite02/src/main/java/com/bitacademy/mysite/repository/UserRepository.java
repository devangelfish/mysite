package com.bitacademy.mysite.repository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.bitacademy.mysite.vo.GuestbookVo;
import com.bitacademy.mysite.vo.UserVo;

public class UserRepository {
	
	public boolean insert(UserVo userVo) {
		boolean result = false;

		Connection conn = null;
		PreparedStatement pstmt = null;
		try {
			conn = getConnection();
			
			// 3. SQL 준비
			String sql =
					" insert" +
					"   into user" +
					" values(null, ?, ?, ?, ?, now())";
			pstmt = conn.prepareStatement(sql);
			
			// 4. 바인딩
			pstmt.setString(1, userVo.getName());
			pstmt.setString(2, userVo.getEmail());
			pstmt.setString(3, userVo.getPassword());
			pstmt.setString(4, userVo.getGender());
			
			// 5. sql문 실행
			int count = pstmt.executeUpdate();
			
			result = count == 1;
			
		} catch (SQLException e) {
			System.out.println("error:" + e);
		} finally {
			try {
				// 3. 자원정리
				if(pstmt != null) {
					pstmt.close();
				}
				if(conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}		
		
		return result;
	}
	
	private Connection getConnection() throws SQLException{
		Connection conn = null;
		try {
			// 1. JDBC Driver 로딩
			Class.forName("org.mariadb.jdbc.Driver");
			
			// 2. 연결하기
			String url = "jdbc:mysql://192.168.1.41:3307/webdb?characterEncoding=utf8";
			conn = DriverManager.getConnection(url, "webdb", "webdb");
		} catch (ClassNotFoundException e) {
			System.out.println("드라이버 로딩 실패:" + e);
		}
		
		return conn;
	}
}
