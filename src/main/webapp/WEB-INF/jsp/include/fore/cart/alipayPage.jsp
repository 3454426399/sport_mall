<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" isELIgnored="false"%>
	
<div class="aliPayPageDiv">
	<div class="aliPayPageLogo">
		<div style="clear:both"></div>
	</div>
	
	<div>
		<span class="confirmMoneyText">扫一扫付款（元）</span>
		<span class="confirmMoney">
		￥<fmt:formatNumber type="number" value="${param.total}" minFractionDigits="2"/></span>
		
	</div>
	<div>
		<img class="aliPayImg" src="img/site/alipay2wei.png">
	</div>

	<div>
		支付密码：<input type="password" class="password" name="password">
	</div>
	
	<div>
		<a class="confirmPayLink" oid="${param.oid}" total="${param.total}" address="${param.address}" receiver="${param.receiver}" mobile="${param.mobile}" onclick="confirmPay()"><button class="confirmPay">确认支付</button></a>
	</div>

</div>