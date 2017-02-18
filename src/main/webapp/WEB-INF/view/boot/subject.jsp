<%@ page language="java" contentType="text/html; charset=utf-8" isELIgnored="false" trimDirectiveWhitespaces="true"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:set var="root" value="<%=request.getContextPath()%>"></c:set>
<c:set var="root" value="<%=request.getContextPath()%>"></c:set>
<%@include file="include.jsp"%>
<body>
<div class="layui-layout layui-layout-admin">
  
    <%@include file="menu.jsp"%>

  <div class="layui-tab layui-tab-brief" lay-filter="demoTitle">
    <div class="layui-body layui-tab-content site-demo site-demo-body">
      <div class="layui-tab-item layui-show">
        <div class="layui-main" style="" id="app">
          <div>
            <button class="btn btn-primary" v-on:click="add">增加</button>
          </div>
          <table class="table table-bordered">
            <thead>
              <tr>
                <th>科目编号</th>
                <th>科目名称</th>
                <th>创建时间</th>
                <th>操作</th>
              </tr>
            </thead>
            <tbody>
              <tr v-for="e in subjects">
                <td v-text="e.id"></td>
                <td v-text="e.name"></td>
                <td>{{e.gmtCreated | date}}</td>
                <td>
                  <button v-on:click="del(e.id)" class="btn btn-danger">删除</button>
                </td>
              </tr>
            </tbody>
          </table>
        </div>
      </div>
      
    </div>
  </div>
</div>


<div class="modal fade" data-backdrop="static" id="add_modal">
  <div class="modal-dialog">
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span>&times;</span></button>
        <h4 class="modal-title">增加科目</h4>
      </div>
      <div class="modal-body">
        <div class="form-horizontal">
          <div class="form-group">
            <label for="inputEmail3" class="col-sm-2 control-label">科目名称</label>
            <div class="col-sm-10">
              <input type="text" class="form-control" id="subject_name" placeholder="科目名称">
            </div>
          </div>
        </div>
      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
        <button type="button" class="btn btn-primary" id="add_submit">保存</button>
      </div>
    </div><!-- /.modal-content -->
  </div><!-- /.modal-dialog -->
</div><!-- /.modal -->
  
</body>
<script>

  layui.use(['element','layer'], function() {
    var 
      layer = layui.layer;

      $(function() {
    	  $('#subject_name').keypress(function(v) {
    		  if (v.keyCode == 13) {
    			  $('#add_submit').trigger('click');
    		  }
    	  });
        $('#add_submit').click(function() {
          var subject = $('#subject_name').val(), exists = false;
          if ('' == subject) {
            layer.msg('科目名称不能为空');
            return;
          }
          $.each(vm.subjects||[], function(i, e) {
             if (e.name == subject) {
              layer.msg('此科目已经存在，请重新输入');
              exists = true;
              return false;
             }
          });
          if (!exists) {
        	  $.ajax({
        		  url: root + '/admin/subject/insert',
        		  method: 'POST',
        		  data: {name: subject},
        		  success: function(r) {
        			  if (r.code != 200) {
        				  layer.msg(r.msg);
        			  } else {
        				  vm.list();
        		          $('#add_modal').modal('hide');
        			  }
        		  }
        	  });
          }
        });
        var vm = new Vue({
          el: '#app',
          data: {
              subjects: []
          },
          methods: {
            add: function() {
              $('#subject_name').val('');
              $('#add_modal').modal('show');
            },
            list: function() {
            	var that = this;
            	$.ajax({
            		url: root + '/admin/subject/list',
            		method: 'GET',
            		success: function(r) {
            			that.subjects = r.data;
            		}
            	});
            },
            del: function(id) {
            	var that = this;
            	layer.confirm('删除科目将会删除下面的所有试题以及答题记录, 是否继续?', {
           		  icon: 3, title: '警告'
           		}, function(){
           		  $.ajax({
           			  url: root + '/admin/subject/delete',
           			  method: 'POST',
           			  data: {id: id},
           			  success: function(r) {
           				  if (r.code == 200) {
           					  layer.msg('删除成功', {time: 1300}, function() {
           						  that.list();
           					  });
           				  } else {
           					  layer.msg(r.msg);
           				  }
           			  }
           		  });
           		});
            }
          }, 
          mounted: function() {
        	  this.list();
          }
        });
      });
  });
</script>
</html>