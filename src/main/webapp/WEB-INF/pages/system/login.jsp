<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page isELIgnored="false" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <base href="<%=basePath%>">
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <!-- 上述3个meta标签*必须*放在最前面，任何其他内容都*必须*跟随其后！ -->
    <title>登录</title>

    <!-- 新 Bootstrap 核心 CSS 文件 -->
    <link rel="stylesheet" type="text/css" href="css/bootstrap.min.css">
</head>
<body>
<div class="container" style="text-align: center">
    <h1>登录</h1>
    <hr/>
    <form:form action="system/login" class="form-horizontal" method="post" commandName="user" role="form">
        <div class='row form-group'>
            <label for='username' class='col-md-4 control-label'>用户名:</label>
            <div class="col-md-4">
                <input type='text' class='form-control' id='username' name='username'/>
            </div>
        </div>
        <div class='row form-group'>
            <label for='password' class='col-sm-4 control-label'>密码:</label>
            <div class="col-md-4">
                <input type="password" class='form-control' id='password' name='password'/>
            </div>
        </div>
        <div class="row form-group">
            <button type="submit" class="btn btn-sm btn-success">登录</button>
        </div>
    </form:form>
</div>

<!-- jQuery文件。务必在bootstrap.min.js 之前引入 -->
<script type="text/javascript" src="script/jquery-3.2.1.min.js"></script>

<!-- 最新的 Bootstrap 核心 JavaScript 文件 -->
<script type="text/javascript" src="script/bootstrap.min.js"></script>
</body>
</html>