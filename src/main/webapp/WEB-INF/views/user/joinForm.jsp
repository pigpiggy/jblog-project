<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>JBlog</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/jblog.css">
<script type="text/javascript" src="${pageContext.request.contextPath }/assets/js/jquery/jquery-1.12.4.js"></script>
<script>
	$(function(){
			$('#id').change(function(){
				$('#btn-checkid').show();
				$('#check-image').hide();
			});
			$('#btn-checkid').click(function(){
				var id = $('#id').val();
				if(id == ''){
				return;
			}
			// ajax 통신
			$.ajax({
				url : "${pageContext.servletContext.contextPath }/user/api/checkid?id="+id, //문자열로 인식이 되는게 아니라 서버에서 el값으로 먼저 치환후 js통신을 한다.
				type : "GET",
				dataType : "json",
				data : "", //post방식일때 값을 여기에 넣어줌
				success:function(response){
					if(response.result != "success"){
						console.error(response.message);
						return;
					}
					if(response.data == true){
						$('#checkid-msg').text("다른 아이디로 가입해주세요.");
						$('#id').focus();
						$('#id').val("");
						return;
					}else{
						$('#checkid-msg').text("사용할 수 있는 아이디 입니다.");
					}
					$('#btn-checkid').hide();
					$('#check-image').show();
				},
				error : function(xhr, error){ //xmlHttpRequest?
						console.error("error : "+error);
				}
			});
		})
	});
	
	function joinform_check(){
		var name = document.getElementById("name");
		var id = document.getElementById("id");
		var password = document.getElementById("password");
		var agree = document.getElementById("agree-prov");
		
		
		if(name.value == ""){
			alert("이름을 입력하세요.");
			name.focus();
			return false;
		};
		
		if(id.value == ""){
			alert("아이디를 입력하세요.");
			id.focus();
			return false;
		};
		
		if($('#checkid-msg').text()=="" || $('#checkid-msg').text() == "다른 아이디로 가입해주세요."){
			alert("아이디 중복체크를 해주세요.");
			return false;
		}
		
		if(password.value == ""){
			alert("패스워드를 입력하세요.");
			password.focus();
			return false;
		};
		
		if(agree.checked == false){
			alert("약관에 동의해주세요.");
			agree.focus();
			return false;
		}; 
	}
	
</script>
</head>
<body>
	<div class="center-content">
		
		<!-- 메인해더 -->
	 	<a href="">
			<img class="logo" src="${pageContext.request.contextPath}/assets/images/logo.jpg">
		</a>
		<ul class="menu">
				<!-- 로그인 전 메뉴 -->
				<li><a href="${pageContext.servletContext.contextPath }/user/loginForm">로그인</a></li>
				<li><a href="${pageContext.servletContext.contextPath }/user/joinForm">회원가입</a></li>

				<!-- 로그인 후 메뉴 -->
				<!-- 
				<li><a href="">로그아웃</a></li>
				<li><a href="">내블로그</a></li> 
				-->
 		</ul>
 		
	<div id="user">
		
		<form:form modelAttribute="userVo" class="join-form" id="join-form" method="post" 
		action="${pageContext.servletContext.contextPath }/user/join" >
			<label class="block-label" for="name">이름</label>
			<input type="text" name="userName" id="name" value="" />
			
			<label class="block-label" for="id">아이디</label>
			<input type="text" name="id" id="id"/>
			
			<input id="btn-checkid" type="button" value="id 중복체크">
			<img style="display: none" id="check-image" src="${pageContext.servletContext.contextPath }/assets/images/check.png"/>
			<p id="checkid-msg" class="form-error"></p>
			
			<label class="block-label" for="password">패스워드</label>
			<input type="password" id="password" name="password"  value="" />

			<fieldset>
				<legend>약관동의</legend>
				<input id="agree-prov" type="checkbox" name="agreeProv" value="y">
				<label class="l-float">서비스 약관에 동의합니다.</label>
			</fieldset>

			<input type="submit" onClick="return joinform_check();" value="가입하기">

		</form:form>
	</div>
</div>
</body>



</html>