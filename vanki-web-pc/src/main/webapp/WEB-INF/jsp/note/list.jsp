<%--
  Created by IntelliJ IDEA.
  User: vanki
  Date: 2017/2/21
  Time: 22:10
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<!doctype html>
<html>
<head>
    <base href="<%=basePath%>">
    <meta charset="utf-8"/>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <meta http-equiv="Expires" content="0"/>
    <meta http-equiv="Cache-Control" content="no-cache"/>
    <meta http-equiv="Pragma" content="no-cache"/>
    <title>奇奇笔记-列表</title>
    <%--<script type="text/javascript" src="/statics/third/jquery/jquery-3.1.1.min.js"></script>--%>

    <link href="/statics/css/note/list.css" rel="stylesheet" type="text/css">

    <jsp:include page="../common/common.jsp"></jsp:include>
    <jsp:include page="../common/ztree.jsp"></jsp:include>
    <jsp:include page="../common/markdown.jsp"></jsp:include>
    <script type="text/javascript" src="/statics/js/note/list_right.js"></script>
    <script type="text/javascript" src="/statics/js/note/list.js"></script>

    <script type="text/javascript">
        var c_myUserId = '${userContext.user.id}';
        var c_noteUserId = '${userId}';
    </script>
</head>
<body>
<jsp:include page="../common/top.jsp"></jsp:include>

<div class="container c_body row">
    <div class="col-xs-3" tips="笔记树">
        <ul id="noteTree" class="ztree" style="overflow-x: scroll"></ul>
    </div>
    <div class="col-xs-9">
        <div tips="笔记内容">
            <div class="note_content_common_view row">
                <div class="col-xs-1">
                    <button id="j_note_edit" type="button" class="btn btn-success">编辑</button>
                </div>
                <div class="col-xs-11 text-center">
                    <span class="label label-info pull-left">标题：<code id="j_note_info_title"></code></span>
                    <span class="label label-info pull-left">私密：<code id="j_note_info_secret"></code></span>
                    <span class="label label-info pull-left">关键词：<code id="j_note_info_keyword"></code></span>
                    <span class="label label-info pull-left">浏览数：<code id="j_note_info_viewNum"></code></span>
                </div>
            </div>
            <div class="note_content_common_edit row">
                <div class="col-xs-1">
                    <button id="j_note_save" type="button" class="btn btn-info">保存</button>
                </div>
                <div class="col-xs-11 form-inline">
                    <div class="form-group has-success pull-left">
                        <span class="label label-info pull-left">标题：
                            <input type="text" class="form-control c_title" id="j_note_info_edit_title"/>
                        </span>
                    </div>
                    <div class="form-group has-success pull-left">
                        <span class="label label-info pull-left">私密：
                            <select id="j_note_info_edit_secret" class="form-control">
                                <option value="0">公开</option>
                                <option value="1">密码访问</option>
                                <option value="2">仅自己可见</option>
                            </select>
                        </span>
                    </div>
                    <div class="form-group has-success pull-left">
                        <span class="label label-info pull-left">关键词：
                            <input type="text" class="form-control" id="j_note_info_edit_keyword"/>
                        </span>
                    </div>
                    <div class="form-group has-success pull-left">
                        <span class="label label-info pull-left">浏览数：
                            <code class="form-control" id="j_note_info_edit_viewNum"></code>
                        </span>
                    </div>
                </div>
            </div>
        </div>
        <div id="vanki-editormd-view-note">
            <textarea id="j_note_content" style="display: none;"></textarea>
        </div>
        <input id="j_curr_note_detail_id" value="" style="display: none">
        <input id="j_curr_note_id" value="" style="display: none">
    </div>

</div>
</div>

<div id="rMenu">
    <ul>
        <li id="m_open" onclick="openNote();">查看内容</li>
        <li id="m_add" onclick="addNote();">添加笔记</li>
        <li id="m_updateTitle" onclick="updateNoteTitle();">修改标题</li>
        <li id="m_editInCurrPage" onclick="editNote();">编辑笔记</li>
        <li id="m_del" onclick="deleteNote();">删除笔记</li>
        <hr id="m_hr1" style="height:1px;border:none;border-top:1px solid #555555;"/>
        <li id="m_secret_open" onclick="setSecretOpen();" title="所有人可见（不关联下级）">设置公开</li>
        <li id="m_secret_pwd" onclick="setSecretPwd();" title="密码访问内容（不关联下级）">设置密码</li>
        <li id="m_secret_private" onclick="setSecretPrivate();" title="只能自己访问（不关联下级）">设置私密</li>
    </ul>
</div>
</body>
</html>