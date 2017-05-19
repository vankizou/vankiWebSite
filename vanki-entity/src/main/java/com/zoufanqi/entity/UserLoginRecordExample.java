package com.zoufanqi.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class UserLoginRecordExample {
    protected String orderByClause;

    protected Integer offset;

    protected Integer rows;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public UserLoginRecordExample() {
        oredCriteria = new ArrayList<Criteria>();
    }

    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    public String getOrderByClause() {
        return orderByClause;
    }

    public void setOffset(Integer offset) {
        this.offset = offset;
    }

    public Integer getOffset() {
        return offset;
    }

    public void setRows(Integer rows) {
        this.rows = rows;
    }

    public Integer getRows() {
        return rows;
    }

    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    public boolean isDistinct() {
        return distinct;
    }

    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    public Criteria createCriteria() {
        Criteria criteria = createCriteriaInternal();
        if (oredCriteria.size() == 0) {
            oredCriteria.add(criteria);
        }
        return criteria;
    }

    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
    }

    protected abstract static class GeneratedCriteria {
        protected List<Criterion> criteria;

        protected GeneratedCriteria() {
            super();
            criteria = new ArrayList<Criterion>();
        }

        public boolean isValid() {
            return criteria.size() > 0;
        }

        public List<Criterion> getAllCriteria() {
            return criteria;
        }

        public List<Criterion> getCriteria() {
            return criteria;
        }

        protected void addCriterion(String condition) {
            if (condition == null) {
                throw new RuntimeException("Value for condition cannot be null");
            }
            criteria.add(new Criterion(condition));
        }

        protected void addCriterion(String condition, Object value, String property) {
            if (value == null) {
                throw new RuntimeException("Value for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value));
        }

        protected void addCriterion(String condition, Object value1, Object value2, String property) {
            if (value1 == null || value2 == null) {
                throw new RuntimeException("Between values for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value1, value2));
        }

        public Criteria andIdIsNull() {
            addCriterion("ID is null");
            return (Criteria) this;
        }

        public Criteria andIdIsNotNull() {
            addCriterion("ID is not null");
            return (Criteria) this;
        }

        public Criteria andIdEqualTo(Long value) {
            addCriterion("ID =", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotEqualTo(Long value) {
            addCriterion("ID <>", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThan(Long value) {
            addCriterion("ID >", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThanOrEqualTo(Long value) {
            addCriterion("ID >=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThan(Long value) {
            addCriterion("ID <", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThanOrEqualTo(Long value) {
            addCriterion("ID <=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdIn(List<Long> values) {
            addCriterion("ID in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotIn(List<Long> values) {
            addCriterion("ID not in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdBetween(Long value1, Long value2) {
            addCriterion("ID between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotBetween(Long value1, Long value2) {
            addCriterion("ID not between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andUserIdIsNull() {
            addCriterion("USER_ID is null");
            return (Criteria) this;
        }

        public Criteria andUserIdIsNotNull() {
            addCriterion("USER_ID is not null");
            return (Criteria) this;
        }

        public Criteria andUserIdEqualTo(Long value) {
            addCriterion("USER_ID =", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdNotEqualTo(Long value) {
            addCriterion("USER_ID <>", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdGreaterThan(Long value) {
            addCriterion("USER_ID >", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdGreaterThanOrEqualTo(Long value) {
            addCriterion("USER_ID >=", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdLessThan(Long value) {
            addCriterion("USER_ID <", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdLessThanOrEqualTo(Long value) {
            addCriterion("USER_ID <=", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdIn(List<Long> values) {
            addCriterion("USER_ID in", values, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdNotIn(List<Long> values) {
            addCriterion("USER_ID not in", values, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdBetween(Long value1, Long value2) {
            addCriterion("USER_ID between", value1, value2, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdNotBetween(Long value1, Long value2) {
            addCriterion("USER_ID not between", value1, value2, "userId");
            return (Criteria) this;
        }

        public Criteria andOriginIsNull() {
            addCriterion("ORIGIN is null");
            return (Criteria) this;
        }

        public Criteria andOriginIsNotNull() {
            addCriterion("ORIGIN is not null");
            return (Criteria) this;
        }

        public Criteria andOriginEqualTo(Integer value) {
            addCriterion("ORIGIN =", value, "origin");
            return (Criteria) this;
        }

        public Criteria andOriginNotEqualTo(Integer value) {
            addCriterion("ORIGIN <>", value, "origin");
            return (Criteria) this;
        }

        public Criteria andOriginGreaterThan(Integer value) {
            addCriterion("ORIGIN >", value, "origin");
            return (Criteria) this;
        }

        public Criteria andOriginGreaterThanOrEqualTo(Integer value) {
            addCriterion("ORIGIN >=", value, "origin");
            return (Criteria) this;
        }

        public Criteria andOriginLessThan(Integer value) {
            addCriterion("ORIGIN <", value, "origin");
            return (Criteria) this;
        }

        public Criteria andOriginLessThanOrEqualTo(Integer value) {
            addCriterion("ORIGIN <=", value, "origin");
            return (Criteria) this;
        }

        public Criteria andOriginIn(List<Integer> values) {
            addCriterion("ORIGIN in", values, "origin");
            return (Criteria) this;
        }

        public Criteria andOriginNotIn(List<Integer> values) {
            addCriterion("ORIGIN not in", values, "origin");
            return (Criteria) this;
        }

        public Criteria andOriginBetween(Integer value1, Integer value2) {
            addCriterion("ORIGIN between", value1, value2, "origin");
            return (Criteria) this;
        }

        public Criteria andOriginNotBetween(Integer value1, Integer value2) {
            addCriterion("ORIGIN not between", value1, value2, "origin");
            return (Criteria) this;
        }

        public Criteria andCreateDatetimeIsNull() {
            addCriterion("CREATE_DATETIME is null");
            return (Criteria) this;
        }

        public Criteria andCreateDatetimeIsNotNull() {
            addCriterion("CREATE_DATETIME is not null");
            return (Criteria) this;
        }

        public Criteria andCreateDatetimeEqualTo(Date value) {
            addCriterion("CREATE_DATETIME =", value, "createDatetime");
            return (Criteria) this;
        }

        public Criteria andCreateDatetimeNotEqualTo(Date value) {
            addCriterion("CREATE_DATETIME <>", value, "createDatetime");
            return (Criteria) this;
        }

        public Criteria andCreateDatetimeGreaterThan(Date value) {
            addCriterion("CREATE_DATETIME >", value, "createDatetime");
            return (Criteria) this;
        }

        public Criteria andCreateDatetimeGreaterThanOrEqualTo(Date value) {
            addCriterion("CREATE_DATETIME >=", value, "createDatetime");
            return (Criteria) this;
        }

        public Criteria andCreateDatetimeLessThan(Date value) {
            addCriterion("CREATE_DATETIME <", value, "createDatetime");
            return (Criteria) this;
        }

        public Criteria andCreateDatetimeLessThanOrEqualTo(Date value) {
            addCriterion("CREATE_DATETIME <=", value, "createDatetime");
            return (Criteria) this;
        }

        public Criteria andCreateDatetimeIn(List<Date> values) {
            addCriterion("CREATE_DATETIME in", values, "createDatetime");
            return (Criteria) this;
        }

        public Criteria andCreateDatetimeNotIn(List<Date> values) {
            addCriterion("CREATE_DATETIME not in", values, "createDatetime");
            return (Criteria) this;
        }

        public Criteria andCreateDatetimeBetween(Date value1, Date value2) {
            addCriterion("CREATE_DATETIME between", value1, value2, "createDatetime");
            return (Criteria) this;
        }

        public Criteria andCreateDatetimeNotBetween(Date value1, Date value2) {
            addCriterion("CREATE_DATETIME not between", value1, value2, "createDatetime");
            return (Criteria) this;
        }

        public Criteria andIpIsNull() {
            addCriterion("IP is null");
            return (Criteria) this;
        }

        public Criteria andIpIsNotNull() {
            addCriterion("IP is not null");
            return (Criteria) this;
        }

        public Criteria andIpEqualTo(String value) {
            addCriterion("IP =", value, "ip");
            return (Criteria) this;
        }

        public Criteria andIpNotEqualTo(String value) {
            addCriterion("IP <>", value, "ip");
            return (Criteria) this;
        }

        public Criteria andIpGreaterThan(String value) {
            addCriterion("IP >", value, "ip");
            return (Criteria) this;
        }

        public Criteria andIpGreaterThanOrEqualTo(String value) {
            addCriterion("IP >=", value, "ip");
            return (Criteria) this;
        }

        public Criteria andIpLessThan(String value) {
            addCriterion("IP <", value, "ip");
            return (Criteria) this;
        }

        public Criteria andIpLessThanOrEqualTo(String value) {
            addCriterion("IP <=", value, "ip");
            return (Criteria) this;
        }

        public Criteria andIpLike(String value) {
            addCriterion("IP like", value, "ip");
            return (Criteria) this;
        }

        public Criteria andIpNotLike(String value) {
            addCriterion("IP not like", value, "ip");
            return (Criteria) this;
        }

        public Criteria andIpIn(List<String> values) {
            addCriterion("IP in", values, "ip");
            return (Criteria) this;
        }

        public Criteria andIpNotIn(List<String> values) {
            addCriterion("IP not in", values, "ip");
            return (Criteria) this;
        }

        public Criteria andIpBetween(String value1, String value2) {
            addCriterion("IP between", value1, value2, "ip");
            return (Criteria) this;
        }

        public Criteria andIpNotBetween(String value1, String value2) {
            addCriterion("IP not between", value1, value2, "ip");
            return (Criteria) this;
        }

        public Criteria andProtocolIsNull() {
            addCriterion("PROTOCOL is null");
            return (Criteria) this;
        }

        public Criteria andProtocolIsNotNull() {
            addCriterion("PROTOCOL is not null");
            return (Criteria) this;
        }

        public Criteria andProtocolEqualTo(String value) {
            addCriterion("PROTOCOL =", value, "protocol");
            return (Criteria) this;
        }

        public Criteria andProtocolNotEqualTo(String value) {
            addCriterion("PROTOCOL <>", value, "protocol");
            return (Criteria) this;
        }

        public Criteria andProtocolGreaterThan(String value) {
            addCriterion("PROTOCOL >", value, "protocol");
            return (Criteria) this;
        }

        public Criteria andProtocolGreaterThanOrEqualTo(String value) {
            addCriterion("PROTOCOL >=", value, "protocol");
            return (Criteria) this;
        }

        public Criteria andProtocolLessThan(String value) {
            addCriterion("PROTOCOL <", value, "protocol");
            return (Criteria) this;
        }

        public Criteria andProtocolLessThanOrEqualTo(String value) {
            addCriterion("PROTOCOL <=", value, "protocol");
            return (Criteria) this;
        }

        public Criteria andProtocolLike(String value) {
            addCriterion("PROTOCOL like", value, "protocol");
            return (Criteria) this;
        }

        public Criteria andProtocolNotLike(String value) {
            addCriterion("PROTOCOL not like", value, "protocol");
            return (Criteria) this;
        }

        public Criteria andProtocolIn(List<String> values) {
            addCriterion("PROTOCOL in", values, "protocol");
            return (Criteria) this;
        }

        public Criteria andProtocolNotIn(List<String> values) {
            addCriterion("PROTOCOL not in", values, "protocol");
            return (Criteria) this;
        }

        public Criteria andProtocolBetween(String value1, String value2) {
            addCriterion("PROTOCOL between", value1, value2, "protocol");
            return (Criteria) this;
        }

        public Criteria andProtocolNotBetween(String value1, String value2) {
            addCriterion("PROTOCOL not between", value1, value2, "protocol");
            return (Criteria) this;
        }

        public Criteria andSchemeIsNull() {
            addCriterion("SCHEME is null");
            return (Criteria) this;
        }

        public Criteria andSchemeIsNotNull() {
            addCriterion("SCHEME is not null");
            return (Criteria) this;
        }

        public Criteria andSchemeEqualTo(String value) {
            addCriterion("SCHEME =", value, "scheme");
            return (Criteria) this;
        }

        public Criteria andSchemeNotEqualTo(String value) {
            addCriterion("SCHEME <>", value, "scheme");
            return (Criteria) this;
        }

        public Criteria andSchemeGreaterThan(String value) {
            addCriterion("SCHEME >", value, "scheme");
            return (Criteria) this;
        }

        public Criteria andSchemeGreaterThanOrEqualTo(String value) {
            addCriterion("SCHEME >=", value, "scheme");
            return (Criteria) this;
        }

        public Criteria andSchemeLessThan(String value) {
            addCriterion("SCHEME <", value, "scheme");
            return (Criteria) this;
        }

        public Criteria andSchemeLessThanOrEqualTo(String value) {
            addCriterion("SCHEME <=", value, "scheme");
            return (Criteria) this;
        }

        public Criteria andSchemeLike(String value) {
            addCriterion("SCHEME like", value, "scheme");
            return (Criteria) this;
        }

        public Criteria andSchemeNotLike(String value) {
            addCriterion("SCHEME not like", value, "scheme");
            return (Criteria) this;
        }

        public Criteria andSchemeIn(List<String> values) {
            addCriterion("SCHEME in", values, "scheme");
            return (Criteria) this;
        }

        public Criteria andSchemeNotIn(List<String> values) {
            addCriterion("SCHEME not in", values, "scheme");
            return (Criteria) this;
        }

        public Criteria andSchemeBetween(String value1, String value2) {
            addCriterion("SCHEME between", value1, value2, "scheme");
            return (Criteria) this;
        }

        public Criteria andSchemeNotBetween(String value1, String value2) {
            addCriterion("SCHEME not between", value1, value2, "scheme");
            return (Criteria) this;
        }

        public Criteria andServerNameIsNull() {
            addCriterion("SERVER_NAME is null");
            return (Criteria) this;
        }

        public Criteria andServerNameIsNotNull() {
            addCriterion("SERVER_NAME is not null");
            return (Criteria) this;
        }

        public Criteria andServerNameEqualTo(String value) {
            addCriterion("SERVER_NAME =", value, "serverName");
            return (Criteria) this;
        }

        public Criteria andServerNameNotEqualTo(String value) {
            addCriterion("SERVER_NAME <>", value, "serverName");
            return (Criteria) this;
        }

        public Criteria andServerNameGreaterThan(String value) {
            addCriterion("SERVER_NAME >", value, "serverName");
            return (Criteria) this;
        }

        public Criteria andServerNameGreaterThanOrEqualTo(String value) {
            addCriterion("SERVER_NAME >=", value, "serverName");
            return (Criteria) this;
        }

        public Criteria andServerNameLessThan(String value) {
            addCriterion("SERVER_NAME <", value, "serverName");
            return (Criteria) this;
        }

        public Criteria andServerNameLessThanOrEqualTo(String value) {
            addCriterion("SERVER_NAME <=", value, "serverName");
            return (Criteria) this;
        }

        public Criteria andServerNameLike(String value) {
            addCriterion("SERVER_NAME like", value, "serverName");
            return (Criteria) this;
        }

        public Criteria andServerNameNotLike(String value) {
            addCriterion("SERVER_NAME not like", value, "serverName");
            return (Criteria) this;
        }

        public Criteria andServerNameIn(List<String> values) {
            addCriterion("SERVER_NAME in", values, "serverName");
            return (Criteria) this;
        }

        public Criteria andServerNameNotIn(List<String> values) {
            addCriterion("SERVER_NAME not in", values, "serverName");
            return (Criteria) this;
        }

        public Criteria andServerNameBetween(String value1, String value2) {
            addCriterion("SERVER_NAME between", value1, value2, "serverName");
            return (Criteria) this;
        }

        public Criteria andServerNameNotBetween(String value1, String value2) {
            addCriterion("SERVER_NAME not between", value1, value2, "serverName");
            return (Criteria) this;
        }

        public Criteria andRemoteAddrIsNull() {
            addCriterion("REMOTE_ADDR is null");
            return (Criteria) this;
        }

        public Criteria andRemoteAddrIsNotNull() {
            addCriterion("REMOTE_ADDR is not null");
            return (Criteria) this;
        }

        public Criteria andRemoteAddrEqualTo(String value) {
            addCriterion("REMOTE_ADDR =", value, "remoteAddr");
            return (Criteria) this;
        }

        public Criteria andRemoteAddrNotEqualTo(String value) {
            addCriterion("REMOTE_ADDR <>", value, "remoteAddr");
            return (Criteria) this;
        }

        public Criteria andRemoteAddrGreaterThan(String value) {
            addCriterion("REMOTE_ADDR >", value, "remoteAddr");
            return (Criteria) this;
        }

        public Criteria andRemoteAddrGreaterThanOrEqualTo(String value) {
            addCriterion("REMOTE_ADDR >=", value, "remoteAddr");
            return (Criteria) this;
        }

        public Criteria andRemoteAddrLessThan(String value) {
            addCriterion("REMOTE_ADDR <", value, "remoteAddr");
            return (Criteria) this;
        }

        public Criteria andRemoteAddrLessThanOrEqualTo(String value) {
            addCriterion("REMOTE_ADDR <=", value, "remoteAddr");
            return (Criteria) this;
        }

        public Criteria andRemoteAddrLike(String value) {
            addCriterion("REMOTE_ADDR like", value, "remoteAddr");
            return (Criteria) this;
        }

        public Criteria andRemoteAddrNotLike(String value) {
            addCriterion("REMOTE_ADDR not like", value, "remoteAddr");
            return (Criteria) this;
        }

        public Criteria andRemoteAddrIn(List<String> values) {
            addCriterion("REMOTE_ADDR in", values, "remoteAddr");
            return (Criteria) this;
        }

        public Criteria andRemoteAddrNotIn(List<String> values) {
            addCriterion("REMOTE_ADDR not in", values, "remoteAddr");
            return (Criteria) this;
        }

        public Criteria andRemoteAddrBetween(String value1, String value2) {
            addCriterion("REMOTE_ADDR between", value1, value2, "remoteAddr");
            return (Criteria) this;
        }

        public Criteria andRemoteAddrNotBetween(String value1, String value2) {
            addCriterion("REMOTE_ADDR not between", value1, value2, "remoteAddr");
            return (Criteria) this;
        }

        public Criteria andRemoteHostIsNull() {
            addCriterion("REMOTE_HOST is null");
            return (Criteria) this;
        }

        public Criteria andRemoteHostIsNotNull() {
            addCriterion("REMOTE_HOST is not null");
            return (Criteria) this;
        }

        public Criteria andRemoteHostEqualTo(String value) {
            addCriterion("REMOTE_HOST =", value, "remoteHost");
            return (Criteria) this;
        }

        public Criteria andRemoteHostNotEqualTo(String value) {
            addCriterion("REMOTE_HOST <>", value, "remoteHost");
            return (Criteria) this;
        }

        public Criteria andRemoteHostGreaterThan(String value) {
            addCriterion("REMOTE_HOST >", value, "remoteHost");
            return (Criteria) this;
        }

        public Criteria andRemoteHostGreaterThanOrEqualTo(String value) {
            addCriterion("REMOTE_HOST >=", value, "remoteHost");
            return (Criteria) this;
        }

        public Criteria andRemoteHostLessThan(String value) {
            addCriterion("REMOTE_HOST <", value, "remoteHost");
            return (Criteria) this;
        }

        public Criteria andRemoteHostLessThanOrEqualTo(String value) {
            addCriterion("REMOTE_HOST <=", value, "remoteHost");
            return (Criteria) this;
        }

        public Criteria andRemoteHostLike(String value) {
            addCriterion("REMOTE_HOST like", value, "remoteHost");
            return (Criteria) this;
        }

        public Criteria andRemoteHostNotLike(String value) {
            addCriterion("REMOTE_HOST not like", value, "remoteHost");
            return (Criteria) this;
        }

        public Criteria andRemoteHostIn(List<String> values) {
            addCriterion("REMOTE_HOST in", values, "remoteHost");
            return (Criteria) this;
        }

        public Criteria andRemoteHostNotIn(List<String> values) {
            addCriterion("REMOTE_HOST not in", values, "remoteHost");
            return (Criteria) this;
        }

        public Criteria andRemoteHostBetween(String value1, String value2) {
            addCriterion("REMOTE_HOST between", value1, value2, "remoteHost");
            return (Criteria) this;
        }

        public Criteria andRemoteHostNotBetween(String value1, String value2) {
            addCriterion("REMOTE_HOST not between", value1, value2, "remoteHost");
            return (Criteria) this;
        }

        public Criteria andCharacterEncodingIsNull() {
            addCriterion("CHARACTER_ENCODING is null");
            return (Criteria) this;
        }

        public Criteria andCharacterEncodingIsNotNull() {
            addCriterion("CHARACTER_ENCODING is not null");
            return (Criteria) this;
        }

        public Criteria andCharacterEncodingEqualTo(String value) {
            addCriterion("CHARACTER_ENCODING =", value, "characterEncoding");
            return (Criteria) this;
        }

        public Criteria andCharacterEncodingNotEqualTo(String value) {
            addCriterion("CHARACTER_ENCODING <>", value, "characterEncoding");
            return (Criteria) this;
        }

        public Criteria andCharacterEncodingGreaterThan(String value) {
            addCriterion("CHARACTER_ENCODING >", value, "characterEncoding");
            return (Criteria) this;
        }

        public Criteria andCharacterEncodingGreaterThanOrEqualTo(String value) {
            addCriterion("CHARACTER_ENCODING >=", value, "characterEncoding");
            return (Criteria) this;
        }

        public Criteria andCharacterEncodingLessThan(String value) {
            addCriterion("CHARACTER_ENCODING <", value, "characterEncoding");
            return (Criteria) this;
        }

        public Criteria andCharacterEncodingLessThanOrEqualTo(String value) {
            addCriterion("CHARACTER_ENCODING <=", value, "characterEncoding");
            return (Criteria) this;
        }

        public Criteria andCharacterEncodingLike(String value) {
            addCriterion("CHARACTER_ENCODING like", value, "characterEncoding");
            return (Criteria) this;
        }

        public Criteria andCharacterEncodingNotLike(String value) {
            addCriterion("CHARACTER_ENCODING not like", value, "characterEncoding");
            return (Criteria) this;
        }

        public Criteria andCharacterEncodingIn(List<String> values) {
            addCriterion("CHARACTER_ENCODING in", values, "characterEncoding");
            return (Criteria) this;
        }

        public Criteria andCharacterEncodingNotIn(List<String> values) {
            addCriterion("CHARACTER_ENCODING not in", values, "characterEncoding");
            return (Criteria) this;
        }

        public Criteria andCharacterEncodingBetween(String value1, String value2) {
            addCriterion("CHARACTER_ENCODING between", value1, value2, "characterEncoding");
            return (Criteria) this;
        }

        public Criteria andCharacterEncodingNotBetween(String value1, String value2) {
            addCriterion("CHARACTER_ENCODING not between", value1, value2, "characterEncoding");
            return (Criteria) this;
        }

        public Criteria andAcceptIsNull() {
            addCriterion("ACCEPT is null");
            return (Criteria) this;
        }

        public Criteria andAcceptIsNotNull() {
            addCriterion("ACCEPT is not null");
            return (Criteria) this;
        }

        public Criteria andAcceptEqualTo(String value) {
            addCriterion("ACCEPT =", value, "accept");
            return (Criteria) this;
        }

        public Criteria andAcceptNotEqualTo(String value) {
            addCriterion("ACCEPT <>", value, "accept");
            return (Criteria) this;
        }

        public Criteria andAcceptGreaterThan(String value) {
            addCriterion("ACCEPT >", value, "accept");
            return (Criteria) this;
        }

        public Criteria andAcceptGreaterThanOrEqualTo(String value) {
            addCriterion("ACCEPT >=", value, "accept");
            return (Criteria) this;
        }

        public Criteria andAcceptLessThan(String value) {
            addCriterion("ACCEPT <", value, "accept");
            return (Criteria) this;
        }

        public Criteria andAcceptLessThanOrEqualTo(String value) {
            addCriterion("ACCEPT <=", value, "accept");
            return (Criteria) this;
        }

        public Criteria andAcceptLike(String value) {
            addCriterion("ACCEPT like", value, "accept");
            return (Criteria) this;
        }

        public Criteria andAcceptNotLike(String value) {
            addCriterion("ACCEPT not like", value, "accept");
            return (Criteria) this;
        }

        public Criteria andAcceptIn(List<String> values) {
            addCriterion("ACCEPT in", values, "accept");
            return (Criteria) this;
        }

        public Criteria andAcceptNotIn(List<String> values) {
            addCriterion("ACCEPT not in", values, "accept");
            return (Criteria) this;
        }

        public Criteria andAcceptBetween(String value1, String value2) {
            addCriterion("ACCEPT between", value1, value2, "accept");
            return (Criteria) this;
        }

        public Criteria andAcceptNotBetween(String value1, String value2) {
            addCriterion("ACCEPT not between", value1, value2, "accept");
            return (Criteria) this;
        }

        public Criteria andAcceptEncodingIsNull() {
            addCriterion("ACCEPT_ENCODING is null");
            return (Criteria) this;
        }

        public Criteria andAcceptEncodingIsNotNull() {
            addCriterion("ACCEPT_ENCODING is not null");
            return (Criteria) this;
        }

        public Criteria andAcceptEncodingEqualTo(String value) {
            addCriterion("ACCEPT_ENCODING =", value, "acceptEncoding");
            return (Criteria) this;
        }

        public Criteria andAcceptEncodingNotEqualTo(String value) {
            addCriterion("ACCEPT_ENCODING <>", value, "acceptEncoding");
            return (Criteria) this;
        }

        public Criteria andAcceptEncodingGreaterThan(String value) {
            addCriterion("ACCEPT_ENCODING >", value, "acceptEncoding");
            return (Criteria) this;
        }

        public Criteria andAcceptEncodingGreaterThanOrEqualTo(String value) {
            addCriterion("ACCEPT_ENCODING >=", value, "acceptEncoding");
            return (Criteria) this;
        }

        public Criteria andAcceptEncodingLessThan(String value) {
            addCriterion("ACCEPT_ENCODING <", value, "acceptEncoding");
            return (Criteria) this;
        }

        public Criteria andAcceptEncodingLessThanOrEqualTo(String value) {
            addCriterion("ACCEPT_ENCODING <=", value, "acceptEncoding");
            return (Criteria) this;
        }

        public Criteria andAcceptEncodingLike(String value) {
            addCriterion("ACCEPT_ENCODING like", value, "acceptEncoding");
            return (Criteria) this;
        }

        public Criteria andAcceptEncodingNotLike(String value) {
            addCriterion("ACCEPT_ENCODING not like", value, "acceptEncoding");
            return (Criteria) this;
        }

        public Criteria andAcceptEncodingIn(List<String> values) {
            addCriterion("ACCEPT_ENCODING in", values, "acceptEncoding");
            return (Criteria) this;
        }

        public Criteria andAcceptEncodingNotIn(List<String> values) {
            addCriterion("ACCEPT_ENCODING not in", values, "acceptEncoding");
            return (Criteria) this;
        }

        public Criteria andAcceptEncodingBetween(String value1, String value2) {
            addCriterion("ACCEPT_ENCODING between", value1, value2, "acceptEncoding");
            return (Criteria) this;
        }

        public Criteria andAcceptEncodingNotBetween(String value1, String value2) {
            addCriterion("ACCEPT_ENCODING not between", value1, value2, "acceptEncoding");
            return (Criteria) this;
        }

        public Criteria andAcceptLanguageIsNull() {
            addCriterion("ACCEPT_LANGUAGE is null");
            return (Criteria) this;
        }

        public Criteria andAcceptLanguageIsNotNull() {
            addCriterion("ACCEPT_LANGUAGE is not null");
            return (Criteria) this;
        }

        public Criteria andAcceptLanguageEqualTo(String value) {
            addCriterion("ACCEPT_LANGUAGE =", value, "acceptLanguage");
            return (Criteria) this;
        }

        public Criteria andAcceptLanguageNotEqualTo(String value) {
            addCriterion("ACCEPT_LANGUAGE <>", value, "acceptLanguage");
            return (Criteria) this;
        }

        public Criteria andAcceptLanguageGreaterThan(String value) {
            addCriterion("ACCEPT_LANGUAGE >", value, "acceptLanguage");
            return (Criteria) this;
        }

        public Criteria andAcceptLanguageGreaterThanOrEqualTo(String value) {
            addCriterion("ACCEPT_LANGUAGE >=", value, "acceptLanguage");
            return (Criteria) this;
        }

        public Criteria andAcceptLanguageLessThan(String value) {
            addCriterion("ACCEPT_LANGUAGE <", value, "acceptLanguage");
            return (Criteria) this;
        }

        public Criteria andAcceptLanguageLessThanOrEqualTo(String value) {
            addCriterion("ACCEPT_LANGUAGE <=", value, "acceptLanguage");
            return (Criteria) this;
        }

        public Criteria andAcceptLanguageLike(String value) {
            addCriterion("ACCEPT_LANGUAGE like", value, "acceptLanguage");
            return (Criteria) this;
        }

        public Criteria andAcceptLanguageNotLike(String value) {
            addCriterion("ACCEPT_LANGUAGE not like", value, "acceptLanguage");
            return (Criteria) this;
        }

        public Criteria andAcceptLanguageIn(List<String> values) {
            addCriterion("ACCEPT_LANGUAGE in", values, "acceptLanguage");
            return (Criteria) this;
        }

        public Criteria andAcceptLanguageNotIn(List<String> values) {
            addCriterion("ACCEPT_LANGUAGE not in", values, "acceptLanguage");
            return (Criteria) this;
        }

        public Criteria andAcceptLanguageBetween(String value1, String value2) {
            addCriterion("ACCEPT_LANGUAGE between", value1, value2, "acceptLanguage");
            return (Criteria) this;
        }

        public Criteria andAcceptLanguageNotBetween(String value1, String value2) {
            addCriterion("ACCEPT_LANGUAGE not between", value1, value2, "acceptLanguage");
            return (Criteria) this;
        }

        public Criteria andUserAgentIsNull() {
            addCriterion("USER_AGENT is null");
            return (Criteria) this;
        }

        public Criteria andUserAgentIsNotNull() {
            addCriterion("USER_AGENT is not null");
            return (Criteria) this;
        }

        public Criteria andUserAgentEqualTo(String value) {
            addCriterion("USER_AGENT =", value, "userAgent");
            return (Criteria) this;
        }

        public Criteria andUserAgentNotEqualTo(String value) {
            addCriterion("USER_AGENT <>", value, "userAgent");
            return (Criteria) this;
        }

        public Criteria andUserAgentGreaterThan(String value) {
            addCriterion("USER_AGENT >", value, "userAgent");
            return (Criteria) this;
        }

        public Criteria andUserAgentGreaterThanOrEqualTo(String value) {
            addCriterion("USER_AGENT >=", value, "userAgent");
            return (Criteria) this;
        }

        public Criteria andUserAgentLessThan(String value) {
            addCriterion("USER_AGENT <", value, "userAgent");
            return (Criteria) this;
        }

        public Criteria andUserAgentLessThanOrEqualTo(String value) {
            addCriterion("USER_AGENT <=", value, "userAgent");
            return (Criteria) this;
        }

        public Criteria andUserAgentLike(String value) {
            addCriterion("USER_AGENT like", value, "userAgent");
            return (Criteria) this;
        }

        public Criteria andUserAgentNotLike(String value) {
            addCriterion("USER_AGENT not like", value, "userAgent");
            return (Criteria) this;
        }

        public Criteria andUserAgentIn(List<String> values) {
            addCriterion("USER_AGENT in", values, "userAgent");
            return (Criteria) this;
        }

        public Criteria andUserAgentNotIn(List<String> values) {
            addCriterion("USER_AGENT not in", values, "userAgent");
            return (Criteria) this;
        }

        public Criteria andUserAgentBetween(String value1, String value2) {
            addCriterion("USER_AGENT between", value1, value2, "userAgent");
            return (Criteria) this;
        }

        public Criteria andUserAgentNotBetween(String value1, String value2) {
            addCriterion("USER_AGENT not between", value1, value2, "userAgent");
            return (Criteria) this;
        }

        public Criteria andConnectionIsNull() {
            addCriterion("CONNECTION is null");
            return (Criteria) this;
        }

        public Criteria andConnectionIsNotNull() {
            addCriterion("CONNECTION is not null");
            return (Criteria) this;
        }

        public Criteria andConnectionEqualTo(String value) {
            addCriterion("CONNECTION =", value, "connection");
            return (Criteria) this;
        }

        public Criteria andConnectionNotEqualTo(String value) {
            addCriterion("CONNECTION <>", value, "connection");
            return (Criteria) this;
        }

        public Criteria andConnectionGreaterThan(String value) {
            addCriterion("CONNECTION >", value, "connection");
            return (Criteria) this;
        }

        public Criteria andConnectionGreaterThanOrEqualTo(String value) {
            addCriterion("CONNECTION >=", value, "connection");
            return (Criteria) this;
        }

        public Criteria andConnectionLessThan(String value) {
            addCriterion("CONNECTION <", value, "connection");
            return (Criteria) this;
        }

        public Criteria andConnectionLessThanOrEqualTo(String value) {
            addCriterion("CONNECTION <=", value, "connection");
            return (Criteria) this;
        }

        public Criteria andConnectionLike(String value) {
            addCriterion("CONNECTION like", value, "connection");
            return (Criteria) this;
        }

        public Criteria andConnectionNotLike(String value) {
            addCriterion("CONNECTION not like", value, "connection");
            return (Criteria) this;
        }

        public Criteria andConnectionIn(List<String> values) {
            addCriterion("CONNECTION in", values, "connection");
            return (Criteria) this;
        }

        public Criteria andConnectionNotIn(List<String> values) {
            addCriterion("CONNECTION not in", values, "connection");
            return (Criteria) this;
        }

        public Criteria andConnectionBetween(String value1, String value2) {
            addCriterion("CONNECTION between", value1, value2, "connection");
            return (Criteria) this;
        }

        public Criteria andConnectionNotBetween(String value1, String value2) {
            addCriterion("CONNECTION not between", value1, value2, "connection");
            return (Criteria) this;
        }
    }

    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }

    public static class Criterion {
        private String condition;

        private Object value;

        private Object secondValue;

        private boolean noValue;

        private boolean singleValue;

        private boolean betweenValue;

        private boolean listValue;

        private String typeHandler;

        public String getCondition() {
            return condition;
        }

        public Object getValue() {
            return value;
        }

        public Object getSecondValue() {
            return secondValue;
        }

        public boolean isNoValue() {
            return noValue;
        }

        public boolean isSingleValue() {
            return singleValue;
        }

        public boolean isBetweenValue() {
            return betweenValue;
        }

        public boolean isListValue() {
            return listValue;
        }

        public String getTypeHandler() {
            return typeHandler;
        }

        protected Criterion(String condition) {
            super();
            this.condition = condition;
            this.typeHandler = null;
            this.noValue = true;
        }

        protected Criterion(String condition, Object value, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.typeHandler = typeHandler;
            if (value instanceof List<?>) {
                this.listValue = true;
            } else {
                this.singleValue = true;
            }
        }

        protected Criterion(String condition, Object value) {
            this(condition, value, null);
        }

        protected Criterion(String condition, Object value, Object secondValue, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.secondValue = secondValue;
            this.typeHandler = typeHandler;
            this.betweenValue = true;
        }

        protected Criterion(String condition, Object value, Object secondValue) {
            this(condition, value, secondValue, null);
        }
    }
}