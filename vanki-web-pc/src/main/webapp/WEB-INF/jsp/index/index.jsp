<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<!doctype html lang="zh-CN">
<html>
<head>
    <base href="<%=basePath%>">
    <meta charset="utf-8"/>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <meta http-equiv="Expires" content="0"/>
    <meta http-equiv="Cache-Control" content="no-cache"/>
    <meta http-equiv="Pragma" content="no-cache"/>
    <title>奇奇笔记-主页</title>

    <jsp:include page="../common/common.jsp"></jsp:include>
    <link rel="stylesheet" href="/statics/css/index/index.css">
</head>
<body>
<script type="text/javascript" src="/statics/third/popwindow/jquery.js"></script>
<jsp:include page="../common/top.jsp"></jsp:include>
<script type="text/javascript" src="/statics/js/index/index.js"></script>

<div class="c_body">
    <div class="c_index_body">
        <div class="panel panel-info" id="j_note_list">
            <!-- Default panel contents -->
            <div class="panel-heading">大神笔记列表</div>
            <%--<div class="panel-body">
            </div>--%>

            <!-- Table -->
            <table class="table table-hover">
                <thead>
                <tr>
                    <td class="text-center">标题</td>
                    <td>所属</td>
                    <td>作者</td>
                    <td>创建时间</td>
                    <td>浏览量</td>
                </tr>
                </thead>
                <tbody id="j_note_list_table">
                </tbody>
            </table>
            <nav aria-label="Page navigation" class="text-center">
                <ul class="pagination">
                    <li><a href="javascript:;" style="font-size: 14px;" id="j_page_info"></a></li>
                    <li title="上一页">
                        <a href="javascript:;" aria-label="Previous"
                           id="j_page_previous" class="j_page_prev_next" val="">
                            <span aria-hidden="true">&laquo;</span>
                        </a>
                    </li>
                    <li title="下一页">
                        <a href="javascript:;" aria-label="Next" id="j_page_next" class="j_page_prev_next" val="">
                            <span aria-hidden="true">&raquo;</span>
                        </a>
                    </li>
                    <li class="form-inline">
                        &nbsp;<input type="text" class="form-control" size="2" id="j_page_jump_val"/>
                        <button class="btn btn-info" id="j_page_jump">跳转</button>
                    </li>
                </ul>
            </nav>
        </div>
    </div>
</div>
</body>
</html>
