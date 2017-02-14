<%@ page language="java" contentType="text/html; charset=utf-8" isELIgnored="false" trimDirectiveWhitespaces="true"%>
<%@include file="include.jsp"%>
<style>
img {
max-width: 100%;
}
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
  .opts-slt {
      	display: inline-block;
	    border: 1px solid #ccc;
	    padding: 0 3px;
	    border-radius: 2px;
  }
  .opts-cnt {
      	display: inline-block;
	    border: 1px solid #e0e0e0;
	    margin: 0 10px;
	    padding: 0 10px;
  		width: 490px;
  }
  .opts {
  	margin: 10px;
  	width: 550px;
  }
  
</style>
<script src="${root }/static/lib/ajaxfileupload.js"></script>
<body>
<div class="layui-layout layui-layout-admin">

<%@include file="menu.jsp"%>
  <div class="layui-tab layui-tab-brief" lay-filter="demoTitle">
    <div class="layui-body layui-tab-content site-demo site-demo-body">
      <div class="layui-tab-item layui-show">
        <div class="layui-main" style="" id="app">
          <div class="form-inline">
          	<div class="form-group">
          		<label>科目:</label>
          		<select v-model="param.subjectId" class="form-control">
          			<option value="0">---</option>
          			<c:forEach var="e" items="${subjects }">
          				<option value="${e.id }">${e.name }</option>
          			</c:forEach>
          		</select>
          	</div>
          	<div class="form-group">
          		<label for="">试卷名称:</label>
          		<input v-model="param.suiteTitle" type="text" class="form-control" />
          	</div>
          	<div class="form-group">
          		<label for="">用户名称:</label>
          		<input v-model="param.memberName" type="text" class="form-control" />
          	</div>
          	<div class="form-group">
          		<label for="">成绩类型:</label>
          		<select v-model="param.suiteType" class="form-control">
          			<option value="">--</option>
          			<option value="-1">考试模式</option>
          			<option value="-2">练习模式</option>
          		</select>
          	</div>
            <button @click="search" class="layui-btn">搜索</button>
          </div>
          <table class="table table-bordered">
	        <thead>
	          <tr>
	            <th>科目名称</th>
	            <th>试卷名称</th>
	            <th>用户名称</th>
	            <th>考试类型</th>
	            <th>考试成绩</th>
	            <th>操作</th>
	          </tr> 
	        </thead>
	        <tbody>
        		<tr v-for="e in list">
        			<td>{{e.subjectName}}</td>
        			<td>{{e.suiteTitle}}</td>
        			<td>{{e.memberName}}</td>
        			<td>{{e.type == -1 ? "模拟考试" : "练习模式"}}</td>
        			<td>{{e.score}}</td>
        			<td>
        				<button class="btn btn-info" @click="see(e.id)">查看</button>
        			</td>
        		</tr>
	        </tbody>
	      </table>
	      <div class="col-md-12">
	      	<ul class="pagination"></ul>
	      </div>
        </div>
        
      </div>
      
    </div>
  </div>
</div>

<div id="detail_modal" class="modal fade" tabindex="-1" role="dialog">
  <div class="modal-dialog" role="document">
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
        <h4 class="modal-title">管理</h4>
      </div>
      <div class="modal-body">
      	<div v-for="e in detail">
      		<h3>{{e.sort}}:{{e.title}}</h3>
      		<p v-show="e.images != ''"><img :src="'${root }/down?path='+e.images" /></p>
      		<div v-html="result(e)">
      		</div>
      		<h4>参考答案：</h4>
      		<p>{{JSON.parse(e.answers).join()}}</p>
   			<h5>分析:</h5>
   			<p>{{e.description}}</p>
   			<p v-show="e.assImages != ''"><img :src="'${root }/down?path='+e.assImages" /></p>
      	</div>
      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
      </div>
    </div><!-- /.modal-content -->
  </div><!-- /.modal-dialog -->
</div><!-- /.modal -->
  
</body>
<script>
  layui.use(['element','layer'], function() {
    var 
      layer = layui.layer;
	vm = new Vue({
		el: '#app',
		data: {
			list: [],
			param: {
				subjectId:0,
				suiteTitle: '',
				memberName:'',
				suiteType: '',
				current: 1,
				size: 20
			},
			detail: []
		},
		methods: {
			search: function() {
				var that = this;
				$.ajax({
					url: root + '/admin/result/page',
					data: JSON.parse(JSON.stringify(that.param)),
					success: function(r) {
						that.list = r.data.data;
						$('.pagination').EduPage({
            		        pageSize : r.data.size,
            		        total : r.data.total,
            		        pageNo: r.data.current,
            		        onPageClicked: function(obj, pageIndex) {
            		        	that.param.current = pageIndex;
            		        	that.search();
            		        }
            		  });
					}
				});
			},
			see: function(id) {
				var that = this;
				$.ajax({
					url: root + '/admin/result/questions',
					data: {resultId: id},
					success: function(r) {
						vm1.detail = r.data;
						$('#detail_modal').modal('show');
					}
				});
			}
		},
		mounted: function() {
			var that = this;
			this.$nextTick(function() {
				that.search();
			});
		}
	});    
	vm1 = new Vue({
		el: '#detail_modal',
		data: {
			detail: []
		},
		methods: {
			result: function(item) {
				var that = this;
				if (item.type < 4) {
					var htm = '';
					var opts = JSON.parse(item.options);
					$.each(opts, function(i, e) {
						htm += '<div class="opts"><span class="opts-slt">'+String.fromCharCode(i + 65)+'</span>'+
						'<span class="opts-cnt">'+e+'</span>';
						var ck = false;
						$.each(JSON.parse(item.resultAnswers), function(ii, ee) {
							if (ee == String.fromCharCode(i + 65)) {
								htm += '<span class="glyphicon glyphicon-ok"></span>';
								return false;
							}
						});
						htm += '</div>';
					});
      				return htm;
				} else {
					return '<i>JSON.parse(item.resultAnswers).join()</i>';
				}
				
			}
		}
	});
    
  });
</script>
</html>