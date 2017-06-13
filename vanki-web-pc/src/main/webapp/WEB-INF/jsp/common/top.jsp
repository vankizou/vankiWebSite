<%--
  Created by IntelliJ IDEA.
  User: vanki
  Date: 16/10/26
  Time: 16:58
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<link href="/statics/css/common/top/top.css" rel="stylesheet" type="text/css">
<link href="/statics/css/common/top/top2.css" rel="stylesheet" type="text/css">
<link href="/statics/css/common/body.css" rel="stylesheet" type="text/css">
<link href="/statics/css/index/login_pop.css" rel="stylesheet" type="text/css" media="all"/>

<script type="text/javascript" src="/statics/third/popwindow/jquery.blockUI.js"></script>
<script type="text/javascript" src="/statics/js/common/top/top.js"></script>
<script type="text/javascript" src="/statics/js/common/top/loginRegister.js"></script>


<div class="navbar navbar-default" style="min-width: 640px;">
    <div class="container">
        <div class="navbar-header" style="vertical-align: middle;display: inline-block;">
            <button type="button" class="navbar-toggle" data-toggle="collapse" data-target="#navbar-ex-collapse">
                <span class="sr-only">菜单</span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
            </button>
            <a class="navbar-brand" href="/" style="color: #9c3328;font-style: italic; font-weight:700;">
                <i class="fa fa-paint-brush"></i>&nbsp;奇奇笔记
            </a>
        </div>

        <div class="collapse navbar-collapse" id="navbar-ex-collapse">
            <ul class="nav navbar-nav navbar-right">
                <li class="text-align">
                    <a href="/"><i class="fa fa-home"></i>&nbsp; 主页</a>
                </li>

                <%--<li class="active">
                    <a href="#">Contacts</a>
                </li>--%>
                <c:choose>
                    <c:when test="${userContext == null}">
                        <li id="j_login_register">
                            <a href="javascript:;">
                                <i class="fa fa-user-circle"></i>&nbsp; 登录注册
                            </a>
                        </li>
                    </c:when>
                    <c:otherwise>
                        <li><a href="/user/${userContext.user.id}.html"><i class="fa fa-paint-brush"></i>&nbsp; 我的笔记</a></li>
                        <li class="dropdown">
                            <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button"
                               aria-expanded="false">
                                <i class="fa fa-street-view"></i>&nbsp;
                                Hi. ${userContext.user.alias}
                            </a>
                            <ul class="dropdown-menu" role="menu">
                                <li>
                                    <a href="javascript:;"
                                       style="font-weight: 700; color: #9966FF;"><i
                                            class="fa fa-bell"></i>&nbsp; 我的ID：${userContext.user.id}</a>
                                </li>
                                <li><a href="/user/${userContext.user.id}.html"><i class="fa fa-paint-brush"></i>&nbsp; 我的笔记</a></li>
                                <li><a href="/user/logout.html"><i class="fa fa-sign-out"></i>&nbsp; 退出</a></li>
                            </ul>
                        </li>
                    </c:otherwise>
                </c:choose>
            </ul>
        </div>
    </div>

    <div id="j_win_loginRegister" class="login" style="display:none; cursor: default" is_pop_win="1">
        <h2>欢&nbsp;&nbsp;迎</h2>
        <div id="j_login">
            <div class="login-top">
                <h1>登录</h1>
                <form>
                    <input id="j_login_id" type="text" placeholder="ID/登录名">
                    <input id="j_login_pwd" type="password" placeholder="密码">
                </form>
                <div class="forgot">
                    <a href="#">忘记密码？</a>
                    <input id="j_login_submit" type="submit" value="登录">
                </div>
            </div>
            <div class="login-bottom">
                <h3>新朋友 &nbsp;<a class="j_change" href="javascript:;">注册</a></h3>
            </div>
        </div>

        <div id="j_register" style="display: none;">
            <div class="login-top">
                <h1>注册</h1>
                <form>
                    <input id="j_reg_alias" type="text" placeholder="昵称" title="用户昵称，只作显示">
                    &nbsp;<i style="color:black;" class="fa fa-question-circle" title="用户昵称，只作显示"></i>
                    <input id="j_reg_pwd" type="password" placeholder="登录密码" title="登录密码">
                    &nbsp;<i style="color:black;" class="fa fa-question-circle" title="登录密码"></i>
                    <input id="j_reg_confirm_pwd" type="password" placeholder="确认密码" title="确认密码">
                    &nbsp;<i style="color:black;" class="fa fa-question-circle" title="确认密码"></i>
                    <input id="j_reg_find_pwd_validation" type="text" placeholder="密保验证符"
                           title="可为任意字符，用于校验身份。如：忘记原密码更改新密码。请勿必牢记！">
                    &nbsp;<i style="color:black;" class="fa fa-question-circle"
                             title="可为任意字符，用于校验身份。如：忘记原密码更改新密码。请勿必牢记！"></i>
                    <div class="c_imgcode_div">
                        <input class="c_imagecode" id="j_reg_imagecode" placeholder="验证码计算值"/>
                        <img class="c_imagecode_img" id="j_reg_imagecode_img" src=""/>
                    </div>
                </form>
                <div class="forgot">
                    <input id="j_register_submit" type="submit" value="注册">
                </div>
            </div>
            <div class="login-bottom">
                <h3>老朋友 &nbsp;<a class="j_change" href="javascript:;">登录</a></h3>
            </div>
        </div>
    </div>
</div>


