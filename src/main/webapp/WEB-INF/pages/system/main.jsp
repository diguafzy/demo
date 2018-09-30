<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
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
    <title>demo</title>

    <!-- 新 Bootstrap 核心 CSS 文件 -->
    <link rel="stylesheet" type="text/css" href="css/bootstrap.min.css">
    
    <style type="text/css">
        html,body,div{
            height:95%;
        }
    </style>
</head>
<body>
	<div class="container-fluid" id="main-container">
    <h1></h1>
    <hr/>
        <div id="main-row" class="row">
           <div class="col-md-2">
                <ul class="nav">
                    <li>
                        <a style="cursor:pointer;" target="mainFrame" href="system/index"><i id="index" class="icon-double-angle-right"></i>知乎</a>
                    </li>
                    <li>
                        <a style="cursor:pointer;" target="mainFrame" href="system/upload"><i id="index" class="icon-double-angle-right"></i>文件上传</a>
                    </li>
                </ul>
           </div>
           <div class="col-md-10">
               <iframe name="mainFrame" id="mainFrame" frameborder="0" style="width:100%;height:100%;"></iframe>
           </div>
        </div>
	</div>

<!-- jQuery文件。务必在bootstrap.min.js 之前引入 -->
<script type="text/javascript" src="script/jquery-3.2.1.min.js"></script>

<!-- 最新的 Bootstrap 核心 JavaScript 文件 -->
<script type="text/javascript" src="script/bootstrap.min.js"></script>
<script type="text/javascript">
</script>
</body>
</html>