package com.zoufanqi.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.List;

public class FileUtil {
    public final static Logger LOG = LoggerFactory.getLogger(FileUtil.class);

    public static void appendFile(String filePath, String content) {
        writeFile(filePath, content, true);
    }

    public static void writeFile(String filePath, String content) {
        writeFile(filePath, content, false);
    }

    private static void writeFile(String filePath, String content, boolean isAppend) {
        if (content == null) return;
        File file = new File(filePath);
        if (!file.getParentFile().exists()) file.getParentFile().mkdirs();

        FileWriter fw = null;
        try {
            if (isAppend) {
                fw = new FileWriter(file, true);
            } else {
                fw = new FileWriter(file);
            }
            fw.write(content);
            fw.flush();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (fw != null) fw.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static List<String> readFileOfLine(String filePath) {
        File file = new File(filePath);
        if (!file.exists() || !file.isFile()) return null;

        FileInputStream fis = null;
        InputStreamReader isr = null;
        BufferedReader br = null;
        List<String> list = new ArrayList<String>();

        try {
            fis = new FileInputStream(file);
            isr = new InputStreamReader(fis);
            br = new BufferedReader(isr);
            String line;
            while ((line = br.readLine()) != null) {
                list.add(line);
            }
        } catch (Exception e) {
            LOG.error(ExceptionUtil.getExceptionAllMsg(e));
        } finally {
            try {
                if (fis != null) fis.close();
                if (isr != null) isr.close();
                if (br != null) br.close();
            } catch (IOException e) {
                LOG.error(ExceptionUtil.getExceptionAllMsg(e));
            }
        }
        return list;
    }

    public static String readFile(String filePath) {
        File file = new File(filePath);
        if (!file.exists() || !file.isFile()) return null;

        FileInputStream fis = null;
        InputStreamReader isr = null;
        BufferedReader br = null;
        StringBuffer sb = new StringBuffer();

        try {
            fis = new FileInputStream(file);
            isr = new InputStreamReader(fis);
            br = new BufferedReader(isr);
            String line;
            while ((line = br.readLine()) != null) {
                sb.append(line);
                sb.append("\r\n");
            }
        } catch (Exception e) {
            LOG.error(ExceptionUtil.getExceptionAllMsg(e));
        } finally {
            try {
                if (fis != null) fis.close();
                if (isr != null) isr.close();
                if (br != null) br.close();
            } catch (IOException e) {
                LOG.error(ExceptionUtil.getExceptionAllMsg(e));
            }
        }
        return sb.toString();
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
            LOG.error("文件复制失败! 异常信息: {}", ExceptionUtil.getExceptionAllMsg(e));
        } finally {
            try {
                if (cOut != null) cOut.close();
                if (cIn != null) cIn.close();
                if (fo != null) fo.close();
                if (fi != null) fi.close();
            } catch (Exception e) {
                LOG.error("文件复制失败! 异常信息: {}", ExceptionUtil.getExceptionAllMsg(e));
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
}
