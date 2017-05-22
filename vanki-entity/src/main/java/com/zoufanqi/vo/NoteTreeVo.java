package com.zoufanqi.vo;

import com.zoufanqi.entity.Note;

import java.util.List;

/**
 * Created by vanki on 2017/4/10.
 */
public class NoteTreeVo {
    private Note note;
    private List<NoteTreeVo> subNoteVoList;

    public Note getNote() {
        return note;
    }

    public void setNote(Note note) {
        this.note = note;
    }

    public List<NoteTreeVo> getSubNoteVoList() {
        return subNoteVoList;
    }

    public void setSubNoteVoList(List<NoteTreeVo> subNoteVoList) {
        this.subNoteVoList = subNoteVoList;
    }
}
