<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html; charset=UTF-8" %>
<html>
<head>
    <title>Login Page</title>
    <link href="css/bootstrap.css" rel="stylesheet">
    <link href="css/board.css" rel="stylesheet">
    <link href="css/login.css" rel="stylesheet">
</head>
<body>

<div class="container">

    <c:if test="${not empty param.error}">
        <div class="alert alert-danger" role="alert">
            Invalid username and password.
        </div>
    </c:if>

    <form class="form-signin" name='loginForm' action="/j_spring_security_check" method='POST'>
        <h2 class="form-signin-heading">Please sign in</h2>
        <label for="username" class="sr-only">Email address</label>
        <input  id="username" name="username" class="form-control" placeholder="Username" required="" autofocus="">
        <label for="password" class="sr-only">Password</label>
        <input type="password" name="password" id="password" class="form-control" placeholder="Password" required="">
        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
        <button class="btn btn-lg btn-primary btn-block" type="submit">Sign in</button>
    </form>

</div>

</body>
</html>