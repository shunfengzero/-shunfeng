package com.sf.spider;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.sf.Util.JDBCUtil;
import com.sf.entity.Books;

public class BookIfno {
	

	public List<Books> bookInfos() {
		List<Books> list = new ArrayList<>();
		String sql = "select * from books where rating_sum>1000 group by title order by score desc";
		Books books = null;
		Connection conn = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		try {
			conn = JDBCUtil.getConnection();
			pst = conn.prepareStatement(sql);
			 rs = pst.executeQuery();
			 int id = 1;
			 while (rs.next()) {
				 books = new Books();
				 books.setId(id);
				 books.setTitle(rs.getString("title"));
				 books.setScore(rs.getString("score"));
				 books.setRating_sum(rs.getString("rating_sum"));
				 books.setAuthor(rs.getString("author"));
				 books.setPress(rs.getString("press"));
				 books.setDate(rs.getString("date"));
				 books.setPrice(rs.getString("price"));
				 
				 System.out.println(id + rs.getString("title") + "\t" + rs.getString("score") +
						 "\t" + rs.getString("rating_sum") + "\t" + rs.getString("author") + "\t" + rs.getString("press")
						+ "\t" + rs.getString("date") + "\t" + rs.getString("price"));
				 id++;
				 list.add(books);
				 
			 }
			 		 
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			JDBCUtil.close(conn, pst, rs);
		}
		return list;
	}
	
}
