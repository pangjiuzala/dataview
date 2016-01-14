package cn.zju.edu.crawl;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import cn.zju.edu.util.Constants;
import cn.zju.edu.util.DataBaseConnection;
import cn.zju.edu.util.URLUtils;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.Statement;

/**
 * @author liuxing
 * 
 */
public class StockCrawl {
	private static Connection connection;
	@SuppressWarnings("unused")
	private Statement statement;
	@SuppressWarnings("unused")
	private ResultSet resultSet;
	PreparedStatement pst = null;

	public StockCrawl() throws SQLException {
		connection = (Connection) DataBaseConnection.getConnection();
		this.getdata();
		// 开始获取十大重参实时数据
		/* System.out.println(this.getdata()); */

	}

	public int getdata() throws SQLException {
		String[] t = Constants.q;
		for (int m = 0; m <= t.length - 1; m++) {
			this.getdataszangzhengq(t[m]);
		}
		return t.length;
	}

	/* 抓取腾讯财经上的上证新兴产业指数 */
	public String getdataszangzhengq(String stockid) throws SQLException {

		String content = URLUtils.getContent("http://qt.gtimg.cn/q=" + stockid);
		String[] datas = content.split("~");
		if (datas.length <= 1) {
			datas = null;// 当没有基金信息时，返回null。无信息的格式为pv_none_match=1;
		}

		// 根据对照自己对应数据

		Statement s = (Statement) connection.createStatement();
		String m = datas[0].toString().replace("v_" + stockid + "=", stockid)
				.replace("51", "");
		String sql = "insert into stockdata(wz,name,code,current_price,closeprice_yesterday,openprice_today,volume,externals,inners,buyone,buyone_hand,buytwo_1,buytwo_2,buythree_1, buythree_2, buyfour_1, buyfour_2, buyfive_1, buyfive_2, sellone, sellonecount, selltwo_1, selltwo_2, sellthree_1,sellthree_2,sellfour_1,sellfour_2,sellfive_1,sellfive_2,recent_deal,times,changes,increase,highest,lowest,price,price_count,price_value,turnover,earnings,unkown,highest_1,highest_2,amplitude,capitalization,total,pb,ricelimit,falllimit) values('"
				+ m.substring(0, m.length() - 1)
				+ "'"
				+ ",'"
				+ datas[1]
				+ "'"
				+ ",'"
				+ datas[2]
				+ "'"
				+ ",'"
				+ datas[3]
				+ "'"
				+ ",'"
				+ datas[4]
				+ "'"
				+ ",'"
				+ datas[5]
				+ "'"
				+ ",'"
				+ datas[6]
				+ "'"
				+ ",'"
				+ datas[7]
				+ "'"
				+ ",'"
				+ datas[8]
				+ "'"
				+ ",'"
				+ datas[9]
				+ "'"
				+ ",'"
				+ datas[10]
				+ "'"
				+ ",'"
				+ datas[11]
				+ "'"
				+ ",'"
				+ datas[12]
				+ "'"
				+ ",'"
				+ datas[13]
				+ "'"
				+ ",'"
				+ datas[14]
				+ "'"
				+ ",'"
				+ datas[15]
				+ "'"
				+ ",'"
				+ datas[16]
				+ "'"
				+ ",'"
				+ datas[17]
				+ "'"
				+ ",'"
				+ datas[18]
				+ "'"
				+ ",'"
				+ datas[19]
				+ "'"
				+ ",'"
				+ datas[20]
				+ "'"
				+ ",'"
				+ datas[21]
				+ "'"
				+ ",'"
				+ datas[22]
				+ "'"
				+ ",'"
				+ datas[23]
				+ "'"
				+ ",'"
				+ datas[24]
				+ "'"
				+ ",'"
				+ datas[25]
				+ "'"
				+ ",'"
				+ datas[26]
				+ "'"
				+ ",'"
				+ datas[27]
				+ "'"
				+ ",'"
				+ datas[28]
				+ "'"
				+ ",'"
				+ datas[29]
				+ "'"
				+ ",'"
				+ datas[30]
				+ "'"
				+ ",'"
				+ datas[31]
				+ "'"
				+ ",'"
				+ datas[32]
				+ "'"
				+ ",'"
				+ datas[33]
				+ "'"
				+ ",'"
				+ datas[34]
				+ "'"
				+ ",'"
				+ datas[35]
				+ "'"
				+ ",'"
				+ datas[36]
				+ "'"
				+ ",'"
				+ datas[37]
				+ "'"
				+ ",'"
				+ datas[38]
				+ "'"
				+ ",'"
				+ datas[39]
				+ "'"
				+ ",'"
				+ datas[40]
				+ "'"
				+ ",'"
				+ datas[41]
				+ "'"
				+ ",'"
				+ datas[42]
				+ "'"
				+ ",'"
				+ datas[43]
				+ "'"
				+ ",'"
				+ datas[44]
				+ "'"
				+ ",'"
				+ datas[45]
				+ "'"
				+ ",'"
				+ datas[46]
				+ "'" + ",'" + datas[47] + "'" + ",'" + datas[48] + "')";
		s.executeUpdate(sql);
		System.out.println("开始抓取数据……");
		return stockid;

	}

	public static void main(String[] args) throws SQLException {

		new StockCrawl();

		// connection.close();

	}
}
