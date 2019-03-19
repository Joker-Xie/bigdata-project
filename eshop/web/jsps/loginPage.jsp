<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>loginPage.jsp</title>
</head>
<body>
<form action="<c:out value='/doLogin'/>" method="post">
    Username :<input type="text" name="name"><br>
    Password :<input type="password" name="password"><br>
    <input type="submit">
</form>
</body>
</html>
