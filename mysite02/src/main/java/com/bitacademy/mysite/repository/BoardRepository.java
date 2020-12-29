package com.bitacademy.mysite.repository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.bitacademy.mysite.vo.BoardVo;

public class BoardRepository {
	public int getBoardVoCount() {
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		int result = 0;
				
		try {
			conn = getConnection();
			
			// 3. SQL 준비
			String sql =
				"select count(*) from board a join user b on a.user_no = b.no";
			stmt = conn.createStatement();
			
			// 4. 바인딩
			
			// 5. sql문 실행
			 rs = stmt.executeQuery(sql);
			
			// 6. 데이터 가져오기
			 if(rs.next()) {
				 result = rs.getInt(1);
			 }
			
		} catch (SQLException e) {
			System.out.println("error:" + e);
		} finally {
			try {
				// 3. 자원정리
				if(rs != null) {
					rs.close();
				}
				if(stmt != null) {
					stmt.close();
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
	
	public List<BoardVo> fetch(int page) {
		List<BoardVo> list = new ArrayList<>();
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
				
		try {
			conn = getConnection();
			
			// 3. SQL 준비
			String sql =
				"   select a.no, a.title, a.contents, date_format(a.reg_date, '%Y/%m/%d %H:%i:%s') as reg_date," +  
				"     a.hit, a.group_no, a.order_no, a.depth, b.name" +
				"     from board a join user b" +
				" on a.user_no = b.no" +
				" order by a.group_no desc, a.order_no asc" +
				" limit ?, 5";
			pstmt = conn.prepareStatement(sql);
			
			// 4. 바인딩
			pstmt.setInt(1, (page-1)*5);
			
			// 5. sql문 실행
			rs = pstmt.executeQuery();
			
			// 6. 데이터 가져오기
			while(rs.next()) {
				Long no = rs.getLong(1);
				String title = rs.getString(2);
				String contents = rs.getString(3);
				String regDate = rs.getString(4);
				Long hit = rs.getLong(5);
				Long groupNo = rs.getLong(6);
				Integer orderNo = rs.getInt(7);
				Integer depth = rs.getInt(8);
				String userName = rs.getString(9);
				
				BoardVo vo = new BoardVo();
				vo.setNo(no);
				vo.setTitle(title);
				vo.setContents(contents);
				vo.setRegDate(regDate);
				vo.setHit(hit);
				vo.setGroupNo(groupNo);
				vo.setOrderNo(orderNo);
				vo.setDepth(depth);
				vo.setUserName(userName);
				list.add(vo);
			}
			
		} catch (SQLException e) {
			System.out.println("error:" + e);
		} finally {
			try {
				// 3. 자원정리
				if(rs != null) {
					rs.close();
				}
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
		
		return list;
	}
	
	public boolean write(BoardVo boardVo) {
		boolean result = false;

		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			conn = getConnection();
			
			// 3. SQL 준비
			String sql =
					" insert" +
					"   into board" +
					" values(null, ?, ?, now(), 0, (select ifnull(max(group_no), 0) from board b) + 1, 1, 1, ?)";
			pstmt = conn.prepareStatement(sql);
			
			// 4. 바인딩
			pstmt.setString(1, boardVo.getTitle());
			pstmt.setString(2, boardVo.getContents());
			pstmt.setLong(3, boardVo.getUserNo());
			
			// 5. sql문 실행
			int count = pstmt.executeUpdate();
			
			result = count == 1;
			
		} catch (SQLException e) {
			System.out.println("error:" + e);
		} finally {
			try {
				// 3. 자원정리
				if(rs != null) {
					rs.close();
				}
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
