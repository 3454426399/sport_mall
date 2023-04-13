<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" isELIgnored="false"%>
	
<div class="categoryMenu">
		<c:forEach items="${cs}" var="c" varStatus="st">
			<c:if test="${st.count<=17}">
				<div cid="${c.id}" class="eachCategory">
					<span class="glyphicon glyphicon-link"></span>
					<a href="forecategory?cid=${c.id}&sort=all">
						${c.name}
					</a>
				</div>
			</c:if>
		</c:forEach>
</div>