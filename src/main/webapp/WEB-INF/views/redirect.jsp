<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
    <title>알림</title>
</head>
<body>
<script>
    alert('${message}');
    location.href='<c:out value="${pageContext.request.contextPath}" />${url}';
</script>
</body>
</html>