package com.zoufanqi.vo;

import com.zoufanqi.entity.Note;
import com.zoufanqi.entity.NoteDetail;
import com.zoufanqi.entity.User;

import java.util.List;

/**
 * Created by vanki on 2017/4/10.
 */
public class NoteVo {
    private Note note;
    private List<NoteDetail> noteDetailList;
    private List<NoteVo> subNoteVoList;
    private Integer isNeedPwd;

    /**
     * 详情页
     */
    private String updateDatetimeStr;
    private Note parentNote;
    private User user;

    public String getUpdateDatetimeStr() {
        return updateDatetimeStr;
    }

    public void setUpdateDatetimeStr(String updateDatetimeStr) {
        this.updateDatetimeStr = updateDatetimeStr;
    }

    public Note getNote() {
        return note;
    }

    public void setNote(Note note) {
        this.note = note;
    }

    public Note getParentNote() {
        return parentNote;
    }

    public void setParentNote(Note parentNote) {
        this.parentNote = parentNote;
    }

    public List<NoteDetail> getNoteDetailList() {
        return noteDetailList;
    }

    public void setNoteDetailList(List<NoteDetail> noteDetailList) {
        this.noteDetailList = noteDetailList;
    }

    public List<NoteVo> getSubNoteVoList() {
        return subNoteVoList;
    }

    public void setSubNoteVoList(List<NoteVo> subNoteVoList) {
        this.subNoteVoList = subNoteVoList;
    }

    public Integer getIsNeedPwd() {
        return isNeedPwd;
    }

    public void setIsNeedPwd(Integer isNeedPwd) {
        this.isNeedPwd = isNeedPwd;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
