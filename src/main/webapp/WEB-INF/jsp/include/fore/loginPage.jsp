<%@ page language="java" contentType="text/html; charset=UTF-8"
		 pageEncoding="UTF-8" isELIgnored="false"%>

<script>
    $(function(){
    	//当用户切换登录方式为手机验证码方式时，触发此事件
		$(".login_type_code").click(function () {
			//把两个文本框的字体图标分别修改为手机号码、短信验证码
			$(".glyphicon-user").addClass("glyphicon-phone");
			$(".glyphicon-user").removeClass("glyphicon-user");
			$(".glyphicon-lock").addClass("glyphicon-text-background");
			$(".glyphicon-lock").removeClass("glyphicon-lock");

			$("#password_or_code").attr("name", "phoneCode");    //把第二个文本框提交的内容修改为手机验证码
			$("#password_or_code").attr("placeholder", "手机验证码");
			$("#password_or_code").attr("type", "text");

			$("#phone").val("");    //这两条语句清空手机号码、短信验证码框的内容
			$("#password_or_code").val("");

			//如果是短信验证码方式，显示请求发送短信验证码的按钮
			$("#password_or_code").css("width", "145px");
			$(".sendPhoneCodeButton").css("display", "inline-block");
		});

		//当用户切换登陆方式为账号密码方式时，触发此事件
		$(".login_type_user").click(function () {
			//把两个文本框的字体图标分别修改为用户、密码
			$(".glyphicon-phone").addClass("glyphicon-user");
			$(".glyphicon-phone").removeClass("glyphicon-phone");
			$(".glyphicon-text-background").addClass("glyphicon-lock");
			$(".glyphicon-text-background").removeClass("glyphicon-text-background");

			$("#password_or_code").attr("name", "password");    //把第二个文本框提交的内容修改为密码
			$("#password_or_code").attr("placeholder", "密码");
			$("#password_or_code").attr("type", "password");

			$("#phone").val("");    //这两条语句清空账号、密码框的内容
			$("#password_or_code").val("");

			//如果是账号密码方式，隐藏请求发送短信验证码的按钮
			$(".sendPhoneCodeButton").css("display", "none");
			$("#password_or_code").css("width", "244px");
		});

		//当文本框获得光标时，触发此事件，隐藏上次的错误提示信息
		$("input").focus(function () {
			$("span.errorMessage").html("");
			$("div.loginErrorMessageDiv").hide();
		});

		//当手机密码框失去光标时，触发此事件，校验手机号码的格式是否正确
		$("#phone").blur(checkPhone = function () {
			var regex = /^[1][3,4,5,6,7,8,9][0-9]{9}$/;    //手机号码需要符合的正则表达式
			var phone = $("#phone").val();

			if (!regex.test(phone)){
				$("span.errorMessage").html("您输入的手机号码格式不对，请重新输入");
				$("div.loginErrorMessageDiv").show();
				return false;
			}

			return true;
		});

		//请求发送手机验证码
		$(".sendPhoneCode").click(function () {
			var result = checkPhone();

			if (result == true){    //只有用户输入的手机号码通过校验才会请求发送

				var phone = $("#phone").val();
				var page = "foreExist";

				$.post(
					page,
					{"phone":phone},

					function (result) {
						if (result == "true"){
							var phone = $("#phone").val();
							var page = "foreSendMobilCheckCode";

							$.post(
									page,
									{"phone":phone},

									function (result) {

										if (result == "success"){
											var second = 60;
											var message = (second-1) + "秒后重发";
											$(".sendPhoneCodeButton").attr("disabled", true);    //验证码有效期内，按钮不可点击
											var secondCount = setInterval(secondDown, 1000);    //每隔1秒钟调用1次setInterval()函数

											function secondDown() {
												$(".sendPhoneCodeButton").attr("value",message);
												second--;
												message = (second-1) + "秒后重发";

												if (second == 0){
													$(".sendPhoneCodeButton").attr("disabled", false);
													$(".sendPhoneCodeButton").attr("value", "获取验证码");

													clearInterval(secondCount);    //当手机验证码过期后，不再定时调用setInterval()函数
												}
											}
										}
									}
							);
						}else {
							$("span.errorMessage").html("您的手机号码还未注册，<a href='foreRegisterPhone' style='text-decoration;'>请先注册</a>");
							$("div.loginErrorMessageDiv").show();
						}
					}
				);
			}
		});

		//登录校验
		$(".login").click(function () {
			var type = $("#password_or_code").attr("placeholder");

			if (checkPhone() == false){    //手机号码格式错误，登录校验失败
				return false;
			}

			if (type == "密码"){    //登录校验-账号密码方式

				var phone = $("#phone").val();
				var password = $("#password_or_code").val();
				var page = "UsernamePasswordLogin.user";

				$.post(
					page,
					{"phone":phone, "password":password},

					function (result) {

						if (result == "success"){
							window.location.href="forehome";
						}else {
							$("span.errorMessage").html("账号或密码错误");
							$("div.loginErrorMessageDiv").show();
						}

					}
				);

			}else {    //登录校验-手机验证码方式

				var phone = $("#phone").val();
				var phoneCode = $("#password_or_code").val();
				var page = "MobileCheckCodeLogin";

				$.post(
					page,
					{"phone":phone, "phoneCode":phoneCode},

					function (result) {

						if (result == "success"){
							window.location.href="forehome";
						}else {
							$("span.errorMessage").html(result);
							$("div.loginErrorMessageDiv").show();
						}
					}
				);
			}
		});

    })
</script>


<div id="loginDiv" style="position: relative">

	<br><br>
	<img id="loginBackgroundImg" class="loginBackgroundImg" src="img/background/userLogin.png">


		<div id="loginSmallDiv" class="loginSmallDiv">
			<div class="loginErrorMessageDiv">
				<div class="alert alert-danger" >
					<button type="button" class="close" data-dismiss="alert" aria-label="Close"></button>
					<span class="errorMessage"></span>
				</div>
			</div>

			<a href="javascript:void(0);" class="login_type_user" style="color: black;font-size: 16px;font-weight: bold">账户登录</a>&nbsp;&nbsp;&nbsp;<a href="javascript:void(0);" class="login_type_code" style="color: black;font-size: 16px;font-weight: bold">短信登录</a>
			<br><br>
			<div class="loginInput">
				<span class="loginInputIcon ">
					<span class="glyphicon glyphicon-user"></span>
				</span>
				<input id="phone" name="phone" placeholder="手机号码" type="text" style="outline: none">
			</div>

			<div class="loginInput" >
				<span class="loginInputIcon ">
					<span class="glyphicon glyphicon-lock"></span>
				</span>
				<input id="password_or_code" name="password" type="password" placeholder="密码" style="outline: none">
				<a class="sendPhoneCode" href="#"><input type="button" class="sendPhoneCodeButton" value="获取验证码" style="width: 85px;height: 30px;position:relative;top: 6px;right: 5px;left: 5px;background: white;color: #0f0f0f;border-style: none;outline:none; display: none;"></a>
			</div>

			<input id="type" name="type" type="hidden" value="password">

			<br>
			<div>
				<a class="forgetPassword" href="forgetPassword">忘记密码</a>
				<a href="foreRegisterPhone" class="pull-right">免费注册</a>
			</div>
			<div style="margin-top:20px">
				<a class="login" href="javascript:void(0);"><button class="btn btn-block loginButton" type="submit" style="background: #C40000;color: white">登 录</button></a>
			</div>
		</div>

</div>	