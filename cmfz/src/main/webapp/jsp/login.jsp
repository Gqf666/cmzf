<%@page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" isELIgnored="false" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!doctype html>
<html>
<head>
    <meta charset="utf-8">
    <title>love</title>
    <link href="favicon.ico" rel="shortcut icon" />
    <link href="../boot/css/bootstrap.min.css" rel="stylesheet">
    <script src="../boot/js/jquery-2.2.1.min.js"></script>
    <script>
       $(function (){
           $("#change").click(
               function () {
                   $("#codeImg").prop("src","${pageContext.request.contextPath}/admin/getCodeImge?="+new Date().getTime())
               }
           )

           $("#log").click(
               function () {
                  var username = $("#username").val();
                  var password = $("#password").val();
                  var vCode =$("#vCode").val();
                   $.post(
                       "${pageContext.request.contextPath}/admin/selectOne",

                       {
                           "username":username,
                           "password":password,
                           "vCode":vCode

                       },
                       function (result) {
                           if (result == "\"用户名账号或密码错误\""){
                               $("#msg").empty();
                               $("#msg").html("<font color='red'>"+result+"</font>")
                           }else if(result=="\"验证码错误\""){
                               $("#msg").empty();
                               $("#msg").html("<font color='red'>"+result+"</font>")
                           }else{
                               location.href="${pageContext.request.contextPath}/jsp/main.jsp";
                           }
                       }
                   )
               }
           )

       })










    </script>
</head>
<body style=" background: url(../img/timg.jpg); background-size: 100%;">


<div class="modal-dialog" style="margin-top: 10%;">
    <div class="modal-content">
        <div class="modal-header">

            <h4 class="modal-title text-center" id="myModalLabel">持明法洲</h4>
        </div>
        <form id="loginForm" method="post" action="${pageContext.request.contextPath}/user/login">
        <div class="modal-body" id = "model-body">
            <div class="form-group">
                <input type="text" class="form-control"placeholder="用户名" autocomplete="off" id="username" name="username">
            </div>
            <div class="form-group">
                <input type="password" class="form-control" placeholder="密码" autocomplete="off" id="password" name="password">
            </div>
            <div class="form-group">
                <input type="text" class="form-control" placeholder="验证码" autocomplete="off" id="vCode" name="vCode">
                <img src="${pageContext.request.contextPath}/admin/getCodeImge" id="codeImg">
                <a id="change" onclick="codeFlush()">看不清？换一张</a>
            </div>
            <span id="msg"></span>
        </div>
        <div class="modal-footer">
            <div class="form-group">
                <button type="button" class="btn btn-primary form-control" id="log" >登录</button>
            </div>
            <div class="form-group">
                <button type="button" class="btn btn-default form-control">注册</button>
            </div>

        </div>
        </form>
    </div>
</div>
</body>
</html>
