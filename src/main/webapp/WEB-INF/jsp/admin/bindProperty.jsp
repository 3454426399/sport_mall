<%@ page language="java" contentType="text/html; charset=UTF-8"
		 pageEncoding="UTF-8" import="java.util.*"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@include file="../include/admin/adminHeader.jsp"%>
<%@include file="../include/admin/adminNavigator.jsp"%>

<script>
    $(function() {

        $("#addForm").submit(function() {
            if (checkEmpty("value", "属性名不能留空"))
                return true;
            if (checkEmpty("name", "属性值不能留空"))
            	return true;
            return false;
        });


    });
</script>

<title>绑定产品属性</title>

<div class="workingArea">

	<ol class="breadcrumb">
		<li><a href="admin_category_list">所有分类</a></li>
		<li><a href="admin_property_list?cid=${p.category.id}">${p.category.name}</a></li>
		<li class="active" >${p.name}</li>
		<li class="active">绑定产品属性</li>
	</ol>

	<div class="listDataTableDiv">
		<table
				class="table table-striped table-bordered table-hover  table-condensed">
			<thead>
			<tr class="success">
				<th>ID</th>
				<th>属性名称</th>
				<th>删除</th>
			</tr>
			</thead>
			<tbody>
			<c:forEach items="${pvs}" var="pv">

				<tr>
					<td>${pv.property.id}</td>
					<td>${pv.property.name}</td>
					<td><a deleteLink="true"
						   href="admin_productproperty_delete?pid=${p.id}&ptid=${pv.property.id}"><span
							class="glyphicon glyphicon-trash"></span></a></td>
				</tr>
			</c:forEach>
			</tbody>
		</table>
	</div>

	<div class="panel panel-warning addDiv">
		<div class="panel-heading">绑定属性</div>
		<div class="panel-body">
			<form method="post" id="addForm" action="admin_product_property_add">
				<table class="addTable">
					<tr>
						<td>属性名称</td>
						<td>
							<select id="name" name="ptid" class="selected" style="width: 290px;height: 35px">
								<c:forEach items="${idlepts}" var="idlept">
									<option value ="${idlept.id}">${idlept.name}</option>
								</c:forEach>
							</select>
						</td>
					</tr>
					<tr>
						<td>属性值</td>
						<td><input id="value" name="value" type="text"
								   class="form-control"></td>
					</tr>
					<tr class="submitTR">
						<td colspan="2" align="center">
							<input type="hidden" name="pid" value="${p.id}">
							<button type="submit" class="btn btn-success">提 交</button>
						</td>
					</tr>
				</table>
			</form>
		</div>
	</div>

</div>

<%@include file="../include/admin/adminFooter.jsp"%>
