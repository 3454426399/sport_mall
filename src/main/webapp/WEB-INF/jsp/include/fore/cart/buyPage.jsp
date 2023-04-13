<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" isELIgnored="false"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<div class="buyPageDiv">

  
	<div class="buyFlow">
		<img class="pull-right" src="img/site/buyflow.png">
		<div style="clear:both"></div>
	</div>
	<div class="address">
		<div class="addressTip">输入收货相关信息</div>
		<div>
		
			<table class="addressTable" style="width: 800px;">
				<tr style="width: 800px;">
					<td>选择收货地址<span class="redStar">*</span></td>
					<td style="width: 500px">
						省&nbsp;
						<select id="province" onchange="foreShowCity()" style="width: 80px;height: 25px">
							<option>请选择</option>
							<option>河北省</option>
							<option>山西省</option>
							<option>广东省</option>
							<option>四川省</option>
							<option>贵州省</option>
							<option>广西壮族自治区</option>
							<option>内蒙古自治区</option>
							<option>山东省</option>
							<option>省1</option>
							<option>省2</option>
							<option>省3</option>
							<option>省4</option>
							<option>省5</option>
							<option>省6</option>
							<option>省7</option>
							<option>省8</option>
							<option>省9</option>
							<option>省10</option>
							<option>省11</option>
							<option>省12</option>
							<option>省13</option>
							<option>省14</option>
							<option>省15</option>
							<option>省16</option>
							<option>省17</option>
							<option>省18</option>
							<option>省19</option>
							<option>省20</option>
						</select>
						市&nbsp;
						<select id="city" onchange="foreShowCounty()" style="width: 80px;height: 25px">
							<option>请选择</option>
							<option>上海市</option>
							<option>北京市</option>
						</select>
						区/县&nbsp;
						<select id="county" onchange="foreShowTown()" style="width: 80px;height: 25px">
							<option>请选择</option>
						</select>
						镇&nbsp;
						<select id="town" style="width: 80px;height: 25px">
							<option>请选择</option>
						</select>
					</td>
				</tr>
				<tr>
					<td >详细地址<span class="redStar">*</span></td>
					<td><textarea id="detailAddress" name="detailAddress" placeholder="建议您如实填写详细收货地址，例如接到名称，门牌号码，楼层和房间号等信息"></textarea></td>
					<td><a id="toCommonAddress" onclick="foreAddCommonAddress()" href="#" style="color: red;">导入到常用地址</a></td>
				</tr>
				<tr style="width: 350px">
					<td >从常用地址中导入<span class="redStar">*</span></td>
					<td>
						<select id="commonAddress" onclick="foreGetCommonAddress()" onchange="foreUseCommonAddress()" style="width: 350px;height: 25px">
							<option>请选择</option>
						</select>
					</td>
				</tr>
				<tr>
					<td>邮政编码<span class="redStar">*</span></td></td>
					<td><input  id="post" name="post" placeholder="如果您不清楚邮递区号，请填写000000" type="text"></td>
				</tr>
				<tr>
					<td>收货人姓名<span class="redStar">*</span></td>
					<td><input  id="receiver" name="receiver"  placeholder="长度不超过25个字符" type="text"></td>
				</tr>
				<tr>
					<td>手机号码 <span class="redStar">*</span></td>
					<td><input id="mobile" name="mobile"  placeholder="请输入11位手机号码" type="text"></td>
				</tr>
			</table>
			
		</div>




		
		
		
	
	</div>
	<div class="productList">
		<div class="productListTip">确认订单信息</div>
		
		
		<table class="productListTable">
			<thead>
				<tr>
					<th colspan="2" class="productListTableFirstColumn">
						<a class="marketLink" href="#nowhere">商城：体育商城</a>
						<a class="wangwanglink" href="#nowhere"> <span class="wangwangGif"></span> </a>
					</th>
					<th>单价</th>
					<th>数量</th>
					<th>小计</th>
					<th>配送方式</th>
				</tr>
				<tr class="rowborder">
					<td  colspan="2" ></td>
					<td></td>
					<td></td>
					<td></td>
					<td></td>
				</tr>
			</thead>
			<tbody class="productListTableTbody">
				<c:forEach items="${ois}" var="oi" varStatus="st" >
					<tr class="orderItemTR">
						<td class="orderItemFirstTD"><img class="orderItemImg" src="img/productSingle_middle/${oi.product.firstProductImage.id}.jpg"></td>
						<td class="orderItemProductInfo">
						<a  href="foreproduct?pid=${oi.product.id}" class="orderItemProductLink">
							${oi.product.name}
						</a>
						
						
							<img src="img/site/creditcard.png" title="支持信用卡支付">
							<img src="img/site/7day.png" title="消费者保障服务,承诺7天退货">
							<img src="img/site/promise.png" title="消费者保障服务,承诺如实描述">
						
						</td>
						<td>
						
						<span class="orderItemProductPrice">￥<fmt:formatNumber type="number" value="${oi.product.promotePrice}" minFractionDigits="2"/></span>
						</td>
						<td>
						<span class="orderItemProductNumber">${oi.number}</span>
						</td>
						<td><span class="orderItemUnitSum">
						￥<fmt:formatNumber type="number" value="${oi.number*oi.product.promotePrice}" minFractionDigits="2"/>
						</span></td>
						<c:if test="${st.count==1}">
						<td rowspan="5"  class="orderItemLastTD">
						<label class="orderItemDeliveryLabel">
							<input type="radio" value="" checked="checked">
							普通配送
						</label>
						
						<select class="orderItemDeliverySelect" class="form-control">
							<option>快递 免邮费</option>
						</select>

						</td>
						</c:if>
						
					</tr>
				</c:forEach>				
				
			</tbody>
			
		</table>
		<div class="orderItemSumDiv">
			<div class="pull-left">
				<span class="leaveMessageText">给卖家留言:</span>
				<span>
					<img class="leaveMessageImg" src="img/site/leaveMessage.png">
				</span>
				<span class="leaveMessageTextareaSpan">
					<textarea id="userMessage" name="userMessage" class="leaveMessageTextarea"></textarea>
					<div>
						<span>还可以输入200个字符</span>
					</div>
				</span>
			</div>
			
			<span class="pull-right">店铺合计(含运费): ￥<fmt:formatNumber type="number" value="${total}" minFractionDigits="2"/></span>
		</div>
		

				
	
	</div>

	<div class="orderItemTotalSumDiv">
		<div class="pull-right"> 
			<span>实付款：</span>
			<span class="orderItemTotalSumSpan">￥<fmt:formatNumber type="number" value="${total}" minFractionDigits="2"/></span>
		</div>
	</div>
	
	<div class="submitOrderDiv">
			<a class="submit" onclick="submit()"><button type="submitButton" class="submitOrderButton">提交订单</button></a>
	</div>

</div>