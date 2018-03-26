<!DOCTYPE html>
<%@ page language="java" import="java.util.*" contentType="text/html;charset=UTF-8" %>
<%@ include file="common.jsp" %>
<html>
<head>
    <title>${companyName}人力资源管理系统</title>
    <meta charset="utf-8"/>
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport"
          content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=no">
    <meta name="apple-mobile-web-app-capable" content="yes"/>
    <meta name="apple-mobile-web-app-status-bar-style" content="black"/>

    <meta name="description" content="index.jsp">
    <meta name="author" content="">
    <link rel="icon" href="">

    <meta http-equiv="Expires" content="0">
    <meta http-equiv="Pragma" content="no-cache">
    <meta http-equiv="Cache-control" content="no-cache">
    <meta http-equiv="Cache" content="no-cache">

    <script type="text/javascript" src="${ctx}/lib/jquery/2.1.1/jquery.js"></script>
    <script type="text/javascript" src="${ctx}/lib/ueditor/ueditor.config.js"></script>
    <script type="text/javascript" src="${ctx}/lib/ueditor/ueditor.all.min.js"></script>
    
    <%--bootstrap--%>
    <%--<link type="text/css" rel="stylesheet" href="${ctx}/lib/bootstrap/3.3.7/css/bootstrap.css"/>--%>
    <%--<script type="text/javascript" src="${ctx}/lib/bootstrap/3.3.7/js/bootstrap.js"></script>--%>


    <%--PRODUCT--%>
    <%--common--%>
    <link type="text/css" rel="stylesheet" href="${ctx}/static/dist/common.css"/>
    <link type="text/css" rel="stylesheet" href="${ctx}/static/dist/index.css"/>

    <script type="text/javascript">
        var displayName = "${displayName}";
    </script>

</head>
<body>


<div id="root" style="height:100%"></div>


<%--PRODUCT--%>
<script type="text/javascript" src="${ctx}/static/dist/common.js"></script>
<script type="text/javascript" src="${ctx}/static/dist/index.js"></script>


</body>
</html>





