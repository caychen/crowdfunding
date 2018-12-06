<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="utf-8" %>
<html>
<head>
	<meta charset="UTF-8">
	<title>尚筹网</title>
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<meta name="description" content="">
	<meta name="keys" content="">
	<meta name="author" content="caychen">

	<link rel="stylesheet" href="${applicationScope.ctx}/bootstrap/css/bootstrap.min.css">
	<link rel="stylesheet" href="${applicationScope.ctx}/css/font-awesome.min.css">
	<link rel="stylesheet" href="${applicationScope.ctx}/css/login.css">
	<style>

	</style>
</head>
<body>
<nav class="navbar navbar-inverse navbar-fixed-top" role="navigation">
	<div class="container">
		<div class="navbar-header">
			<div><a class="navbar-brand" href="${ctx}/main" style="font-size:32px;">尚筹网-创意产品众筹平台</a></div>
		</div>
	</div>
</nav>

<div class="container">
	<h1 style="color:red;text-align: center">${param.errorMsg}</h1>
	<form id="loginForm" action="doLogin" method="post" class="form-signin" role="form">
		<h2 class="form-signin-heading"><i class="glyphicon glyphicon-user"></i> 用户登录</h2>
		<div class="form-group has-success has-feedback">
			<input value="caychen" type="text" class="form-control" id="username" name="username" placeholder="请输入登录账号" autofocus>
			<span class="glyphicon glyphicon-user form-control-feedback"></span>
		</div>
		<div class="form-group has-success has-feedback">
			<input value="caychen" type="text" class="form-control" id="password" name="password" placeholder="请输入登录密码" style="margin-top:10px;">
			<span class="glyphicon glyphicon-lock form-control-feedback"></span>
		</div>
		<div class="form-group has-success has-feedback">
			<select class="form-control" >
				<option value="member">会员</option>
				<option value="manager">管理</option>
			</select>
		</div>
		<div class="checkbox">
			<label>
				<input type="checkbox" value="remember-me"> 记住我
			</label>
			<br>
			<label>
				忘记密码
			</label>
			<label style="float:right">
				<a href="reg.html">我要注册</a>
			</label>
		</div>
		<a class="btn btn-lg btn-success btn-block" onclick="dologin()" > 登录</a>
	</form>
</div>
<script src="${ctx}/jquery/jquery-3.2.1.min.js"></script>
<script src="${ctx}/bootstrap/js/bootstrap.min.js"></script>
<script src="${ctx}/layer/layer.js"></script>
<script>
	function dologin() {
		//非空校验
		var loginUsername = $("#username").val();
		if(loginUsername == null || loginUsername === ''){
			layer.msg("用户登录帐号不能为空，请输入", {time:2000, icon:5, shift:6}, function(){

			});
			return ;
		}

		var loginPassword = $("#password").val();
		if(loginPassword == null || loginPassword === ''){
			layer.msg("用户登录密码不能为空，请输入", {time:2000, icon:5, shift:6}, function(){

			});
			return;
		}

		var loadingIndex = null;
		$.when($.ajax({
			url:$("#loginForm").prop("action"),
			type:'post',
			data:{
				username:loginUsername,
				password:loginPassword
			},
			beforeSend: function () {
				loadingIndex = layer.msg("处理中", {icon: 16});
			}
		})).then(function (data) {
			layer.close(loadingIndex);
			if(data.code == 0){
				window.location.href = "main";
			}else{
				layer.msg(data.message, {time:2000, icon:5, shift:6}, function(){

				});
			}
		}, function(error){
			layer.msg(error.responseText, {time:5000, icon:5, shift:6}, function(){

			});
		});
	}
</script>
</body>
</html>
