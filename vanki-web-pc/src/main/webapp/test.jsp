<%--
  Created by IntelliJ IDEA.
  User: vanki
  Date: 16/10/22
  Time: 20:02
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() +
            ":" + request.getServerPort() + path + "/";
%>
<html lang="zh-CN">
<head>
    <base href="<%=basePath%>">
    <title>test</title>
    <link href="/statics/bootstrap/css/bootstrap.min.css" rel="stylesheet">
    <link href="/statics/bootstrap/css/bootstrap-responsive.min.css" rel="stylesheet">
    <script type="text/javascript" src="/statics/jquery/jquery-3.1.1.min.js"></script>
    <script type="text/javascript" src="/statics/bootstrap/js/bootstrap.min.js"></script>
    <script type="text/javascript" >
        $(function () {
            var params = {id: 123, folderId: 222, title: "i am the title"};
            $.ajax({
                url: '/test/aa.html',
                type: 'GET',
                dataType: 'JSON',
                data: params,
                success: function (data) {
                    console.info(data);
                }
            })
        });
    </script>
</head>
<body>

<p class="navbar-text">Signed in as Mark Otto</p>
<nav class="navbar navbar-default navbar-fixed-top" role="navigation">
    <form class="bs-docs-example form-search">
        <input type="text" id="inputInfo">
    </form>
</nav>


</body>
</html>
