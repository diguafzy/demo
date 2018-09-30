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
    <title>首页</title>

    <!-- 新 Bootstrap 核心 CSS 文件 -->
    <link rel="stylesheet" type="text/css" href="css/bootstrap.min.css">
</head>
<body>
    <h3></h3>
    <div class="container">
        <div class="row">
            <div style="float:left;">
                <table border="0" width="40%">
                    <tr>
                        <td>
						    <jsp:include
						         page="../FusionChartsHTMLRenderer.jsp" flush="true">
						         <jsp:param name="chartSWF" value="FusionCharts/Pie2D.swf" />
						         <jsp:param name="strURL" value="" />
						         <jsp:param name="strXML" value="${strXML}" />
						         <jsp:param name="chartId" value="myNext" />
						         <jsp:param name="chartWidth" value="400" />
						         <jsp:param name="chartHeight" value="300" />
						         <jsp:param name="debugMode" value="false" />
						     </jsp:include>
                        </td>
                    </tr>
                </table>
            </div>
            <div style="float:right;">
                <table border="0" width="40%">
                    <tr>
                        <td><jsp:include
                                page="../FusionChartsHTMLRenderer.jsp" flush="true">
                                <jsp:param name="chartSWF" value="FusionCharts/Bar2D.swf" />
                                <jsp:param name="strURL" value="" />
                                <jsp:param name="strXML" value="${strXML1}" />
                                <jsp:param name="chartId" value="myNext" />
                                <jsp:param name="chartWidth" value="400" />
                                <jsp:param name="chartHeight" value="300" />
                                <jsp:param name="debugMode" value="false" />
                            </jsp:include></td>
                    </tr>
                </table>
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