<%@ page language="java" contentType="text/html; charset=UTF-8"
		 pageEncoding="UTF-8" isELIgnored="false"%>

<script>
    $(function(){
		// 显示错误提示信息
        <c:if test="${!empty msg}">
			$("span.errorMessage").html("${msg}");
			$("div.resetPasswordErrorMessageDiv").css("visibility","visible");
        </c:if>

		$("#password").keyup(checkPassword = function(){
			var password = $("#password").val();
			var regex = /^(?=.*[a-zA-Z])(?=.*\d)(?=.*[#@!~%^&*])[a-zA-Z\d#@!~%^&*_]{8,16}$/;    //密码需要符合的正则表达式

			if(password.length == 0){    //密码不能为空
				$("span.errorMessage").html("密码为空，请您输入密码");
				$("div.resetPasswordErrorMessageDiv").css("visibility", "visible");
				return false;
			}else if(!regex.test(password)){    //密码不符合要求
				$("span.errorMessage").html("密码长度为8-16位，且应包含字母、数字和_ & *等特殊符号");
				$("div.resetPasswordErrorMessageDiv").css("visibility", "visible");
				return false;
			}

			$("span.errorMessage").html("");
			$("div.resetPasswordErrorMessageDiv").css("visibility", "hidden");
			return true;
		}).focus(function(){
			var phone = $("#password").val();

			if(phone.length == 0){    //密码不能为空
				$("span.errorMessage").html("密码为空，请您输入密码");
				$("div.resetPasswordErrorMessageDiv").css("visibility", "visible");
				return false;
			}

			return true;
		});

		$("#repeatpassword").keyup(checkRepeatPassword = function(){
			var repeatPassword = $("#repeatpassword").val();
			var password = $("#password").val();

			if(repeatPassword.length == 0){    //确认密码不能为空
				$("span.errorMessage").html("请您再次确认密码");
				$("div.resetPasswordErrorMessageDiv").css("visibility", "visible");
				return false;
			}else if(password != repeatPassword){    //两次输入的密码不一致
				$("span.errorMessage").html("您两次输入的密码不一致");
				$("div.resetPasswordErrorMessageDiv").css("visibility", "visible");
				return false;
			}

			$("span.errorMessage").html("");
			$("div.resetPasswordErrorMessageDiv").css("visibility", "hidden");
			return true;
		}).focus(function(){
			var repeatPassword = $("#repeatpassword").val();

			if(repeatPassword.length == 0){    //确认密码不能为空
				$("span.errorMessage").html("请您再次确认密码");
				$("div.resetPasswordErrorMessageDiv").css("visibility", "visible");
				return false;
			}

			return true;
		});

		$(".resetPassword").click(function () {
			var phone = $("#phone").val();
			var password = $("#password").val();
			var page = "foreResetPassword";

			if (checkPassword() && checkRepeatPassword()){    //密码和确认密码通过校验，进行密码重置
				$.post(
					page,
					{"phone":phone, "password":password},

					function (result) {
						if (result == "success"){    //密码重置成功，请求跳转至成功页面
							window.location.href="foreResetPasswordSuccess";
						}
					}
				);
			}
		});
    })
</script>
<style>
	div.resetSuccessDiv {
		margin: 10px 20px;
		background-color: #F3FDF6;
		border: 1px solid #DEF3E6;
		font-size: 16px;
		color: #3C3C3C;
		padding: 20px 130px;
	}

	div.resetPasswordDiv {
		margin: 10px 20px;
		text-align: center;
	}

	table.resetPasswordTable {
		color: #3C3C3C;
		font-size: 16px;
		table-layout: fixed;
		margin-top: 50px;
	}

	table.resetPasswordTable td {
		/* 	border:1px dotted skyblue !important; */
		padding: 10px 30px;
	}

	td.resetPasswordTableLeftTD {
		width: 300px;
		text-align: right;
	}

	td.resetPasswordTableRightTD {
		width: 300px;
		text-align: left;
	}

	td.resetPasswordTip {
		font-weight: bold;
	}

	table.resetPasswordTable input {
		border: 1px solid #DEDEDE;
		width: 300px;
		height: 36px;
		font-size: 12px;
	}

	td.resetPasswordButtonTD {
		text-align: center;
	}

	table.resetPasswordTable button {
		width: 170px;
		height: 36px;
		border-radius: 2px;
		color: white;
		background-color: #C40000;
		border-width: 0px;
	}

	table.resetPasswordTable {

	}
</style>

	<div class="resetPasswordDiv">
		<div class="resetPasswordErrorMessageDiv">
			<div class="alert alert-danger" role="alert">
				<button type="button" class="close" data-dismiss="alert" aria-label="Close"></button>
				<span class="errorMessage"></span>
			</div>
		</div>


		<table class="resetPasswordTable" align="center">
			<tr>
				<td  class="resetPasswordTip resetPasswordTableLeftTD">设置密码</td>
				<td></td>
			</tr>
			<tr>
				<td class="resetPasswordTableLeftTD">手机号</td>
				<td  class="resetPasswordTableRightTD"><input id="phone" name="phone" value="${phone}" disabled="disabled"></td>
			</tr>
			<tr>
				<td class="resetPasswordTableLeftTD">登陆密码</td>
				<td class="resetPasswordTableRightTD"><input id="password" name="password" type="password"  placeholder="设置你的登陆密码" > </td>
			</tr>
			<tr>
				<td class="resetPasswordTableLeftTD">密码确认</td>
				<td class="resetPasswordTableRightTD"><input id="repeatpassword" type="password"   placeholder="请再次输入你的密码" > </td>
			</tr>

			<br>

			<tr>
				<td colspan="2" class="resetPasswordButtonTD">
					<a href="#" class="resetPassword"><button class="resetPassword_Button">重置密码</button></a>
				</td>
			</tr>
		</table>
	</div>