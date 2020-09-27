<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>View Test Page</title>
    </head>
<body>
    <div>JSP List Test</div>
    ${name}
    ${list}
    <c:forEach var="item" items="${list}" varStatus="idx">
        ${idx.index}, ${item} <br />
    </c:forEach>
</body>
</html>
