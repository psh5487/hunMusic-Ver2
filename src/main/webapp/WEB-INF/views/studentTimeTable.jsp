<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page import="java.util.*" %>

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

<!-- context path -->
<link id="contextPathHolder" data-contextPath="<%=request.getContextPath()%>">
 
<!-- bootstrap -->
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css">
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js"></script>
</head>

<body>
<c:import url="navbar.jsp"></c:import>

	<!-- 시간표 테이블  -->
	<div class="container">
	  <br><br><h2>개인 시간표 입력</h2><br>  
	  <p>💡수업이나 스케줄이 있는 시간을 클릭하세요. </p>  
	  <p>⚠️예를 들어 4시 15분에 끝나는 수업이 있다면, 4-5시 칸도 선택해야 합니다.</p>   
	  
	  <div class="table-responsive">     
	  <table class="table table-bordered">
	    <thead class="table-info">
	      <tr>
	      	<th>시간</th>
	        <th>월</th>
	        <th>화</th>
	        <th>수</th>
	        <th>목</th>
	        <th>금</th>
	      </tr>
	    </thead>
	    <tbody>
	      <tr class="time">
	        <td class="except">1시-2시</td>
	        <td id="M1"></td>
	        <td id="T1"></td>
	        <td id="W1"></td>
	        <td id="TH1"></td>
	        <td id="F1"></td>
	      </tr>
	      <tr class="time">
	        <td class="except">2시-3시</td>
	        <td id="M2"></td>
	        <td id="T2"></td>
	        <td id="W2"></td>
	        <td id="TH2"></td>
	        <td id="F2"></td>
	      </tr>
	      <tr class="time">
	        <td class="except">3시-4시</td>
	        <td id="M3"></td>
	        <td id="T3"></td>
	        <td id="W3"></td>
	        <td id="TH3"></td>
	        <td id="F3"></td>
	      </tr>
	      <tr class="time">
	        <td class="except">4시-5시</td>
	        <td id="M4"></td>
	        <td id="T4"></td>
	        <td id="W4"></td>
	        <td id="TH4"></td>
	        <td id="F4"></td>
	      </tr>
	      <tr class="time">
	        <td class="except">5시-6시</td>
	        <td id="M5"></td>
	        <td id="T5"></td>
	        <td id="W5"></td>
	        <td id="TH5"></td>
	        <td id="F5"></td>
	      </tr> 
	      <tr class="time">
	        <td class="except">6시-7시</td>
	        <td id="M6"></td>
	        <td id="T6"></td>
	        <td id="W6"></td>
	        <td id="TH6"></td>
	        <td id="F6"></td>
	      </tr> 
	    </tbody>
	  </table>
	  </div>
	  
	  <form name="class_form" method="POST" action="OperationTableServlet" accept-charset="utf-8">
		<h5>이름</h5>
		<input type = "text" name = "timeTableOwner" maxlength="20" id = "input_name" class="form-control" /><br>
	  </form>
	  
	  <span id = "submit_button" class="btn btn-info">제출하기</span><br><br>
	  
	  <br><h2>시간표 제출한 사람</h2><br>
	  <c:forEach items="${studentTimeTableAll}" var="item" begin="0" end="${studentTimeTableLength}">
	  	<form name="trash_form" id="trash_form" method="POST" action="deleteStudentTimeTable" accept-charset="utf-8">
			<input type="image" src="img/remove.png" name ="trash" id="trashButton" width="21px" height="auto" />
			<input type="hidden" name="item_id" value="${item.id}" />
			&ensp;<span style="font-size: 20px">${item.name}</span>
		</form>
	  </c:forEach>
	</div><br><br>

</body>
	<script type="text/javascript" src="js/studentTimeTable.js"></script>
</html>
