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
<br>

<div class="container">
	<!-- 제목 -->
	<br><h2>선곡표 만들기</h2><br>

	<img class = "chooseMusicImg" src="img/music_player.png" alt="선곡표 만들기">
	<span style="font-size: 18px; margin-left: 20px">" Music List에 있는 곡들을 랜덤으로 뽑아 생성합니다. "</span>
	<!-- <span style="font-size: 18px; margin-left: 20px">" Music List에 있는 곡들 중 [시대 + 작곡가 + 선호도] 를 고려하여 랜덤으로 생성됩니다. "</span> -->
	
	<form name="class_form" method="POST" action="chooseMusic" accept-charset="utf-8">
		<br><br>
		<!-- <input type = "text" name = "chooseMusicName" id = "input_name" class="form-control" placeholder="선곡표 이름을 입력하세요" required/><br> -->
		<input type = "submit" value = "    선곡표 짜기    " id = "submit_button" class="btn btn-info"/>
	</form>
	<br><br>
	
	<br><h2>결과</h2><br>
	
	<div class="table-responsive">
	<table class="table table-bordered">
		<thead class="thead-light">
			<tr>
		    	<th>Time</th>
		        <th>Record Title</th>
		        <th>Artist</th>
		        <th>Composer</th>
		        <!-- <th>Category</th> -->
		        <!-- <th>Label</th> -->
		    </tr>
		</thead>
	  
		<tbody>
			<tr>
		      <td class="table-warning">Monday 2pm</td>
		      <td>${choosedMusicAll.get(0).title}</td>
		      <td>${choosedMusicAll.get(0).artist}</td>
		      <td>${choosedMusicAll.get(0).composer}</td>
		    </tr>
		    <tr>
		      <td class="table-warning">Monday 3pm</td>
		      <td>${choosedMusicAll.get(1).title}</td>
		      <td>${choosedMusicAll.get(1).artist}</td>
		      <td>${choosedMusicAll.get(1).composer}</td> 
		    </tr>
		    <tr>
		      <td class="table-warning">Monday 4pm</td>
		      <td>${choosedMusicAll.get(2).title}</td>
		      <td>${choosedMusicAll.get(2).artist}</td>
		      <td>${choosedMusicAll.get(2).composer}</td>
		    </tr>
		    <tr>
		      <td class="table-warning">Tuesday 2pm</td>
		      <td>${choosedMusicAll.get(3).title}</td>
		      <td>${choosedMusicAll.get(3).artist}</td>
		      <td>${choosedMusicAll.get(3).composer}</td>
		    </tr>
		    <tr>
		      <td class="table-warning">Tuesday 3pm</td>
		      <td>${choosedMusicAll.get(4).title}</td>
		      <td>${choosedMusicAll.get(4).artist}</td>
		      <td>${choosedMusicAll.get(4).composer}</td>
		    </tr>  
		    <tr>
		      <td class="table-warning">Tuesday 4pm</td>
		      <td>${choosedMusicAll.get(5).title}</td>
		      <td>${choosedMusicAll.get(5).artist}</td>
		      <td>${choosedMusicAll.get(5).composer}</td>
		    </tr>
		    <tr>
		      <td class="table-warning">Wednesday 2pm</td>
		      <td>${choosedMusicAll.get(6).title}</td>
		      <td>${choosedMusicAll.get(6).artist}</td>
		      <td>${choosedMusicAll.get(6).composer}</td>
		    </tr>
		    <tr>
		      <td class="table-warning">Wednesday 3pm</td>
		      <td>${choosedMusicAll.get(7).title}</td>
		      <td>${choosedMusicAll.get(7).artist}</td>
		      <td>${choosedMusicAll.get(7).composer}</td>
		    </tr>  
		    <tr>
		      <td class="table-warning">Wednesday 4pm</td>
		      <td>${choosedMusicAll.get(8).title}</td>
		      <td>${choosedMusicAll.get(8).artist}</td>
		      <td>${choosedMusicAll.get(8).composer}</td>
		    </tr> 
		    <tr>
		      <td class="table-warning">Thursday 2pm</td>
		      <td>${choosedMusicAll.get(9).title}</td>
		      <td>${choosedMusicAll.get(9).artist}</td>
		      <td>${choosedMusicAll.get(9).composer}</td>
		    </tr>
		    <tr>
		      <td class="table-warning">Thursday 3pm</td>
		      <td>${choosedMusicAll.get(10).title}</td>
		      <td>${choosedMusicAll.get(10).artist}</td>
		      <td>${choosedMusicAll.get(10).composer}</td>
		    </tr>  
		    <tr>
		      <td class="table-warning">Thursday 4pm</td>
		      <td>${choosedMusicAll.get(11).title}</td>
		      <td>${choosedMusicAll.get(11).artist}</td>
		      <td>${choosedMusicAll.get(11).composer}</td>
		    </tr>
		    <tr>
		      <td class="table-warning">Friday &ensp; 2pm</td>
		      <td>${choosedMusicAll.get(12).title}</td>
		      <td>${choosedMusicAll.get(12).artist}</td>
		      <td>${choosedMusicAll.get(12).composer}</td>
		    </tr>
		    <tr>
		      <td class="table-warning">Friday &ensp; 3pm</td>
		      <td>${choosedMusicAll.get(13).title}</td>
		      <td>${choosedMusicAll.get(13).artist}</td>
		      <td>${choosedMusicAll.get(13).composer}</td>
		    </tr>  
		    <tr>
		      <td class="table-warning">Friday &ensp; 4pm</td>
		      <td>${choosedMusicAll.get(14).title}</td>
		      <td>${choosedMusicAll.get(14).artist}</td>
		      <td>${choosedMusicAll.get(14).composer}</td>
		    </tr>
		</tbody>
	</table>
	</div>
</div><br><br>
</body>
</html>




