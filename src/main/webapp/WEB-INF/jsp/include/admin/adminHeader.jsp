<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix='fmt' %> 

<html ng-app="app" lang="en">

<head>
	<link href="css/back/style.css" rel="stylesheet">
	<link href="css/bootstrap/3.3.6/bootstrap.min.css" rel="stylesheet">
	<link rel="stylesheet" type="text/css" href="css/angularjs/wui.min.css">
	<link rel="stylesheet" type="text/css" href="css/angularjs/style.css">

    <%-- 先引入jquery,再引入bootstrap --%>
	<script src="js/jquery/2.0.0/jquery.min.js"></script>
	<script src="js/bootstrap/3.3.6/bootstrap.min.js"></script>
	<script type="text/javascript" src="js/angularjs/jquery.min.js"></script>
	<script type="text/javascript" src="js/angularjs/angular.min.js"></script>
	<script type="text/javascript" src="js/angularjs/wui-date.js" charset="utf-8"></script>
	<script type="text/javascript" src="js/echarts/4.7.0/echarts.min.js"></script>
	<script type="text/javascript">
		var app = angular.module('app',["wui.date"]);
	</script>
	
<script>
function checkEmpty(id, name){
	var value = $("#"+id).val();
	if(value.length==0){
		alert(name+ "不能为空");
		$("#"+id)[0].focus();
		return false;
	}
	return true;
}

function getkey() {
	var currentParh = window.location.pathname;
	var path = currentParh.substring(12,currentParh.length);

	if (event.keyCode == 13){
		var totalPage = ${page.totalPage};
		var inputNumber = $(".pageNumINput").val();

		if (inputNumber <= 0 || inputNumber > totalPage){
			alert("您要查询的数据不存在");
			return false;
		}

		var count = ${page.count};
		var index = inputNumber - 1;
		var param = '${page.param}';

		window.location.href = path + "?start=" + (index * count) + param;
	}
}

function checkNumber(id, name){
	var value = $("#"+id).val();
	if(value.length==0){
		alert(name+ "不能为空");
		$("#"+id)[0].focus();
		return false;
	}
	if(isNaN(value)){
		alert(name+ "必须是数字");
		$("#"+id)[0].focus();
		return false;
	}
	
	return true;
}
function checkInt(id, name){
	var value = $("#"+id).val();
	var regex = /^[0-9]+$/;

	if(value.length==0){
		alert(name+ "不能为空");
		$("#"+id)[0].focus();
		return false;
	}
	if(!regex.test(value)){
		alert(name+ "必须是整数");
		$("#"+id)[0].focus();
		return false;
	}
	
	return true;
}

$(function(){
	$("a").click(function(){
		var deleteLink = $(this).attr("deleteLink");
		console.log(deleteLink);
		if("true"==deleteLink){
			var confirmDelete = confirm("确认要删除");
			if(confirmDelete)
				return true;
			return false;
			
		}
	});

	/*
	  提醒用户是否确认删除日志记录
	 */
	$(".deleteButton").click(function () {
		var result = confirm("您正在执行日志的删除操作，您确定要继续吗？");

		if (result == false)    //不删除，表单不会被提交
			return false;
	});

	$(".dayWay").click(function () {
		$(".dayWay").css("background", "lawngreen");
		$(".monthWay").css("background", "white");
	});

	$(".monthWay").click(function () {
		$(".dayWay").css("background", "white");
		$(".monthWay").css("background", "lawngreen");
	});
})
</script>	
</head>
<body>

