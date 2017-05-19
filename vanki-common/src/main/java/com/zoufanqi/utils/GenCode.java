package com.zoufanqi.utils;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class GenCode {
    public static void main(String[] args) throws Exception {
        //System.out.println(getBeanName());
//		String dir1 = "src/main/java/cn.lofficiel/aaa/";
//		String dir2 = "src/main/java/cn.lofficiel/bbb/";
//		ArrayList<String> list1 = new ArrayList<String>();
//		list1.add("User");
//		list1.add("Abc");
//		
//		list1 = getBeanName();

        ArrayList<String> list1 = new ArrayList<String>();
        list1.add("ShareBicycle");
//        list1.add("MyFile");

		writeServiceInterface(list1,"f:/service");
		writeServiceImplCls(list1, "f:/service/impl");

        //entityAddJsonIgnore();
    }

    @SuppressWarnings("unused")
    private static void entityAddJsonIgnore() throws Exception {
        File file = new File("src/main/java/com/zoufanqi/entity");
        File[] files = file.listFiles();
        for (File f : files) {
            String name = f.getName();
            StringBuilder sb = new StringBuilder();
            if (!name.contains("Example")) {
                BufferedReader br = new BufferedReader(new FileReader(f));
                String strLine = null;
                while ((strLine = br.readLine()) != null) {
                    if (strLine.contains("private Integer isDel;")) {
                        sb.append("\t@JsonIgnore");
                        sb.append("\r\n");
                        sb.append(strLine);
                        sb.append("\r\n");
                    } else {
                        sb.append(strLine);
                        sb.append("\r\n");
                    }
                    if (strLine.contains("package")) {
                        sb.append("\r\n");
                        sb.append("import com.fasterxml.jackson.annotation.JsonIgnore;");
                        sb.append("\r\n");
                    }
                }
                File file_output = new File("src/main/java/com/zoufanqi/entity", name);
                file_output.createNewFile();
                BufferedWriter bw = new BufferedWriter(new FileWriter(file_output));
                bw.write(sb.toString());
                bw.close();
                br.close();
            }
        }
    }

    /**
     * xxxServiceImple.java
     *
     * @throws Exception
     */
    private static void writeServiceImplCls(List<String> listBeanName, String dir) throws Exception {
        //ArrayList<String> list = getBeanName();
        File fileDir = new File(dir);
        if (!fileDir.exists()) {
            fileDir.mkdirs();
        }
        for (String beanName : listBeanName) {
            /*
			 * service接口名称
			 */
            String serviceName = beanName + "ServiceImpl";
            File file = new File(dir, serviceName + ".java");
            file.createNewFile();

			/*
			 * 接口文件中内容
			 */
            StringBuilder sb = new StringBuilder();
			/*
			 * 包
			 */
            sb.append("package com.zoufanqi.service.impl;");
            sb.append("\r\n\r\n");
			/*
			 * 导包
			 */
            sb.append("import org.springframework.beans.factory.annotation.Autowired;\r\n");
            sb.append("import org.springframework.stereotype.Service;");
            sb.append("\r\n\r\n");
            sb.append("import com.zoufanqi.entity.");
            sb.append(beanName);
            sb.append(";\r\n");
            sb.append("import com.zoufanqi.entity.");
            sb.append(beanName);
            sb.append("Example;");
            sb.append("\r\n");
            sb.append("import com.zoufanqi.mapper.");
            sb.append(beanName);
            sb.append("Mapper;");
            sb.append("\r\n");
            sb.append("import com.zoufanqi.service.");
            sb.append(beanName);
            sb.append("Service;");
            sb.append("\r\n\r\n");
			/*
			 * 接口名
			 */
            String clsName = beanName + "ServiceImpl";
            String clsVariableName = String.valueOf(beanName.charAt(0)).toLowerCase() + beanName.substring(1) + "Service";
            sb.append("@Service(\"");
            sb.append(clsVariableName);
            sb.append("\")\r\n");
            sb.append("public class ");
            sb.append(clsName);
            sb.append(" extends ReflectBaseServiceImpl<Long, ");
            sb.append(beanName);
            sb.append(", ");
            sb.append(beanName);
            sb.append("Example");
            sb.append("> implements ");
            sb.append(beanName);
            sb.append("Service");
            sb.append(" {");
            sb.append("\r\n\r\n");
			/*
			 * 属性
			 */
            String mapperName = beanName + "Mapper";
			String mapperVariableName = String.valueOf(beanName.charAt(0)).toLowerCase()+beanName.substring(1) + "Mapper";
            sb.append("\tprivate ");
            sb.append(mapperName);
            sb.append(" ");
            sb.append(mapperVariableName);
            sb.append(";\r\n\r\n");
			/*
			 * 构造方法
			 */
            sb.append("\t@Autowired");
            sb.append("\r\n");
            sb.append("\tpublic ");
            sb.append(clsName);
            sb.append("(" + mapperName + " mapper) {");
            sb.append("\r\n\t\t");
            sb.append("super(mapper);");
            sb.append("\r\n\t}\r\n\r\n");
			/*
			 * mapper的getter方法
			 */
            String setterName = "set" + beanName + "Mapper";
            String getterName = "get" + beanName + "Mapper";
            sb.append("\tpublic ");
            sb.append(mapperName);
            sb.append(" ");
            sb.append(getterName);
            sb.append("() {\r\n\t\t");
            sb.append("return this.");
            sb.append(mapperVariableName);
            sb.append(";\r\n");
            sb.append("\t}\r\n\r\n");
			/*
			 * mapper的setter方法
			 */
            sb.append("\t@Autowired\r\n");
            sb.append("\tpublic void ");
            sb.append(setterName);
            sb.append("(");
            sb.append(mapperName);
            sb.append(" ");
            sb.append(mapperVariableName);
            sb.append(") {\r\n");
            sb.append("\t\tthis.");
            sb.append(mapperVariableName);
            sb.append(" = ");
            sb.append(mapperVariableName);
            sb.append(";\r\n\t}\r\n");
            sb.append("}");

            BufferedWriter bw = new BufferedWriter(new FileWriter(file));
            bw.write(sb.toString());
            bw.close();
        }
    }

    /**
     * xxxService.java
     *
     * @throws Exception
     */
    private static void writeServiceInterface(List<String> listBeanName, String dir) throws Exception {
        File fileDir = new File(dir);
        if (!fileDir.exists()) {
            fileDir.mkdirs();
        }
        for (String beanName : listBeanName) {
			/*
			 * service接口名称
			 */
            String serviceName = beanName + "Service";
            File file = new File(dir, serviceName + ".java");
            file.createNewFile();

			/*
			 * 接口文件中内容
			 */
            StringBuilder sb = new StringBuilder();
			/*
			 * 包
			 */
            sb.append("package com.zoufanqi.service;");
            sb.append("\r\n\r\n");
			/*
			 * 导包
			 */
            sb.append("import com.zoufanqi.entity.");
            sb.append(beanName);
            sb.append(";\r\n");
            sb.append("import com.zoufanqi.entity.");
            sb.append(beanName);
            sb.append("Example;");
            sb.append("\r\n\r\n");
			/*
			 * 接口名
			 */
            sb.append("public interface ");
            sb.append(beanName);
            sb.append("Service extends BaseService<Long, ");
            sb.append(beanName);
            sb.append(", ");
            sb.append(beanName);
            sb.append("Example");
            sb.append("> {");
            sb.append("\r\n\r\n");
            sb.append("}");

            BufferedWriter bw = new BufferedWriter(new FileWriter(file));
            bw.write(sb.toString());
            bw.close();
        }
    }

    public static ArrayList<String> getBeanName() {
        ArrayList<String> returnList = new ArrayList<String>();
        File file = new File("E:\\workspace\\lofficiel\\lovetv\\lovetv-entity\\src\\main\\java\\tv\\love\\entity");
        File[] files = file.listFiles();
        for (File f : files) {
            String name = f.getName();
            if (!name.contains("Example")) {
                returnList.add(name.split("\\.")[0]);
            }
        }
        return returnList;
    }
}
