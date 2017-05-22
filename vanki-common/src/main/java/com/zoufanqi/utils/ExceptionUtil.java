package com.zoufanqi.utils;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * 异常处理工具类
 * 
 * @author vanki 2015年12月12日 上午11:30:31
 */
public class ExceptionUtil {
	/**
	 * 获取异常的全部信息，便于记录log
	 * 
	 * @param ex
	 * @return 2015年12月12日 上午11:28:20
	 */
	public static String getExceptionAllMsg(Exception ex) {
		StringWriter sw = new StringWriter();
		PrintWriter pw = new PrintWriter(sw);
		ex.printStackTrace(pw);
		pw.flush();
		sw.flush();
		if (pw != null) pw.close();
		try {
			if (sw != null) sw.close();
		} catch (IOException e1) {
		}
		return sw.toString();
	}
}
