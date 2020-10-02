<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
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
<!-- 네브바 -->
<!-- <nav class="navbar fixed-top navbar-expand-md bg-dark navbar-dark"> -->
<nav class="navbar navbar-expand-md bg-dark navbar-dark">
  <a class="navbar-brand" href="<%=request.getContextPath()%>/">음취헌</a>
  <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#collapsibleNavbar">
    <span class="navbar-toggler-icon"></span>
  </button>
  <div class="collapse navbar-collapse" id="collapsibleNavbar">
    <ul class="navbar-nav">
      <li class="nav-item">
        <a class="nav-link" href="<%=request.getContextPath()%>/MusicList">음반 리스트</a>
      </li> 
      <li class="nav-item">
        <a class="nav-link" href="<%=request.getContextPath()%>/chooseMusic">선곡표 만들기</a>
      </li>
      <li class="nav-item">
        <a class="nav-link" href="<%=request.getContextPath()%>/MakingOperationTable">운영시간표</a>
      </li>  
      <li class="nav-item">
        <a class="nav-link" href="<%=request.getContextPath()%>/operationTable">개인시간표 입력</a>
      </li>
    </ul>
  </div>  
</nav>

<!-- <div id="page-wrapper"> -->
	<!-- 사이드바  -->
	<%-- <div id="sidebar-wrapper">
	  <ul class="sidebar-nav">
	    <!-- <li class="sidebar-brand" style="color:#999">운영시간표 짜기</li> --><br>
	    <li><a href="<%=request.getContextPath()%>/operationTable">개인 시간표 입력</a></li>
	    <li><a href="<%=request.getContextPath()%>/MakingOperationTable">운영시간표 짜기 </a></li>
	  </ul>
	</div> --%>
 	 
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
	  <c:forEach items="${classTimeTableAll}" var="item" begin="0" end="${classTimesLength}">
	  	<form name="trash_form" id="trash_form" method="POST" action="DeleteClassTimeServlet" accept-charset="utf-8">
			<input type="image" src="img/remove.png" name ="trash" id="trashButton" width="21px" height="auto" />
			<input type="hidden" name="item_id" value="${item.id}" />
			&ensp;<span style="font-size: 20px">${item.name}</span>
		</form>
	  </c:forEach>
	</div><br><br>
<!-- </div> -->

</body>
	<script type="text/javascript" src="js/operationJS.js"></script>
</html>
