<%@ page language="java" contentType="text/html; charset=utf-8" isELIgnored="false" trimDirectiveWhitespaces="true"%>
<%@include file="include.jsp"%>
<body>
<div class="layui-layout layui-layout-admin">

<%@include file="menu.jsp"%>


  <div class="layui-tab layui-tab-brief" lay-filter="demoTitle">
    <div class="layui-body layui-tab-content site-demo site-demo-body">
      <div class="layui-tab-item layui-show">
        <div class="layui-main" style="" id="app">
          <div class="form-inline">
          	<div class="form-group">
          		<label>标题:</label>
          		<input type="text" v-model="param.title" class="form-control" />
          	</div>
          	<div class="form-group">
          		<label for="">年份:</label>
          		<input type="text" v-model="param.years" class="form-control" />
          	</div>
          	<div class="form-group">
          		<label for="">月份:</label>
          		<input type="text" v-model="param.months" class="form-control" />
          	</div>
          	<div class="form-group">
          		<label for="">科目:</label>
          		<select class="form-control" v-model="param.subject">
          			<option v-for="e in subjects" v-bind:value="e.id">{{e.name}}</option>
          		</select>
          	</div>
            <button v-on:click="search" class="layui-btn">搜索</button>
            <button v-on:click="add" class="layui-btn">增加</button>
          </div>
          <table class="table table-bordered">
	        <thead>
	          <tr>
	            <th>试卷名称</th>
	            <th>所属科目</th>
	            <th>年月份</th>
	            <th>题目数量</th>
	            <th>创建时间</th>
	            <th>操作</th>
	          </tr> 
	        </thead>
	        <tbody>
	          <tr v-for="s in suites">
	            <td v-text="s.title"></td>
	            <td v-text="s.subject"></td>
	            <td v-text="s.years + '年' + s.months"></td>
	            <td v-text="s.questions"></td>
	            <td>{{s.gmtCreated | date}}</td>
	            <td>
	              <button @click="del(s.id)" class="layui-btn layui-btn-small layui-btn-danger">删除</button>
	              <button v-on:click="edit(s.id)" class="layui-btn layui-btn-small layui-btn-normal">编辑</button>
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

  
</body>
<script>
  layui.use(['element','layer'], function() {
    var 
      layer = layui.layer;

      $(function() {
        //var 
          vm = new Vue({
            el: '#app',
            data: {
              suites: [],
              subjects: [{}],
              param: {
            	  title: '',
            	  years: '',
            	  months: '',
            	  subject: 0,
            	  current: 1,
            	  size: 20
              }
            },
            methods: {
              add: function() {
                window.location.href = root + '/admin/suite/modify';
              }, 
              search: function() {
            	  var that = this;
            	  $.ajax({
            		  url: root + '/admin/suite/page',
            		  data: that.param,
            		  success: function(r) {
            			  that.suites = r.data.data;
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
              edit: function(id) {
            	  window.location.href = root + '/admin/suite/modify?id='+id
              },
              del: function(id) {
            	  var that = this;
            	  $.ajax({
              		url: root + '/admin/suite/delete',
              		method: 'POST',
              		data: {id: id},
              		success: function(r) {
              			that.search();
              		}
              	}); 
              }
            },
            mounted: function() {
            	var that = this;
            	this.$nextTick(function() {
            		$.ajax({
                		url: root + '/admin/subject/list',
                		success: function(r) {
                			that.subjects = r.data;
                		}
                	});
                	that.search();
            	});
            }
          });
      });
  });
</script>
</html>