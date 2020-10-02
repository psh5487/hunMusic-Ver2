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
<c:import url="navbar.jsp"></c:import>
<br><br>

<div class="title">
	<!-- 제목 -->
	<div style="width: 250px">
		<a class="nav-link" href="<%=request.getContextPath()%>/addMusicDirectly"><h2 style="color: black">곡 직접 입력</h2></a>
	</div>
</div>

<div class="content">
<form name="music_form" method="POST" action="addMusicDirectly" accept-charset="utf-8">
	<br><h5>바코드 넘버 (필수)</h5>
	<input type = "text" name = "barcode" maxlength="20" id = "input_barcode" class="form-control" placeholder="숫자로만 입력" required pattern="\d*" title="숫자로만 입력하세요."/><br><br>
	
	<h5>장르 (필수)</h5>
	<select name="category" class="form-control" required>
	  <option value="" disabled selected>장르 선택</option>
	  <option value="독주곡">독주곡(Solo)</option>
	  <option value="교향곡">교향곡(Symphony)</option>
	  <option value="콘체르토">콘체르토(Concerto)</option>
	  <option value="실내악">실내악(Chamber)</option>
	  <option value="오페라">오페라(Opera)</option>
	  <option value="기타">기타</option>
	</select>
	<br><br>
	
	<h5>제목 (필수)</h5>
	<input type = "text" name = "title" class="form-control" required/><br><br>
	
	<h5>아티스트 (필수)</h5>
	<textarea rows="1" name="artist" id="artist" class="form-control" placeholder="예) Evgeny Kissin, Seong Jin Cho" required></textarea><br><br>
	
	<h5>트랙 리스트</h5>
	<textarea rows="10" name="track" id="track" class="form-control"></textarea><br><br>
	
	<h5>음반사</h5>
	<input type = "text" name = "label" class="form-control"/><br><br>
	
	<h5>디스크 수</h5>
	<input type = "text" name = "numOfDisc" class="form-control" placeholder="숫자로만 입력" pattern="\d*" title="숫자로만 입력하세요."/><br><br>
	
	<h5>작곡가</h5>
	<select name="composerFromSelect" class="form-control">
	  <option value="" disabled selected>대표 작곡가 선택</option>
	  <option value="" disabled>Last name abc 순서</option>
	  
	  <option value="Bach">Bach</option>
	  <option value="Bartok">Bartok</option> 
	  <option value="Beethoven">Beethoven</option>
	  <option value="Bernstein">Bernstein</option> 
	  <option value="Bizet">Bizet</option> 
	  <option value="Brahms">Brahms</option> 
	  <option value="Britten">Britten</option> 
	  <option value="Chopin">Chopin</option> 
	  <option value="Debussy">Debussy</option> 
	  <option value="Dvorak">Dvorak</option> 
	  <option value="Elgar">Elgar</option> 
	  <option value="Faure">Faure</option> 
	  <option value="Franck">Franck</option> 
	  <option value="Gershwin">Gershwin</option> 
	  <option value="Grieg">Grieg</option> 
	  <option value="Haydn">Haydn</option>
	  <option value="Handel">Handel</option>
	  <option value="Johann Strauss II">Johann Strauss II</option> 
	  <option value="Liszt">Liszt</option> 
	  <option value="Mahler">Mahler</option> 
	  <option value="Mendelssohn">Mendelssohn</option> 
	  <option value="Messiaen">Messiaen</option> 
	  <option value="Mozart">Mozart</option>
	  <option value="Mussorgsky">Mussorgsky</option> 
	  <option value="Paganini">Paganini</option> 
	  <option value="Prokofiev">Prokofiev</option> 
	  <option value="Puccini">Puccini</option> 
	  <option value="Ravel">Ravel</option> 
	  <option value="Rachmaninoff">Rachmaninoff</option> 
	  <option value="Richard Strauss">Richard Strauss</option> 
	  <option value="Rimsky-Korsakov">Rimsky-Korsakov</option> 
	  <option value="Rodrigo">Rodrigo</option> 
	  <option value="Rosssini">Rosssini</option> 
	  <option value="Rubinstein">Rubinstein</option> 
	  <option value="Saint-Saens">Saint-Saens</option>
	  <option value="Salieri">Salieri</option> 
	  <option value="Schoenberg">Schoenberg</option>
	  <option value="Schubert">Schubert</option> 
	  <option value="Schumann">Schumann</option>  
	  <option value="Shostakovich">Shostakovich</option> 
	  <option value="Sibelius">Sibelius</option> 
	  <option value="Stravinsky">Stravinsky</option> 
	  <option value="Tchaikovsky">Tchaikovsky</option> 
	  <option value="Telemann">Telemann</option>
	  <option value="Verdi">Verdi</option> 
	  <option value="Vivaldi">Vivaldi</option>
	  <option value="Wagner">Wagner</option> 
	  <option value="Weber">Weber</option> 
	  <option value="기타">기타</option> 
	</select><br>
	
	<input type = "text" name = "composerFromInput" class="form-control" placeholder="기타일 경우 성만 직접 입력. 예)Bach" />
	<br><br>
			   
	<h5>선호도</h5>
	<input type = "radio" name = "importance" value = "3" id = "input_importance_1" />&nbsp;상&ensp;&ensp;
	<input type = "radio" name = "importance" value = "2" />&nbsp;중&ensp;&ensp;
	<input type = "radio" name = "importance" value = "1" />&nbsp;하<br><br><br>
	
	<p class="text-muted">제출 후, 결과창이 뜰 때까지 기다리세요 :)</p>
	<div class="after_form">
		<a href="<%=request.getContextPath()%>/MusicList?page=0&size=10&sort=registeredAt,desc" class="btn btn-outline-info"> ← 이전 </a> &ensp;
		<input type = "reset" value = "내용지우기" class="btn btn-outline-info"/>&ensp;
		<input type = "submit" value = "제출하기" id = "submit_button" class="btn btn-info"/>
	</div>
</form>
<br><br>
</div>
</body>
</html>
