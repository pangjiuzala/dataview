package cn.zju.edu.dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import cn.zju.edu.model.Predict;
import cn.zju.edu.util.DataBaseConnection;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;
import com.mysql.jdbc.ResultSet;
/**
 * @author liuxing
 * @category query  history data of stock,and transfer data to history.jsp
 */
public class PredictHistoryDao {
	private Connection conn;
	private static Predict result = null;

	public PredictHistoryDao() throws Exception {
		conn = (Connection) DataBaseConnection.getConnection();
		/* this.findPredict(id); */

	}

	public void initpredict(int ididinit) throws SQLException {
		Iterator<Predict> iterators = this.findPredict(ididinit).iterator();
		if (iterators.hasNext()) { // 如果有下一个值，进入循环
			result = iterators.next(); // 得到迭代器中下一个值 返回String类型
			// 输出结果
		}
	}

	public double gettrue(int id) throws SQLException {

		initpredict(id);
		return result.getTruevalue();
	}

	public double getpredict(int id) throws SQLException {
		initpredict(id);
		return result.getPredictvalue();
	}

	public ArrayList<Predict> findPredict(int idf) {
		ArrayList<Predict> predictlist = new ArrayList<Predict>();
		PreparedStatement pstmt = null;
		String sql = "SELECT * FROM historyresult where id=" + idf;
		try {
			pstmt = (PreparedStatement) conn.prepareStatement(sql);
			ResultSet rs = (ResultSet) pstmt.executeQuery();
			while (rs.next()) {
				Predict pd = new Predict();
				pd.setId(rs.getInt("id"));
				pd.setTruevalue(rs.getDouble("truevalue"));
				pd.setPredictvalue(rs.getDouble("predictvalue"));

				/*
				 * System.out.println(pd.getId() + "," + pd.getTruevalue() + ","
				 * + pd.getPredictvalue());
				 */
				predictlist.add(pd);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return predictlist;
	}

	public static void main(String[] args) throws Exception {
		PredictHistoryDao pds = new PredictHistoryDao();
		System.out.println(pds.gettrue(1));
		System.out.println(pds.getpredict(1));
		System.out.println(pds.gettrue(2));
		System.out.println(pds.getpredict(2));

		/*
		 * pds.findPredict(1); pds.findPredict(2);
		 */
	}

}
