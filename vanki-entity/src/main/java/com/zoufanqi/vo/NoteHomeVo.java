package com.zoufanqi.vo;

import com.zoufanqi.entity.Note;
import com.zoufanqi.entity.User;

/**
 * Created by vanki on 2017/5/18.
 */
public class NoteHomeVo {
    private User user;
    private Note parentNote;
    private Note note;

    public Note getParentNote() {
        return parentNote;
    }

    public void setParentNote(Note parentNote) {
        this.parentNote = parentNote;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Note getNote() {
        return note;
    }

    public void setNote(Note note) {
        this.note = note;
    }
}
