<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>My Page 프로필 업로드</title>

    <!-- favicon 적용  -->
    <link rel="shortcut icon" href="img/favicon.ico" style="user-select: auto;">
    <link rel="icon" href="img/favicon.ico" style="user-select: auto;">
</head>

<body>
<h1>프로필 업로드</h1><hr>

<form method="POST" action="/registerProfile" enctype="multipart/form-data">
    아이디 : <input type="text" name="user_id"> <br>
    파일 : <input type="file" name="file"> <br>
    <button type="submit">등록하기</button>
</form>

</body>
</html>