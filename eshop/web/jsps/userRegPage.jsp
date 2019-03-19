<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>userRegPage.jsp</title>
</head>
<body>
<form action="<c:out value='/doReg'/>" method="post">
    Username :<input type="text" name="name"><br>
    Email :<input type="text" name="email"><c:out value="${requestScope['error.email.registed']}"/><br>
    Nickname :<input type="text" name="nickName"><br>
    Confirmpass :<input type="password" name="confirmpass"><c:out value="${requestScope['error.password.nosame']}"/><br>
    Password :<input type="password" name="password"><br>
    <input type="submit">
</form>
</body>
</html>
