package cn.zju.edu.datasvr;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import libsvm.svm;
import libsvm.svm_model;
import libsvm.svm_node;
import libsvm.svm_parameter;
import libsvm.svm_problem;
import cn.zju.edu.util.DataBaseConnection;
import cn.zju.edu.util.DataUtil;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.Statement;

public class StockdataSvr {

	public static void main(String[] args) throws Exception {
		new StockdataSvr();
	}

	public void createtable() throws SQLException {
		/* 创建historyresult表，存放预测结果 */
		String sql = "DROP TABLE IF EXISTS `stockdataresult`;";
		String sqls = "CREATE TABLE `stockdataresult` (`id` int(100) NOT NULL AUTO_INCREMENT,truevalue double(100,20) DEFAULT NULL,predictvalue double(100,20) DEFAULT NULL ,PRIMARY KEY (`id`)) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8";
		Connection conn = (Connection) DataBaseConnection.getConnection();
		Statement st = (Statement) conn.createStatement();
		st.execute(sql);
		st.execute(sqls);
	}

	public void cleartable() throws SQLException {
		Connection conn = (Connection) DataBaseConnection.getConnection();
		Statement st = (Statement) conn.createStatement();
		st.executeUpdate("delete from stockdataresult where 1=1");// 清空数据库重复内容
		st.execute("ALTER table stockdataresult AUTO_INCREMENT = 1");// 设置初始字段为1
	}

	public StockdataSvr() throws Exception {
		createtable();
		cleartable();
		DataUtil du = new DataUtil();
		du.deletefile("./datasource/stockdataresult.txt");
		String wz1 = "sz300267";
		String wz2 = "sz002642";
		du.getTrainStockdata(wz1, 1);
		du.getTestStockdata(wz2, 4);
		List<Double> label = new ArrayList<Double>();
		List<svm_node[]> nodeSet = new ArrayList<svm_node[]>();
		getData(nodeSet, label, "./datasource/trainStockdata.txt");

		int dataRange = nodeSet.get(0).length;
		svm_node[][] datas = new svm_node[nodeSet.size()][dataRange]; // 训练集的向量表
		for (int i = 0; i < datas.length; i++) {
			for (int j = 0; j < dataRange; j++) {
				datas[i][j] = nodeSet.get(i)[j];
			}
		}
		double[] lables = new double[label.size()]; // a,b 对应的lable
		for (int i = 0; i < lables.length; i++) {
			lables[i] = label.get(i);
		}
		// 定义svm_problem对象
		svm_problem problem = new svm_problem();
		problem.l = nodeSet.size(); // 向量个数
		problem.x = datas; // 训练集向量表
		problem.y = lables; // 对应的lable数组
		// 定义svm_parameter对象
		svm_parameter param = new svm_parameter();
		param.svm_type = svm_parameter.EPSILON_SVR;
		param.kernel_type = svm_parameter.LINEAR;
		param.cache_size = 100;
		param.eps = 0.00001;
		param.C = 1.9;
		// 训练SVM分类模型
		System.out.println(svm.svm_check_parameter(problem, param));
		// 如果参数没有问题，则svm.svm_check_parameter()函数返回null,否则返回error描述。
		svm_model model = svm.svm_train(problem, param);
		// svm.svm_train()训练出SVM分类模型
		// 获取测试数据
		List<Double> testlabel = new ArrayList<Double>();
		List<svm_node[]> testnodeSet = new ArrayList<svm_node[]>();
		getData(testnodeSet, testlabel, "./datasource/testStockdata.txt");
		svm_node[][] testdatas = new svm_node[testnodeSet.size()][dataRange]; // 训练集的向量表
		for (int i = 0; i < testdatas.length; i++) {
			for (int j = 0; j < dataRange; j++) {
				testdatas[i][j] = testnodeSet.get(i)[j];
			}
		}
		double[] testlables = new double[testlabel.size()]; // a,b 对应的lable
		for (int i = 0; i < testlables.length; i++) {
			testlables[i] = testlabel.get(i);
		}
		// 预测测试数据的lable
		double err = 0.0;
		for (int i = 0; i < testdatas.length; i++) {
			double truevalue = testlables[i];
			System.out.print("真实值：" + truevalue + " ");
			double predictValue = svm.svm_predict(model, testdatas[i]);
			System.out.println("预测值：" + predictValue);
			err += Math.abs(predictValue - truevalue);
			Connection conns = (Connection) DataBaseConnection.getConnection();
			Statement sts = (Statement) conns.createStatement();
			sts.executeUpdate("insert into stockdataresult(truevalue,predictvalue) values('"
					+ truevalue + "'" + "," + "'" + predictValue + "');");
			DataUtil.newFile("./datasource/stockdataresult.txt", "真实值："
					+ truevalue + " " + "预测值：" + predictValue + " " + "err="
					+ err);
		}
		/* System.out.println("err=" + err / datas.length); */

	}

	public static void getData(List<svm_node[]> nodeSet, List<Double> label,
			String filename) {
		try {
			FileReader fr = new FileReader(new File(filename));
			@SuppressWarnings("resource")
			BufferedReader br = new BufferedReader(fr);
			String line = null;
			while ((line = br.readLine()) != null) {
				String[] datas = line.split(",");
				svm_node[] vector = new svm_node[datas.length - 1];
				for (int i = 0; i < datas.length - 1; i++) {
					svm_node node = new svm_node();
					node.index = i + 1;
					node.value = Double.parseDouble(datas[i]);
					vector[i] = node;
				}
				nodeSet.add(vector);
				double lablevalue = Double.parseDouble(datas[datas.length - 1]);
				label.add(lablevalue);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}