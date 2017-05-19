package com.zoufanqi.utils.gencode;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by vanki on 2017/3/24.
 */
public class GenMapperImplFile {
    private static String tempPath;
    private static String mapperPath;
    private static String mapperImplPath;
    private static String templateContent;

    public static void main(String[] args) {
        GenMapperImplFile.start();
    }

    public static void start() {
        getPath();
        readTemplate();

        final String tempEntity = "###entity###";
        final String tempMapper = "###mapper###";
        final String tempExample = "###example###";
        final String mapperSuffix = "Mapper";
        final String exampleSuffix = "Example";
        final String mapperImplSuffix = "Impl";

        File mapperImplFile = new File(mapperImplPath);
        if (!mapperImplFile.exists()) {
            mapperImplFile.mkdirs();
        }

        String[] mapperImplNames = mapperImplFile.list();
        List<String> mapperImplList = mapperImplNames == null ? new ArrayList<String>() : Arrays.asList(mapperImplNames);

        for (String mapperName : new File(mapperPath).list()) {
            mapperName = mapperName.replace(".java", "");
            if (!mapperName.endsWith(mapperSuffix)) continue;

            final String entityName = mapperName.replace(mapperSuffix, "");
            final String exampleName = entityName + exampleSuffix;
            final String implName = mapperName + mapperImplSuffix + ".java";
            final String filePath = mapperImplPath + implName;

            if (mapperImplList.contains(implName)) continue;

            String mapperImplContent = templateContent.replace(tempEntity, entityName);
            mapperImplContent = mapperImplContent.replace(tempMapper, mapperName);
            mapperImplContent = mapperImplContent.replace(tempExample, exampleName);


            System.out.println("生成mapper实现类: " + filePath);
            writerFile(filePath, mapperImplContent);
        }
    }

    private static void readTemplate() {
        final String tempName = "genMapperImplTemplate.txt";
        templateContent = readFile(tempPath + tempName);
    }

    private static void writerFile(String filePath, String content) {
        BufferedWriter bw = null;
        try {
            bw = new BufferedWriter(new FileWriter(new File(filePath)));
            bw.write(content);
            bw.flush();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (bw != null) bw.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private static String readFile(String filePath) {
        String content = null;
        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader(new File(filePath)));
            StringBuffer sb = new StringBuffer();
            String line;
            while ((line = br.readLine()) != null) {
                sb.append(line);
                sb.append("\r\n");
            }
            content = sb.toString();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (br != null) br.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return content;
    }

    private static void getPath() {
        String path = GenMapperImplFile.class.getResource("").getPath();
        path = path.replace("target/classes", "src/main/java");
        tempPath = path;
        mapperPath = path.replace("utils/gencode", "mapper");
        mapperImplPath = mapperPath + "impl/";
    }
}