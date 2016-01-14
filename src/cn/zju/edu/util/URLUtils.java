package cn.zju.edu.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Iterator;
import java.util.Map;

public class URLUtils {

	public static String getContent(String url) {
		HttpURLConnection urlConn = null;
		InputStream in = null;
		BufferedReader bufin = null;
		StringBuffer sb = new StringBuffer();
		String str = null;
		try {
			urlConn = (HttpURLConnection) new URL(url).openConnection();

			urlConn.addRequestProperty("Accept",
					"text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
			urlConn.addRequestProperty("Accept-Language", "en-US,en;q=0.5");
			urlConn.addRequestProperty("Accept-Encoding", "gzip, deflate");
			urlConn.addRequestProperty("User-Agent",
					"Mozilla/5.0 (X11; Linux x86_64; rv:38.0) Gecko/20100101 Firefox/38.0");
			urlConn.addRequestProperty("If-None-Match", "1440076710-193");
			urlConn.addRequestProperty("Connection", "keep-alive");
			urlConn.addRequestProperty("Cache-Control", "max-age=0");
			urlConn.addRequestProperty("Host", "qt.gtimg.cn");

			in = urlConn.getInputStream();
			bufin = new BufferedReader(new InputStreamReader(in, "utf8"));
			while ((str = bufin.readLine()) != null) {
				sb.append(str + "\r\n");
			}
		} catch (IOException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		} finally {
			StreamUtils.closeStream(bufin);
			StreamUtils.closeStream(in);
		}
		return sb.toString();
	}

	public static String getContent(String url, String cookie) {
		HttpURLConnection urlConn = null;
		String content = null;
		try {
			urlConn = (HttpURLConnection) new URL(url).openConnection();

			urlConn.addRequestProperty("Accept",
					"text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
			urlConn.addRequestProperty("Accept-Language", "zh-CN,zh;q=0.8");
			urlConn.addRequestProperty("User-Agent",
					"Mozilla/5.0 (X11; Linux x86_64; rv:17.0) Gecko/20131029 Firefox/17.0");
			urlConn.addRequestProperty("DNT", "1");
			urlConn.addRequestProperty("Connection", "keep-alive");
			urlConn.addRequestProperty("Cache-Control", "max-age=0");
			urlConn.addRequestProperty("Cookie", cookie);
			InputStreamReader input = new InputStreamReader(
					urlConn.getInputStream(), "utf-8");
			char[] ch = new char[1024];
			while (input.read(ch) != -1) {
				String str = new String(ch);
				content = content + str;
			}
		} catch (IOException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		return content;
	}

	public static String getCookie(String url, String username, String password) {
		HttpURLConnection urlConn = null;
		StringBuilder cookie = null;
		String str = null;
		try {
			urlConn = (HttpURLConnection) new URL(url).openConnection();

			urlConn.addRequestProperty("Accept",
					"text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
			urlConn.addRequestProperty("Accept-Language", "zh-CN,zh;q=0.8");
			urlConn.addRequestProperty("User-Agent",
					"Mozilla/5.0 (X11; Linux x86_64; rv:17.0) Gecko/20131029 Firefox/17.0");
			urlConn.addRequestProperty("DNT", "1");
			urlConn.addRequestProperty("Connection", "keep-alive");
			urlConn.addRequestProperty("Cache-Control", "max-age=0");

			urlConn.setRequestMethod("POST");
			urlConn.setDoOutput(true);
			OutputStreamWriter out = new OutputStreamWriter(
					urlConn.getOutputStream(), "UTF-8");
			StringBuilder buf = new StringBuilder();
			buf.append("return_url=http://www.jisilu.cn/");
			buf.append("&user_name=" + URLEncoder.encode(username, "UTF-8"));
			buf.append("&password=" + URLEncoder.encode(password, "UTF-8"));
			buf.append("&net_auto_login=1&_post_type=ajax");
			out.write(buf.toString());
			out.flush();
			out.close();

			cookie = new StringBuilder();
			for (int i = 1; (str = urlConn.getHeaderFieldKey(i)) != null; i++) {
				if ("Set-Cookie".equalsIgnoreCase(str)) {
					String temp = urlConn.getHeaderField(i);
					if (temp != null && !temp.contains("deleted"))
						cookie.append(temp.substring(0, temp.indexOf(";"))
								+ ";");
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		return cookie.toString();
	}

	public static String getContent(String url, String cookie,
			Map<String, String> map) {
		HttpURLConnection urlConn = null;
		InputStream in = null;
		BufferedReader bufin = null;
		StringBuffer sb = new StringBuffer();
		String str = null;
		try {
			urlConn = (HttpURLConnection) new URL(url).openConnection();

			urlConn.addRequestProperty("Accept",
					"text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
			urlConn.addRequestProperty("Accept-Language", "zh-CN,zh;q=0.8");
			urlConn.addRequestProperty("User-Agent",
					"Mozilla/5.0 (X11; Linux x86_64; rv:17.0) Gecko/20131029 Firefox/17.0");
			urlConn.addRequestProperty("DNT", "1");
			urlConn.addRequestProperty("Connection", "keep-alive");
			urlConn.addRequestProperty("Cache-Control", "max-age=0");
			urlConn.addRequestProperty("Cookie", cookie);

			urlConn.setDoOutput(true);
			OutputStreamWriter out = new OutputStreamWriter(
					urlConn.getOutputStream(), "UTF-8");

			StringBuilder buf = new StringBuilder();
			Iterator<String> iter = map.keySet().iterator();
			for (; iter.hasNext();) {
				String key = iter.next();
				buf.append(key + map.get(key) + "&");
			}
			buf = buf.deleteCharAt(buf.length() - 1);
			out.write(buf.toString());
			out.flush();
			out.close();

			in = urlConn.getInputStream();
			bufin = new BufferedReader(new InputStreamReader(in));
			while ((str = bufin.readLine()) != null) {
				sb.append(str + "\n");
			}
		} catch (IOException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		} finally {
			StreamUtils.closeStream(bufin);
			StreamUtils.closeStream(in);
		}
		return sb.toString();
	}
}
