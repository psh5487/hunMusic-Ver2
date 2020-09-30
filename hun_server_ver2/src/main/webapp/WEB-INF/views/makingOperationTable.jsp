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
 	 
	<div class="container">
	  <br><br><h2>시간표 제출한 사람</h2><br>
	  
	  <c:forEach items="${classTimeTableAll}" var="item" begin="0" end="${classTimesLength}">
	  	<span style="font-size: 15px">${item.name}</span>&ensp;
	  </c:forEach><br><br>
	  
	  <br><h2>운영 시간표 이름</h2><br>
	  <!-- <form name="operationTable_form" method="POST" action="MakingOperationTable" accept-charset="utf-8">
		<input type = "text" name = "operationTable_name" required maxlength="50" id = "input_name" class="form-control"/><br> 
	  	<input type="submit" value="운영 시간표 짜기" class="btn btn-info"/>
	  </form><br><br> -->
	  
	  <form method="POST" action="MakingOperationTable" accept-charset="utf-8">
	      <input type = "text" name = "operationTable_name" required maxlength="50" id = "input_name" class="form-control"/><br> 
	      <input type="submit" value="운영시간표 짜기 (타임당 1명)" class="btn btn-info"/> &ensp;
	      <input type="submit" formaction="MakingOperationTable2" value="운영시간표 짜기 (타임당 2명)" class="btn btn-info">
	  </form>
	
	  <br><h2>운영 시간표</h2><br>
	  <c:forEach items="${operationTableAll}" var="item" begin="0" end="${operationTablesLength}">
		  <form name="trash_form" id="trash_form" method="POST" action="DeleteOperationTableServlet" accept-charset="utf-8" style="margin-bottom: 10px; ">
		  		<span style="font-size: 25px">${item.name}</span> &ensp;
				<input type="image" src="img/remove.png" name ="trash" id="trashButton" width="21px" height="auto" />
				<input type="hidden" name="item_id" value="${item.id}" />
		  </form>
		  
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
		      <tr>
		        <td class="table-warning">1시</td>
		        <td id="M1">${item.m1}</td>
		        <td id="T1">${item.t1}</td>
		        <td id="W1">${item.w1}</td>
		        <td id="TH1">${item.TH1}</td>
		        <td id="F1">${item.f1}</td>
		      </tr>
		      <tr>
		        <td class="table-warning">2시</td>
		        <td id="M2">${item.m2}</td>
		        <td id="T2">${item.t2}</td>
		        <td id="W2">${item.w2}</td>
		        <td id="TH2">${item.TH2}</td>
		        <td id="F2">${item.f2}</td>
		      </tr>
		      <tr>
		        <td class="table-warning">3시</td>
		        <td id="M3">${item.m3}</td>
		        <td id="T3">${item.t3}</td>
		        <td id="W3">${item.w3}</td>
		        <td id="TH3">${item.TH3}</td>
		        <td id="F3">${item.f3}</td>
		      </tr>
		      <tr>
		        <td class="table-warning">4시</td>
		        <td id="M4">${item.m4}</td>
		        <td id="T4">${item.t4}</td>
		        <td id="W4">${item.w4}</td>
		        <td id="TH4">${item.TH4}</td>
		        <td id="F4">${item.f4}</td>
		      </tr>
		      <tr>
		        <td class="table-warning">5시</td>
		        <td id="M5">${item.m5}</td>
		        <td id="T5">${item.t5}</td>
		        <td id="W5">${item.w5}</td>
		        <td id="TH5">${item.TH5}</td>
		        <td id="F5">${item.f5}</td>
		      </tr>  
		    </tbody>
		  </table>
		  </div>
	  </c:forEach>
	
	</div><br>
<!-- </div> -->

</body>
</html>
