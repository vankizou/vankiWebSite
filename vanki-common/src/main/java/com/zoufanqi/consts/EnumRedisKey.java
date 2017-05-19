package com.zoufanqi.consts;

/**
 * Created by vanki on 17/1/21.
 */
public enum EnumRedisKey {
    MAP_USER_ID_TOKEN,  // 用户ID -> TOKEN
    MAP_TOKEN_USER_ID,  // TOKEN -> 用户ID

    TIME_NOTE_(60 * 60 * 2),            // 单个笔记信息，#_noteId -> Note
    TIME_NOTE_DETAIL_LIST_(60 * 60 * 2),     // 单个笔记详情，#_noteId -> List<NoteDetail>
    TIME_NOTE_PAGE_TREE_(60 * 60 * 2),   // 笔记树分页信息，#_userId: {parentId_pageNo_pageSize_navNum -> Page<Note>}
    TIME_NOTE_PAGE_HOME(60 * 60 * 2),    // 主页笔记分页信息，{pageNo_pageSize_navNum -> Page<Note>}
    S_OPENED_NOTE_ID_,  // 用户打开了的笔记树目录，#_userId -> [noteId]
    MAP_NOTE_VIEW_NUM,  // 笔记浏览量，noteId -> viewNum

    ;
    private int time;

    EnumRedisKey() {
    }

    EnumRedisKey(int time) {
        this.time = time;
    }

    public int getTime() {
        return time;
    }
}
