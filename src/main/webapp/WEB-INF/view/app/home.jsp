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
body {background: #f2f7fa;    font-family: "Microsoft YaHei",'微软雅黑';}
.m10{margin:10px;}
.m10t{margin-top:10px;}
.m10r{margin-right:10px;}
.m10b{margin-bottom: 10px;}
.m10l{margin-left:10px;}
.p10 {padding:10px;}
.center{text-align: center;}
.hide{display:none;}
[v-cloak] {
  display: none;
}
</style>
<script>root='${root}';</script>
</head>
<body>
<div id="app" v-cloak>
	<div class="container content center">
		<div style="margin-top: 30px;border: 1px solid #ccc; height: 50px;display: flex;align-items: center;font-weight: bold;">
			<span style="flex:1;">今天是2016年5月21日 星期三 欢迎你: ${member.name }</span>
		</div>
		<div style="margin-top:30px;height: 150px;border: 1px solid #ccc; display:flex;align-items: center;">
			<span style="flex: 1;border-right: 1px solid #e0e0e0;">
				<span class="glyphicon glyphicon-th" style="display:block;font-size:2rem;"></span>
				<span style="display:block;">总共答题</span>
				<span>${empty data ? 0 : data[0].total }道</span>
			</span>
			<span style="flex: 1;border-right: 1px solid #e0e0e0;">
				<span class="glyphicon glyphicon-remove-circle" style="display:block;font-size:2rem;"></span>
				<span style="display:block;">错误数量</span>
				<span>${empty data ? 0 : data[0].wrongs }道</span>
			</span>
			<span @click="index" style="flex: 1;color: #336699;">
				<span class="glyphicon glyphicon-road" style="display:block;font-size:2rem;"></span>
				<span>答题练习</span>
			</span>
		</div>
		<div style="margin-top:30px;">
			<div style="border-bottom:1px solid;">
				<div style="position: relative;top: 13px;">
					<h3 style="width: 130px;margin:auto;background: #f2f7fa;">答题记录</h3>
				</div>
			</div>
			<div style="margin-top:30px;">
				<c:forEach var="e" items="${data }">
					<div style="display: flex;border-bottom:1px solid #ccc;padding-bottom: 5px;" class="m10b">
						<span style="color:#fff;display:inline-block;width:30px;height:30px;border-radius:100%;background:#4ebdf4;line-height:30px;">${e.type == -2 ? "练" : "考" }</span>
						<span style="padding: 0 3px;color:#fff;display:inline-block;height:30px;border-radius:100%;background:#cd69b2;line-height:30px;">${e.subjectName }</span>
						<span style="flex:1;text-overflow: ellipsis;white-space: nowrap;overflow: hidden;margin: auto 10px;">${e.suiteTitle }</span>
						<span style="padding: 0 3px;color:#fff;background:#68da73;height: 30px;line-height: 30px;display: inline-block;border-radius:3px;">${e.score }分</span>
					</div>
				</c:forEach>
			<!-- cd69b2 -->
			</div>
		</div>
	</div>
</div>
</body>
<script>
	$(function() { 
		//var 
		vm = new Vue({
			el: '#app',
			data: {
			},
			watch: {
			},
			filters: {
			},
			methods: {
				msg: function(msg) {
					 layer.open({
					    content: msg,
					    skin: 'msg',
					    time: 2
					  });
				},
				index: function() { 
					window.location.href = root + '/app.index/go';
				}
			},
			mounted: function() {
				var that = this;
				this.$nextTick(function() {
					// pass
				});
			}
		});
	});
</script>
</html>