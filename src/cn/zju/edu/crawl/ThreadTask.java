package cn.zju.edu.crawl;

import java.sql.SQLException;
import java.util.Calendar;
import java.util.TimerTask;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ThreadTask extends TimerTask {

	@SuppressWarnings("unused")
	private static ThreadPoolExecutor threadPool = new ThreadPoolExecutor(150,
			150, 3, TimeUnit.SECONDS, new LinkedBlockingQueue<Runnable>());

	@Override
	public void run() {

		try {

			Calendar c = Calendar.getInstance();
			if (c.get(Calendar.HOUR_OF_DAY) > 6
					&& c.get(Calendar.HOUR_OF_DAY) < 21
					&& (c.get(Calendar.DAY_OF_WEEK) > 1 && c
							.get(Calendar.DAY_OF_WEEK) < 7)) {// 时间控制，周六日股市休息
				new StockCrawl();// 启动爬虫类

			} else {
				System.out.println("对不起，股市已关闭……");
				System.exit(0);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
