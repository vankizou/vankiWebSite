package com.zoufanqi.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.zoufanqi.serialize.DateSerializer;

import java.util.Date;

public class Note {
    private Long id;

    private Long parentId;

    private Long userId;

    private Integer type;

    private Integer countNote;

    private Integer countNoteContent;

    private Integer secret;

    private String password;

    private String title;

    private String keyword;

    private Integer sequence;

    private String description;

    @JsonSerialize(using = DateSerializer.class)
    private Date updateDatetime;

    @JsonSerialize(using = DateSerializer.class)
    private Date createDatetime;

    private Long viewNum;

    private String digest;

    private String author;

    private String originUrl;

    private Integer status;

    private String statusDescription;

    @JsonIgnore
    private Integer isDel;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getCountNote() {
        return countNote;
    }

    public void setCountNote(Integer countNote) {
        this.countNote = countNote;
    }

    public Integer getCountNoteContent() {
        return countNoteContent;
    }

    public void setCountNoteContent(Integer countNoteContent) {
        this.countNoteContent = countNoteContent;
    }

    public Integer getSecret() {
        return secret;
    }

    public void setSecret(Integer secret) {
        this.secret = secret;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public Integer getSequence() {
        return sequence;
    }

    public void setSequence(Integer sequence) {
        this.sequence = sequence;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getUpdateDatetime() {
        return updateDatetime;
    }

    public void setUpdateDatetime(Date updateDatetime) {
        this.updateDatetime = updateDatetime;
    }

    public Date getCreateDatetime() {
        return createDatetime;
    }

    public void setCreateDatetime(Date createDatetime) {
        this.createDatetime = createDatetime;
    }

    public Long getViewNum() {
        return viewNum;
    }

    public void setViewNum(Long viewNum) {
        this.viewNum = viewNum;
    }

    public String getDigest() {
        return digest;
    }

    public void setDigest(String digest) {
        this.digest = digest;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getOriginUrl() {
        return originUrl;
    }

    public void setOriginUrl(String originUrl) {
        this.originUrl = originUrl;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getStatusDescription() {
        return statusDescription;
    }

    public void setStatusDescription(String statusDescription) {
        this.statusDescription = statusDescription;
    }

    public Integer getIsDel() {
        return isDel;
    }

    public void setIsDel(Integer isDel) {
        this.isDel = isDel;
    }
}