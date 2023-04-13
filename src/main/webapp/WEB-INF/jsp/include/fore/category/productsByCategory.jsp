<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix='fmt' uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<div class="categoryProducts">
	<c:forEach items="${c.products}" var="p" varStatus="stc">
		<c:if test="${stc.count<=c.productNumber}">
		<div class="productUnit" price="${p.promotePrice}">
			<div class="productUnitFrame">
				<a href="foreproduct?pid=${p.id}">
					<img class="productImage" src="img/productSingle_middle/${p.firstProductImage.id}.jpg">
				</a>
				<span class="productPrice">¥<fmt:formatNumber type="number" value="${p.promotePrice}" minFractionDigits="2"/></span>
				<a class="productLink" href="foreproduct?pid=${p.id}">
				 ${p.name}
				</a>
				
				<a  class="tmallLink" href="foreproduct?pid=${p.id}">商城专卖</a>
	
				<div class="show1 productInfo">
					<span class="monthDeal ">月成交 <span class="productDealNumber">${p.saleCount}笔</span></span>
					<span class="productReview">评价<span class="productReviewNumber">${p.reviewCount}</span></span>
					<span class="wangwang">
					<a class="wangwanglink" href="#nowhere">
						<img src="img/site/wangwang.png">
					</a>
					
					</span>
				</div>
			</div>
		</div>
		</c:if>
	</c:forEach>

	<div style="clear:both"></div>

</div>