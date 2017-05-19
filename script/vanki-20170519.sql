/*
 Navicat Premium Data Transfer

 Source Server         : 本地
 Source Server Type    : MySQL
 Source Server Version : 50710
 Source Host           : localhost
 Source Database       : vanki

 Target Server Type    : MySQL
 Target Server Version : 50710
 File Encoding         : utf-8

 Date: 05/19/2017 11:44:38 AM
*/

SET NAMES utf8;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
--  Table structure for `t_auth_resource`
-- ----------------------------
DROP TABLE IF EXISTS `t_auth_resource`;
CREATE TABLE `t_auth_resource` (
  `ID` bigint(11) NOT NULL AUTO_INCREMENT,
  `URL` varchar(100) DEFAULT NULL,
  `ORIGIN` int(2) DEFAULT NULL COMMENT '接口所属。1PC',
  `OPERATION` int(2) DEFAULT NULL COMMENT '权限操作。0无需权限，1添加，2修改，4删除，8查询。复合权限如全部15，添加和查询9',
  `DESCRIPTION` varchar(200) DEFAULT NULL,
  `CREATE_DATETIME` datetime DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
--  Table structure for `t_auth_role`
-- ----------------------------
DROP TABLE IF EXISTS `t_auth_role`;
CREATE TABLE `t_auth_role` (
  `ID` bigint(11) NOT NULL AUTO_INCREMENT,
  `NAME` varchar(32) DEFAULT NULL,
  `DESCRIPTION` varchar(32) DEFAULT NULL,
  `CREATE_DATETIME` datetime DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
--  Table structure for `t_auth_role_operation`
-- ----------------------------
DROP TABLE IF EXISTS `t_auth_role_operation`;
CREATE TABLE `t_auth_role_operation` (
  `ID` bigint(11) NOT NULL AUTO_INCREMENT,
  `ROLE_ID` bigint(11) DEFAULT NULL,
  `CONTENT` varchar(200) DEFAULT NULL COMMENT 'type=1:/a - 匹配 /a/* 子url, /b/b.html；type=2: 1 或多个 1,2,3   数字代表资源表中的origin',
  `TYPE` int(2) DEFAULT '1' COMMENT '操作类型。1正常，2限定资源所属',
  `OPERATION` int(2) DEFAULT NULL COMMENT '权限操作，只当type=1时有效。1添加，2修改，4删除，8查询。复合权限如全部15，添加和查询9',
  `DESCRIPTION` varchar(200) DEFAULT NULL,
  `CREATE_DATETIME` datetime DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
--  Table structure for `t_auth_user_role`
-- ----------------------------
DROP TABLE IF EXISTS `t_auth_user_role`;
CREATE TABLE `t_auth_user_role` (
  `ID` bigint(11) NOT NULL AUTO_INCREMENT,
  `USER_ID` bigint(11) DEFAULT NULL,
  `ROLE_ID` bigint(11) DEFAULT NULL,
  `CREATE_DATETIME` datetime DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
--  Table structure for `t_env_var`
-- ----------------------------
DROP TABLE IF EXISTS `t_env_var`;
CREATE TABLE `t_env_var` (
  `ID` bigint(3) NOT NULL AUTO_INCREMENT,
  `K` varchar(32) DEFAULT NULL COMMENT '唯一',
  `V` varchar(64) DEFAULT NULL,
  `DESCRIPTION` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC;

-- ----------------------------
--  Table structure for `t_note`
-- ----------------------------
DROP TABLE IF EXISTS `t_note`;
CREATE TABLE `t_note` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `PARENT_ID` bigint(20) DEFAULT '-1' COMMENT '父ID',
  `USER_ID` bigint(11) DEFAULT NULL COMMENT '用户ID',
  `TYPE` int(2) DEFAULT NULL COMMENT '笔记类型. 1普通笔记, 2Markdown',
  `COUNT_NOTE` int(11) DEFAULT NULL COMMENT '笔记数量. 只当是目录时有值',
  `COUNT_NOTE_CONTENT` int(11) DEFAULT '0' COMMENT '笔记内容段数量',
  `SECRET` int(2) DEFAULT '0' COMMENT '私密. 0公开, 1密码访问, 2不公开',
  `PASSWORD` varchar(64) DEFAULT NULL COMMENT '密码, 笔记为私密时有值',
  `TITLE` varchar(255) DEFAULT NULL COMMENT '笔记标题',
  `KEYWORD` varchar(255) DEFAULT NULL COMMENT '关键词，多个以空隔分隔',
  `SEQUENCE` int(11) DEFAULT NULL COMMENT '序号',
  `DESCRIPTION` varchar(255) DEFAULT NULL,
  `UPDATE_DATETIME` datetime DEFAULT NULL COMMENT '上一次更新日期',
  `CREATE_DATETIME` datetime DEFAULT NULL COMMENT '创建日期',
  `VIEW_NUM` bigint(20) DEFAULT '0' COMMENT '浏览数',
  `DIGEST` varchar(200) DEFAULT NULL COMMENT '摘要',
  `AUTHOR` varchar(64) DEFAULT NULL COMMENT '作者',
  `ORIGIN_URL` varchar(500) DEFAULT NULL COMMENT '文章来源',
  `STATUS` int(2) DEFAULT '1' COMMENT '-1待审核，0不通过，1不通过',
  `STATUS_DESCRIPTION` varchar(200) DEFAULT NULL COMMENT '审核理由',
  `IS_DEL` int(2) DEFAULT '0' COMMENT '是否被删除。0否，1是，默认0',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='笔记';

-- ----------------------------
--  Table structure for `t_note_detail`
-- ----------------------------
DROP TABLE IF EXISTS `t_note_detail`;
CREATE TABLE `t_note_detail` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `USER_ID` bigint(11) DEFAULT NULL,
  `NOTE_ID` bigint(20) DEFAULT NULL,
  `CONTENT` text,
  `TYPE` int(2) DEFAULT '1' COMMENT '类型. 1普通笔记, 2Markdown',
  `SEQUENCE` int(11) DEFAULT NULL COMMENT '序号',
  `CREATE_DATETIME` datetime DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC COMMENT='笔记详情';

-- ----------------------------
--  Table structure for `t_picture`
-- ----------------------------
DROP TABLE IF EXISTS `t_picture`;
CREATE TABLE `t_picture` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `USER_ID` bigint(11) DEFAULT NULL COMMENT '用户ID',
  `NAME` varchar(100) DEFAULT NULL,
  `PATH` varchar(100) DEFAULT NULL,
  `WIDTH` int(6) DEFAULT NULL,
  `HEIGHT` int(6) DEFAULT NULL,
  `SIZE` bigint(20) DEFAULT NULL,
  `TYPE` varchar(10) DEFAULT NULL COMMENT '图片类型, 如: jpg, gif',
  `CRATE_DATETIME` datetime DEFAULT NULL,
  `IS_DEL` int(2) DEFAULT '0' COMMENT '是否删除. 0否, 1是. 默认0',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC COMMENT='图片库';

-- ----------------------------
--  Table structure for `t_unite`
-- ----------------------------
DROP TABLE IF EXISTS `t_unite`;
CREATE TABLE `t_unite` (
  `ID` bigint(11) NOT NULL AUTO_INCREMENT,
  `USER_ID` bigint(11) DEFAULT NULL COMMENT '用户ID',
  `TYPE` int(2) DEFAULT NULL COMMENT '1微信, 2QQ, 3微博',
  `OPEN_ID` varchar(100) DEFAULT NULL COMMENT '第三方唯一标识',
  `UNION_ID` varchar(100) DEFAULT NULL COMMENT '微信联合标识, 只当type=2时有值',
  `AVATAR_PATH` varchar(200) DEFAULT NULL COMMENT '第三方头像',
  `NAME` varchar(100) DEFAULT NULL COMMENT '第三方用户名',
  `CREATE_DATETIME` datetime DEFAULT NULL COMMENT '第一次登录时间',
  `IS_DEL` int(2) DEFAULT '0' COMMENT '是否删除. 0否, 1是. 默认0',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC COMMENT='联合登录';

-- ----------------------------
--  Table structure for `t_user`
-- ----------------------------
DROP TABLE IF EXISTS `t_user`;
CREATE TABLE `t_user` (
  `ID` bigint(11) NOT NULL AUTO_INCREMENT,
  `NAME` varchar(32) DEFAULT NULL COMMENT '登录名，唯一，可空',
  `ALIAS` varchar(20) DEFAULT NULL COMMENT '别名, 必填',
  `PASSWORD` varchar(64) DEFAULT NULL COMMENT '密码',
  `AVATAR_ID` bigint(20) DEFAULT NULL,
  `GENDER` int(2) DEFAULT '0' COMMENT '性别. 0保密, 1男, 2女. 默认0',
  `STATUS` int(2) DEFAULT '1' COMMENT '状态. 0禁用, 1启用, 默认1',
  `FIND_PWD_VALIDATION` varchar(32) DEFAULT NULL COMMENT '找回密码验证字符',
  `MOTTO` varchar(255) DEFAULT NULL COMMENT '格言',
  `PHONE` varchar(20) DEFAULT NULL COMMENT '手机号',
  `EMAIL` varchar(64) DEFAULT NULL COMMENT '邮箱',
  `CONTACT` varchar(64) DEFAULT NULL,
  `REGISTER_ORIGIN` int(2) DEFAULT NULL COMMENT '注册来源. 1无，2手机号，3微信，4QQ，5微博',
  `REGISTER_IP` varchar(32) DEFAULT NULL COMMENT '注册IP',
  `DESCRIPTION` varchar(64) DEFAULT NULL,
  `CREATE_DATETIME` datetime DEFAULT NULL COMMENT '注册时间',
  `IS_DEL` int(2) DEFAULT '0' COMMENT '是否删除. 0否, 1是. 默认0',
  PRIMARY KEY (`ID`),
  KEY `PHONE` (`PHONE`)
) ENGINE=InnoDB AUTO_INCREMENT=1000 DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC COMMENT='用户信息';

-- ----------------------------
--  Table structure for `t_user_login_record`
-- ----------------------------
DROP TABLE IF EXISTS `t_user_login_record`;
CREATE TABLE `t_user_login_record` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `USER_ID` bigint(11) DEFAULT NULL,
  `ORIGIN` int(2) DEFAULT NULL COMMENT '登录来源. 1无，2微信，3QQ，4微博',
  `CREATE_DATETIME` datetime DEFAULT NULL,
  `IP` varchar(32) DEFAULT NULL,
  `PROTOCOL` varchar(32) DEFAULT NULL,
  `SCHEME` varchar(16) DEFAULT NULL,
  `SERVER_NAME` varchar(32) DEFAULT NULL,
  `REMOTE_ADDR` varchar(32) DEFAULT NULL,
  `REMOTE_HOST` varchar(32) DEFAULT NULL,
  `CHARACTER_ENCODING` varchar(16) DEFAULT NULL,
  `ACCEPT` varchar(64) DEFAULT NULL,
  `ACCEPT_ENCODING` varchar(32) DEFAULT NULL,
  `ACCEPT_LANGUAGE` varchar(32) DEFAULT NULL,
  `USER_AGENT` varchar(200) DEFAULT NULL,
  `CONNECTION` varchar(32) DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

SET FOREIGN_KEY_CHECKS = 1;
