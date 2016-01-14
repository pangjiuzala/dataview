package cn.zju.edu.dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;

import cn.zju.edu.model.History;
import cn.zju.edu.util.DataBaseConnection;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;
import com.mysql.jdbc.ResultSet;

/**
 * @author liuxing
 * @category query   data  from table history
 */
public class HistoryDao {
	private Connection conn;
	private static History result = null;

	public HistoryDao() throws Exception {
		conn = (Connection) DataBaseConnection.getConnection();

	}

	public void inithistory(String time, int idinit) throws SQLException {

		Iterator<History> iterators = this.findHistory(time, idinit).iterator();
		if (iterators.hasNext()) { // 如果有下一个值，进入循环
			result = iterators.next(); // 得到迭代器中下一个值 返回String类型
			// 输出结果
		}
	}

	public String getindex(String time, int id) throws SQLException {
		inithistory(time, id);
		return result.getIndex();

	}

	public String getopen(String time, int id) throws SQLException {
		inithistory(time, id);
		return result.getOpen();
	}

	public String gethighest(String time, int id) throws SQLException {
		inithistory(time, id);

		return result.getHighest();
	}

	public String getlowest(String time, int id) throws SQLException {
		inithistory(time, id);

		return result.getLowest();
	}

	public String getclose(String time, int id) throws SQLException {
		inithistory(time, id);

		return result.getClose();
	}

	public String getchange(String time, int id) throws SQLException {

		inithistory(time, id);
		return result.getChange();
	}

	public String getincrease(String time, int id) throws SQLException {
		inithistory(time, id);

		return result.getIncrease();
	}

	public String getamplitude(String time, int id) throws SQLException {
		inithistory(time, id);

		return result.getAmplitude();
	}

	public String gethandall(String time, int id) throws SQLException {
		inithistory(time, id);

		return result.getHandsAll();
	}

	public String getmoney(String time, int id) throws SQLException {
		inithistory(time, id);

		return result.getMoney();
	}

	public ArrayList<History> findHistory(String timef, int idf) {
		ArrayList<History> userlist = new ArrayList<History>();
		PreparedStatement pstmt = null;
		String sql = "SELECT * FROM history where Time='" + timef
				+ "' and `Index`=" + idf;
		;
		try {
			pstmt = (PreparedStatement) conn.prepareStatement(sql);
			ResultSet rs = (ResultSet) pstmt.executeQuery();
			if (rs.next()) {
				History ht = new History();
				ht.setIndex(rs.getString("Index"));
				ht.setTime(rs.getString("Time"));
				ht.setOpen(rs.getString("Open"));
				ht.setHighest(rs.getString("Highest"));
				ht.setLowest(rs.getString("Lowest"));
				ht.setClose(rs.getString("Close"));
				ht.setChange(rs.getString("Change"));
				ht.setIncrease(rs.getString("Increase"));
				ht.setAmplitude(rs.getString("Amplitude"));
				ht.setHandsAll(rs.getString("HandsAll"));
				ht.setMoney(rs.getString("Money"));

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
		HistoryDao hd = new HistoryDao();
		System.out.println(hd.getindex(("2015-10-12,1"), 1));
		System.out.println(hd.getclose(("2015-10-12,1"), 1));
	}

}
