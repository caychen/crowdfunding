<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
	<meta charset="UTF-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<meta name="description" content="">
	<meta name="author" content="caychen">

	<link rel="stylesheet" href="${applicationScope.ctx}/bootstrap/css/bootstrap.min.css">
	<link rel="stylesheet" href="${applicationScope.ctx}/css/font-awesome.min.css">
	<link rel="stylesheet" href="${applicationScope.ctx}/css/main.css">
	<style>
		.tree li {
			list-style-type: none;
			cursor:pointer;
		}
		table tbody tr:nth-child(odd){background:#F4F4F4;}
		table tbody td:nth-child(even){color:#C00;}

	</style>
</head>
<body>
<nav class="navbar navbar-inverse navbar-fixed-top" role="navigation">
	<div class="container-fluid">
		<div class="navbar-header">
			<div><a class="navbar-brand" style="font-size:32px;" href="#">众筹平台 - 用户维护</a></div>
		</div>
		<div id="navbar" class="navbar-collapse collapse">
			<ul class="nav navbar-nav navbar-right">
				<li style="padding-top:8px;">
					<div class="btn-group">
						<button type="button" class="btn btn-default btn-success dropdown-toggle" data-toggle="dropdown">
							<i class="glyphicon glyphicon-role"></i> ${sessionScope.loginrole.rolename} <span class="caret"></span>
						</button>
						<ul class="dropdown-menu" role="menu">
							<li><a href="#"><i class="glyphicon glyphicon-cog"></i> 个人设置</a></li>
							<li><a href="#"><i class="glyphicon glyphicon-comment"></i> 消息</a></li>
							<li class="divider"></li>
							<li><a href="index.html"><i class="glyphicon glyphicon-off"></i> 退出系统</a></li>
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
			<div class="panel panel-default">
				<div class="panel-heading">
					<h3 class="panel-title"><i class="glyphicon glyphicon-th"></i> 数据列表</h3>
				</div>
				<div class="panel-body">
					<form class="form-inline" role="form" style="float:left;">
						<div class="form-group has-feedback">
							<div class="input-group">
								<div class="input-group-addon">查询条件</div>
								<input id="queryText" class="form-control has-success" type="text" placeholder="请输入查询条件">
							</div>
						</div>
						<button type="button" class="btn btn-warning" id="queryBtn"><i class="glyphicon glyphicon-search"></i> 查询</button>
					</form>
					<button type="button" class="btn btn-danger" onclick="deleteRoles()" style="float:right;margin-left:10px;"><i class=" glyphicon glyphicon-remove"></i> 删除</button>
					<button type="button" class="btn btn-primary" style="float:right;" onclick="window.location.href='${ctx}/role/add'"><i class="glyphicon glyphicon-plus"></i> 新增</button>
					<br>
					<hr style="clear:both;">
					<div class="table-responsive">
						<table class="table  table-bordered">
							<thead>
							<tr >
								<th width="10">#</th>
								<th width="10"><input type="checkbox" id="selectAll"></th>
								<th>名称</th>
								<th width="100">操作</th>
							</tr>
							</thead>
							<tbody id="roleList">

							</tbody>
							<tfoot>
								<tr>
									<td colspan="6" align="center">
										<ul class="pagination">
										</ul>
									</td>
								</tr>
							</tfoot>
						</table>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>

<script src="${applicationScope.ctx}/jquery/jquery-2.1.1.min.js"></script>
<script src="${applicationScope.ctx}/bootstrap/js/bootstrap.min.js"></script>
<script src="${applicationScope.ctx}/script/docs.min.js"></script>
<script src="${applicationScope.ctx}/bootstrap-paginator/bootstrap-paginator.js"></script>
<script src="${applicationScope.ctx}/layer/layer.js"></script>
<script type="text/javascript">
	var pageNum = 1, pageSize = 10;
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

		pageQuery(pageNum, pageSize);

		$("#queryBtn").on('click', function(){
			pageQuery(pageNum, pageSize);
		});

		$("#selectAll").on('click', function(){
			var flag = this.checked;
			$("#roleList :checkbox").each(function(){
				this.checked = flag;
			})
		})
	});
	$("tbody .btn-success").click(function(){
		window.location.href = "assignRole.html";
	});
	$("tbody .btn-primary").click(function(){
		window.location.href = "edit.html";
	});

	function pageQuery(pageNum, pageSize){
		var loadingIndex = null;
		$.when($.ajax({
			url:'${ctx}/role/query',
			method:'post',
			data:{
				pageNum: pageNum,
				pageSize: pageSize,
				queryText: $("#queryText").val()
			},
			dataType:'json',
			beforeSend:function(){
				loadingIndex = layer.msg("查询中...", {icon: 16});
			}
		})).then(function(data){
			layer.close(loadingIndex);
			if(data.code == 0){
				//局部刷新页面
				var pageInfo = data.data;
				var roleList = pageInfo.list;

				var tableContent = '';
				$.each(roleList, function(index, role){
					tableContent += '<tr>';
					tableContent += '  <td>' + (index + 1) + '</td>';
					tableContent += '  <td><input type="checkbox" name="roleid" value="' + role.id + '"></td>';
					tableContent += '  <td>' + role.name+'</td>';
					tableContent += '  <td>';
					tableContent += '      <button type="button" onclick="goAssignPage(' + role.id + ')" class="btn btn-success btn-xs"><i class=" glyphicon glyphicon-check"></i></button>';
					tableContent += '      <button type="button" onclick="goUpdatePage(' + role.id + ')" class="btn btn-primary btn-xs"><i class=" glyphicon glyphicon-pencil"></i></button>';
					tableContent += '	  <button type="button" onclick="deleteRole(' + role.id + ', \'' + role.name + '\')" class="btn btn-danger btn-xs"><i class=" glyphicon glyphicon-remove"></i></button>';
					tableContent += '  </td>';
					tableContent += '</tr>';
				});

				$("#roleList").html(tableContent);

				$(".pagination").bootstrapPaginator({
					currentPage: pageInfo.pageNum,
					totalPages: pageInfo.pages,
					alignment: 'center',
					bootstrapMajorVersion: 3,
					numberOfPages: 5,
					size: 'normal',
					itemTexts: function(type, page, current) {
						switch (type) {
							case "first":
								return "首页";
							case "prev":
								return "上一页";
							case "next":
								return "下一页";
							case "last":
								return "末页";
							case "page":
								return page;
						}
					},
					onPageClicked: function(event, originalEvent, type, page){
						pageQuery(page, pageSize);
					}
				});
			}
		}, function(error){
			layer.msg("分页查询失败...", {icon: 5, shift: 6, time: 2000}, function(){});
		});
	}

	function goUpdatePage(id){
		window.location.href="${ctx}/role/edit?id=" + id;
	}

	function goAssignPage(id){
		window.location.href="${ctx}/role/assign?id=" + id;
	}

	function deleteRole(id, rolename) {
		layer.confirm("删除角色信息：【" + rolename + "】，是否继续？", {icon: 3, title: '提示'}, function(cindex){
			//确认删除
			var loadingIndex = null;
			$.when($.ajax({
				url:'${ctx}/role/delete/' + id,
				type:'delete',
				dataType:'json',
				beforeSend: function () {
					loadingIndex = layer.msg("处理中", {icon: 16});
				}
			})).then(function(response){
				layer.close(loadingIndex);
				if(response.code == 0){
					layer.msg("角色信息删除成功", {time: 1000, icon: 6}, function(){
						window.location.href = "${ctx}/role/";
					});
				}
			}, function(error){
				layer.msg("角色信息删除失败", {time: 2000, icon: 5, shift: 6}, function(){
				});
			});
			layer.close(cindex);
		}, function(cindex){
			//取消删除
			layer.close(cindex);
		});

	}

	function deleteRoles(){
		var boxes = $("#roleList :checkbox:checked");
		var ids = [];
		boxes.each(function(){
			ids.push($(this).val());
		});
		if(boxes.length == 0){
			layer.msg("至少选择一项！", {time: 2000, icon: 5, shift: 6}, function(){
			});
		}else{
			layer.confirm("确认删除角色信息？", {icon: 3, title: '提示'}, function(cindex){
				//确认删除
				var loadingIndex = null;
				$.when($.ajax({
					url:'${ctx}/role/batchdelete?ids=' + (ids.join(",")),
					type:'delete',
					dataType:'json',
					beforeSend: function () {
						loadingIndex = layer.msg("处理中", {icon: 16});
					}
				})).then(function(response){
					layer.close(loadingIndex);
					if(response.code == 0){
						layer.msg("角色信息删除成功", {time: 1000, icon: 6}, function(){
							window.location.href = "${ctx}/role/";
						});
					}
				}, function(error){
					layer.msg("角色信息删除失败", {time: 2000, icon: 5, shift: 6}, function(){
					});
				});
				layer.close(cindex);
			}, function(cindex){
				//取消删除
				layer.close(cindex);
			});
		}
	}
</script>
</body>
</html>
