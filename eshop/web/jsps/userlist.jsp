<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>userlist.jsp</title>
</head>
<body>
<table border="1">
    <tr>
        <td>ID</td>
        <td>name</td>
        <td>password</td>
        <td>email</td>
        <td>nickName</td>
        <td>regDate</td>
        <td>删除</td>
        <td>查看</td>
        <td>编辑</td>
    </tr>
    <c:forEach items="${requestScope.allUsers}" var="u">
        <tr>
            <td><c:out value="${u.id}"/></td>
            <td><c:out value="${u.name}"/></td>
            <td><c:out value="${u.password}"/></td>
            <td><c:out value="${u.email}"/></td>
            <td><c:out value="${u.nickName}"/></td>
            <td><c:out value="${u.regDate}"/></td>
            <td><a href="<c:out value='/admin/delUser?uid=${u.id}'/>">删除</a></td>
            <td><a href="<c:out value='/admin/viewUser?uid=${u.id}'/>">查看</a></td>
            <td><a href="<c:out value='/admin/editUser?uid=${u.id}'/>">编辑</a></td>
        </tr>
    </c:forEach>
</table>
</form>
</body>
</html>
