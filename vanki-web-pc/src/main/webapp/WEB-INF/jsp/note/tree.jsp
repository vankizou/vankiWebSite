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
<jsp:include page="../common/top.jsp"/>
<div class="VC_nav_top">
    <div class="section">
        <div class="container">
            <div class="row">
                <div class="col-md-3" title="树" style="overflow:auto;">
                    <button type="button" id="VJ_add_tree_root" class="btn btn-primary" data-loading-text="正在添加...">
                        添加文件夹
                    </button>

                    <ul id="VJ_note_tree" class="easyui-tree"></ul>

                    <div id="VJ_dialog_choose_node_type" style="display:none;">
                        添加: &nbsp;<input type="radio" name="VJ_FOLDER_OR_FILE" value="0" checked/> 文件&nbsp;&nbsp;
                        <input type="radio" name="VJ_FOLDER_OR_FILE" value="1"/> 文件夹&nbsp;&nbsp;
                    </div>

                    <div id="VJ_dialog_add_tree_note" class="div_add_note" title="添加">
                        是否公开: &nbsp;<input type="radio" name='VJ_isPublic' value="1" checked/> 是&nbsp;&nbsp;
                        <input type="radio" name='VJ_isPublic' value="0"/> 否<br/>

                        标题: &nbsp;<input id="VJ_tree_title" class="input_title" placeholder="必填"/><br/>
                        描述: <input id="VJ_tree_description"/>
                    </div>
                </div>
                <div class="col-md-9" title="笔记">
                    <input value="sldkfjskdlfjslkdf">
                </div>
            </div>
        </div>
    </div>
</div>
<%--<div title="树" style="width: 10px; float:left;">
    <ul id="VJ_note_tree" class="easyui-tree"></ul>
    <input type="button" value="添加节点" id="VJ_add_tree_root"/>
    <div id="VJ_FOLDER_OR_FILE_DIV" style="display:none;">
        节点类型:&nbsp;<input type="radio" name="VJ_FOLDER_OR_FILE" value="0" checked/>文件&nbsp;&nbsp;
        <input type="radio" name="VJ_FOLDER_OR_FILE" value="1"/>文件夹&nbsp;&nbsp;
    </div>

    <div id="VJ_dialog_add_tree_note" class="div_add_note" title="添加">
        是否公开:&nbsp;<input type="radio" name='VJ_isPublic' value="1" checked/> 是&nbsp;&nbsp;
        <input type="radio" name='VJ_isPublic' value="0"/>否<br/>

        标题:&nbsp;<input id="VJ_tree_title" class="input_title" placeholder="必填"/><br/>
        描述: <input id="VJ_tree_description"/>
    </div>
</div>
<div title="笔记" style="width:80%;float:right;">
    <input value="sldkfjskdlfjslkdf">
</div>--%>
</body>
</html>
