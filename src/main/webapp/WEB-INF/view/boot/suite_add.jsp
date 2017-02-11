<%@ page language="java" contentType="text/html; charset=utf-8" isELIgnored="false" trimDirectiveWhitespaces="true"%>
<%@include file="include.jsp"%>
<style>
	.questions {
		border: 1px solid #ccc;
		margin-top: 10px;
		min-height: 20px;
	}
	.questions button {
		margin: 5px;
	}
	.center {
	text-align: center;
	}
	.pointer {cursor: pointer;}
	
</style>
<script src="${root }/static/lib/ajaxfileupload.js"></script>
<body>
<div class="layui-layout layui-layout-admin">

<%@include file="menu.jsp"%>

	<input type="hidden" id="suite_id" value="${suiteId }" />

  <div class="layui-tab layui-tab-brief" lay-filter="demoTitle">
    <div class="layui-body layui-tab-content site-demo site-demo-body">
      <div class="layui-tab-item layui-show">
        <div class="layui-main" style="" id="app">
        	<div class="form-horizontal" style="width:600px;margin:auto;">
			  <div class="form-group">
			    <label class="col-md-2 control-label">试卷名称</label>
			    <div class="col-md-10">
			      <input v-model="suites.title" type="text" class="form-control" placeholder="text">
			    </div>
			  </div>
			  <div class="form-group">
			    <label class="col-md-2 control-label">所属科目</label>
			    <div class="col-md-10">
			    	<select v-model="suites.subjectId" class="form-control">
			    		<c:forEach var="e" items="${subs }">
			    			<option value="${e.id }">${e.name }</option>
			    		</c:forEach>
			    	</select>
			    </div>
			  </div>
			  <div class="form-group">
			    <label class="col-md-2 control-label">年月份</label>
			    <div class="input-group">
			      <div class="input-group-addon">年</div>
			      <input v-model="suites.years" type="text" class="form-control">
			      <div class="input-group-addon">月</div>
			      <input v-model="suites.months" type="text" class="form-control">
			    </div>
			  </div>
			  <div class="form-group">
			  	<label for="" class="col-md-2 control-label">试题</label>
			  	<div class="col-md-10">
			  		<div class="col-md-12">
			  			<button v-on:click="addQuestion" class="btn btn-primary">增加题目</button>
			  		</div>
			  		<div class="col-md-12 questions">
			  			<button class="btn" v-for="(q, i) in questions" v-text="q.sort" v-on:click="edit(i)"></button>
			  		</div>
			  	</div>
			  </div>
			  <div class="form-group">
			  	<label for="" class="col-md-2 control-label">试卷总分数</label>
			  	<div class="col-md-10">
			  		<input type="text" v-model="scoref" readonly class="form-control">
			  	</div>
			  </div>
			  <div class="form-group">
			    <div class="col-md-offset-2 col-md-10">
			      <button v-on:click="insert" type="button" class="btn btn-primary">保存</button>
			      <button v-on:click="back" type="button" class="btn">取消</button>
			    </div>
			  </div>
			</div>
        </div>
      </div>
      
    </div>
  </div>
</div>

<div class="modal fade" data-backdrop="static" id="add_quesion_modal">
  <div class="modal-dialog" role="document">
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
        <h4 class="modal-title">增加题目</h4>
      </div>
      <div class="modal-body">
      	<div class="form-horizontal">
			  <div class="form-group">
			    <label class="col-md-2 control-label">题目编号</label>
			    <div class="col-md-10">
			      <input type="text" id="qstn_code" class="form-control" placeholder="题目编号">
			    </div>
			  </div>
			  <div class="form-group">
			  	<label for="" class="col-md-2 control-label">题目类型</label>
			  	<div class="col-md-10">
			  		<select id="qstn_type" class="form-control">
			  			<option value="1">单选题</option>
			  			<option value="2">多选题</option>
			  			<option value="3">判断题</option>
			  			<option value="4">解答题</option>
			  		</select>
			  	</div>
			  </div>
			  <div class="form-group">
			  	<label for="" class="col-md-2 control-label">题目标题</label>
			  	<div class="col-md-10">
			  		<textarea id="qstn_title" rows="2" class="form-control"></textarea>
			  	</div>
			  </div>
			  <div class="form-group">
			  	<label for="" class="col-md-2 control-label">题目图片</label>
			  	<div class="col-md-10" id="qstn_imagep">
			  		<div class="input-group">
				      <input id="qstn_image" type="text" class="form-control">
				      <div onclick="up(0)" class="input-group-addon pointer">上传图片</div>
				    </div>
			  	</div>
			  </div>
			  <div id="opts_div" class="form-group">
			  	<label for="" class="col-md-2 control-label">题目选项</label>
			  	<div class="col-md-10" id="qoptions">
			  	</div>
			  </div>
			  <div class="form-group">
			  	<label for="" class="col-md-2 control-label">题目答案</label>
			  	<div id="answers" class="col-md-10">
			  	</div>
			  </div>
			  <div class="form-group">
			  	<label for="" class="col-md-2 control-label">解题说明</label>
			  	<div class="col-md-10">
			  		<textarea id="qstn_desc" rows="2" class="form-control"></textarea>
			  	</div>
			  </div>
			  <div class="form-group">
			  	<label for="" class="col-md-2 control-label">答案图片</label>
			  	<div class="col-md-10" id="qstn_ass_imagep">
			  		<div class="input-group">
				      <input id="qstn_ass_image" type="text" class="form-control">
				      <div onclick="up(1)" class="input-group-addon pointer">上传图片</div>
				    </div>
			  	</div>
			  </div>
			  <div class="form-group">
			  	<label for="" class="col-md-2 control-label">题目分数</label>
			  	<div class="col-md-10">
			  		<input id="qstn_score" type="text" class="form-control">
			  	</div>
			  </div>
		</div>
      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
        <button type="button" id="qstn_delete" class="btn btn-danger" data-dismiss="modal">删除</button>
        <button type="button" id="qstn_submit" class="btn btn-primary">保存</button>
      </div>
    </div><!-- /.modal-content -->
  </div><!-- /.modal-dialog -->
</div><!-- /.modal -->

<div id="upload_cnt" style="display: none;">
	<div class="form-horizontal">
		<div class="form-group">
			<label class="col-md-4 control-label">选择图片:</label>
			<div class="col-md-8">
				<input type="file" id="slt_file" class="form-control">
			</div>
		</div>
		<div class="form-group m10t center">
			<button type="button" class="btn btn-default cancel">取消</button>
			<button type="button" class="btn btn-primary upload">上传</button>
		</div>
	</div>
</div>

</body>
<script>

var uphtm = function(id) {
	return '<div class="col-md-12"><div class="form-horizontal"><div class="form-group"><label class="col-md-4 control-label">选择图片:</label><div class="col-md-8"><input name="file" type="file" id="'+id+'" class="form-control"></div></div><div class="form-group m10t center"><button type="button" class="btn btn-default cancel m10r">取消</button><button type="button" class="btn btn-primary upload">上传</button></div></div></div>';
};

var up = function(t) {
  var id = 'up_file_'+new Date().getTime();
	//<div class="form-horizontal"><div class="form-group"><label class="col-md-4 control-label">选择图片:</label><div class="col-md-8"><input type="file" id="slt_file" class="form-control"></div></div><div class="form-group m10t center"><button type="button" class="btn btn-default cancel">取消</button><button type="button" class="btn btn-primary upload">上传</button></div></div>
  layer.open({
    type: 1,
    area: ['420px', '170px'], //宽高
    content: uphtm(id),
    success: function(layero, index) {
      layero.find('.upload').on('click', function() {
        if ($('#'+id).val() == '') {
          layer.msg('请选择文件');
        } else {
          $.ajaxFileUpload({
            url: root + '/admin/suite/upload',
            fileElementId: id,
            secureuri: false,
            dataType: 'json',
            success: function(r) {
              if (r.code == 200) {
                $(t==0?'#qstn_image':'#qstn_ass_image').val(r.msg);
                layer.close(index);
              } else {
                layer.msg(r.msg);
              }
            }
          });
        }
      });
    }
  });
};

  layui.use(['element','layer'], function() {
    var 
      layer = layui.layer;

      $(function() {
      	var initQstn = function(id, optindex) {
      		var opts = {
      				type: 1,
      				options: '[""]',
      				answers: '[]'
      		};
      		if (id > 0) {
      			$('#qstn_submit').data('edit', 1);
      			$('#qstn_submit').data('qid', id);
      			$('#qstn_submit').data('optindex', optindex);
      			var __I = layer.load();
      			$.ajax({
      				async: false,
      				url: root + '/admin/question/load',
      				data: {id: id},
      				success: function(r) {
      					layer.close(__I);
      					if (r.code == 200) {
      						opts = r.data;
      					} else {
      						layer.msg(r.msg, {time: 1300}, function() {
      			              	$('#add_quesion_modal').modal('hide');
      						});
      					}
      				},
      				error: function() {
      					layer.close(__I);
      				}
      			});
      		} else {
      			$('#qstn_submit').data('edit', 0);
      			$('#qstn_submit').data('qid', 0);
      		}
      		$('#qstn_code').val(opts.sort || (+((vm.questions[vm.questions.length - 1]||{}).sort||0) + 1));
      		$('#qstn_type').val(opts.type || 1);
      		$('#qstn_title').val(opts.title || '');
          	$('#qstn_image').val(opts.images || '');
          	$('#qstn_ass_image').val(opts.assImages || '');
      		//$('#qstn_imagep').html('<input id="qstn_image" type="file" class="form-control">');
      		//$('#qstn_ass_imagep').html('<input id="qstn_ass_image" type="file" class="form-control">');
      		$('#qstn_score').val(opts.score || '');
         	$('#qstn_desc').val(opts.description || '');
      		if (opts.type == 1 || opts.type == 2) {
            $('#opts_div').show();
      			var htm = '';
      			$.each(JSON.parse(opts.options), function(i, e) {
      				if (i == 0) {
      					htm += '<div class="input-group col-md-12 m10b">\
							      <div class="input-group-addon opts">A</div>\
							      <input value="'+e+'" type="text" class="form-control">\
							      <div class="input-group-addon" id="add_qoption" style="background: #128067;cursor: pointer;color:#fff;">+</div>\
							  	</div>';
      				} else {
      					htm += '<div class="input-group col-md-12 m10b">\
							      <div class="input-group-addon opts"></div>\
							      <input value="'+e+'" type="text" class="form-control">\
							      <div class="input-group-addon" id="del_qoption" style="font-weight: bold;background: #da5c1d;cursor: pointer;color:#fff;">-</div>\
							  	</div>';
      				}
      			});
      			$('#qoptions').html(htm);
      			optsRefresh(JSON.parse(opts.answers));
      		} else if(opts.type == 3) {
	            var r = opts.answers;
	            $('#answers').html('<label><input '+(r==0?'checked':'')+' name="v_q" type="radio" value="0">正确</label><label><input name="v_q" '+(r==1?'checked':'')+' type="radio" value="1">错误</label>');
	            $('#opts_div').hide();
           } else {
	            $('#opts_div').hide();
	            $('#answers').html('<textarea rows="2" class="form-control"></textarea>');
	      		$('#answers textarea').val(JSON.parse(opts.answers)||'');
		   }

      	};

      	$('#qstn_delete').click(function() {
      		var $id= $('#qstn_submit').data('qid');
      		if ($id > 0) {
      			var __I = layer.load();
          		$.ajax({
          			url: root + '/admin/question/delete',
          			method: 'POST',
          			data: {id: $('#qstn_submit').data('qid')},
          			success: function(r) {
          				layer.close(__I);
          				if (r.code == 200) {
          					vm.questions.splice($('#qstn_submit').data('optindex'), 1);
          					$('#add_quesion_modal').modal('hide');
          				}
          			}
          		});
      		} else {
      			$('#add_quesion_modal').modal('hide');
      		}
      		
      	});
      	
      	$('#qstn_submit').click(function() {
      		var 
      			code = $('#qstn_code').val(), 
      			ok = true, 
      			edit = $(this).data('edit'),
      			optindex = $(this).data('optindex'),
      			$id = $(this).data('qid');
      		if (code == '') {
      			layer.msg('题目编号不能为空');
      			return;
      		}
      		$.each(vm.questions, function(i, e) {
      			if (edit == 1 && i == optindex) {
      				return true;
      			}
      			if (e.sort == code) {
      				layer.msg('题目编号已存在');
      				return (ok = false);
      			}
      		});
      		if (ok) {
      			var options = [], answers = [], qstnType = $.trim($('#qstn_type').val());
      			$('#qoptions input').each(function(i, e) {
      				options.push($(e).val());
      			});
      			if (qstnType == 4) {
      				answers.push($('#answers textarea').val());
      			} else {
      				$('#answers input[name="v_q"]:checked').each(function(i, e){
      					answers.push($(e).val());
      				});
      			}
      			var qobj = {
					id: $id,   					
   					sort: code,
       				title: $('#qstn_title').val(),
       				type: qstnType,
       				suiteId: vm.ssid,
       				subjectId: vm.subjectId,
       				score: $('#qstn_score').val(),
       				options: JSON.stringify(options),
       				answers: JSON.stringify(answers),
       				description: $('#qstn_desc').val(),
       				images: $('#qstn_image').val(),
       				assImages: $('#qstn_ass_image').val()
      			};
      			if (!/^\d+$/.test(qobj.sort)) {
      				layer.msg('编号输入错误,只能是数字');
      				return;
      			}
      			if (!/^\d+$/.test(qobj.score)) {
      				layer.msg('分数输入错误,只能是数字');
      				return;
      			}
      			if ((qstnType == 1 || qstnType == 2 || qstnType == 3) && qobj.answers == '') {
      				layer.msg('请选择一个答案');
      				return;
      			}
      			
      			if (edit == 1) {
      				var __I = layer.load();
      				$.ajax({
      					url: root + '/admin/question/update',
      					method: 'POST',
      					data: qobj,
      					success: function(r) {
   							layer.close(__I);
      						if (r.code == 200) {
      		      				vm.questions[optindex] = qobj;
      			      			$('#add_quesion_modal').modal('hide');
      						} else {
      							layer.msg(r.msg);
      						}
       					}, 
       					error: function() {
   							layer.close(__I);
       					}
      				});
      				
      			} else {
      				var __I = layer.load();
      				qobj.suiteId=0;
      				$.ajax({
      					url: root + '/admin/question/insert',
      					method: 'POST',
      					data: qobj,
      					success: function(r) {
   							layer.close(__I);
      						if (r.code == 200) {
      		      				qobj.id = r.data;
      		          			vm.questions.push(qobj);
      			      			$('#add_quesion_modal').modal('hide');
      						} else {
      							layer.msg(r.msg);
      						}
       					}, 
       					error: function() {
   							layer.close(__I);
       					}
      				});
      			}
      			console.log(JSON.parse(JSON.stringify(vm.questions)));
      		}
      	});
      	$('#qstn_type').change(function() {
      		var v = $.trim($(this).val());
      		if (v == 1 || v == 2) {
      			$('#opts_div').show();
      			optsRefresh();
      		} else if (v == 3) {
      			$('#opts_div').hide();
      			$('#answers').html('<label><input name="v_q" type="radio" value="0">正确</label><label><input name="v_q" type="radio" value="1">错误</label>');
      		} else {
      			$('#opts_div').hide();
      			$('#answers').html('<textarea rows="2" class="form-control"></textarea>');
      		}
      	});
      	$('#qoptions').on('click', '#del_qoption', function() {
      		$(this).parent().remove();
      		optsRefresh();
      	});
      	var optsRefresh = function(answers) {
      		$('#answers').empty();
      		var v = $.trim($('#qstn_type').val());
      		$('#qoptions').children().each(function(i, e){
      			var t = String.fromCharCode(65+i), ck = false;
      			$(e).find('.opts').text(t);
      			if (answers && answers.length > 0) {
      				for(var a in answers) {
	      				if (answers[a] == t) {
	      					ck = true;
	      					break;
	      				}
	      			}
      			}
      			$('#answers').append('<label><input '+(ck?"checked":"")+' name="v_q" type="'+(v==1?'radio':'checkbox')+'" value="'+t+'">'+t+'</label>');
      		});
      	};
      	$('#qoptions').on('click', '#add_qoption', function() {
      		//var opts = String.fromCharCode(65 + $('#qoptions').children().length);
      		$('#qoptions').append('<div class="input-group col-md-12 m10b">\
				      <div class="input-group-addon opts"></div>\
				      <input type="text" class="form-control">\
				      <div class="input-group-addon" id="del_qoption" style="font-weight: bold;background: #da5c1d;cursor: pointer;color:#fff;">-</div>\
				  	</div>');
      		optsRefresh();
      	});
        //var 
          vm = new Vue({
            el: '#app',
            data: {
			  ssid: 0,      
			  scoref: 0,
              suites: {
                title: '',
                subjectId: 1,
                years: '',
                months: '',
                questions: 0,
                score: 0
              },
              questions: [],
              subjectId: 1
            },
            watch: {
            	questions: function(val) {
            		var v = 0;
            		$.each(val, function(i, e) {
            			v += e.score*1;
            		});
            		this.suites.score = v; //  * 10
            		this.scoref = v;
            	}
            },
            methods: {
              add: function() {

              },
              addQuestion: function() {
              	initQstn();
              	$('#add_quesion_modal').modal('show');
              },
              back: function() {
              	window.location.href = root + '/boot.suite/go#hash_suite';
              },
              edit: function(i) {
              	initQstn(this.questions[i].id, i);
              	$('#add_quesion_modal').modal('show');
              },
              upload: function() {
            	  $.ajaxFileUpload({
            		  url: root + '/admin/suite/upload',
            		  fileElementId: 'file',
            		  secureuri: false,
            		  dataType: 'json',
            		  success: function(r) {
            			  console.log(r);
            		  }
            	  });
              },
              insert: function() {
                var that = this;
                that.suites.questions = that.questions.length;
                that.suites.score = that.suites.scoref * 10;
                var s = JSON.parse(JSON.stringify(that.suites));
                delete s.scoref;
                var ids = [];
                $.each(that.questions, function(i, e) {
                	ids.push(e.id);
                });
                if (that.ssid > 0) {
                	$.ajax({
                        url: root + '/admin/suite/update',
                        method: 'POST',
                        data: {suites: JSON.stringify(s), questions: JSON.stringify(ids)},
                        success: function(r) {
                          if (r.code == 200) {
                          	layer.msg('添加成功', {time: 1300}, function() {
                                that.back();
                          	});
                          } else {
                          	layer.msg('修改失败,请重试');
                          }
                        }
                   });
                } else {
                    $.ajax({
                      url: root + '/admin/suite/insert',
                      method: 'POST',
                      data: {suites: JSON.stringify(s), questions: JSON.stringify(ids)},
                      success: function(r) {
                        if (r.code == 200) {
                        	layer.msg('添加成功', {time: 1300}, function() {
                              	that.back();
                        	});
                        } else {
                        	layer.msg('增加失败,请重试');
                        }
                      }
                    });
                }
              }
            }, // end methods
            mounted: function() {
            	var that = this, ssid = $('#suite_id').val();
            	that.ssid = ssid;
            	if (ssid > 0) {
					var __I = layer.load();
                	$.ajax({
                		url: root + '/admin/suite/load/qstn',
                		data: {id: ssid},
                		success: function(r) {
                			layer.close(__I);
                			that.suites = r.data.suite;
                			that.questions = r.data.questions;
                		}
                	});
            	}
            }
          });
      });
  });
</script>
</html>