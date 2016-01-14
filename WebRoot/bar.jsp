<%@ page language="java"
	import="java.util.*, java.sql.*,java.io.File,

weka.classifiers.Classifier,
weka.classifiers.trees.J48,
weka.core.Instances,
cn.zju.edu.dao.HistoryDao,
cn.zju.edu.model.History,
cn.zju.edu.util.J48Util,
cn.zju.edu.util.SVMUtil,
cn.zju.edu.util.DataBaseConnection,
weka.core.converters.ArffLoader,
weka.classifiers.functions.LibSVM,
weka.experiment.InstanceQuery,
weka.core.converters.ConverterUtils.DataSink,
weka.filters.Filter,
weka.filters.unsupervised.attribute.AddID,
weka.filters.unsupervised.attribute.Discretize;"
	pageEncoding="gb2312"%>

<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<%
	request.setCharacterEncoding("UTF-8");
%>
<html xmlns="http://www.w3.org/1999/xhtml">


<head>
<title>柱状图</title>
<script src="./Chart.js" type="text/javascript"></script>
<meta name="viewport" content="initial-scale = 1, user-scalable = no">
<style type="text/css">
canvas {
	
}

#main {
	width: 960px;
	height: auto;
}

#left {
	width: 250px;
	height: 600px;
}

#right {
	width: 2000px;
	height: 600px;
}

#left,#right {
	float: left;
}
</style>
</head>

<body>

	<div id="left" style="width: 40%">
		<canvas id="canvab" height="512" width="512"></canvas>
	</div>

	<table border="2" width="60%" id="mytable" style="display: none;">

		<!-- 比较股票在2015-10-12,1和2015-10-13,2这两个不同时间段的行情指数 -->
		<%
			HistoryDao hds = new HistoryDao();
			String date1 = "2015-10-12,1";
			String date2 = "2015-10-13,2";
			int id1=1;
			int id2=242;
		%>

		<tr>
			<td width="100" id="Index"><%=hds.getindex(date1,id1)%></td>
			<td width="100" id="Open"><%=hds.getopen(date1,id1)%></td>
			<td width="100" id="Highest"><%=hds.gethighest(date1,id1)%></td>
			<td width="100" id="Lowest"><%=hds.getlowest(date1,id1)%></td>
			<td width="100" id="Close"><%=hds.getclose(date1,id1)%></td>
			<td width="100" id="Change"><%=hds.getchange(date1,id1)%></td>
			<td width="100" id="Increase"><%=hds.getincrease(date1,id1)%></td>
			<td width="100" id="Amplitude"><%=hds.getamplitude(date1,id1)%></td>
			<td width="100" id="HandsAll"><%=hds.gethandall(date1,id1)%></td>
			<td width="100" id="Money"><%=hds.getmoney(date1,id1)%></td>
			<td width="100" id="j48"><%=new J48Util().getAccuracy()%></td>

		</tr>




		<tr>
			<td width="100" id="Indexs"><%=hds.getindex(date2,id2)%></td>
			<td width="100" id="Opens"><%=hds.getopen(date2,id2)%></td>
			<td width="100" id="Highests"><%=hds.gethighest(date2,id2)%></td>
			<td width="100" id="Lowests"><%=hds.getlowest(date2,id2)%></td>
			<td width="100" id="Closes"><%=hds.getclose(date2,id2)%></td>
			<td width="100" id="Changes"><%=hds.getchange(date2,id2)%></td>
			<td width="100" id="Increases"><%=hds.getincrease(date2,id2)%></td>
			<td width="100" id="Amplitudes"><%=hds.getamplitude(date2,id2)%></td>
			<td width="100" id="HandsAlls"><%=hds.gethandall(date2,id2)%></td>
			<td width="100" id="Moneys"><%=hds.getmoney(date2,id2)%></td>
			<td width="100" id="svm"><%=0.004819277108433735%></td>

		</tr>
	</table>


	<script type="text/javascript">
		var barChartData = {
			labels : [ "Open", "Highest", "Lowest", "Close", "Change",
					"Increase", "Amplitude", "HandsAll", "Money", "Accuracy" ],
			datasets : [
					{
						fillColor : "rgba(220,220,220,0.5)",
						strokeColor : "rgba(220,220,220,0.8)",
						highlightFill : "rgba(220,220,220,0.75)",
						highlightStroke : "rgba(220,220,220,1)",
						data : [ document.getElementById("Open").innerHTML,
								document.getElementById("Highest").innerHTML,
								document.getElementById("Lowest").innerHTML,
								document.getElementById("Close").innerHTML,
								document.getElementById("Change").innerHTML,
								document.getElementById("Increase").innerHTML,
								document.getElementById("Amplitude").innerHTML,
								document.getElementById("HandsAll").innerHTML,
								document.getElementById("Money").innerHTML,
								document.getElementById("j48").innerHTML, ]
					},
					{
						fillColor : "rgba(151,187,205,0.5)",
						strokeColor : "rgba(151,187,205,0.8)",
						highlightFill : "rgba(151,187,205,0.75)",
						highlightStroke : "rgba(151,187,205,1)",
						data : [
								document.getElementById("Opens").innerHTML,
								document.getElementById("Highests").innerHTML,
								document.getElementById("Lowests").innerHTML,
								document.getElementById("Closes").innerHTML,
								document.getElementById("Changes").innerHTML,
								document.getElementById("Increases").innerHTML,
								document.getElementById("Amplitudes").innerHTML,
								document.getElementById("HandsAlls").innerHTML,
								document.getElementById("Moneys").innerHTML,
								document.getElementById("svm").innerHTML, ]
					} ]

		}
		window.onload = function() {

			window.myBar = new Chart(document.getElementById("canvab")
					.getContext("2d")).Bar(barChartData, {
				responsive : true
			});
		}
	</script>
	<%
		new DataBaseConnection().close();
	%>
</body>
</html>

