<%@ page import="com.yjl.entity.Pet" %>
<%@ page import="java.util.List" %>
<%@ page import="com.yjl.entity.PetStore" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
    <title>宠物主人出售宠物页面</title>
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

    <h2>您的宠物类表如下：</h2>
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
    <h2>宠物商店类表如下：</h2>
    <table>
        <tr>
            <th>id</th>
            <th>name</th>
        </tr>
        <c:forEach items="${requestScope.petStores}" var="petStore">
            <tr>
                <td>${petStore.id}</td>
                <td>${petStore.name}</td>
            </tr>
        </c:forEach>
    </table>

    <div>
        <%--<form action="/petOwner?opr=sell" method="post">--%>
            <p>请选择您要出售的宠物序号：<input type="text" name="petId" id="petId"></p>
            <p>请选择您要出售的商店序号：<input type="text" name="storeId" id="storeId"></p>
            <input type="button" value="出售" onclick="sell()"/>
        <%--</form>--%>
    </div>

</body>
<script type="text/javascript" src="js/jquery-1.12.4.js"></script>
<script>
    function sell() {
        var petId = $("#petId").val();
        var storeId = $("#storeId").val();
        if(petId==""||storeId==""){
            alert("宠物id跟商店序号不能为空值")
            return;
        }
        //通过jquery封装的ajax访问sevelt
        $.ajax({
            "url":"/petOwner?opr=sell",
            "type":"get",
            "date":"petId="+petId+"&storeId="+storeId,
            "success":function (result) {
                if("true"==result){
                    alert("出售成功")

                }else {
                    alert("出售失败")
                }
            },
            "error":function () {
                alert("服务器异常，请联系管理员。")
            }
        })
    }
</script>
</html>
