<%@ page import="com.yjl.entity.PetOwner" %>
<%@ page import="com.yjl.entity.Pet" %>
<%@ page import="java.util.List" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Title</title>
</head>
<body>

    <c:if test="${sessionScope.petOwner==null}">
        <script type="text/javascript">
            location.href="login.jsp";
        </script>
    </c:if>

    <h1>
        您的个人信息如下：
    </h1>
    <p>id  用户名  密码  元宝数</p>
    <p>
        ${sessionScope.petOwner.id}
        ${sessionScope.petOwner.name}
        ${sessionScope.petOwner.password}
        ${sessionScope.petOwner.money}
    </p>

    <ul>
        <li><a href="/petOwner?opr=toBuyPet">购买宠物</a></li>
        <li><a href="/petOwner?opr=toSellPet">出售宠物</a></li>
        <li><a href="/petOwner?opr=list">展示我的宠物列表</a></li>
    </ul>

    <%@include file="count.jsp"%>
</body>

</html>
