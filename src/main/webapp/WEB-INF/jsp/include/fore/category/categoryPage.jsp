<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" isELIgnored="false"%>
<title>体育商城-${c.name}</title>
<div id="category" style="text-align: center">
	<img src="img/category/${c.id}.jpg">
	<div class="categoryPageDiv">
		<%@include file="sortBar.jsp"%>
		<%@include file="productsByCategory.jsp"%>
	</div>

</div>