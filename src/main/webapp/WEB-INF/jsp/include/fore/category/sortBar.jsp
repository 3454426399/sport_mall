<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<script>
$(function(){
	$("input.sortBarPrice").keyup(function(){
		var begin = $("input.beginPrice").val();
		begin = parseInt(begin);
		if (isNaN(begin) || begin <= 0){    //如果用户输入的起始筛选条件不符合规范，设置为1
			begin = 1;
		}else {
			$("input.beginPrice").val(begin);
		}

		var end = $("input.endPrice").val();    //如果用户输入的终止筛选条件不符合规范，设置为最大值
		end = parseInt(end);
		if (isNaN(end) || end <= 0){
			end = Number.MAX_VALUE;
		}else {
			$("input.endPrice").val(end);
		}

		if(!isNaN(begin) && !isNaN(end)){    //如果起始和终止筛选条件都设置了，按两个条件筛选
			$("div.productUnit").hide();
			$("div.productUnit").each(function(){
				var price = $(this).attr("price");
				price = new Number(price);

				if(price<=end && price>=begin)
					$(this).show();
			});
		}else if (!isNaN(begin)){    //如果只设置了起始筛选条件，则按起始筛选条件筛选
			$("div.productUnit").hide();
			$("div.productUnit").each(function(){
				var price = $(this).attr("price");
				price = new Number(price);

				if(price>=begin)
					$(this).show();
			});
		}else if (!isNaN(end)){    //如果只设置了终止筛选条件，则按终止筛选条件筛选
			$("div.productUnit").hide();
			$("div.productUnit").each(function(){
				var price = $(this).attr("price");
				price = new Number(price);

				if(price<=end)
					$(this).show();
			});
		}
	});
});
</script>	
<div class="categorySortBar">

	<table class="categorySortBarTable categorySortTable">
		<tr>
			<td <c:if test="${'all'==param.sort}">class="grayColumn"</c:if> ><a href="?cid=${c.id}&sort=all">综合<span class="glyphicon glyphicon-arrow-down"></span></a></td>
			<td <c:if test="${'review'==param.sort}">class="grayColumn"</c:if> ><a href="?cid=${c.id}&sort=review">人气<span class="glyphicon glyphicon-arrow-down"></span></a></td>
			<td <c:if test="${'date'==param.sort}">class="grayColumn"</c:if>><a href="?cid=${c.id}&sort=date">新品<span class="glyphicon glyphicon-arrow-down"></span></a></td>
			<td <c:if test="${'saleCount'==param.sort}">class="grayColumn"</c:if>><a href="?cid=${c.id}&sort=saleCount">销量<span class="glyphicon glyphicon-arrow-down"></span></a></td>
			<td <c:if test="${'price'==param.sort}">class="grayColumn"</c:if>><a href="?cid=${c.id}&sort=price">价格<span class="glyphicon glyphicon-resize-vertical"></span></a></td>
		</tr>
	</table>
	
	
	
	<table class="categorySortBarTable">
		<tr>
			<td><input class="sortBarPrice beginPrice" type="text" placeholder="请输入"></td>
			<td class="grayColumn priceMiddleColumn">-</td>
			<td><input class="sortBarPrice endPrice" type="text" placeholder="请输入"></td>
		</tr>
	</table>

</div>