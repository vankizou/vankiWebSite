package com.zoufanqi.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
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
}
