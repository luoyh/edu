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
.opts {
	padding: 10px;
	margin: 10px;
	border: 1px solid #e0e0e0;
	display: flex;	
}
.qstn {
padding: 10px;
}
.qstn-title {
display: flex;
}
.qstn-type {
	font-size:17px;
	width: 25px;
    height: 25px;
    background: #18b4ed;
    color: #fff;
    text-align: center;
    display: inline-block;
    line-height: 25px;
}
.qstn-cnt {
flex: 1;
display: inline-block;
padding: 0 10px;
}
.opts-chs {
	width: 30px;
    height: 30px;
    border: 1px solid #c8c8c8;
    border-radius: 100%;
    text-align: center;
    font-size: 17px;
    font-weight: bold;
    color: #828282;
    line-height: 30px;
}
.opts-cnt {
flex: 1;
margin-left: 10px;
}
.footer{
    position: fixed;
    width: 100%;
    height: 50px;
    background: #fff;
    bottom: 0;
    display: flex;
    align-items: center;
    text-align: center;
    border-top: 1px solid #ccc;
}
.footer span {
flex: 1;
display: inline-block;
}
.content {
margin-bottom: 50px;
}
.fbr{
    border-right: 1px solid #e0e0e0;
}
.opt-right {
	color: #fff!important;
	/*background: #18b4ed!important;*/
	background: #32b16c!important;
	display: inline-block;
	width: 100%;
	height: 100%;
	border-radius: 100%;
}

.opt-multi {
	color: #fff!important;
	background: #77c7e4!important;
	display: inline-block;
	border-radius: 100%;
}

.opt-wrong {
	color: #fff!important;
	background: #f25e5e!important;
	display: inline-block;
	width: 100%;
	height: 100%;
	border-radius: 100%;
}
.rd {
background: #ccc;
}
.header {
    position: fixed;
    width: 100%;
    height: 50px;
    background: #fff;
    top: 0;
    left: 0;
    display: flex;
    align-items: center;
    text-align: center;
    background: #f0f0f0;
}
</style>
<script>root='${root}';</script>
</head>
<body>
<input type="hidden" id="subject_id" value="${subjectId }" />
<div id="app" v-cloak>
	<div class="container content">
		<h3>第{{questions[current].sort}}题 ({{questions[current].score}}分):</h3>
		<div>
			<div class="qstn-title">
				<span class="qstn-type">{{questions[current].type | qstnType}}</span>
				<span class="qstn-cnt">{{questions[current].title}}</span>
			</div>
			<div v-show="questions[current].adjunct!=''" class="center m10t">
				<img v-if="questions[current].adjunctType==0" v-bind:src="'${root }/down?path='+questions[current].adjunct">
				<audio v-if="questions[current].adjunctType==1" :src="'${root }/down?path='+questions[current].adjunct" controls="controls">
					Your browser does not support the audio element.
				</audio>
				<video v-if="questions[current].adjunctType==2" :src="'${root }/down?path='+questions[current].adjunct" controls="controls">
					Your browser does not support the video tag.
				</video>
			</div>
		</div>
		<div :class="{hide: questions[current].type==4}" v-for="(e, i) in options" class="opts" @click="solve(i)">
			<span class="opts-chs" :class="{'opt-multi': !!multis[i]}" v-html="optFilter(i)"></span>
			<div class="opts-cnt">{{e}}</div>
		</div>
		<div :class="{hide: questions[current].type!=4}" style="padding: 10px;margin: 10px;border: 1px solid #e0e0e0;">
			<textarea v-bind:class="{rd: ro}" v-bind:readonly="ro" v-bind:readonly="ro" v-model="sa" rows="7" style="width:100%;resize: none;" placeholder="在这里回答"></textarea>
			<button @click="sas" class="btn btn-info" style="width:100%;">提交</button>
		</div>
		<div :class="{hide: hidea}">
			<h3>试题详解:</h3>
			<span>{{questions[current].description}}</span>
			<div v-show="questions[current].assAdjunct!=''" class="center m10t">
				<img v-if="questions[current].assAdjunctType==0" v-bind:src="'${root }/down?path='+questions[current].assAdjunct">
				<audio v-if="questions[current].assAdjunctType==1" :src="'${root }/down?path='+questions[current].assAdjunct" controls="controls">
					Your browser does not support the audio element.
				</audio>
				<video v-if="questions[current].assAdjunctType==2" :src="'${root }/down?path='+questions[current].assAdjunct" controls="controls">
					Your browser does not support the video tag.
				</video>
			</div>
		</div>
	</div>
	<div class="header">
		<span style="flex:1;"></span>
		<span style="flex:2;font-size: 2rem;">模拟考试</span>
		<a style="flex:1;text-decoration: none;" class="glyphicon glyphicon-home" href="${root }/wx/home"></a>
	</div>
	<div class="footer">
		<span class="fbr" @click="prev">上一题</span>
		<span class="fbr" @click="next">下一题</span>
		<span class="fbr">{{current+1}}/{{questions.length}}</span>
		<span @click="submit">提交</span>
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
				questions: [{}],
				current: 0,
				options: [],
				answers: {},
				submits: {},
				answer: [],
				clss: ['', 'opt-right', 'opt-wrong'],
				multis: {},
				sa: '',
				ro: false,
				hidea: true,
				finished: false
			},
			watch: {
				current: function(val, old) {
					this.answer = (this.answers[val]||{}).a || [];
					this.options = JSON.parse(this.questions[val].options);
				}
			},
			filters: {
				qstnType: function(t) {
					if (typeof t == undefined) {
						t = 1;
					}
					return ['单', '多', '判', '解'][t - 1];
				},
				optFilter: function(v) {
					return '&radic;';//String.fromCharCode(65 + (v||0));
				}
			},
			methods: {
				optFilter: function(v) {
					var a = String.fromCharCode(65 + (v||0)), cls = 0, ret = String.fromCharCode(65 + (v||0));
					var ans = this.answer || [], r = this.answers[this.current];
					if (ans.length == 0 || !r || !r.d) {
						return ret;
					} else {
						$.each(r.r, function(ir, ie) {
							if (ie == a) {
								ret = '&times;';
								cls = 2;
								return false;
							}
						});
						$.each(ans, function(i, e) {
							if (e == a) {
								ret = '&radic;';
								cls = 1;
							}
						});
						//return this.answer[v]; //this.answer[v]; //'&radic;';//
						return '<span class="'+this.clss[cls]+'">'+ret+'</span>';
					}
				},
				msg: function(msg) {
					 layer.open({
					    content: msg,
					    skin: 'msg',
					    time: 2
					  });
				},
				loadQuestions: function() {
					var that = this;
					$.get(root + '/wx/wronged', {subjectId: $('#subject_id').val()}, function(r) {
						if (r.data && r.data.length > 0) {
							that.questions = r.data;
							that.options = JSON.parse(r.data[0].options);
						} else {
							that.questions = [{}];
							that.options = {};
							layer.open({
								  content: '没有数据',
								  btn: '返回首页',
								  shadeClose: false,
								  yes: function(){
									  window.location.href = root + '/wx/index'
								  }
							});
						}
					});
				},
				prev: function() {
					if (this.current <= 0) {
						return;
					}
					this.current --;
					this.releaseMulti();
				},
				next: function() {
					if (this.current >= this.questions.length - 1) {
						return;
					}
					this.current ++;
					this.releaseMulti();
				},
				solve: function(i) {
					var that = this;
					var qstn = this.questions[this.current];
					var ans = JSON.parse(qstn.answers), r = false, s = String.fromCharCode(65 + i);
					if (that.answers[that.current] && that.answers[that.current].d) {
						return;
					}
					if (qstn.type == 1 || qstn.type == 3) {
						$.each(ans, function(ii, e) {
							if (s == e) {
								r = true;
							}
						});
						that.answers[that.current] = {
							a: ans,
							r: [s],
							s: r ? qstn.score : 0,
							d: 1,
							t: qstn.type,
							id: qstn.id
						};
						that.answer = ans;
						that.hidea = false;
					}
					if (qstn.type == 2) { // multi choose
						r = true;
						var _a = that.answers[that.current] || {};
						_a.a = ans;
						var _ar = _a.r || [];
						if (_ar.indexOf(s) != -1) {
							return;
						}
						_ar.push(s);
						_a.r = _ar;
						if (_ar.length == ans.length) {
							_ar = _ar.sort();
							$.each(ans.sort(), function(ii, e) {
								if (String.fromCharCode(65 + _ar[ii]) != e) {
									return (r = false);
								}
							});
							_a.s = r ? qstn.score : 0;
							_a.d = 1;
							that.hidea = false;
							that.answer = ans;
						} else {
							var _m = JSON.parse(JSON.stringify(that.multis||{}));
							_m[i] = true;
							that.multis = _m;
						}
						_a.t = qstn.type;
						_a.id = qstn.id;
						that.answers[that.current] = _a;
					}
				},
				submit: function() {
					if (this.finished) return;
					this.finished = true;
					var __I = layer.open({type: 2});
					var 
						that = this, 
						msg = '总题数:' + this.questions.length, 
						c = 0, r = 0, s = 0, 
						data = [];
					$.each(that.questions, function(l, m) {
						if (m.type < 4) {
							c ++;
						}
						var _ans = that.answers[l];
						if (_ans) {
							if (m.type < 4 && _ans.s > 0) {
								r ++;
								s += _ans.s;
								data.push(m.wrongId);
							}
						}
					});
					console.log(data);
					layer.open({
						btn: '我知道了',
						content: msg + '<br>选择题:' + c + '<br>正确数:' + r + '<br>得分:' + s
					});

					//layer.close(__I);
					//if(true) return;
					$.ajax({
						url: root + '/wx/wronged/submit',
						method: 'POST',
						data: {
							ids: data.join()
						},
						success: function(r) {
							layer.close(__I);
						}
					});
				},
				releaseMulti: function() {
					this.multis = {};
					this.sav();
					if (this.answers[this.current] && this.answers[this.current].d) {
						this.hidea = false;
					} else {
						this.hidea = true;
					}
				},
				sas: function() {
					var that = this,
					    qstn = this.questions[this.current];
					if (this.answers[this.current] && this.answers[this.current].d) {
						return;
					}
					this.answers[this.current] = {
							a: '',
							r: [this.sa],
							s: 0,
							d: 1,
							t: qstn.type,
							id: qstn.id
					};
					that.hidea = false;
					this.ro = true;
				},
				sav: function() {
					var a = this.answers[this.current]||{};
					if (a && a.t == 4 && a.d == 1) {
						this.ro = true;
						this.sa = a.r[0];
					} else {
						this.ro = false;
						this.sa = '';
					}
				}
			},
			mounted: function() {
				var that = this;
				this.$nextTick(function() {
					that.loadQuestions();
				});
			}
		});
	});
</script>
</html>