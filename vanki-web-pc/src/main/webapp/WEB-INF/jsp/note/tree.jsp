<%--
  Created by IntelliJ IDEA.
  User: vanki
  Date: 16/10/27
  Time: 17:12
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>笔记列表树</title>
    <jsp:include page="../common/common.jsp"></jsp:include>
    <script type="text/javascript" src="statics/easyUI/plugins/jquery.tree.js"></script>

    <script type="text/javascript" src="statics/js/note/tree.js"></script>

    <link href="statics/css/note/tree.css" rel="stylesheet" type="text/css">
</head>
<body>

<ul id="VJ_note_tree" class="easyui-tree"></ul>
<input type="button" value="添加节点" id="VJ_add_tree_root"/>

<div id="VJ_win_add_tree_note" class="div_add_note" title="添加">
    是否公开:&nbsp;<input type="radio" name='VJ_isPublic' value="1" checked/> 是&nbsp;&nbsp;
    <input type="radio" name='VJ_isPublic' value="0"/>否<br/>
    标题:&nbsp;<input id="VJ_tree_title" class="input_title" placeholder="必填"/><br/>
    描述: <input id="VJ_tree_description"/>
</div>
</body>
</html>
