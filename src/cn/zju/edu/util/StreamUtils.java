package cn.zju.edu.util;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.codec.binary.Hex;

public class StreamUtils {

	/**
	 * 读取txt文件
	 * 
	 * @param filePath
	 */
	public static String readTxtFile(String filePath) {
		String strBeans = "";
		try {
			String encoding = "UTF-8";
			File file = new File(filePath);
			if (file.isFile() && file.exists()) { // 判断文件是否存在
				InputStreamReader read = new InputStreamReader(
						new FileInputStream(file), encoding);// 考虑到编码格�?
				BufferedReader bufferedReader = new BufferedReader(read);
				String lineTxt = null;
				while ((lineTxt = bufferedReader.readLine()) != null) {
					strBeans += lineTxt + "\n";
				}
				read.close();
			} else {
				System.out.println("找不到指定的文件");
			}
		} catch (Exception e) {
			System.out.println("读取文件内容出错");
			e.printStackTrace();
		}
		return strBeans;
	}

	// 华丽丽的分割�?
	public static void closeStream(Closeable c) {
		if (c != null) {
			try {
				c.close();
				c = null;
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public static void write2File(String content, String path) {
		BufferedWriter bufin = null;
		try {
			bufin = new BufferedWriter(new FileWriter(path, true));
			bufin.write(content);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			StreamUtils.closeStream(bufin);

		}

	}

	public static String getSHA256(String content) {
		MessageDigest digest = null;
		byte[] hash = null;
		try {
			digest = MessageDigest.getInstance("SHA-256");
			hash = digest.digest(content.getBytes("UTF-8"));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return Hex.encodeHexString(hash);
	}

	/**
	 * 写入单条property信息
	 * 
	 * 
	 * @param file
	 * @param parameterName
	 * @param parameterValue
	 */

	public static void writeProperties(File file, String parameterName,
			String parameterValue) {
		Properties prop = new Properties();
		try (InputStream fis = new FileInputStream(file)) {

			// 从输入流中读取属性列表（键和元素对）
			prop.load(fis);
			// 调用 Hashtable 的方�?put。使�?getProperty 方法提供并行性�?
			// 强制要求为属性的键和值使用字符串。返回�?�?Hashtable 调用 put 的结果�?
			OutputStream fos = new FileOutputStream(file);
			// 调用 Hashtable 的方�?put�?
			prop.setProperty(parameterName, parameterValue);
			// 以�?合使�?load 方法加载�?Properties 表中的格式，
			// 将此 Properties 表中的属性列表（键和元素对）写入输出�?
			prop.store(fos, "Update '" + parameterName + "' value");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 将信息批量写入properties信息
	 * 
	 * @param outfile
	 * @param map
	 * @param b
	 *            true:实现续写
	 */
	public static void write2AllProperties(File outfile,
			Map<String, String> map, boolean b) {

		FileInputStream fis = null;
		FileOutputStream fos = null;

		Properties properties = new Properties();
		try {
			if (b) {
				// 将文件读入流
				fis = new FileInputStream(outfile);
				// 加载文件
				properties.load(fis);
			}
			// 准备写入�?
			fos = new FileOutputStream(outfile);
			// 将信息放入Properites
			properties.putAll(map);
			// 保存此Properites
			properties.store(fos, "更新�?��列表");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			closeStream(fis);
			closeStream(fos);
		}

	}

	/**
	 * 读取properties的全部信�?
	 * 
	 * @param file
	 * @return
	 */
	public static Properties readProperties(File file) {

		Properties props = new Properties();
		InputStream in = null;
		try {
			in = new BufferedInputStream(new FileInputStream(file));
			props.load(in);
			return props;
		} catch (IOException e) {
			throw new RuntimeException("error");
		} finally {
			closeStream(in);
		}
	}
}