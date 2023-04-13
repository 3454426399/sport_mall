<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8"
		 pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix='fmt' uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>



<html>

<head>
	<script src="js/jquery/2.0.0/jquery.min.js"></script>
	<link href="css/bootstrap/3.3.6/bootstrap.min.css" rel="stylesheet">
	<script src="js/bootstrap/3.3.6/bootstrap.min.js"></script>
	<link href="css/fore/style.css" rel="stylesheet">
	<script>
		var first = true;

        function formatMoney(num){
            num = num.toString().replace(/\$|\,/g,'');
            if(isNaN(num))
                num = "0";
            sign = (num == (num = Math.abs(num)));
            num = Math.floor(num*100+0.50000000001);
            cents = num%100;
            num = Math.floor(num/100).toString();
            if(cents<10)
                cents = "0" + cents;
            for (var i = 0; i < Math.floor((num.length-(1+i))/3); i++)
                num = num.substring(0,num.length-(4*i+3))+','+
                    num.substring(num.length-(4*i+3));
            return (((sign)?'':'-') + num + '.' + cents);
        }
        function checkEmpty(id, name){
            var value = $("#"+id).val();
            if(value.length==0){

                $("#"+id)[0].focus();
                return false;
            }
            return true;
        }


        $(function(){


            $("a.productDetailTopReviewLink").click(function(){
                $("div.productReviewDiv").show();
                $("div.productDetailDiv").hide();
            });
            $("a.productReviewTopPartSelectedLink").click(function(){
                $("div.productReviewDiv").hide();
                $("div.productDetailDiv").show();
            });

            $("span.leaveMessageTextareaSpan").hide();
            $("img.leaveMessageImg").click(function(){

                $(this).hide();
                $("span.leaveMessageTextareaSpan").show();
                $("div.orderItemSumDiv").css("height","100px");
            });

            $("div#footer a[href$=#nowhere]").click(function(){
                alert("这里并不会跳转到实际的页面");
            });


            $("a.wangwanglink").click(function(){
                alert("这里并不会跳转到实际的页面");
            });

        });

        //根据省份查询城市
        function foreShowCity() {
			var province = $("#province").val();

			$("#city").find("option").remove();
			$("#county").find("option").remove();
			$("#town").find("option").remove();

			if (province != "请选择"){

				var page = "foreShowCity";

				$.post(
						page,
						{"province":province},

						function (result) {
							$("#city").append('<option>请选择</option>');
							for (var i = 0; i < result.length; i++){
								$("#city").append('<option>' + result[i] + '</option>');
							}

							$("#county").append('<option>请选择</option>');

							$("#town").append('<option>请选择</option>');
						}
				);
			}else {    //直辖市
				$("#city").append('<option>请选择</option>');
				$("#city").append('<option>上海市</option>');
				$("#city").append('<option>北京市</option>');

				$("#county").append('<option>请选择</option>');

				$("#town").append('<option>请选择</option>');
			}
		}

		//根据省份、城市查询区县
		function foreShowCounty() {
			var province = $("#province").val();
			var city = $("#city").val();

			$("#county").find("option").remove();
			$("#town").find("option").remove();

			if (city != "请选择"){

				var page = "foreShowCounty";

				$.post(
						page,
						{"province":province, "city":city},

						function (result) {
							$("#county").append('<option>请选择</option>');
							for (var i = 0; i < result.length; i++){
								$("#county").append('<option>' + result[i] + '</option>');
							}

							$("#town").append('<option>请选择</option>');
						}
				);
			}else {
				$("#county").append('<option>请选择</option>');

				$("#town").append('<option>请选择</option>');
			}
		}

		//根据省份、城市、区县查询城镇
		function foreShowTown() {
			var province = $("#province").val();
			var city = $("#city").val();
			var county = $("#county").val();

			$("#town").find("option").remove();

			if (county != "请选择"){

				var page = "foreShowTown";

				$.post(
						page,
						{"province":province, "city":city, "county":county},

						function (result) {
							$("#town").append('<option>请选择</option>');
							for (var i = 0; i < result.length; i++){
								$("#town").append('<option>' + result[i] + '</option>');
							}
						}
				);
			}else {
				$("#town").append('<option>请选择</option>');
			}
		}

		//添加常用地址
		function foreAddCommonAddress() {
        	var province = $("#province").val();
        	var city = $("#city").val();
        	var county = $("#county").val();
        	var town = $("#town").val();
        	var detailAddress = $("#detailAddress").val();
        	var page = "foreAddCommonAddress";

        	if (town == "请选择"){
        		alert("收货地址需要具体到镇");
        		return false;
			}

        	if (detailAddress.length == 0){
        		alert("请填写详细收货地址");
        		return false;
			}

        	$.post(
        		page,
				{"province":province, "city":city, "county":county, "town":town, "detailAddress":detailAddress},

				function (result) {
        			if (result == "false"){
        				alert("该地址您已经导入过了");
        				return false;
					}
					$("#commonAddress").append('<option>' + result + '</option>');
        			alert("添加成功");
				}
			);
		}

		//使用常用收货地址
		function foreUseCommonAddress() {
        	var address = $("#commonAddress").val();

        	if (address != "请选择"){
				$('#province').attr("disabled",true);
				$('#city').attr("disabled",true);
				$('#county').attr("disabled",true);
				$('#town').attr("disabled",true);
				$('#detailAddress').attr("disabled",true);
			}else {
				$('#province').attr("disabled",false);
				$('#city').attr("disabled",false);
				$('#county').attr("disabled",false);
				$('#town').attr("disabled",false);
				$('#detailAddress').attr("disabled",false);
			}
		}

		//查询用户的常用收货地址
		function foreGetCommonAddress() {
        	if (first){
				var page = "foreGetCommonAddress";

				$.post(
						page,

						function (result) {
							$("#commonAddress").find("option").remove();

							$("#commonAddress").append('<option>请选择</option>');
							for (var i = 0; i < result.length; i++){
								$("#commonAddress").append('<option>' + result[i] + '</option>');
							}
						}
				);
				first = false;
			}
		}

		//结算-后台生成订单
		function submit() {
        	var imp = $("#commonAddress").val();
        	var post = $("#post").val();
        	var receiver = $("#receiver").val();
        	var mobile = $("#mobile").val();
        	var userMessage = $("#userMessage").val();
        	var address = "";

        	if (imp != "请选择"){    //从常用地址中导入
				address = $("#commonAddress").val();
			} else {
        		var town = $("#town").val();
        		var detail = $("#detailAddress").val();

        		if (town == "请选择" || detail.length == 0){
        			alert("请填写完整地址");
        			return false;
				}

        		var province = $("#province").val();
        		var city = $("#city").val();
        		var county = $("#county").val();

        		if (province == "请选择")    //地址是直辖市的
        			address = city + "-" + county + "-" + town + "-" + detail;
        		else    //地址包括省份
        			address = province + "-" + city + "-" + county + "-" + town + "-" + detail;
			}

        	if (post.length == 0){
        		alert("邮政编码是必填项");
        		return false;
			}

        	if (receiver.length == 0){
        		alert("收件人姓名是必填项");
        		return false;
			}

        	if (mobile.length == 0){
        		alert("联系方式是必填项");
        		return "false";
			}

        	var page = "forecheckLogin";

        	$.post(
        		page,

				function (result) {
        			if (result == "success"){
        				var page = "forecreateOrder";

        				$.post(
        					page,
							{"post":post, "receiver":receiver, "mobile":mobile, "userMessage":userMessage, "address":address},

							function (result) {
        						window.location.href = result;    //重定向到支付页面
							}
						);
					}
				}
			);
		}

		<%--href="forepayed?oid=${param.oid}&total=${param.total}"--%>
		function confirmPay(){
			var oid = $(".confirmPayLink").attr("oid");
			var total = $(".confirmPayLink").attr("total");
			var password = $(".password").val();
			var page = "forecheckLogin"

			$.post(
				page,

				function (result) {
					if (result == "success"){
						var page = "forepayed";

						if (password.length == 0){
							alert("请输入支付密码");
							return false;
						}

						$.post(
							page,
							{"oid":oid, "total":total, "password":password},

							function (result) {
								if (result != "success"){
									alert(result);
								}else {
									window.location.href = "paySuccess?total=" + total;
								}
							}
						);
					}
				}
			);
		}

	</script>
</head>

<body>

