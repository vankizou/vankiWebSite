package com.zoufanqi.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLDecoder;
import java.nio.channels.FileChannel;
import java.util.List;
import java.util.Map;

public class FileUtil {
    public final static Logger logger = LoggerFactory.getLogger(FileUtil.class);
    private final static String PATH_PREFIX = "";
    private static String prefix = "";

    static {
        init();
    }

    /**
     * 关联文件路径
     *
     * @param path
     * @param name
     *
     * @return
     *
     * @throws Exception
     */
    public static String associateFilePath(String path, String name) throws Exception {
        if (name == null)
            throw new Exception("文件名不能为空");
        StringBuffer file = new StringBuffer();
        file.append(prefix);
        if (path != null) {
            file.append(path);
        }
        if (!file.toString().endsWith(File.separator)) {
            file.append(File.separator);
        }
        file.append(name);
        return file.toString();
    }

    /**
     * 复制文件
     *
     * @param srcFile
     * @param destFile
     *
     * @return
     */
    public static boolean copyFile(File srcFile, File destFile) {
        if (srcFile == null || destFile == null || !srcFile.exists()) return false;
        File parentFile = destFile.getParentFile();
        if (!parentFile.exists()) parentFile.mkdirs();

        FileInputStream fi = null;
        FileOutputStream fo = null;
        FileChannel cIn = null;
        FileChannel cOut = null;
        try {
            fi = new FileInputStream(srcFile);
            fo = new FileOutputStream(destFile);
            cIn = fi.getChannel();
            cOut = fo.getChannel();
            cIn.transferTo(0, cIn.size(), cOut);
            return true;
        } catch (Exception e) {
            logger.error("文件复制失败! 异常信息: {}", ExceptionUtil.getExceptionAllMsg(e));
        } finally {
            try {
                if (cOut != null) cOut.close();
                if (cIn != null) cIn.close();
                if (fo != null) fo.close();
                if (fi != null) fi.close();
            } catch (Exception e) {
                logger.error("文件复制失败! 异常信息: {}", ExceptionUtil.getExceptionAllMsg(e));
            }
        }
        return false;
    }

    /**
     * 设置文件权限
     *
     * @param file
     * @param permissionNum
     *
     * @throws IOException
     */
    public static void setFilePermission(File file, int permissionNum) throws IOException {
        if (file != null && file.exists()) {
            StringBuffer sb = new StringBuffer().append("chmod ").append(permissionNum).append(" ").append(file.getAbsolutePath());
            Runtime.getRuntime().exec(sb.toString());
        }
    }

    public static void setFilePermission(String path, int permissionNum) throws IOException {
        StringBuffer sb = new StringBuffer().append("chmod ").append(permissionNum).append(" ").append(path);
        Runtime.getRuntime().exec(sb.toString());
    }

    public static void setFolderAndAllSubPermission(String path, int permissionNum) throws IOException {
        StringBuffer sb = new StringBuffer().append("chmod -R ").append(permissionNum).append(" ").append(path);
        Runtime.getRuntime().exec(sb.toString());
    }

    /**
     * 向指定URL发送GET方法的请求
     *
     * @param url   发送请求的URL
     * @param param 请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
     *
     * @return URL 所代表远程资源的响应结果
     */
    public static String sendGet(String url, String param) {
        String result = "";
        BufferedReader in = null;
        try {
            String urlNameString = url + "?" + param;
            URL realUrl = new URL(urlNameString);
            // 打开和URL之间的连接
            URLConnection connection = realUrl.openConnection();
            // 设置通用的请求属性
            connection.setRequestProperty("accept", "*/*");
            connection.setRequestProperty("connection", "Keep-Alive");
            connection.setRequestProperty("user-agent",
                    "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            // 建立实际的连接
            connection.connect();
            // 获取所有响应头字段
            Map<String, List<String>> map = connection.getHeaderFields();
            // 遍历所有的响应头字段
            /*for (String key : map.keySet()) {
                System.out.println(key + "--->" + map.get(key));
            }*/
            // 定义 BufferedReader输入流来读取URL的响应
            in = new BufferedReader(new InputStreamReader(
                    connection.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
            System.out.println("发送GET请求出现异常！" + e);
            e.printStackTrace();
        }
        // 使用finally块来关闭输入流
        finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
        return result;
    }

    /**
     * 向指定 URL 发送POST方法的请求
     *
     * @param url   发送请求的 URL
     * @param param 请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
     *
     * @return 所代表远程资源的响应结果
     */
    public static String sendPost(String url, String param) {
        PrintWriter out = null;
        BufferedReader in = null;
        String result = "";
        try {
            URL realUrl = new URL(url);
            // 打开和URL之间的连接
            URLConnection conn = realUrl.openConnection();
            // 设置通用的请求属性
            conn.setRequestProperty("accept", "*/*");
            conn.setRequestProperty("connection", "Keep-Alive");
            conn.setRequestProperty("user-agent",
                    "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            // 发送POST请求必须设置如下两行
            conn.setDoOutput(true);
            conn.setDoInput(true);
            // 获取URLConnection对象对应的输出流
            out = new PrintWriter(conn.getOutputStream());
            // 发送请求参数
            out.print(param);
            // flush输出流的缓冲
            out.flush();
            // 定义BufferedReader输入流来读取URL的响应
            in = new BufferedReader(
                    new InputStreamReader(conn.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
            System.out.println("发送 POST 请求出现异常！" + e);
            e.printStackTrace();
        }
        //使用finally块来关闭输出流、输入流
        finally {
            try {
                if (out != null) {
                    out.close();
                }
                if (in != null) {
                    in.close();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        return result;
    }

    private static void init() {
        prefix = PATH_PREFIX;
    }

    public static void main(String[] args) throws Exception {
//        System.out.println(FileUtil.associateFilePath("/lkk/fdsaf", "TEST_" + System.currentTimeMillis()));

//        copyFile(new File("/Users/vanki/Documents/company/lofficiel/imgType/1/1.jpg"), new File("/Users/vanki/Documents/company/lofficiel/imgType/1/1/1/1"));

//        setFilePermission(new File("/Users/vanki/Documents/company/lofficiel/resgit/idea/out/img/qg/4235d441354441448dce583480aa54bd.jpg"), 777);

//        InputStream is = getFileFromUrl("http://avatar.csdn.net/B/D/2/1_a79412906.jpg");

        String str = URLDecoder.decode(sendPost("http://localhost:8080/file/uploadMultiImport.html", "url=http://img.have.com/data/profile/ao/00/04/19/ed/%E6%97%B6%E8%A3%85logosss.jpg"), "UTF-8");
        System.out.println(str);
        str = str.split("&value=")[1];
        str = str.substring(0, str.length() - 1);
        System.out.println(str);
    }

	public static void downLoadFromUrl(String musicPath, String newfilename,String savePath) {
		 
	}
	
	
	
	
	
}
