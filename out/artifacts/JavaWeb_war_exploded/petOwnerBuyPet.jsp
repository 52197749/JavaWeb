<%@ page import="com.yjl.entity.Pet" %>
<%@ page import="java.util.List" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<html>
<head>
    <title>购买宠物</title>
    <style>
        table{
            border: 1px solid #333;
            margin-bottom: 5px;
        }
        th, td{
            border: 1px solid gray;
        }
    </style>
</head>
<body>
<c:if test="${sessionScope.petOwner==null}">
    <script type="text/javascript">
        location.href="login.jsp";
    </script>
</c:if>

    <p>库存宠物列表如下</p>
    <%--使用jstl遍历展示集合数据--%>
    <table>
        <tr>
            <th>id</th>
            <th>name</th>
            <th>typeName</th>
            <th>health</th>
            <th>love</th>
            <th>birthday</th>
        </tr>
        <c:forEach items="${requestScope.pets}" var="pet">
            <tr>
                <td>${pet.id}</td>
                <td>${pet.name}</td>
                <td>${pet.typeName}</td>
                <td>${pet.health}</td>
                <td>${pet.love}</td>
                <td>
                    <fmt:formatDate pattern="yyyy-MM-dd" value="${pet.birthday}"></fmt:formatDate>
                </td>
            </tr>
        </c:forEach>

    </table>


    <%--<form action="/petOwner?opr=buyPet" method="post">--%>
        <%--<input type="hidden" name="opr" value="buyPet">--%>
        请输入要购买的宠物序号：<input type="text" name="id" id="id">
        <input type="button" value="购买"onclick="GouMai()">
    <%--</form>--%>

    <%@include file="count.jsp"%>
</body>
<script type="text/javascript" src="js/jquery-1.12.4.js"></script>
<script>
    function GouMai() {
        var id = $("#id").val();
        if(id==""){
            alert("购买id不能为空");
            return;
        }
        $.ajax({
            "url":"/petOwner?opr=buyPet",
            "type":"get",
            "date":"id="+id,
            "success":function (result) {
                if("true"==result){
                    alert("购买成功")
                }else {
                    alert("购买失败")
                }
            },
            "error":function () {
                alert("服务器异常，请联系管理员。")
            }
        })
    }
</script>
</html>
