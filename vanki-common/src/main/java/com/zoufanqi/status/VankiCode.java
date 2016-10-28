package com.zoufanqi.status;

import com.alibaba.druid.support.spring.stat.annotation.Stat;

/**
 * Created by vanki on 16/10/28.
 */
public class VankiCode {
    public static final class Tree {
        public static final StatusCode NOT_FOLDER = new StatusCode(20001, "不是文件夹, 不能添加文件");
        public static final StatusCode ROOT_MUST_FOLDER = new StatusCode(20002, "根节点必须为文件夹");
    }
}
