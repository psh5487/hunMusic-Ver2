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
</head>

<body>
<br><br>
<div class="title">
    <!-- 제목 -->
    <div style="width: 200px">
        <a class="nav-link"><h2 style="color: black">회원가입</h2></a>
    </div>
</div>

<div class="content">
    <form name="join_form" method="POST" action="/join" accept-charset="utf-8">
        <div class="form-group">
            <label for="email">Email Address</label>
            <input type="email" class="form-control" name="email" id="email" placeholder="Enter email" required>
        </div>
        <div class="form-group">
            <label for="name">이름</label>
            <input type="text" class="form-control" name="name" id="name" placeholder="이름을 입력하세요" maxlength="20" required>
        </div>
        <div class="form-group">
            <label for="password">Password</label>
            <input type="password" class="form-control" name="password" id="password" placeholder="Password">
        </div>
<%--        <div class="form-group">--%>
<%--            <label for="profile_img">Profile Image (선택)</label>--%>
<%--            <input type="file" class="form-control" id="profile_img">--%>
<%--        </div>--%>

        <button type="submit" class="btn btn-info">가입</button>
    </form>
</div>

</body>
</html>