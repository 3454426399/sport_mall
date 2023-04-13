<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix='fmt' uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<script>
$(function(){
	$("div.productsAsideCategorys div.row a").each(function(){
		var v = Math.round(Math.random() *6);
		if(v == 1)
			$(this).css("color","#87CEFA");
	});
});

</script>
<c:forEach items="${cs}" var="c">
	<div cid="${c.id}" class="productsAsideCategorys">
	 
		<c:forEach items="${c.products}" var="p">
			<div class="row show1">
				<a href="foreproduct?pid=${p.id}">
					${p.name}
				</a>
				<div class="seperator"></div>
			</div>		
		</c:forEach>
	</div>			
</c:forEach>
	
