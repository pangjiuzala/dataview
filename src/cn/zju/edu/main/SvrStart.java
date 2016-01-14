package cn.zju.edu.main;

import cn.zju.edu.datasvr.HistorySvr;
import cn.zju.edu.datasvr.StockdataSvr;
import cn.zju.edu.util.DataBaseConnection;

public class SvrStart {
	public static void main(String[] args) throws Exception {
		new HistorySvr();//预测股票历史数据
		new StockdataSvr();//预测股票实时数据
		new DataBaseConnection().close();
	}
}
