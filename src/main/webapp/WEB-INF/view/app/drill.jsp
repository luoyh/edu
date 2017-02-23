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
<link rel="stylesheet" href="${root}/static/lib/jquery.mobile-1.4.5.min.css">
<script src="${root}/static/lib/jquery.min.js"></script>
<script src="${root}/static/lib/jquery.mobile-1.4.5.min.js"></script>
<script src="${root}/static/lib/vue.min.js"></script>
<script src="${root}/static/lay/mobile/layer.js"></script>
<script src="${root}/static/bootstrap/js/bootstrap.min.js"></script>
<script src="${root}/static/lib/swipe.js"></script>
<style>
body {background: #f2f7fa;    font-family: "Microsoft YaHei",'微软雅黑';}
img {max-width: 100%;}
video {max-width: 100%;}
audio {max-width: 100%;}
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
word-break: break-all;
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
min-height: 30px;
line-height: 30px;
font-weight: bold;
font-size: 1.5rem;
word-break: break-all;
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
    position: absolute;
    left: 0;
    top: 50px;
    bottom: 50px;
    right: 0;
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
	width: 100%;
	height: 100%;
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
background: #ccc!important;
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

/* Swipe 2 required styles */

.swipe {
  overflow: hidden;
  visibility: hidden;
  position: relative;
}
.swipe-wrap {
  overflow: hidden;
  position: relative;
  padding-bottom: 60px;
}
.swipe-wrap > div {
  float:left;
  width:100%;
  position: relative;
}
/* End Swipe 2 required styles */
</style>
<script>root='${root}';</script>
</head>
<body>
<input type="hidden" id="suite_id" value="${suite.id }" />
<input type="hidden" id="subject_id" value="${suite.subjectId }" />
<div id="app" v-cloak>
	<div class="container content">
		<div id="wrapper" class="swipe">
			<div id="scoller" class="scoller swipe-wrap">
				<div v-for="(item, idx) in questions" :style="{height: calcHeight(idx)}">
					<h3>第{{item.sort}}题 ({{item.score}}分):</h3>
					<div class="qstn-title">
						<span class="qstn-type">{{item.type | qstnType}}</span>
						<span class="qstn-cnt">{{item.title}}</span>
					</div>
					<div v-show="item.adjunct!=''" class="center m10t">
						<img v-if="item.adjunctType==0" v-bind:src="'${root }/down?path='+item.adjunct">
						<audio v-if="item.adjunctType==1" :src="'${root }/down?path='+item.adjunct" controls="controls">
							Your browser does not support the audio element.
						</audio>
						<video v-if="item.adjunctType==2" :src="'${root }/down?path='+item.adjunct" controls="controls">
							Your browser does not support the video tag.
						</video>
					</div>
					<div class="out-opts" :id="'opts'+idx">
						<div :class="{hide: item.type==4}" v-for="(e, i) in parse(item.options)" class="opts" @click="solve(i, idx, $event)">
							<span class="opts-chs"> 
								<span>{{String.fromCharCode(i + 65)}}</span>
							</span>
							<div class="opts-cnt">{{e}}</div>
						</div>
					</div>
					<div :class="{hide: item.type!=4}" style="padding: 10px;margin: 10px;border: 1px solid #e0e0e0;">
						<textarea :id="'solve_result'+idx" rows="7" style="width:100%;resize: none;" placeholder="在这里回答"></textarea>
						<button @click="sas(idx)" class="btn btn-info" style="width:100%;">提交</button>
					</div>
					<div style="display:none;" :id="'drilled'+idx">
						<h3>试题详解:</h3>
						<span style="word-break: break-all;">{{item.description}}</span>
						<div v-show="item.assAdjunct!=''" class="center m10t">
							<img v-if="item.assAdjunctType==0" v-bind:src="'${root }/down?path='+item.assAdjunct">
							<audio v-if="item.assAdjunctType==1" :src="'${root }/down?path='+item.assAdjunct" controls="controls">
								Your browser does not support the audio element.
							</audio>
							<video v-if="item.assAdjunctType==2" :src="'${root }/down?path='+item.assAdjunct" controls="controls">
								Your browser does not support the video tag.
							</video>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
	<div class="header">
		<span style="flex:1;"></span>
		<span style="flex:2;font-size: 2rem;">练习</span>
		<span style="flex:1;text-decoration: none;" class="glyphicon glyphicon-home" @click="home"></span>
	</div>
	<div class="footer">
		<span class="fbr" @click="prev">上一题</span>
		<span class="fbr" @click="next">下一题</span>
		<span class="fbr">{{curr+1}}/{{questions.length}}</span>
		<span style="color:#337ab7;" @click="solve(-1, -1, $event)" class="fbr">题解</span>
		<span style="color:#337ab7;" @click="joinWrong()">加入错题</span>
	</div>
</div>
</body>
<script>
	$(function() { 
		//var 
		vm = new Vue({
			el: '#app',
			data: {
				width: (window.innerWidth > 0) ? window.innerWidth : screen.width,
				height: ((window.innerHeight > 0) ? window.innerHeight : screen.height) - 100,
				questions: [{}],
				answers: {},
				clss: ['', 'opt-right', 'opt-wrong'],
				multis: {},
				finished: false,
				joined: [],
				curr: 0,
				drilled: []
			},
			watch: {
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
				calcOptCls: function(item, i) {
					console.log(item, i); 
					return 'opt-right';					
				},
				parse: function(o) {
					return null == o ? [] : JSON.parse(o);//JSON.parse(o);
				},
				calcHeight: function(idx) {
					return idx == this.curr ? 'auto' : (this.height +'px');
					//return 'auto';
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
					$.get(root + '/admin/question/of/suite', {suiteId: $('#suite_id').val()}, function(r) {
						that.questions = r.data;
						that.$nextTick(function() {
							window.mySwipe = Swipe($('#wrapper')[0], {
								continuous: false,
								speed: 0,
								callback: function(index, element) {
									setTimeout(function() {
										that.curr = index;
									}, 300);
									//$('div').animate({scrollTop:0},10);
									$('.ui-page').animate({scrollTop:0},100);
								}
							});
						});
					});
				},
				prev: function() {
					mySwipe.prev();
				},
				next: function() {
					mySwipe.next();
				},
				solve: function(i, idx, event) {
					var 
						that = this,
						dom = $(event.target).closest('.out-opts'),
						qstn = this.questions[this.curr],
					 	anwers = JSON.parse(qstn.answers), 
					 	result = false, 
					 	selectOpts = String.fromCharCode(65 + i);
					
					if (that.answers[that.curr] && that.answers[that.curr].d) {
						return;
					}
					if (i == -1) {
						this.answers[this.curr] = {
								result: [''],
								score: 0,
								d: 1,
								type: qstn.type,
								id: qstn.id
						};
						this._end(this.curr);
					}
					// single choose
					if (qstn.type == 1 || qstn.type == 3) {
						$.each(anwers, function(_i, _e) {
							if (selectOpts == _e) {
								return !(result = true);
							}
						});
						that.answers[that.curr] = {
							result: [selectOpts],
							score: result ? qstn.score : 0,
							d: 1,
							type: qstn.type,
							id: qstn.id
						};
						var allOpts = dom.find('.opts');
						$.each(anwers, function(_j, _m) {
							var d = allOpts.eq(_m.charCodeAt(0)-65).find('.opts-chs span');
							d.html('&radic;');
							d.addClass('opt-right');
							//allOpts.eq(_m.charCodeAt(0)-65).find('.opts-chs').children().addClass('opt-right');
						});
						allOpts.each(function(_ii, _ee) {
							var d = $(this).find('.opts-chs span');
							if (_ii == i) {
								if (result) {
									d.html('&radic;');
									d.addClass('opt-right');
								} else {
									d.html('&times;');
									d.addClass('opt-wrong');
								}
							}
						});
						
						$('#drilled'+idx).show();
					}
					if (qstn.type == 2) { // multi choose
						result = true;
						var answer = that.answers[that.curr] || {};
						var answerResult = answer.result || [];
						if (answerResult.indexOf(selectOpts) != -1) {
							return;
						}
						answerResult.push(selectOpts);
						answer.result = answerResult;
						var allOpts = dom.find('.opts');
						if (answerResult.length == anwers.length) {
							answerResult = answerResult.sort();
							$.each(anwers.sort(), function(ii, e) {
								if (String.fromCharCode(65 + answerResult[ii]) != e) {
									return (result = false);
								}
							});
							answer.score = result ? qstn.score : 0;
							answer.d = 1;
							$.each(answerResult, function(_ii, _ee) {
								var d = allOpts.eq(_ee.charCodeAt(0)-65).find('.opts-chs span');
								d.html('&times;');
								d.removeClass('opt-multi').addClass('opt-wrong');
							});
							$.each(anwers, function(_j, _m) {
								var d = allOpts.eq(_m.charCodeAt(0)-65).find('.opts-chs span');
								d.html('&radic;'); 
								d.removeClass('opt-multi').removeClass('opt-wrong').addClass('opt-right');
								//allOpts.eq(_m.charCodeAt(0)-65).find('.opts-chs').children().addClass('opt-right');
							});
							$('#drilled'+idx).show();
						} else {
							//var _m = JSON.parse(JSON.stringify(that.multis||{}));
							//_m[i] = true;
							//that.multis = _m;
							allOpts.eq(i).find('.opts-chs span').addClass('opt-multi');
						}
						answer.type = qstn.type;
						answer.id = qstn.id;
						if (i == -1) {
							answer.d = 1;
						}
						that.answers[that.curr] = answer;
					}
				},
				submit: function() {
				},
				sas: function(idx) {
					var that = this,
					    qstn = this.questions[this.curr];
					if (this.answers[this.curr] && this.answers[this.curr].d) {
						return;
					}
					this.answers[this.curr] = {
							result: [$('#solve_result'+idx).val()],
							score: 0,
							d: 1,
							type: qstn.type,
							id: qstn.id
					};
					$('#drilled'+idx).show();
					$('#solve_result'+idx).attr({
						readonly: 'readonly',
						disabled: 'disabled'
					}).addClass('rd');
				},
				joinWrong: function() {
					if (this.joined[this.curr] == 1) {
						this.msg('已加入错题');
						return;
					}
					var qstn = this.questions[this.curr], that = this;
					$.ajax({
						url: root + '/wx/wronged/insert',
						method: 'POST',
						data: {
							subjectId: qstn.subjectId,
							suiteId: qstn.suiteId,
							questionId: qstn.id,
							answers: '',
							cnd: 0
						},
						success: function(r) {
							if (r.code == 200) {
								that.msg('加入成功');
								that.joined[that.curr] = 1;
							}
						}
					});
				},
				home: function() {
					window.location.href = root + '/wx/home';
				},
				_end: function(idx) {
					var 
						that = this,
						qstn = that.questions[idx],
						answer = that.answers[idx] || {},
						answers = JSON.parse(qstn.answers),
						allOpts = $('#opts'+idx).find('.opts');

					$('#drilled'+idx).show();
					if (qstn.type < 4) {
						$.each(answer.result || [], function(_ii, _ee) {
							var d = allOpts.eq(_ee.charCodeAt(0)-65).find('.opts-chs span');
							d.html('&times;');
							d.removeClass('opt-multi').addClass('opt-wrong');
						});
						$.each(answers, function(_j, _m) {
							var d = allOpts.eq(_m.charCodeAt(0)-65).find('.opts-chs span');
							d.html('&radic;'); 
							d.removeClass('opt-multi').removeClass('opt-wrong').addClass('opt-right');
							//allOpts.eq(_m.charCodeAt(0)-65).find('.opts-chs').children().addClass('opt-right');
						});
					} else {
						$('#solve_result'+idx).attr({
							readonly: 'readonly',
							disabled: 'disabled'
						}).addClass('rd');
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