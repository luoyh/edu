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
  
</style>
<body>
<div class="layui-layout layui-layout-admin">

<%@include file="menu.jsp"%>


  <div class="layui-tab layui-tab-brief">
    <div class="layui-body layui-tab-content site-demo site-demo-body">
      <div class="layui-tab-item layui-show">
        <div class="layui-main" style="" id="app">
          <div class="form-inline">
          	<div class="form-group">
          		<label>手机号:</label>
          		<input type="text" v-model="mobile" class="form-control" />
          	</div>
          	<div class="form-group">
          		<label for="">姓名:</label>
          		<input type="text" v-model="name" class="form-control" />
          	</div>
          	<div class="form-group">
          		<label for="">学校:</label>
          		<input type="text" v-model="school" class="form-control" />
          	</div>
            <button v-on:click="refresh" class="layui-btn">刷新</button>
          </div>
          <table class="layui-table">
        <colgroup>
          <col width="150">
          <col width="200">
          <col>
          <col>
        </colgroup>
        <thead>
          <tr>
            <th>名称</th>
            <th>openid</th>
            <th>电话</th>
            <th>年龄</th>
            <th>学校</th>
            <th>性别</th>
          </tr> 
        </thead>
        <tbody>
          <tr v-for="e in members">
            <td v-text="e.name"></td>
            <td v-text="e.openid"></td>
            <td v-text="e.mobile"></td>
            <td v-text="e.age"></td>
            <td v-text="e.school"></td>
            <td v-text="e.sex"></td>
          </tr>
        </tbody>
      </table>
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
    	var vm = new Vue({
    		el: '#app',
    		data: {
    			mobile: '',
    			name: '',
    			school: '',
    			members: []
    		}, 
    		methods: {
    			refresh: function() {
    				var that = this;
        			$.ajax({
        				url: root + '/admin/member/page',
        				method: 'GET',
        				data: {
        					mobile: that.mobile,
        					name: that.name,
        					school: that.school
        				},
        				success: function(r) {
        					that.members = r.data.data;
        				}
        			});
    			}
    		},
    		mounted: function() {
    			this.refresh();
    		}
    	});
    });
  });
</script>
</html>