package cn.zju.edu.util;

import java.io.File;

import weka.classifiers.Classifier;
import weka.classifiers.trees.J48;
import weka.core.Instances;
import weka.core.converters.ArffLoader;
import weka.core.converters.ConverterUtils.DataSink;
import weka.experiment.InstanceQuery;
import weka.filters.unsupervised.attribute.Discretize;

public class J48Util {
	public boolean getdata() throws Exception {
		InstanceQuery query = new InstanceQuery();
		query.setDatabaseURL("jdbc:mysql://localhost:3306/test");
		query.setUsername("root");
		query.setPassword("");
		query.setQuery("SELECT `Open`,Highest,Lowest,`Close`, `Change`,Increase,Amplitude,HandsAll,Money from history where Increase>=0.1;");

		Instances data = query.retrieveInstances();
		data.setClassIndex(0); // 设置分类属性所在行号（第一行为0号）,instancesTest.numAttributes()可以取得属性总数
		data.setClassIndex(0);
		Discretize discretize = new Discretize();
		String[] options = new String[6];
		options[0] = "-B";
		options[1] = "8";
		options[2] = "-M";
		options[3] = "-1.0";
		options[4] = "-R";
		options[5] = "2-last";
		discretize.setOptions(options);
		discretize.setInputFormat(data);
		DataSink.write("C://j48.arff", data);
		return true;
	}

	public double getAccuracy() throws Exception {

		File inputFile = new File("C://j48.arff");// 训练语料文件

		ArffLoader atf = new ArffLoader();

		atf.setFile(inputFile);

		Instances instancesTrain = atf.getDataSet(); // 读入测试文件

		instancesTrain.setClassIndex(0); // 设置分类属性所在行号（第一行为0号）,instancesTest.numAttributes()可以取得属性总数

		double sum = instancesTrain.numInstances(), // 测试语料实例数

		right = 0.0f;

		Classifier m_classifier = new J48();

		m_classifier.buildClassifier(instancesTrain); // 训练

		for (int i = 0; i < sum; i++)// 测试分类结果

		{
			if (m_classifier.classifyInstance(instancesTrain.instance(i)) == instancesTrain
					.instance(i).classValue())// 如果预测值和答案值相等（测试语料中的分类列提供的须为正确答案,结果才有意义）
			{

				right++;// 正确值加1
			}

		}

		return right / sum;
	}

	public static void main(String[] args) throws Exception {
		System.out.println(new J48Util().getAccuracy());
	}
}
