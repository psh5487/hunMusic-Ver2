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
<link rel="icon" href="img/favicon.ico" style="user-select: auto;">
<meta name="viewport" content="width=device-width, initial-scale=1">
<!-- css file -->
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

<div class="title">
	<!-- 제목 -->
	<div style="width: 250px">
		<a class="nav-link" href="<%=request.getContextPath()%>/MusicList"><h2 style="color: black">헌 음반 리스트</h2></a>
	</div>
</div>

<div class="content">
	<img class = "searchImg" src="img/search.png" alt="돋보기" style="width:45px; margin-left: 70px; margin-bottom: 20px">
	
	<!-- 검색 결과 알림 -->
	<div style="font-size: 20px; margin-bottom: 10px">검색어 <span class="text-danger">${searchKeyword}</span> 결과 총 <span class="text-danger">${count}</span>건</div>
    
    <!-- 곡 리스트 테이블 -->
    <div class="table-responsive">
    <table class="table">
    <thead class="thead-light">
      <tr>
        <!-- <th>id</th> -->
        <th>Title</th>
        <th>Artist</th>
        <th>Composer</th>
        <th>Category</th>
        <th>NumOfDisc</th>
        <th>Label</th>
        <th>Barcode</th>
        <th></th>
        <th></th>
      </tr>
    </thead>
    
    <tbody>
	<c:forEach items="${list}" var="item" begin="0" end="10" varStatus="vs">
		<tr>
			<%-- <td>${item.id}</td> --%>
			<td>${item.title}
			    <!-- Button to Open the Modal -->
	  			<div data-toggle="modal" id="modalBtn" data-target="#myModal${vs.index}">
	   				<div style="cursor: pointer; color: gray; margin-top: 10px">▼Track</div>
	  			</div>
				
				<!-- The Modal -->
				<div class="modal" data-backdrop="false" id="myModal${vs.index}">
				  <div class="modal-dialog modal-dialog-centered modal-xl">
				    <div class="modal-content">
				    
				      <!-- Modal Header -->
				      <div class="modal-header">
				        <h2 class="modal-title">${item.title}</h2>
				        <button type="button" class="close" data-dismiss="modal">×</button>
				      </div>
				      
				      <!-- Modal body -->
				      <div class="modal-body">
				        <p style="white-space:pre-line;">${item.track}</p>
				      </div>
				      
				      <!-- Modal footer -->
				      <div class="modal-footer">
				        <button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
				      </div>
				      
				    </div>
				  </div>
				</div>
				
			</td>
			<td><p style="white-space:pre;">${item.artist}</p></td>
			<td><p style="white-space:pre-line;">${item.composer}</p></td>
			<td>${item.category}</td>
			<td>${item.numOfDisc}</td>
			<td>${item.label}</td>
			<td>${item.barcode}</td>
			<td>
				<form name="trash_form" id = "trash_form" method="POST" action="DeleteMusicServlet" accept-charset="utf-8">
					<input type="image" src="img/remove.png" name ="trash" id="trashButton" width="21px" height="auto"/>
					<input type="hidden" name="item_barcode" value="${item.barcode}" />
				</form>
			</td>
			<td>
				<form name="edit_form" id = "edit_form" method="GET" action="updateMusic" accept-charset="utf-8">
					<input type="image" src="img/edit.png" name ="edit" id="editButton" width="20px" height="auto"/>
					<input type="hidden" name="barcode" value="${item.barcode}" />
				</form>
			</td>
		</tr>
	</c:forEach>
	<tr>
		<td></td> <td></td> <td></td> <td></td> <td></td> <td></td> <td></td> <td></td> <td></td>
	</tr>
  </tbody>
  </table>
  </div>
</div>	
<br><br>  
	
</body>
</html>
