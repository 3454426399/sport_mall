<%@ page language="java" contentType="text/html; charset=UTF-8"
		 pageEncoding="UTF-8" isELIgnored="false"%>

<script>
    $(function(){
		// 显示错误提示信息
        <c:if test="${!empty msg}">
			$("span.errorMessage").html("${msg}");
			$("div.registerErrorMessageDiv").css("visibility","visible");
        </c:if>

		$("#password").keyup(checkPassword = function(){
			var password = $("#password").val();
			var regex = /^(?=.*[a-zA-Z])(?=.*\d)(?=.*[#@!~%^&*])[a-zA-Z\d#@!~%^&*_]{8,16}$/;    //密码需要符合的正则表达式

			if(password.length == 0){    //密码不能为空
				$("span.errorMessage").html("密码为空，请您输入密码");
				$("div.registerErrorMessageDiv").css("visibility", "visible");
				return false;
			}else if(!regex.test(password)){    //密码不符合要求
				$("span.errorMessage").html("密码长度为8-16位，且应包含字母、数字和_ & *等特殊符号");
				$("div.registerErrorMessageDiv").css("visibility", "visible");
				return false;
			}

			$("span.errorMessage").html("");
			$("div.registerErrorMessageDiv").css("visibility", "hidden");
			return true;
		}).focus(function(){
			var phone = $("#password").val();

			if(phone.length == 0){    //密码不能为空
				$("span.errorMessage").html("密码为空，请您输入密码");
				$("div.registerErrorMessageDiv").css("visibility", "visible");
				return false;
			}

			return true;
		});

		$("#repeatpassword").keyup(checkRepeatPassword = function(){
			var repeatPassword = $("#repeatpassword").val();
			var password = $("#password").val();

			if(repeatPassword.length == 0){    //确认密码不能为空
				$("span.errorMessage").html("请您再次确认密码");
				$("div.registerErrorMessageDiv").css("visibility", "visible");
				return false;
			}else if(password != repeatPassword){    //两次输入的密码不一致
				$("span.errorMessage").html("您两次输入的密码不一致");
				$("div.registerErrorMessageDiv").css("visibility", "visible");
				return false;
			}

			$("span.errorMessage").html("");
			$("div.registerErrorMessageDiv").css("visibility", "hidden");
			return true;
		}).focus(function(){
			var repeatPassword = $("#repeatpassword").val();

			if(repeatPassword.length == 0){    //确认密码不能为空
				$("span.errorMessage").html("请您再次确认密码");
				$("div.registerErrorMessageDiv").css("visibility", "visible");
				return false;
			}

			return true;
		});

		$(".register").click(function () {
			var phone = $("#phone").val();
			var password = $("#password").val();
			var page = "foreRegister";

			if (checkPassword() && checkRepeatPassword()){    //密码和确认密码通过校验，进行注册
				$.post(
					page,
					{"phone":phone, "password":password},

					function (result) {
						if (result == "success"){    //注册成功，请求跳转至成功页面
							window.location.href="foreRegisterSuccess";
						}
					}
				);
			}
		});
    })
</script>

	<div class="registerDiv">
		<div class="registerErrorMessageDiv">
			<div class="alert alert-danger" role="alert">
				<button type="button" class="close" data-dismiss="alert" aria-label="Close"></button>
				<span class="errorMessage"></span>
			</div>
		</div>


		<table class="registerTable" align="center">
			<tr>
				<td  class="registerTip registerTableLeftTD">设置密码</td>
				<td></td>
			</tr>
			<tr>
				<td class="registerTableLeftTD">手机号</td>
				<td  class="registerTableRightTD"><input id="phone" name="phone" value="${phone}" disabled="disabled"></td>
			</tr>
			<tr>
				<td class="registerTableLeftTD">登陆密码</td>
				<td class="registerTableRightTD"><input id="password" name="password" type="password"  placeholder="设置你的登陆密码" > </td>
			</tr>
			<tr>
				<td class="registerTableLeftTD">密码确认</td>
				<td class="registerTableRightTD"><input id="repeatpassword" type="password"   placeholder="请再次输入你的密码" > </td>
			</tr>

			<br>

			<tr>
				<td colspan="2" class="registerButtonTD">
					<a href="#" class="register"><button class="register_Button">注  册</button></a>
				</td>
			</tr>
		</table>
	</div>