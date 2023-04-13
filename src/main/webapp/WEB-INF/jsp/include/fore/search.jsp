<%@ page language="java" contentType="text/html; charset=UTF-8"
		 pageEncoding="UTF-8" isELIgnored="false"%>

<form action="foresearch" method="post" >
	<div class="searchDiv">
		<input name="keyword" type="text" value="${param.keyword}" placeholder="篮球">
		<input name="pageNo" type="hidden" value="0">
		<button  type="submit" class="searchButton">搜索</button>
		<div class="searchBelow">
			<c:forEach items="${cs}" var="c" varStatus="st">
				<c:if test="${st.count>=5 and st.count<=8}">
						<span>
							<a href="forecategory?cid=${c.id}&sort=all">
									${c.name}
							</a>
							<c:if test="${st.count!=8}">
								<span>|</span>
							</c:if>
						</span>
				</c:if>
			</c:forEach>
		</div>
	</div>
</form>
