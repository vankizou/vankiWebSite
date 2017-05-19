<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: vanki
  Date: 2017/2/21
  Time: 23:22
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%--<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>--%>
<!doctype html>
<html>
<head>
    <title>奇奇笔记-${noteVo.note.title}</title>
    <jsp:include page="../common/common.jsp"></jsp:include>
    <jsp:include page="../common/markdown.jsp"></jsp:include>
    <script type="text/javascript" src="/statics/js/note/view.js"></script>

    <script type="text/javascript">
        var c_noteId = '${noteVo.note.id}';
        var c_isNeedPwd = '${noteVo.isNeedPwd}';
    </script>
</head>
<body>
<jsp:include page="../common/top.jsp"></jsp:include>
<div class="container c_body row">
    <div class="col-xs-12">
        <div id="j_empty_content" style="display: none; margin: 50px auto; font-size:18px; font-weight:bold;">
            笔记内容为空!
        </div>
        <div id="layout">
            <div id="vanki-editormd-view-note">
                <textarea id="j_content" style="display: none;"><c:if
                        test="${!empty noteVo.noteDetailList && !empty noteVo.noteDetailList[0].content}">${noteVo.noteDetailList[0].content}
                </c:if></textarea>
            </div>
        </div>
    </div>
</div>
</body>
</html>
