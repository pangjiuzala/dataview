package cn.zju.edu.main;

import java.util.Timer;

import cn.zju.edu.crawl.ThreadTask;

public class CrawlStart {
	public CrawlStart() {
		Timer timer = new Timer();
		timer.schedule(new ThreadTask(), 0, 3000);
	}

}
