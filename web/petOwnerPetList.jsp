<%@ page import="com.yjl.entity.Pet" %>
<%@ page import="java.util.List" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
    <title>我的宠物列表</title>
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

    <h2>我的宠物列表   <a href="home.jsp">返回首页</a></h2>
    <table id="sTable">
        <tr>
            <th>id</th>
            <th>name</th>
            <th>typeName</th>
            <th>health</th>
            <th>love</th>
            <th>birthday</th>
        </tr>
    </table>
    <p id="pageId">
        <%--<a href="#" onclick="fenye(1)">首页</a>
        <a href="#" >上一页</a>
        <a href="#">下一页</a>
        <a href="#">末页</a>
        总页数：/--%>
    </p>

    <%@include file="count.jsp"%>
</body>
<script type="text/javascript" src="js/jquery-1.12.4.js"></script>
<script>
    fenye(1);
    //ajax获取我的宠物列表
    function fenye(pageNo) {
        $.ajax({
            "url":"petOwner?opr=list",
            "type":"post",
            "data":"pageNo="+pageNo,
            "dataType":"json",
            "success":function (result) {
                //初始化页面
                intPage(result);
            },
            "error":function () {
                alert("服务器异常，请联系管理员。")
            }
        })
    }


    function intPage(result) {
        console.log(result);
        var pets = result.pets;//获取宠物信息：json数组

        $("#sTable").html("<tr><th>id</th><th>name</th><th>typeName</th>" +
            "<th>health</th><th>love</th><th>birthday</th></tr>");
        $(pets).each(function (index, s) {
            console.log(index+":" + s.id+" "+s.name+" "+s.birthday);
            var $tr = $("<tr></tr>");//创建一个tr对象
            $tr.append("<td>"+s.id+"</td>");
            $tr.append("<td>"+s.name+"</td>");
            $tr.append("<td>"+s.typeName+"</td>");
            $tr.append("<td>"+s.health+"</td>");
            $tr.append("<td>"+s.love+"</td>");
            $tr.append("<td>"+s.birthday+"</td>");
            $("#sTable").append($tr);

            var pageNo = result.pageNo;
            var totalPage = result.totalPageNo;
            $("#pageId").html("");
            $("#pageId").append("<a href='#' onclick='fenye(1)'>首页</a>");
            $("#pageId").append("<a href='#' onclick='fenye("+(pageNo-1)+")'>上一页</a>");
            $("#pageId").append("<a href='#' onclick='fenye("+(pageNo+1)+")'>下一页</a>");
            $("#pageId").append("<a href='#' onclick='fenye("+totalPage+")'>末页</a>");
            $("#pageId").append("总页数："+pageNo+"/"+totalPage);
        })
    }
</script>


</html>
