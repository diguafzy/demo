<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
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
    <title>调度列表</title>

    <!-- 新 Bootstrap 核心 CSS 文件 -->
    <link rel="stylesheet" type="text/css" href="css/bootstrap.min.css">
    <link rel="stylesheet" type="text/css" href="css/jquery.pagination.css">
<style>
	body {
		font-family: "微软雅黑";
		background: #eee;
	}
</style>
</head>
<body>
<div class="container-fluid">
  <div class="row-fluid">
    <h3> 任务调度 <a href="system/job/add" type="button" class="btn btn-primary btn-sm">添加</a></h3>
	<form action="/system/job" method="get" name="jobForm" id="jobForm">
	</form>
    <table id="jobinfo" class="table table-bordered table-striped">
        <tr>
            <th>行号</th>
            <th>任务名称</th>
            <th>任务类</th>
            <th>规则表达式</th>
            <th>是否启用</th>
            <th>操作</th>
        </tr>

		<c:choose>
			<c:when test="${not empty jobList}">
	        <c:forEach items="${jobList}" var="job" varStatus="status">
	            <tr>
	                <td>${status.index+1}</td>
	                <td>${job.jobName}</td>
	                <td>${job.jobClass}</td>
	                <td>${job.cronExpression}</td>
	                <td>${job.isEnable}</td>
	                <td>
	                    <a href="system/jobs/delete/${job.jobId}" type="button" onclick="return confirm('确定要删除吗？')" class="btn btn-sm btn-danger">删除</a>
	                </td>
	            </tr>
	        </c:forEach>
	        </c:when>
			<c:otherwise>
				<tr class="main_info">
					<td colspan="100" class="center" >没有相关数据</td>
				</tr>
			</c:otherwise>
		</c:choose>
    </table>
	</div>
</div>

<!-- jQuery文件。务必在bootstrap.min.js 之前引入 -->
<script type="text/javascript" src="script/jquery-3.2.1.min.js"></script>

<!-- 最新的 Bootstrap 核心 JavaScript 文件 -->
<script type="text/javascript" src="script/bootstrap.min.js"></script>
<script type="text/javascript" src="script/jquery.pagination.min.js"></script>
<script type="text/javascript">
$(function() {
});
</script>
</body>
</html>