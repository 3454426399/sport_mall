<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" isELIgnored="false"%>
	
<div class="payedDiv">
	<div class="payedTextDiv">
		<img src="img/site/paySuccess.png">
		<span>您已成功付款</span> 
		
	</div>
	<div class="payedAddressInfo">
		<ul>
			<li>收货地址：${param.address}</li>
			<li>收获人姓名：${param.receiver}</li>
			<li>联系方式：${param.mobile}</li>
			<li>实付款：<span class="payedInfoPrice">
			￥<fmt:formatNumber type="number" value="${param.total}" minFractionDigits="2"/>
			</li>
			<li>预计08月08日送达	</li>
		</ul>
				
		<div class="paedCheckLinkDiv">
			您可以
			<a class="payedCheckLink" href="forebought">查看已买到的宝贝</a>
			<a class="payedCheckLink" href="forebought">查看交易详情 </a>
		</div>
			
	</div>
	
	<div class="payedSeperateLine">
	</div>
	
	<div class="warningDiv">
		<img src="img/site/warning.png">
		<b>安全提醒：</b>下单后，<span class="redColor boldWord">用QQ给您发送链接办理退款的都是骗子！谨防假冒客服电话诈骗！</span>
	</div>

	

</div>