<%@ page language="java" contentType="text/html; charset=utf-8"
	isELIgnored="false" trimDirectiveWhitespaces="true"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<c:set var="root" value="<%=request.getContextPath()%>"></c:set>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport"
	content="width=device-width,initial-scale=1.0,maximum-scale=1.0,user-scalable=0" />
<title>Insert title here</title>
<link rel="stylesheet"
	href="${root}/static/bootstrap/css/bootstrap.min.css">
<script src="${root}/static/lib/jquery.min.js"></script>
<script src="${root}/static/bootstrap/js/bootstrap.min.js"></script>
<style>
.login {width: 500px;margin:100px auto;border:1px solid #e0e0e0;background:#e0e0e0;padding:40px;border-radius: 10px;}
body {background:#ccc;}
</style>
</head>
<script>root='${root}';</script>
<body>
	<div class="form-horizontal login">
		<div class="form-group">
			<label class="col-sm-2 control-label">Username</label>
			<div class="col-sm-10">
				<input type="text" class="form-control username" value="admin" placeholder="Username">
			</div>
		</div>
		<div class="form-group">
			<label class="col-sm-2 control-label">Password</label>
			<div class="col-sm-10">
				<input type="password" class="form-control password" value="administrator" placeholder="Password">
			</div>
		</div>
		<div class="form-group">
			<div class="col-sm-offset-2 col-sm-10">
				<button type="button" class="btn btn-default">Sign in</button>
			</div>
		</div>
	</div>
</body>
<script>
	$(function() {
		$('.btn-default').click(function() {
			$.post(root+'/login',{
				username: $('.username').val(),
				password: $('.password').val()
			}, function(r) {
				console.log(r);
				if (r.code == 200) {
					window.location.href = root + '/boot.member/go';
				} else {
					alert('用户名或密码错误');
				}
			});
		});
	});
</script>
</html>