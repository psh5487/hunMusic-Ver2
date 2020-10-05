<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>음취헌</title>
    <!-- favicon 적용  -->
    <link rel="shortcut icon" href="img/favicon.ico" style="user-select: auto;">
    <link rel="icon" href="img/favicon.ico" style="user-select: auto;">

    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="css/hundbStyle.css">

    <!-- bootstrap -->
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js"></script>

    <!-- context path -->
    <link id="contextPathHolder" data-contextPath="<%=request.getContextPath()%>">

    <%-- axios --%>
    <script src="https://unpkg.com/axios/dist/axios.min.js"></script>
</head>

<body>
<br><br>
<div class="title">
    <!-- 제목 -->
    <div style="width: 200px">
        <a class="nav-link"><h2 style="color: black">로그인</h2></a>
    </div>
</div>

<div class="content">
<form id="logInForm">
    <div class="form-group">
        <label for="email">Email Address</label>
        <input type="email" class="form-control" id="email" name="email" placeholder="Enter email">
    </div>
    <div class="form-group">
        <label for="password">Password</label>
        <input type="password" class="form-control" id="password" name="password" placeholder="Password">
    </div>
    <button class="btn btn-info" id="loginButton">로그인</button>
    <a href="<%=request.getContextPath()%>/joinForm" class="btn btn-outline-info">회원가입</a>
</form>
</div>

</body>
    <script type="text/javascript" src="js/login.js"></script>
</html>