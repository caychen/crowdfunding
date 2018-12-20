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
	<link rel="stylesheet" href="${applicationScope.ctx}/ztree/zTreeStyle.css">
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
					<ul id="permissionTree" class="ztree"></ul>
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
<script src="${applicationScope.ctx}/ztree/jquery.ztree.all-3.5.min.js"></script>
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

		var setting = {
			async: {
				enable: true,
				url: '${ctx}/permission/load',
			},
			view: {
				selectedMulti: false,
				addDiyDom: function(treeId, treeNode){
					var icoObj = $("#" + treeNode.tId + "_ico"); // tId = permissionTree_1, $("#permissionTree_1_ico")
					if ( treeNode.icon ) {
						icoObj.removeClass("button ico_docu ico_open").addClass("fa fa-fw " + treeNode.icon).css("background", "");
					}
				},
				addHoverDom: function(treeId, treeNode){
					//   <a><span></span></a>
					var aObj = $("#" + treeNode.tId + "_a"); // tId = permissionTree_1, ==> $("#permissionTree_1_a")
					aObj.attr("href", "javascript:;");
					if (treeNode.editNameFlag || $("#btnGroup"+treeNode.tId).length > 0)
						return;
					var s = '<span id="btnGroup' + treeNode.tId + '">';
					if ( treeNode.level == 0 ) {//顶级菜单
						s += '<a class="btn btn-info dropdown-toggle btn-xs" style="margin-left:10px;padding-top:0px;" href="#" onclick="addNode(' + treeNode.id + ')">&nbsp;&nbsp;<i class="fa fa-fw fa-plus rbg "></i></a>';
					} else if ( treeNode.level == 1 ) {//一级菜单
						s += '<a class="btn btn-info dropdown-toggle btn-xs" style="margin-left:10px;padding-top:0px;"  href="#" title="修改权限信息" onclick="editNode(' + treeNode.id + ')">&nbsp;&nbsp;<i class="fa fa-fw fa-edit rbg "></i></a>';
						if (treeNode.children.length == 0) {//如果有子菜单，则添加删除按钮
							s += '<a class="btn btn-info dropdown-toggle btn-xs" style="margin-left:10px;padding-top:0px;" href="#" onclick="delNode(' + treeNode.id + ')">&nbsp;&nbsp;<i class="fa fa-fw fa-times rbg "></i></a>';
						}
						s += '<a class="btn btn-info dropdown-toggle btn-xs" style="margin-left:10px;padding-top:0px;" href="#" onclick="addNode(' + treeNode.id + ')">&nbsp;&nbsp;<i class="fa fa-fw fa-plus rbg "></i></a>';
					} else if ( treeNode.level == 2 ) {//非顶级菜单，也不是一级菜单
						s += '<a class="btn btn-info dropdown-toggle btn-xs" style="margin-left:10px;padding-top:0px;"  href="#" title="修改权限信息" onclick="editNode(' + treeNode.id + ')">&nbsp;&nbsp;<i class="fa fa-fw fa-edit rbg "></i></a>';
						s += '<a class="btn btn-info dropdown-toggle btn-xs" style="margin-left:10px;padding-top:0px;" href="#" onclick="delNode(' + treeNode.id + ')">&nbsp;&nbsp;<i class="fa fa-fw fa-times rbg "></i></a>';
					}

					s += '</span>';
					aObj.after(s);
				},
				removeHoverDom: function(treeId, treeNode){
					$("#btnGroup" + treeNode.tId).remove();
				}
			},
		};
		/*var zNodes =[
			{ name:"父节点1 - 展开", open:true,
				children: [
					{ name:"父节点11 - 折叠",
						children: [
							{ name:"叶子节点111"},
							{ name:"叶子节点112"},
							{ name:"叶子节点113"},
							{ name:"叶子节点114"}
						]},
					{ name:"父节点12 - 折叠",
						children: [
							{ name:"叶子节点121"},
							{ name:"叶子节点122"},
							{ name:"叶子节点123"},
							{ name:"叶子节点124"}
						]},
					{ name:"父节点13 - 没有子节点", isParent:true}
				]},
			{ name:"父节点2 - 折叠",
				children: [
					{ name:"父节点21 - 展开", open:true,
						children: [
							{ name:"叶子节点211"},
							{ name:"叶子节点212"},
							{ name:"叶子节点213"},
							{ name:"叶子节点214"}
						]},
					{ name:"父节点22 - 折叠",
						children: [
							{ name:"叶子节点221"},
							{ name:"叶子节点222"},
							{ name:"叶子节点223"},
							{ name:"叶子节点224"}
						]},
					{ name:"父节点23 - 折叠",
						children: [
							{ name:"叶子节点231"},
							{ name:"叶子节点232"},
							{ name:"叶子节点233"},
							{ name:"叶子节点234"}
						]}
				]},
			{ name:"父节点3 - 没有子节点", isParent:true}

		];*/

		$.fn.zTree.init($("#permissionTree"), setting);

	});

	function addNode(id){
		window.location.href="${ctx}/permission/add?id=" + id;
	}

	function editNode(id){
		window.location.href="${ctx}/permission/edit?id=" + id;
	}

	function delNode(id){
		layer.confirm("删除许可信息，是否继续？", {icon: 3, title: '提示'}, function(cindex){
			//确认删除
			var loadingIndex = null;
			$.when($.ajax({
				url:'${ctx}/permission/delete/' + id,
				type:'delete',
				dataType:'json',
				beforeSend: function () {
					loadingIndex = layer.msg("处理中", {icon: 16});
				}
			})).then(function(response){
				layer.close(loadingIndex);
				if(response.code == 0){
					layer.msg("许可信息删除成功", {time: 1000, icon: 6}, function(){
						var treeObj = $.fn.zTree.getZTreeObj("permissionTree");
						treeObj.reAsyncChildNodes(null, "refresh");
					});
				}
			}, function(error){
				layer.msg("许可信息删除失败", {time: 2000, icon: 5, shift: 6}, function(){
				});
			});
			layer.close(cindex);
		}, function(cindex){
			//取消删除
			layer.close(cindex);
		});
	}

</script>
</body>
</html>
