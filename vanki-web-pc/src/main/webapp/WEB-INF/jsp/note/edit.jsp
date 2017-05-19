<%--
  Created by IntelliJ IDEA.
  User: vanki
  Date: 2017/2/21
  Time: 23:22
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>笔记详情-${noteVo.note.title}</title>
    <jsp:include page="../common/common.jsp"></jsp:include>
    <jsp:include page="../common/markdown.jsp"></jsp:include>
    <link rel="stylesheet" href="/statics/css/note/edit.css">
    <script type="text/javascript" src="/statics/js/note/edit.js"></script>
</head>
<body>
<div class="c_top_note_info">
    标题: <input id="j_note_title" class="c_top_note_info_input" value="${noteVo.note.title}"/>
    &nbsp;&nbsp;&nbsp;&nbsp;
    描述: <input id="j_note_description" class="c_top_note_info_input" value="${noteVo.note.description}"/>
    &nbsp;&nbsp;&nbsp;&nbsp;
    关键词: <input id="j_note_keyword" class="c_top_note_info_input" value="${noteVo.note.keyword}" placeholder="多个以空隔隔开"/>
</div>
<%-- markdown 笔记noteContent 只有一个 --%>
<span id="J_note_content_id" style="display: none">${empty noteVo.noteContents ? "" : noteVo.noteContents[0].id}</span>
<div id="layout">
    <div id="vanki-editormd-edit-note">
        <textarea style="display: none;"
                  id="J_content">${empty noteVo.noteContents ? "" : noteVo.noteContents[0].content}</textarea>
    </div>
    <span id="J_note_id" style="display: none">${noteVo.note.id}</span>
</div>
</body>
</html>
