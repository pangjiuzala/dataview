package cn.zju.edu.util;

import java.io.File;

import weka.classifiers.Classifier;
import weka.classifiers.functions.LibSVM;
import weka.core.Instances;
import weka.core.converters.ArffLoader;
import weka.core.converters.ConverterUtils.DataSink;
import weka.experiment.InstanceQuery;
import weka.filters.unsupervised.attribute.Discretize;

public class SVMUtil {
	public boolean getdata() throws Exception {
		InstanceQuery querys = new InstanceQuery();
		querys.setDatabaseURL("jdbc:mysql://localhost:3306/test");
		querys.setUsername("root");
		querys.setPassword("");
		querys.setQuery("SELECT `Open`,Highest,Lowest,`Close`, `Change`,Increase,Amplitude,HandsAll,Money from history where Increase>=0.1;");

		Instances datas = querys.retrieveInstances();
		datas.setClassIndex(0); // 设置分类属性所在行号（第一行为0号）,instancesTest.numAttributes()可以取得属性总数
		datas.setClassIndex(0);
		Discretize discretizes = new Discretize();
		String[] optiond = new String[6];
		optiond[0] = "-B";
		optiond[1] = "8";
		optiond[2] = "-M";
		optiond[3] = "-1.0";
		optiond[4] = "-R";
		optiond[5] = "2-last";
		discretizes.setOptions(optiond);
		discretizes.setInputFormat(datas);
		DataSink.write("E://svm.arff", datas);
		return true;
	}

	public double getAccuracy() throws Exception {

		File inputFiles = new File("E://svm.arff");// 训练语料文件

		ArffLoader atfs = new ArffLoader();

		atfs.setFile(inputFiles);

		Instances instancesTrains = atfs.getDataSet(); // 读入测试文件

		instancesTrains.setClassIndex(0); // 设置分类属性所在行号（第一行为0号）,instancesTest.numAttributes()可以取得属性总数

		double sums = instancesTrains.numInstances(), // 测试语料实例数

		rights = 0.0f;

		Classifier m_classifiers = new LibSVM();
		m_classifiers.buildClassifier(instancesTrains); // 训练

		for (int i = 0; i < sums; i++)// 测试分类结果

		{
			if (m_classifiers.classifyInstance(instancesTrains.instance(i)) == instancesTrains
					.instance(i).classValue())// 如果预测值和答案值相等（测试语料中的分类列提供的须为正确答案,结果才有意义）
			{

				rights++;// 正确值加1
			}

		}

		return rights / sums;

	}

	public static void main(String[] args) throws Exception {

		System.out.println(new SVMUtil().getAccuracy());
	}
}
