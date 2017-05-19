package com.zoufanqi.consts;

/**
 * Created by vanki on 16/10/22.
 */
public class ConstDB {
    public static int ISDEL_FALSE = 0;
    public static int ISDEL_TRUE = 1;

    public static long DEFAULT_PARENT_ID = -1;   // 默认父节点ID
    public static int FIRST_SEQUENCE = 0;       // 序号起始

    public static final class Note {

        public static final int TYPE_NORMAL = 1;    // 笔记内容类型, 普通
        public static final int TYPE_MARKDOWN = 2;  // 笔记内容类型, Markdown
        public static final int SECRET_OPEN = 0;    // 私密, 公开

        public static final int SECRET_PWD = 1;     // 私密, 公开但需要密码
        public static final int SECRET_CLOSE = 2;   // 私密, 不公开
        public static final int IS_OPEN_NO = 0;     // 笔记目录是否展开，否

        public static final int IS_OPEN_YES = 1;    // 笔记目录是否展开，是
        public static final int STATUS_CHECKING = -1;   // 审核状态，待审核

        public static final int STATUS_NO_PASS = 0;     // 审核状态，未通过
        public static final int STATUS_PASS = 1;        // 审核状态，已通过
    }

    public static final class NoteDetail {

        public static final int TYPE_NORMAL = 1;    // 笔记内容类型, 普通
        public static final int TYPE_MARKDOWN = 2;  // 笔记内容类型, Markdown
    }

    public static final class User {
        public static final int SEX_DEFAULT = 0;    // 性别, 默认
        public static final int SEX_MALE = 1;       // 性别, 男
        public static final int SEX_FEMALE = 2;     // 性别, 女
    }

    public static final class UserLoginRecord {
        public static final int ORIGIN_NONE = 1;    // 登录帐号归属，无
        public static final int ORIGIN_WEIXIN = 2;  // 登录帐号归属，微信
        public static final int ORIGIN_QQ = 3;      // 登录帐号归属，无
        public static final int ORIGIN_WEIBO = 4;   // 登录帐号归属，无
    }
}
