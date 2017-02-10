<%@ page language="java" contentType="text/html; charset=utf-8" isELIgnored="false" trimDirectiveWhitespaces="true"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:set var="root" value="<%=request.getContextPath()%>"></c:set>
<c:set var="root" value="<%=request.getContextPath()%>"></c:set>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport" content="width=device-width,initial-scale=1.0,maximum-scale=1.0,user-scalable=0" />
<title>招考重庆</title>
<link rel="stylesheet" href="${root}/static/bootstrap/css/bootstrap.min.css">
<script src="${root}/static/lib/jquery.min.js"></script>
<script src="${root}/static/lib/vue.min.js"></script>
<script src="${root}/static/lay/mobile/layer.js"></script>
<script src="${root}/static/bootstrap/js/bootstrap.min.js"></script>
<style>
body {background: #f2f7fa;}
.p10 {padding:10px;}
.center{text-align: center;}
.hide{display:none;}
[v-cloak] {
  display: none;
}
.subject {
	height: 50px;
	text-align: center;
	width:100%;
	padding: 20px;
	margin-top: 20px;
}
</style>
<script>root='${root}';</script>
</head>
<body>
<div id="app" class="container" v-cloak>
	<div id="register" class="p10">
	  <div class="form-group">
	    <input type="text" class="form-control" id="username" placeholder="姓名">
	  </div>
	  <div class="form-group">
	    <input type="number" class="form-control" id="mobile" placeholder="手机号码">
	  </div>
	  <div class="form-group">
	    <input type="text" class="form-control" id="school" placeholder="学校">
	  </div>
	  <div class="form-group">
	    <input type="number" class="form-control" id="age" placeholder="年龄">
	  </div>
	  <div class="checkbox center">
	    <label>
	      <input class="sex" name="sex" type="radio" value="1"> 男
	    </label>
	    <label>
	      <input class="sex" name="sex" type="radio" value="2" checked> 女
	    </label>
	  </div>
	  <button v-on:click="register" type="button" class="btn btn-primary" style="width:100%;">提交</button>
	</div>
</div>
</body>
<script>
$(document).ajaxComplete(function(evt, xhr) {
	try {
		if('TimeOut' == xhr.getResponseHeader('SessionStatus')) {
			alert('由于长时间无动作，请重新登录');
		}
	} catch(e) {}
});
	$(function() { 
		//var 
		vm = new Vue({
			el: '#app',
			data: {
				step: 0
			},
			methods: {
				msg: function(msg) {
					 layer.open({
					    content: msg,
					    skin: 'msg',
					    time: 2
					  });
				},
				register: function() {
					var that = this;
					var data = {
							name: $('#username').val(),
							mobile: $('#mobile').val(),
							school: $('#school').val(),
							age: $('#age').val(),
							sex: $.trim($('.sex:checked').val())
					};
					if (data.name == '') {
						that.msg('姓名不能为空');
						return;
					}
					if (data.mobile == '') {
						that.msg('手机号码不能为空');
						return;
					} 
					if (data.school == '') {
						that.msg('学校不能为空');
						return;
					}
					if (!/^\d+$/.test(data.age)) {
						that.msg('请输入正确的年龄');
						return;
					}
					var __I = layer.open({type: 2});
					$.ajax({
						url: root + '/wx/register',
						method: 'POST',
						data: data,
						success: function(r) {
							layer.close(__I);
							if (r.code == 200) {
								window.location.href = root + '/wx/index';
							} else {
								that.msg(r.msg);
							}
						}
					});
				},
				drill: function(e) {
					this.step = 2;
				}
			}
		});
	});
</script>
</html>