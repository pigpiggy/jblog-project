<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>JBlog</title>
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/assets/css/jblog.css">
<script type="text/javascript"
	src="${pageContext.request.contextPath }/assets/js/jquery/jquery-1.12.4.js"></script>
<script type="text/javascript">
	   $(function(){
	      initData();
	   });
	   
	   //카테고리 리스트 값 가져오기
	   function initData(){
	      $.ajax({
	         type : "GET",
	         dataType : "json",
	         url : "${pageContext.servletContext.contextPath}/catelist",
	         async : false, 
	         success : function(obj){//obj는 컨트롤러/서비스에서 리턴값 받을 변수
	            var resultCate="";
	            for(var i=0; i<obj.length;i++){
	               console.log(obj[i]);
	               var cateNo = obj[i]['cateNo'];
	               resultCate += "<tr class = 'tr'>"
	               resultCate += "<td class = 'no'>" + (obj.length-i) + "</td>"
	               resultCate += "<td>" + obj[i]['cateName'] + "</td>"
	               resultCate += "<td class = 'count'>" + obj[i]['countPost'] + "</td>" //포스트 수 올리기 위해 만든 컬럼
	               resultCate += "<td>" + obj[i]['description'] + "</td>"
	               resultCate += "<td>" + "<a href='${pageContext.servletContext.contextPath }/${authUser.id}/delete/"+cateNo+"' class = 'delete'>" + "<img src='${pageContext.request.contextPath}/assets/images/delete.jpg'></a>" + "</td>"
	               resultCate += "</tr>"
	            }
	            $("#result_data tbody").html(resultCate); //여긴 아래에 상단바 작성 테이블 아이디를 가져와서 테이블 안에 tbody부분에 결과값을 뿌려주는 용도
	         },
	         error : function(xhr,status,error){
	            alert(error+"에러");
	         }
	      })
	      
	   }
		//카테고리 추가 
		$(document).ready(function(){//jsp페이지가 시작되면서 아래 문장을 실행시켜주는 
			$("#btnAddCate").click(
				function(){
					var catename = $("#catename").val();
					var description = $("#description").val();
					$.ajax({
						type : "post",
						data : "catename=" + catename + "&description=" + description,
						url : "${pageContext.servletContext.contextPath}/${authUser.id}/admin/insertcate",
				        async : false, 
						success : function(obj){ //return 값으로 list를 obj로 가져왔
							var resultCate=""; //문자열에 추가해주려고 
				            for(var i=0; i<obj.length;i++){
				               console.log(obj[i]); //요소마다 어떤 게 들어갔는지 확인
				               var cateNo = obj[i]['cateNo'];
				               resultCate += "<tr class = 'tr'>" //문자열이 그 뒤에 들어오면 
				            	if($('.no:eq(0)')[0]==undefined){
				            		resultCate += "<td class='no'>1</td>"
				            	}else{
				             		resultCate += "<td class = 'no'>" + (Number($('.no:eq(0)')[0].innerHTML)+1) + "</td>" //넘버마다 no라는 클래스가 들어감. Number는 숫자 형변환
				            	}
				               resultCate += "<td>" + obj[i]['cateName'] + "</td>"
				               resultCate += "<td class = 'count'>" + obj[i]['countPost'] + "</td>" //포스트 수 올리기 위해 만든 컬럼
				               resultCate += "<td>" + obj[i]['description'] + "</td>"
				               resultCate += "<td>" + "<a href='${pageContext.servletContext.contextPath }/${authUser.id}/delete/"+cateNo+"' class = 'delete'>" + "<img src='${pageContext.request.contextPath}/assets/images/delete.jpg'></a>"  +"</td>"
				               resultCate += "</tr>"
				            }
				            $("#result_data tbody").prepend(resultCate); // prepend - tbody밑에 자식요소 맨 첫번째에 붙여준다. 새로운 값을 붙여주는 맨 위 / append는 맨 아래
						},
						
						error : function(xhr,status,error){
					            alert(error+"에러");
					    }
					})
				}
			)
			preventDelete();
		});	
		
		function preventDelete(){
			for(var i=0; i<document.getElementsByClassName('tr').length; i++ ){//tr 갯수가 여러개니까 for문을 돌려서 delete를 가져옴
				if((document.getElementsByClassName('count')[i].textContent) > 0){ //post 수가 0보다 크면 카테고리 지울 수 없음
					document.getElementsByClassName('delete')[i].addEventListener('click', function(e){ //click햇을 때 이벤트 함수를 실행시켜줌 a 태그 눌렀을 때 함수를 실행해주는데 function에 e라는 인자를 통해서 눌러도 href주소로 안넘어가게 해주는
							e.preventDefault(); //a태그 눌러도 이동 못하게 실행
								alert('삭제할 수 없습니다.');
					})
				}
			}
		}
		
		
		
</script>

</head>
<body>

	<div id="container">

		<!-- 블로그 해더 -->
		<div id="header">
			<h1>
				<a href="${pageContext.servletContext.contextPath }/${authUser.id}">
					${blogVo.blogTitle}</a>
			</h1>
			<ul>
				<c:choose>
					<c:when test='${empty authUser}'>
						<li><a
							href="${pageContext.servletContext.contextPath }/user/loginForm">로그인</a></li>
					</c:when>
					<c:otherwise>
						<li><a
							href="${pageContext.servletContext.contextPath }/user/logout">로그아웃</a></li>
						<li><a
							href="${pageContext.servletContext.contextPath }/${authUser.id}/admin/basic">내블로그
								관리</a></li>
					</c:otherwise>
				</c:choose>
			</ul>
		</div>


		<div id="wrapper">
			<div id="content" class="full-screen">
				<ul class="admin-menu">
					<li class="selected"><a
						href="${pageContext.servletContext.contextPath }/${authUser.id}/admin/basic">기본설정</a></li>
					<li class="selected"><a
						href="${pageContext.servletContext.contextPath }/${authUser.id}/admin/category">카테고리</a></li>
					<li class="selected"><a
						href="${pageContext.servletContext.contextPath }/${authUser.id}/admin/write">글작성</a></li>
				</ul>

				<table class="admin-cat" id="result_data">
					<thead>
						<tr>
							<th>번호</th>
							<th>카테고리명</th>
							<th>포스트 수</th>
							<th>설명</th>
							<th>삭제</th>
						</tr>
					</thead>
					<tbody id="catelist">

					</tbody>
				</table>

				<h4 class="n-c">새로운 카테고리 추가</h4>
				<table id="admin-cat-add">
					<tr>
						<td class="t">카테고리명</td>
						<td><input type="text" id="catename" name="name" value=""></td>
					</tr>
					<tr>
						<td class="t">설명</td>
						<td><input type="text" id="description" name="desc"></td>
					</tr>
					<tr>
						<td class="s">&nbsp;</td>
						<td><input id="btnAddCate" type="submit" value="카테고리 추가"></td>
					</tr>
				</table>

			</div>
		</div>

		<!-- 푸터-->
		<div id="footer">
			<p>
				<strong>Spring 이야기</strong> is powered by JBlog (c)2018
			</p>
		</div>
	</div>
</body>



</html>