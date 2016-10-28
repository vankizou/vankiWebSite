package com.zoufanqi.consts;

/**
 * Created by vanki on 16/10/22.
 */
public class ConstDB {
    public static int ISDEL_FALSE = 0;
    public static int ISDEL_TRUE = 1;

    public static long DEFAULT_PARENT_ID = -1L;   // 默认父节点ID

    public static final class User {
        public static final int SEX_DEFAULT = 0;    // 性别, 默认
        public static final int SEX_MALE = 1;       // 性别, 男
        public static final int SEX_FEMALE = 2;     // 性别, 女
    }

    public static final class Tree {
        public static final int TYPE_NOTE = 1;  // 内容类型, 笔记

        public static final int IS_PUBLIC_NO = 0;   // 是否公开, 否
        public static final int IS_PUBLIC_YES = 1;  // 是否公开, 是

        public static final int IS_OPEN_NO = 0;     // 是否已打开, 否
        public static final int IS_OPEN_YES = 1;    // 是否已打开, 是
    }

    public static final class Note {
        public static final int STATUS_NO_PASS = 0; // 审核, 不通过
        public static final int STATUS_PASS = 1;    // 审核, 通过

        public static final int IS_PUBLIC_NO = 0;   // 是否公开, 否
        public static final int IS_PUBLIC_YES = 1;  // 是否公开, 是
    }

    public static final class NoteContent {
        public static final int TYPE_TXT = 1;      // 笔记内容类型, 文字
    }
}
