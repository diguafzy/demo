<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page isELIgnored="false" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<html>
<head>
    <base href="<%=basePath%>">
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
</head>
<body>
<table border="0" width="50%">
    <tr>
        <td>
		    <jsp:include
		         page="../FusionChartsHTMLRenderer.jsp" flush="true">
		         <jsp:param name="chartSWF" value="FusionCharts/Pie2D.swf" />
		         <jsp:param name="strURL" value="" />
		         <jsp:param name="strXML" value="${strXML}" />
		         <jsp:param name="chartId" value="myNext" />
		         <jsp:param name="chartWidth" value="500" />
		         <jsp:param name="chartHeight" value="300" />
		         <jsp:param name="debugMode" value="false" />
		     </jsp:include>
        </td>
    </tr>
</table>
</body>
</html>