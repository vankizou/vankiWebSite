package com.zoufanqi.utils;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by vanki on 2017/5/26.
 */
public class TemplateUtil {
    public static final String key_exportNoteTemp_title = "###NOTE_TITLE###";
    public static final String key_exportNoteTemp_content = "###NOTE_CONTENT###";
    private static final List<String> exportNoteTempList = new ArrayList<>();

    static {
        URL tempUrl = TemplateUtil.class.getClassLoader().getResource("temp");
        if (tempUrl != null) {
            initExportNoteTemp(tempUrl.getPath());
        }
    }

    public static final List<String> getExportNoteTempList() {
        return exportNoteTempList.isEmpty() ? null : exportNoteTempList;
    }

    private static void initExportNoteTemp(String tempRootPath) {
        if (StringUtil.isEmpty(tempRootPath)) return;
        String data = FileUtil.readFile(tempRootPath + "/exportNoteTemp.html");
        if (StringUtil.isEmpty(data)) return;
        /**
         * 从上到下，顺序分割要填充的数据
         */
        // 标题
        String[] arr = data.split(key_exportNoteTemp_title, 2);
        if (arr.length == 2) {
            exportNoteTempList.add(arr[0]);
            exportNoteTempList.add(key_exportNoteTemp_title);
            data = arr[1];
        }
        // 标题
        arr = data.split(key_exportNoteTemp_title, 2);
        if (arr.length == 2) {
            exportNoteTempList.add(arr[0]);
            exportNoteTempList.add(key_exportNoteTemp_title);
            data = arr[1];
        }
        // 内容
        arr = data.split(key_exportNoteTemp_content, 2);
        if (arr.length == 2) {
            exportNoteTempList.add(arr[0]);
            exportNoteTempList.add(key_exportNoteTemp_content);
            data = arr[1];
        }
        exportNoteTempList.add(data);
    }
}
