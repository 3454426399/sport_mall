<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix='fmt' uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<div class="homepageCategoryProducts">
	<c:forEach items="${cs}" var="c" varStatus="stc">
		<c:if test="${stc.count<=categorycount}">
			<div class="eachHomepageCategoryProducts">
				<div class="left-mark"></div>
				<span class="categoryTitle"><a href="forecategory?cid=${c.id}&sort=saleCount">${c.name}</a></span>
				<br>
				<c:forEach items="${c.products}" var="p" varStatus="st">
					<c:if test="${st.count<=5}">
						<div class="productItem">
							<a href="foreproduct?pid=${p.id}"><img width="100px" src="img/productSingle_middle/${p.firstProductImage.id}.jpg"></a>
							<a class="productItemDescLink" href="foreproduct?pid=${p.id}">
								<span class="productItemDesc">[热销]
								${fn:substring(p.name, 0, p.name.length())}
								</span>
						    </a>
							<br><br>
							<span class="productPrice">
								<fmt:formatNumber type="number" value="${p.promotePrice}" minFractionDigits="2"/>
							</span>
						</div>
					</c:if>				
				</c:forEach>
				<div style="clear:both"></div>
			</div>
		</c:if>
	</c:forEach>
	
	
	<img id="endpng" class="endpng" src="img/site/end.png">

</div>