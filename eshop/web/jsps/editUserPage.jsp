<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>editUserPage.jsp</title>
</head>
<body>
<form action="<c:out value='/admin/updateUser'/>" method="post">
    <input type="hidden" name="id" value="<c:out value="${user.id}"/>" >
    Username :<input type="text" name="name" value="<c:out value="${user.name}"/>"><br>
    Email :<input type="text" name="email" value="<c:out value="${user.email}"/>"><c:out value="${requestScope['error.email.registed']}"/><br>
    Nickname :<input type="text" name="nickName" value="<c:out value="${user.nickName}"/>"><br>
    Confirmpass :<input type="password" name="confirmpass" ><c:out value="${requestScope['error.password.nosame']}"/><br>
    Password :<input type="password" name="password" value="<c:out value="${user.password}"/>" ><br>
    <input type="submit">
</form>
</body>
</html>
