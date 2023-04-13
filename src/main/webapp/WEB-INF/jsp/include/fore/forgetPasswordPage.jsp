<%@ page language="java" contentType="text/html; charset=UTF-8"
		 pageEncoding="UTF-8" isELIgnored="false"%>

<script>
    $(function(){
		// 显示错误提升信息
        <c:if test="${!empty msg}">
			$("span.errorMessage").html("${msg}");
			$("div.forgetPasswordErrorMessageDiv").css("visibility","visible");
        </c:if>

		$("#phone").keyup(checkPhone = function(){
			var phone = $("#phone").val();
			var regex = /^[1][3,4,5,6,7,8,9][0-9]{9}$/;    //手机号码需要符合的正则表达式

			if(phone.length == 0){    //手机号码不能为空
				$("span.errorMessage").html("手机号不能为空，请输入您的手机号码");
				$("div.forgetPasswordErrorMessageDiv").css("visibility", "visible");
				return false;
			}else if(!regex.test(phone)){    //输入的不符合手机号码的规则
				$("span.errorMessage").html("您输入的手机号码格式不对，请重新输入");
				$("div.forgetPasswordErrorMessageDiv").css("visibility", "visible");
				return false;
			}

			$("span.errorMessage").html("");
			$("div.forgetPasswordErrorMessageDiv").css("visibility", "hidden");
			return true;
		}).focus(function(){
			var phone = $("#phone").val();

			if(phone.length == 0){    //手机号码不能为空
				$("span.errorMessage").html("手机号不能为空，请输入您的手机号码");
				$("div.forgetPasswordErrorMessageDiv").css("visibility", "visible");
				return false;
			}

			return true;
		});

		$(".sendMobilCheckCode").click(function () {    //请求发送手机验证码
			var result = checkPhone();

			if (result == true){    //只有用户输入的手机号码通过校验才会请求发送

				var phone = $("#phone").val();
				var page = "foreExist";

				$.post(
					page,
					{"phone":phone},

					function (result) {
						var page = "foreSendMobilCheckCode";

						if (result == "true"){
							$.post(
									page,
									{"phone":phone},

									function (result) {

										if (result == "success"){
											var second = 60;
											var message = (second-1) + "秒后重发";
											$(".mobilCheckCodeButton").attr("disabled", true);    //验证码有效期内，按钮不可点击
											var secondCount = setInterval(secondDown, 1000);    //每隔1秒钟调用1次setInterval()函数

											function secondDown() {
												$(".mobilCheckCodeButton").attr("value",message);
												second--;
												message = (second-1) + "秒后重发";

												if (second == 0){
													$(".mobilCheckCodeButton").attr("disabled", false);
													$(".mobilCheckCodeButton").attr("value", "获取验证码");

													clearInterval(secondCount);    //当手机验证码过期后，不再定时调用setInterval()函数
												}
											}
										}

									}
							);
						}else {
							$("span.errorMessage").html("该手机号码未注册.<a href='foreRegisterPhone' style='color: #00AAEE;'>现在注册</a>");
							$("div.forgetPasswordErrorMessageDiv").css("visibility", "visible");
						}
					}
				);
			}
		});

		$(".nextStep").click(function () {    //校验手机验证码
			var result = checkPhone();
			var phone = $("#phone").val();
			var phoneCode = $("#phoneCode").val();
			var page = "foreCheckPhoneCode";

			if (result == true){    //只有用户输入的手机号码通过校验时才会校验手机验证码

				if (phoneCode.length == 0){    //手机验证码不能为空
					$("span.errorMessage").html("手机验证码不能为空，请输入手机验证码");
					$("div.forgetPasswordErrorMessageDiv").css("visibility", "visible");
					return false;
				}

				$.post(    //发送后台手机验证码校验请求
					page,
					{"phone":phone, "phoneCode":phoneCode},

					function (result) {
						if (result == "success"){
							window.location.href="foreResetPasswordPage?phone=" + phone;
						}else {
							$("span.errorMessage").html("手机验证码错误");
							$("div.forgetPasswordErrorMessageDiv").css("visibility", "visible");
						}
					}
				);
			}
		});

    })
</script>
<style>
	div.forgetPasswordDiv {
		margin: 10px 20px;
		text-align: center;
	}

	table.forgetPasswordTable {
		color: #3C3C3C;
		font-size: 16px;
		table-layout: fixed;
		margin-top: 50px;
	}

	table.forgetPasswordTable td {
		/* 	border:1px dotted skyblue !important; */
		padding: 10px 30px;
	}

	td.forgetPasswordTableLeftTD {
		width: 300px;
		text-align: right;
	}

	td.forgetPasswordTableRightTD {
		width: 300px;
		text-align: left;
	}

	td.forgetPasswordTip {
		font-weight: bold;
	}

	table.forgetPasswordTable input {
		border: 1px solid #DEDEDE;
		width: 300px;
		height: 36px;
		font-size: 12px;
	}

	td.forgetPasswordButtonTD {
		text-align: center;
	}

	table.forgetPasswordTable button {
		width: 170px;
		height: 36px;
		border-radius: 2px;
		color: white;
		background-color: #C40000;
		border-width: 0px;
	}

	table.forgetPasswordTable {

	}
</style>

	<div class="forgetPasswordDiv">
		<div class="forgetPasswordErrorMessageDiv">
			<div class="alert alert-danger" role="alert">
				<button type="button" class="close" data-dismiss="alert" aria-label="Close"></button>
				<span class="errorMessage"></span>
			</div>
		</div>


		<table class="forgetPasswordTable" align="center">
			<tr>
				<td  class="forgetPasswordTip forgetPasswordTableLeftTD">您的手机号码</td>
				<td></td>
			</tr>
			<tr>
				<td class="forgetPasswordTableLeftTD">手机号</td>
				<td  class="forgetPasswordTableRightTD"><input id="phone" name="phone" placeholder="请输入您的手机号" > </td>
			</tr>
			<tr>
				<td class="forgetPasswordTableLeftTD">手机验证码</td>
				<td  class="forgetPasswordTableRightTD"><input id="phoneCode" name="phoneCode" placeholder="请输入手机验证码" ></td>
				<td><a href="#" class="sendMobilCheckCode"><input type="button" class="mobilCheckCodeButton" value="获取验证码" style="width: 100px; height: 30px"></a></td>
			</tr>

			<br>

			<tr>
				<td colspan="2" class="forgetPasswordButtonTD">
					<a href="#" class="nextStep"><button class="nextStep_Button">下 一 步</button></a>
				</td>
			</tr>
		</table>
	</div>