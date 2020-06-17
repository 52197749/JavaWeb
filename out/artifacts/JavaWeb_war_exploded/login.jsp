
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
  <head>
    <title>宠物主人登录</title>
  </head>
  <body>
  <h1>宠物主人登录 <lable style="color:red; font-size:12px">${requestScope.error}</lable></h1>
    <form action="/login" method="post">
      <p>用户名：<input type="text" name="username"/></p>
      <p>密码：<input type="password" name="password"/></p>
      <input type="submit" value="提交">
    </form>


  <%@include file="count.jsp"%>
  </body>
</html>
