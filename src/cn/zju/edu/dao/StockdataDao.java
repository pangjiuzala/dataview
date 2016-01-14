package cn.zju.edu.dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;

import cn.zju.edu.model.Stockdata;
import cn.zju.edu.util.DataBaseConnection;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;
import com.mysql.jdbc.ResultSet;
/**
 * @author liuxing
 * @category query   data  from table Stockdata
 */
public class StockdataDao {
	private Connection conn;
	private static Stockdata result = null;

	public StockdataDao() throws Exception {
		conn = (Connection) DataBaseConnection.getConnection();
	}

	public void initStockdata(String wz, int idinit) throws SQLException {

		Iterator<Stockdata> iterators = this.findStockdata(wz, idinit)
				.iterator();
		if (iterators.hasNext()) { // 如果有下一个值，进入循环
			result = iterators.next(); // 得到迭代器中下一个值 返回String类型
			// 输出结果
		}
	}

	public String getincrease(String wz, int id) throws SQLException {
		initStockdata(wz, id);

		return result.getIncrease();
	}

	public ArrayList<Stockdata> findStockdata(String wzf, int idf) {
		ArrayList<Stockdata> userlist = new ArrayList<Stockdata>();
		PreparedStatement pstmt = null;
		String sql = "SELECT * FROM Stockdata where wz='" + wzf + "' and id="
				+ idf;
		try {
			pstmt = (PreparedStatement) conn.prepareStatement(sql);
			ResultSet rs = (ResultSet) pstmt.executeQuery();
			if (rs.next()) {
				Stockdata ht = new Stockdata();
				ht.setId(rs.getInt("id"));
				ht.setWz(rs.getString("wz"));
				ht.setIncrease(rs.getString("increase"));

				/*
				 * System.out.println(ht.getIndex() + "," + ht.getIncrease() +
				 * "," + ht.getClose());
				 */
				userlist.add(ht);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return userlist;
	}

	public static void main(String[] args) throws Exception {
		StockdataDao hd = new StockdataDao();
		System.out.println(hd.getincrease("sz300267", 101));
		System.out.println(hd.getincrease("sz300104", 102));
	}

}
