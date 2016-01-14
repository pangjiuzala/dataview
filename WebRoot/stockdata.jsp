<%@page import="cn.zju.edu.datasvr.StockdataSvr"%>
<%@page import="cn.zju.edu.main.CrawlStart"%>
<%@page import="cn.zju.edu.crawl.StockCrawl"%>
<%@page import="cn.zju.edu.util.Constants"%>
<%@page import="cn.zju.edu.dao.StockdataDao"%>
<%@ page language="java"
	import="java.util.*,java.sql.*,java.io.File,cn.zju.edu.dao.PredictHistoryDao,cn.zju.edu.model.Predict,weka.classifiers.Classifier,weka.classifiers.trees.J48,cn.zju.edu.dao.PredictHistoryDao,cn.zju.edu.dao.PredictStokcdataDao,weka.core.Instances,weka.core.converters.ArffLoader,weka.classifiers.functions.LibSVM,weka.experiment.InstanceQuery,cn.zju.edu.util.DataBaseConnection,weka.core.converters.ConverterUtils.DataSink;"
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
<title>FinaceDataView</title>
<script src="./Chart.js"></script>
<meta name="viewport" content="initial-scale = 1, user-scalable = no">
<style>
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

	<div style="width:40%">
		<div>
			<canvas id="canvas" height="512" width="512"></canvas>
		</div>
	</div>


	<%
		PredictStokcdataDao pds = new PredictStokcdataDao();
		int firstid = 0;
	%>



	<table border="2" width="60%" id="mytable" style="display:none;">

		<!-- 比较股票在2015-10-12,1和2015-10-13,2这两个不同时间段的行情指数 -->

		<tr>
			<td width="100" id="1"><%=pds.gettrue(firstid + 1)%></td>
			<td width="100" id="2"><%=pds.gettrue(firstid + 2)%></td>
			<td width="100" id="3"><%=pds.gettrue(firstid + 3)%></td>
			<td width="100" id="4"><%=pds.gettrue(firstid + 4)%></td>
			<td width="100" id="5"><%=pds.gettrue(firstid + 5)%></td>
			<td width="100" id="6"><%=pds.gettrue(firstid + 6)%></td>
			<td width="100" id="7"><%=pds.gettrue(firstid + 7)%></td>
			<td width="100" id="8"><%=pds.gettrue(firstid + 8)%></td>
			<td width="100" id="9"><%=pds.gettrue(firstid + 9)%></td>
			<td width="100" id="10"><%=pds.gettrue(firstid + 10)%></td>
			<td width="100" id="11"><%=pds.gettrue(firstid + 11)%></td>
			<td width="100" id="12"><%=pds.gettrue(firstid + 12)%></td>
		</tr>


		<tr>
			<td width="100" id="1s"><%=pds.getpredict(firstid + 1)%></td>
			<td width="100" id="2s"><%=pds.getpredict(firstid + 2)%></td>
			<td width="100" id="3s"><%=pds.getpredict(firstid + 3)%></td>
			<td width="100" id="4s"><%=pds.getpredict(firstid + 4)%></td>
			<td width="100" id="5s"><%=pds.getpredict(firstid + 5)%></td>
			<td width="100" id="6s"><%=pds.getpredict(firstid + 6)%></td>
			<td width="100" id="7s"><%=pds.getpredict(firstid + 7)%></td>
			<td width="100" id="8s"><%=pds.getpredict(firstid + 8)%></td>
			<td width="100" id="9s"><%=pds.getpredict(firstid + 9)%></td>
			<td width="100" id="10s"><%=pds.getpredict(firstid + 10)%></td>
			<td width="100" id="11s"><%=pds.getpredict(firstid + 11)%></td>
			<td width="100" id="12s"><%=pds.getpredict(firstid + 12)%></td>
		</tr>

	</table>


	<script>
		/* var randomScalingFactor = function() {
			return Math.round(Math.random() * 100)
		}; */
		var lineChartData = {
			labels : [ "5", "10", "15", "20", "25", "30", "35", "40", "45",
					"50", "55", "60" ],
			datasets : [
					{
						label : "My First dataset",
						fillColor : "rgba(220,220,220,0.2)",
						strokeColor : "rgba(220,220,220,1)",
						pointColor : "rgba(220,220,220,1)",
						pointStrokeColor : "#fff",
						pointHighlightFill : "#fff",
						pointHighlightStroke : "rgba(220,220,220,1)",
						data : [ document.getElementById("1").innerHTML,
								document.getElementById("2").innerHTML,
								document.getElementById("3").innerHTML,
								document.getElementById("4").innerHTML,
								document.getElementById("5").innerHTML,
								document.getElementById("6").innerHTML,
								document.getElementById("7").innerHTML,
								document.getElementById("8").innerHTML,
								document.getElementById("9").innerHTML,
								document.getElementById("10").innerHTML,
								document.getElementById("11").innerHTML,
								document.getElementById("12").innerHTML, ]
					},
					{
						label : "My Second dataset",
						fillColor : "rgba(151,187,205,0.2)",
						strokeColor : "rgba(151,187,205,1)",
						pointColor : "rgba(151,187,205,1)",
						pointStrokeColor : "#fff",
						pointHighlightFill : "#fff",
						pointHighlightStroke : "rgba(151,187,205,1)",
						data : [ document.getElementById("1s").innerHTML,
								document.getElementById("2s").innerHTML,
								document.getElementById("3s").innerHTML,
								document.getElementById("4s").innerHTML,
								document.getElementById("5s").innerHTML,
								document.getElementById("6s").innerHTML,
								document.getElementById("7s").innerHTML,
								document.getElementById("8s").innerHTML,
								document.getElementById("9s").innerHTML,
								document.getElementById("10s").innerHTML,
								document.getElementById("11s").innerHTML,
								document.getElementById("12s").innerHTML, ]
					} ]

		}

		window.onload = function() {
			var ctx = document.getElementById("canvas").getContext("2d");
			window.myLine = new Chart(ctx).Line(lineChartData, {
				responsive : true
			});
		}
	</script>
	<%
		new DataBaseConnection().close();
	%>
</body>
</html>

