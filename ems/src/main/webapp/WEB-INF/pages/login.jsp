<%@ page language="java" import="java.util.*" contentType="text/html;charset=UTF-8" %>
<%@ include file="common.jsp" %>
<html>
<head>
    <title>励源人力资源管理系统 - 登陆</title>
    <meta charset="utf-8"/>
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=no">
    <meta name="apple-mobile-web-app-capable" content="yes"/>
    <meta name="apple-mobile-web-app-status-bar-style" content="black"/>

    <meta name="description" content="励源人力资源管理系统 - 登陆">
    <meta name="author" content="">
    <link rel="icon" href="">


    <meta http-equiv="Expires" content="0">
    <meta http-equiv="Pragma" content="no-cache">
    <meta http-equiv="Cache-control" content="no-cache">
    <meta http-equiv="Cache" content="no-cache">

    <script type="text/javascript" src="${ctx}/lib/jquery/2.1.1/jquery.min.js"></script>
    <script type="text/javascript">
        $(document).ready(function () {


        });

        //登陆请求
        function doLogin() {
            var span = $("#errorBox");
            span.hide();

            $.ajax({
                url: '${ctx}/doLogin',
                type: 'POST',
                data: {
                    username: $("#username").val(),
                    password: $("#password").val()
                },
                async: true,
                dataType: "json",
                success: function (result) {
                    if(result.success){
                        location.replace('${ctx}/loginRedirect');
                    }else {
                        setErrorMsg(result.msg);
                    }
                },
                error: function (result) {
                    setErrorMsg("登陆失败，请检查是否连接网络");
                }
            });
        }

        function setErrorMsg(msg) {
            msg = msg || "账号或密码不正确";
            var span = $("#errorBox");
            span.show();
            span.find("div").text(msg);
        }
    </script>
</head>
<body>

<img class="body-bg" src="${ctx}/static/images/manager/login_bg.jpg"/>
<div class="login_wrap">

    <h6 class="clearfix">
        <img class="logo" src="${ctx}/static/images/manager/login_logo.png"><span>励源人力资源管理系统</span>
    </h6>

    <div class="login_box">

        <div class="input_box mb20px">
            <span><img src="${ctx}/static/images/outPass_icon01.png"></span>
            <input id="username" type="text" class="account_input" placeholder="请输入账号">
        </div>
        <div class="input_box">
            <span><img src="${ctx}/static/images/outPass_icon02.png"></span>
            <input id="password" type="password" class="password_input" placeholder="请输入密码">
        </div>
        <div class="error">
            <span id="errorBox" style=" display:none;">
                <img src="${ctx}/static/images/manager/error.png">
                <div>账号或密码错误</div>
            </span>
        </div>
        <div class="button_box">
            <input id="loginButton" type="button" class="login_button" onclick="doLogin();" value="登录">
        </div>
    </div>

</div>

</body>
</html>





