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
<c:import url="navbar.jsp"></c:import>
<br><br>

<div class="title">
	<!-- 제목 -->
	<div style="width: 250px">
		<a class="nav-link" href="<%=request.getContextPath()%>/MusicList?page=0&size=10&sort=registeredAt,desc"><h2 style="color: black">헌 음반 리스트</h2></a>
	</div>
</div>

<div class="content">
	<div class="before_list">
	    <!-- 검색 상자&버튼 -->
	    <div>
			<form name="search_form" id = "search_form" method="POST" action="search" accept-charset="utf-8" class="form-inline">
				<div class="form-group" style="margin-right: 10px">
			 		<input type="text" placeholder="검색" name="search" class="form-control">
			 	</div>
			 	<div class="form-group" style="margin-right: 20px">
			 		<input type="image" src="img/search.png" name="search" id="searchButton" width="20px" height="auto"/>
			 	</div>
			</form>
		</div>
		<div class = "musicAdd_button">  
			<a href="<%=request.getContextPath()%>/addMusicDirectly">
				<button type="button" class="btn btn-info" id="hard_button">+ 곡 직접 추가</button>
			</a>
			<a href="<%=request.getContextPath()%>/addMusicSimply">
				<button type="button" class="btn" id="easy_button">+ 곡 간편 추가</button>
			</a>
		</div>
	</div>
	<br>
    
    <div class="countMusic">Total <span class="text-danger">${count}</span>곡, ${cur}/${pageCount}</div>
	
	
  	<div class="table-responsive">  
  	<table class="table">
    
    <thead class="thead-light">
      <tr>
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

 	<c:forEach items="${list}" var="item" begin="0" end="10" varStatus="vs">
    <tbody>
		<tr>
			<td>${item.title} <br>
	  			<a href="#" data-toggle="modal" id="modalBtn" data-target="#myModal${vs.index}">
	   				<div style="cursor: pointer; color: gray; margin-top: 10px">▼Track</div>
	  			</a>
			</td>
			<td><p style="white-space:pre;">${item.artist}</p></td>
			<td><p style="white-space:pre-line;">${item.composer}</p></td>
			<td>${item.category}</td>
			<td>${item.numOfDisc}</td>
			<td>${item.label}</td>
			<td>${item.barcode}</td>
			<td>
				<form name="edit_form" id = "edit_form" method="GET" action="updateMusic" accept-charset="utf-8">
					<input type="image" src="img/edit.png" name ="edit" id="editButton" width="20px" height="auto"/>
					<input type="hidden" name="barcode" value="${item.barcode}" />
				</form>
			</td>
			<td>
				<form name="trash_form" id = "trash_form" method="POST" action="deleteMusic" accept-charset="utf-8">
					<input type="image" src="img/remove.png" name ="trash" id="trashButton" width="21px" height="auto"/>
					<input type="hidden" name="item_barcode" value="${item.barcode}" />
				</form>
			</td>
		</tr>		
	<%-- </c:forEach> --%>
    </tbody>
  
    <!-- The Modal -->
	<div class="modal" data-backdrop="false" id="myModal${vs.index}">
	  <div class="modal-dialog modal-dialog-centered modal-xl">
	    
	    <div class="modal-content">
	      <!-- Modal Header -->
	      <div class="modal-header">
	        <h5 class="modal-title">${item.title}</h5>
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
    </c:forEach>
    
  </table>
  </div>
  </div>
    
  <!-- pagination -->
  <ul class="pagination justify-content-center">
  	<li class="page-item <c:if test="${begin==1}">disabled</c:if>"><a class="page-link" id="gotoHead" href="MusicList?page=0&size=10&sort=registeredAt,desc">처음</a></li>
    <li class="page-item <c:if test="${begin==1}">disabled</c:if>"><a class="page-link" id="previous" href="MusicList?page=${begin - pageRange -1}&size=10&sort=registeredAt,desc"><</a></li>

    <c:choose>
    <c:when test="${pageCount < begin+pageRange}">
    	<c:forEach var="i" begin="${begin}" end="${pageCount}">
    		<li class="page-item <c:if test="${i==cur}">active</c:if>"> <a class="page-link" id="pageBtn" href="MusicList?page=${i-1}&size=10&sort=registeredAt,desc">${i}</a></li>
    	</c:forEach>
    </c:when>
    
    <c:otherwise>
    	<c:forEach var="i" begin="${begin}" end="${begin+pageRange -1}">
    		<li class="page-item <c:if test="${i==cur}">active</c:if>"> <a class="page-link" id="pageBtn" href="MusicList?page=${i-1}&size=10&sort=registeredAt,desc">${i}</a></li>
    	</c:forEach>
    </c:otherwise>
	</c:choose>

    <li class="page-item <c:if test="${begin==lastBegin}">disabled</c:if>"><a class="page-link" id="next" href="MusicList?page=${begin + pageRange -1}&size=10&sort=registeredAt,desc">></a></li>
    <li class="page-item <c:if test="${begin==lastBegin}">disabled</c:if>"><a class="page-link" id="gotoTail" href="MusicList?page=${pageCount -1}&size=10&sort=registeredAt,desc">끝</a></li>
  </ul>
  
  <br><br>

</body>
</html>


