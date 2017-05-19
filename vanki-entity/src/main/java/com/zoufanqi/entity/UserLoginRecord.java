package com.zoufanqi.entity;

import com.zoufanqi.utils.RemoteRequestUtil;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

public class UserLoginRecord {
    private Long id;

    private Long userId;

    private Integer origin;

    private Date createDatetime;

    private String ip;

    private String protocol;

    private String scheme;

    private String serverName;

    private String remoteAddr;

    private String remoteHost;

    private String characterEncoding;

    private String accept;

    private String acceptEncoding;

    private String acceptLanguage;

    private String userAgent;

    private String connection;

    public static UserLoginRecord build(Long userId, Integer origin, HttpServletRequest request) {
        if (userId == null || origin == null || request == null) return null;

        UserLoginRecord record = new UserLoginRecord();

        record.setUserId(userId);
        record.setOrigin(origin);
        record.setCreateDatetime(new Date());
        record.setIp(RemoteRequestUtil.getRequestIP(request));
        record.setProtocol(request.getProtocol());
        record.setScheme(request.getScheme());
        record.setServerName(request.getServerName());
        record.setRemoteAddr(request.getRemoteAddr());
        record.setRemoteHost(request.getRemoteHost());
        record.setCharacterEncoding(request.getCharacterEncoding());
        record.setAccept(request.getHeader("Accept"));
        record.setAcceptLanguage(request.getHeader("Accept-Language"));
        record.setAcceptEncoding(request.getHeader("Accept-Encoding"));
        record.setUserAgent(request.getHeader("User-Agent"));
        record.setConnection(request.getHeader("Connection"));

        return record;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Integer getOrigin() {
        return origin;
    }

    public void setOrigin(Integer origin) {
        this.origin = origin;
    }

    public Date getCreateDatetime() {
        return createDatetime;
    }

    public void setCreateDatetime(Date createDatetime) {
        this.createDatetime = createDatetime;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getProtocol() {
        return protocol;
    }

    public void setProtocol(String protocol) {
        this.protocol = protocol;
    }

    public String getScheme() {
        return scheme;
    }

    public void setScheme(String scheme) {
        this.scheme = scheme;
    }

    public String getServerName() {
        return serverName;
    }

    public void setServerName(String serverName) {
        this.serverName = serverName;
    }

    public String getRemoteAddr() {
        return remoteAddr;
    }

    public void setRemoteAddr(String remoteAddr) {
        this.remoteAddr = remoteAddr;
    }

    public String getRemoteHost() {
        return remoteHost;
    }

    public void setRemoteHost(String remoteHost) {
        this.remoteHost = remoteHost;
    }

    public String getCharacterEncoding() {
        return characterEncoding;
    }

    public void setCharacterEncoding(String characterEncoding) {
        this.characterEncoding = characterEncoding;
    }

    public String getAccept() {
        return accept;
    }

    public void setAccept(String accept) {
        this.accept = accept;
    }

    public String getAcceptEncoding() {
        return acceptEncoding;
    }

    public void setAcceptEncoding(String acceptEncoding) {
        this.acceptEncoding = acceptEncoding;
    }

    public String getAcceptLanguage() {
        return acceptLanguage;
    }

    public void setAcceptLanguage(String acceptLanguage) {
        this.acceptLanguage = acceptLanguage;
    }

    public String getUserAgent() {
        return userAgent;
    }

    public void setUserAgent(String userAgent) {
        this.userAgent = userAgent;
    }

    public String getConnection() {
        return connection;
    }

    public void setConnection(String connection) {
        this.connection = connection;
    }
}