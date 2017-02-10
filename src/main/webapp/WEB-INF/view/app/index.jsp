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
.subject {
	height: 50px;
	text-align: center;
	width:100%;
	padding: 20px;
	margin-top: 20px;
}
.ml50 {
margin-left:50px;
}
</style>
<script>root='${root}';</script>
</head>
<body>
<div id="app" v-cloak>
	<div class="container content">
		<div class="row" style="display:flex;height:50px;background:#f0f0f0;text-align: center;align-items: center;">
			<span v-bind:class="{hide: step==1}" class="glyphicon glyphicon-chevron-left" style="width:50px;" @click="step--"></span>
			<span :class="{ml50: step==1}" style="flex: 1;">招考重庆</span>
			<span class="glyphicon glyphicon-home" style="width:50px;" @click="home"></span>
		</div>
		<div class="row" v-bind:class="{hide: step!=1}">
			<div class="col-xs-12 center">
				<h3>科目类型</h3>
			</div>
			<div class="col-xs-12">
				<button v-for="e in subjects" v-on:click="drill(e.id)" class="btn btn-info subject">{{e.name}}</button>
			</div>
		</div>
		<div class="row" v-bind:class="{hide: step!=2}">
			<div class="col-xs-12 center">
				<h3>答题方式</h3>
			</div>
			<div class="col-xs-12">
				<button @click="suite('exam')" class="btn btn-info subject">试卷答题</button>
				<button @click="suite('drill')" class="btn btn-info subject">练习模式</button>
				<a v-bind:href="'${root }/wx/wrong/'+subjectId" class="btn btn-info subject">错题</a>
			</div>
		</div>
		<div class="row" v-bind:class="{hide: step!=3}">
			<div class="col-xs-12 center">
				<h3>选择试卷</h3>
			</div>
			<div class="col-xs-12">
				<a v-for="e in suites" v-bind:href="'${root }/wx/'+pathType+'/'+e.id" class="btn btn-info subject">{{e.title}}</a>
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
				step: 1,
				subjects: [],
				subjectId: 0,
				suites: [],
				pathType: 'drill'
			},
			watch: {
			},
			filters: {
			},
			methods: {
				register: function() {
					this.step = 1;
				},
				drill: function(id) {
					this.step = 2;
					this.subjectId = id;
				},
				msg: function(msg) {
					 layer.open({
					    content: msg,
					    skin: 'msg',
					    time: 2
					  });
				},
				suite: function(p) {
					var that = this;
					that.pathType = p;
					that.step = 3;
					$.get(root + '/admin/suite/by/subject', {subjectId: this.subjectId}, function(r) {
						that.suites = r.data;
					});
				},
				home: function() {
					window.location.href = root + '/wx/home';
				}
			},
			mounted: function() {
				var that = this;
				this.$nextTick(function() {
					$.ajax({
						url: root + '/admin/subject/list',
						success: function(r) {
							if (r.code == 200) {
								that.subjects = r.data;
							} else {
								that.msg(r.msg);
							}
						} 
					});
				});
			}
		});
	});
</script>
</html>