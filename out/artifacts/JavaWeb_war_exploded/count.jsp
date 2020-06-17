
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>统计网站的访问次数</title>
</head>
<body>
    <%
        //servelt上下文
        //ServletContext servletContext = request.getServletContext();
        if(application.getAttribute("count") == null){
            application.setAttribute("count", 1);
        }else{
            Integer count = (Integer) application.getAttribute("count");
            count++;
            application.setAttribute("count", count);
        }
    %>

    <p style="color:gray">
        当前网站的访问次数：<%=application.getAttribute("count")%>
    </p>
</body>
</html>
