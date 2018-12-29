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
    <!-- 新 Bootstrap 核心 CSS 文件 -->
    <link rel="stylesheet" type="text/css" href="css/bootstrap.min.css">
</head>
<body>
<div class="container" style="text-align: center">
    <form:form action="system/job/save" class="form-horizontal" method="post" commandName="job" role="form">
        <div class='row form-group'>
            <label for='jobName' class='col-md-4 control-label'>任务名称:</label>
            <div class="col-md-4">
                <input type='text' class='form-control' id='jobName' name='jobName'/>
            </div>
        </div>
        <div class='row form-group'>
            <label for='jobClass' class='col-md-4 control-label'>任务类:</label>
            <div class="col-md-4">
                <input type='text' class='form-control' id='jobClass' name='jobClass'/>
            </div>
        </div>
        <div class='row form-group'>
            <label for='cronExpression' class='col-md-4 control-label'>规则表达式:</label>
            <div class="col-md-4">
                <input type='text' class='form-control' id='cronExpression' name='cronExpression' />
            </div>
        </div>
        <div class='row form-group'>
            <label for='isEnable' class='col-md-4 control-label'>是否启用:</label>
            <div class="col-md-4">
                <select class="form-control" id="isEnable" name="isEnable">
                    <option value="启用">启用</option>
                    <option value="禁用">禁用</option>
                </select>
            </div>
        </div>
        <div class="row form-group">
            <button type="submit" class="btn btn-sm btn-success">提交</button>
        </div>
    </form:form>
</div>

<!-- jQuery文件。务必在bootstrap.min.js 之前引入 -->
<script type="text/javascript" src="script/jquery-3.2.1.min.js"></script>

<!-- 最新的 Bootstrap 核心 JavaScript 文件 -->
<script type="text/javascript" src="script/bootstrap.min.js"></script>
</body>
</html>