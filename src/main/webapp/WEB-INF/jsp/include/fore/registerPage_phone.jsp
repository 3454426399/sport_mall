<%@ page language="java" contentType="text/html; charset=UTF-8"
		 pageEncoding="UTF-8" isELIgnored="false"%>

<script>
    $(function(){
		// 显示错误提升信息
        <c:if test="${!empty msg}">
			$("span.errorMessage").html("${msg}");
			$("div.registerErrorMessageDiv").css("visibility","visible");
        </c:if>

		$("#phone").keyup(checkPhone = function(){
			var phone = $("#phone").val();
			var regex = /^[1][3,4,5,6,7,8,9][0-9]{9}$/;    //手机号码需要符合的正则表达式

			if(phone.length == 0){    //手机号码不能为空
				$("span.errorMessage").html("手机号不能为空，请输入您的手机号码");
				$("div.registerErrorMessageDiv").css("visibility", "visible");
				return false;
			}else if(!regex.test(phone)){    //输入的不符合手机号码的规则
				$("span.errorMessage").html("您输入的手机号码格式不对，请重新输入");
				$("div.registerErrorMessageDiv").css("visibility", "visible");
				return false;
			}

			$("span.errorMessage").html("");
			$("div.registerErrorMessageDiv").css("visibility", "hidden");
			return true;
		}).focus(function(){
			var phone = $("#phone").val();

			if(phone.length == 0){    //手机号码不能为空
				$("span.errorMessage").html("手机号不能为空，请输入您的手机号码");
				$("div.registerErrorMessageDiv").css("visibility", "visible");
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

						if (result == "false"){

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
							$("span.errorMessage").html("该手机号已经注册过了.<a href='foreLoginPage' style='color: #00AAEE;'>现在登录</a>");
							$("div.registerErrorMessageDiv").css("visibility", "visible");
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
					$("div.registerErrorMessageDiv").css("visibility", "visible");
					return false;
				}

				$.post(    //发送后台手机验证码校验请求
					page,
					{"phone":phone, "phoneCode":phoneCode},

					function (result) {
						if (result == "success"){
							window.location.href="foreRegisterPassword?phone=" + phone;
						}else {
							$("span.errorMessage").html("手机验证码错误");
							$("div.registerErrorMessageDiv").css("visibility", "visible");
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
				<td  class="registerTip registerTableLeftTD">设置账号</td>
				<td></td>
			</tr>
			<tr>
				<td class="registerTableLeftTD">手机号</td>
				<td  class="registerTableRightTD"><input id="phone" name="phone" placeholder="请输入您的手机号" > </td>
			</tr>
			<tr>
				<td class="registerTableLeftTD">手机验证码</td>
				<td  class="registerTableRightTD"><input id="phoneCode" name="phoneCode" placeholder="请输入手机验证码" ></td>
				<td><a href="#" class="sendMobilCheckCode"><input type="button" class="mobilCheckCodeButton" value="获取验证码" style="width: 100px; height: 30px"></a></td>
			</tr>

			<br>

			<tr>
				<td colspan="2" class="registerButtonTD">
					<a href="#" class="nextStep"><button class="nextStep_Button">下 一 步</button></a>
				</td>
			</tr>
		</table>
	</div>