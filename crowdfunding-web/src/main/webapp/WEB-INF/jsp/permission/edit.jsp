<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<html>
<head>
	<meta charset="UTF-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<meta name="description" content="">
	<meta name="author" content="caychen">

	<link rel="stylesheet" href="${ctx}/bootstrap/css/bootstrap.min.css">
	<link rel="stylesheet" href="${ctx}/css/font-awesome.min.css">
	<link rel="stylesheet" href="${ctx}/css/main.css">
	<link rel="stylesheet" href="${ctx}/css/doc.min.css">
	<style>
		.tree li {
			list-style-type: none;
			cursor:pointer;
		}
	</style>
</head>
<body>
<nav class="navbar navbar-inverse navbar-fixed-top" role="navigation">
	<div class="container-fluid">
		<div class="navbar-header">
			<div><a class="navbar-brand" style="font-size:32px;" href="user.html">众筹平台 - 用户维护</a></div>
		</div>
		<div id="navbar" class="navbar-collapse collapse">
			<ul class="nav navbar-nav navbar-right">
				<li style="padding-top:8px;">
					<div class="btn-group">
						<button type="button" class="btn btn-default btn-success dropdown-toggle" data-toggle="dropdown">
							<i class="glyphicon glyphicon-user"></i> ${sessionScope.loginUser.permissionname} <span class="caret"></span>
						</button>
						<ul class="dropdown-menu" role="menu">
							<li><a href="#"><i class="glyphicon glyphicon-cog"></i> 个人设置</a></li>
							<li><a href="#"><i class="glyphicon glyphicon-comment"></i> 消息</a></li>
							<li class="divider"></li>
							<li><a href="login.html"><i class="glyphicon glyphicon-off"></i> 退出系统</a></li>
						</ul>
					</div>
				</li>
				<li style="margin-left:10px;padding-top:8px;">
					<button type="button" class="btn btn-default btn-danger">
						<span class="glyphicon glyphicon-question-sign"></span> 帮助
					</button>
				</li>
			</ul>
			<form class="navbar-form navbar-right">
				<input type="text" class="form-control" placeholder="Search...">
			</form>
		</div>
	</div>
</nav>

<div class="container-fluid">
	<div class="row">
		<div class="col-sm-3 col-md-2 sidebar">
			<div class="tree">
				<%@ include file="/WEB-INF/jsp/common/menu.jsp"%>
			</div>
		</div>
		<div class="col-sm-9 col-sm-offset-3 col-md-10 col-md-offset-2 main">
			<ol class="breadcrumb">
				<li><a href="#">首页</a></li>
				<li><a href="#">数据列表</a></li>
				<li class="active">修改</li>
			</ol>
			<div class="panel panel-default">
				<div class="panel-heading">表单数据<div style="float:right;cursor:pointer;" data-toggle="modal" data-target="#myModal"><i class="glyphicon glyphicon-question-sign"></i></div></div>
				<div class="panel-body">
					<form role="form" id="permissionForm">
						<div class="form-group">
							<label for="permissionname">许可名称</label>
							<input type="text" class="form-control" id="permissionname" placeholder="请输入许可名称" value="${permission.name}">
						</div>
						<div class="form-group">
							<label for="url">链接地址</label>
							<input type="text" class="form-control" id="url" placeholder="请输入链接地址" value="${permission.url}">
						</div>
						<button id="saveBtn" type="button" class="btn btn-success"><i class="glyphicon glyphicon-pencil"></i> 修改</button>
						<button id="resetBtn" type="button" class="btn btn-danger"><i class="glyphicon glyphicon-refresh"></i> 重置</button>
					</form>
				</div>
			</div>
		</div>
	</div>
</div>
<div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>
				<h4 class="modal-title" id="myModalLabel">帮助</h4>
			</div>
			<div class="modal-body">
				<div class="bs-callout bs-callout-info">
					<h4>测试标题1</h4>
					<p>测试内容1，测试内容1，测试内容1，测试内容1，测试内容1，测试内容1</p>
				</div>
				<div class="bs-callout bs-callout-info">
					<h4>测试标题2</h4>
					<p>测试内容2，测试内容2，测试内容2，测试内容2，测试内容2，测试内容2</p>
				</div>
			</div>
			<!--
			<div class="modal-footer">
			  <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
			  <button type="button" class="btn btn-primary">Save changes</button>
			</div>
			-->
		</div>
	</div>
</div>
<script src="${ctx}/jquery/jquery-2.1.1.min.js"></script>
<script src="${ctx}/bootstrap/js/bootstrap.min.js"></script>
<script src="${ctx}/script/docs.min.js"></script>
<script src="${ctx}/layer/layer.js"></script>
<script type="text/javascript">
	$(function () {
		$(".list-group-item").click(function(){
			if ( $(this).find("ul") ) {
				$(this).toggleClass("tree-closed");
				if ( $(this).hasClass("tree-closed") ) {
					$("ul", this).hide("fast");
				} else {
					$("ul", this).show("fast");
				}
			}
		});

		$("#saveBtn").on('click', function(){
			var permissionname = $("#permissionname").val();
			if(permissionname == null || permissionname === ''){
				layer.msg("许可名称不能为空，请输入", {time:2000, icon:5, shift:6}, function(){

				});
				return ;
			}

			var url = $("#url").val();
			if(url == null || url === ''){
				layer.msg("链接地址不能为空，请输入", {time:2000, icon:5, shift:6}, function(){

				});
				return;
			}

			var loadingIndex = null;
			$.when($.ajax({
				url:'${ctx}/permission/save',
				type:'post',
				data:{
					name: permissionname,
					url: url,
					id: ${permission.id}
				},
				beforeSend: function () {
					loadingIndex = layer.msg("处理中", {icon: 16});
				}
			})).then(function (data) {
				layer.close(loadingIndex);
				if(data.code == 0){
					layer.msg("许可信息修改成功", {time: 1000, icon: 6}, function(){
						window.location.href = "${ctx}/permission/";
					});
				}else{
					layer.msg(data.message, {time:2000, icon:5, shift:6}, function(){

					});
				}
			}, function(error){
				layer.msg(error.responseText, {time:5000, icon:5, shift:6}, function(){

				});
			});
		});
		
		$("#resetBtn").on('click', function () {
			$("#permissionForm")[0].reset();
		})
	});
</script>
</body>
</html>
