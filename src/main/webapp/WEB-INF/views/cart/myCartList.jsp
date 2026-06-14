<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"
	isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %> 

<c:set var="contextPath"  value="${pageContext.request.contextPath}"  />
<c:set var="myCartList"  value="${cartMap.myCartList}"  />
<c:set var="myGoodsList"  value="${cartMap.myGoodsList}"  />

<c:set  var="totalGoodsNum" value="0" />  <!--주문 개수 -->
<c:set  var="totalDeliveryPrice" value="0" /> <!-- 총 배송비 --> 
<c:set  var="totalDiscountedPrice" value="0" /> <!-- 총 할인금액 -->
<head>
<script type="text/javascript">
function calcGoodsPrice(bookPrice, obj) {
    var row = obj.closest("tr");
    var goods_qty = row.querySelector("input[name='cart_qty']");

    var p_totalGoodsNum = document.getElementById("p_totalGoodsNum");
    var p_totalGoodsPrice = document.getElementById("p_totalGoodsPrice");
    var p_final_totalPrice = document.getElementById("p_final_totalPrice");

    var h_totalGoodsNum = document.getElementById("h_totalGoodsNum");
    var h_totalGoodsPrice = document.getElementById("h_totalGoodsPrice");
    var h_totalDeliveryPrice = document.getElementById("h_totalDeliveryPrice");
    var h_final_totalPrice = document.getElementById("h_final_totalPrice");

    var qty = Number(goods_qty.value);
    var price = Number(bookPrice) * qty;
    var delivery = Number(h_totalDeliveryPrice.value);

    var totalNum = Number(h_totalGoodsNum.value);
    var totalPrice = Number(h_totalGoodsPrice.value);

    if (obj.checked == true) {
        totalNum = totalNum + 1;
        totalPrice = totalPrice + price;
    } else {
        totalNum = totalNum - 1;
        totalPrice = totalPrice - price;
    }

    var finalTotalPrice = totalPrice + delivery;

    h_totalGoodsNum.value = totalNum;
    h_totalGoodsPrice.value = totalPrice;
    h_final_totalPrice.value = finalTotalPrice;

    p_totalGoodsNum.innerHTML = totalNum + "개";
    p_totalGoodsPrice.innerHTML = totalPrice + "원";
    p_final_totalPrice.innerHTML = finalTotalPrice + "원";
}
function modify_cart_qty(goods_id,bookPrice,index){
	//alert(index);
     var cart_qty = document.frm_order_all_cart.cart_qty;
   var _cart_goods_qty=0;
	    if (cart_qty.length > 1) {
        _cart_goods_qty = cart_qty[index].value;
    } else {
        _cart_goods_qty = cart_qty.value;
    }
		
	var cart_goods_qty=Number(_cart_goods_qty);
	//alert("cart_goods_qty:"+cart_goods_qty);
	//console.log(cart_goods_qty);
	$.ajax({
		type : "post",
		async : false, //false인 경우 동기식으로 처리한다.
		url : "${contextPath}/cart/modifyCartQty.do",
		data : {
			goods_id:goods_id,
			cart_goods_qty:cart_goods_qty
		},
		
		success : function(data, textStatus) {
			//alert(data);
			if(data.trim()=='modify_success'){
				alert("수량을 변경했습니다!!");	
				 location.reload();
			}else{
				alert("다시 시도해 주세요!!");	
			}
			
		},
		error : function(data, textStatus) {
			alert("에러가 발생했습니다."+data);
		},
		complete : function(data, textStatus) {
			//alert("작업을완료 했습니다");
			
		}
	}); //end ajax	
}

function delete_cart_goods(cart_id){
	var cart_id=Number(cart_id);
	var formObj=document.createElement("form");
	var i_cart = document.createElement("input");
	i_cart.name="cart_id";
	i_cart.value=cart_id;
	
	formObj.appendChild(i_cart);
    document.body.appendChild(formObj); 
    formObj.method="post";
    formObj.action="${contextPath}/cart/removeCartGoods.do";
    formObj.submit();
}

function fn_order_each_goods(goods_id, goods_title, goods_sales_price, fileName, index){
    var cart_qty = document.frm_order_all_cart.cart_qty;
    var _order_goods_qty = 0;

    if (cart_qty.length > 1) {
        _order_goods_qty = cart_qty[index].value;
    } else {
        _order_goods_qty = cart_qty.value;
    }

    var formObj = document.createElement("form");

    var i_goods_id = document.createElement("input"); 
    var i_goods_title = document.createElement("input");
    var i_goods_sales_price = document.createElement("input");
    var i_fileName = document.createElement("input");
    var i_order_goods_qty = document.createElement("input");

    i_goods_id.name = "goods_id";
    i_goods_title.name = "goods_title";
    i_goods_sales_price.name = "goods_sales_price";
    i_fileName.name = "goods_fileName";
    i_order_goods_qty.name = "order_goods_qty";

    i_goods_id.value = goods_id;
    i_goods_title.value = goods_title;
    i_goods_sales_price.value = goods_sales_price;
    i_fileName.value = fileName;
    i_order_goods_qty.value = _order_goods_qty;

    formObj.appendChild(i_goods_id);
    formObj.appendChild(i_goods_title);
    formObj.appendChild(i_goods_sales_price);
    formObj.appendChild(i_fileName);
    formObj.appendChild(i_order_goods_qty);

    document.body.appendChild(formObj); 
    formObj.method = "post";
    formObj.action = "${contextPath}/order/orderEachGoods.do";
    formObj.submit();
}

function fn_order_all_cart_goods() {
    var objForm = document.frm_order_all_cart;
    var cart_qty = objForm.cart_qty;
    var checked_goods = objForm.checked_goods;

    if (!checked_goods) {
        alert("주문할 상품이 없습니다.");
        return;
    }

    // 이전에 만들어진 hidden cart_goods_qty 제거
    var oldHidden = objForm.querySelectorAll("input[name='cart_goods_qty']");
    for (var k = 0; k < oldHidden.length; k++) {
        oldHidden[k].remove();
    }

    var checkedCount = 0;

    if (checked_goods.length > 1) {
        for (var i = 0; i < checked_goods.length; i++) {
            if (checked_goods[i].checked == true) {
                var order_goods_id = checked_goods[i].value;
                var order_goods_qty = cart_qty[i].value;

                var hidden = document.createElement("input");
                hidden.type = "hidden";
                hidden.name = "cart_goods_qty";
                hidden.value = order_goods_id + ":" + order_goods_qty;

                objForm.appendChild(hidden);
                checkedCount++;
            }
        }
    } else {
        if (checked_goods.checked == true) {
            var hidden = document.createElement("input");
            hidden.type = "hidden";
            hidden.name = "cart_goods_qty";
            hidden.value = checked_goods.value + ":" + cart_qty.value;

            objForm.appendChild(hidden);
            checkedCount++;
        }
    }

    if (checkedCount == 0) {
        alert("주문할 상품을 선택하세요.");
        return;
    }

    objForm.method = "post";
    objForm.action = "${contextPath}/order/orderAllCartGoods.do";
    objForm.submit();
}

</script>
</head>
<body>
<%@ include file="../common/layout.jsp" %>
<%@ include file="../common/header.jsp" %>
	<form name="frm_order_all_cart">
	<table class="list_view">
		<tbody align=center >
			<tr style="background:#33ff00" >
				<td class="fixed" >구분</td>
				<td colspan=2 class="fixed">상품명</td>
				<td>정가</td>
				<td>판매가</td>
				<td>수량</td>
				<td>합계</td>
				<td>주문</td>
			</tr>
			
			 <c:choose>
				    <c:when test="${ empty myCartList }">
				    <tr>
				       <td colspan=8 class="fixed">
				         <strong>장바구니에 상품이 없습니다.</strong>
				       </td>
				     </tr>
				    </c:when>
			        <c:otherwise>
			       
               
				      <c:forEach var="item" items="${myGoodsList }" varStatus="cnt">
				       <c:set var="cart_goods_qty" value="${myCartList[cnt.count-1].cart_goods_qty}" />
				       <c:set var="cart_id" value="${myCartList[cnt.count-1].cart_id}" />
					   <tr> 
					<td><input type="checkbox" name="checked_goods"  checked  value="${item.goods_id }"  onClick="calcGoodsPrice(${item.goods_sales_price },this)"></td>
					<td class="goods_image">
					<a href="${contextPath}/goods/goodsDetail.do?goods_id=${item.goods_id }">
						<img width="75" alt="" src="${contextPath}/thumbnails.do?goods_id=${item.goods_id}&fileName=${item.goods_fileName}"  />
					</a>
					</td>
					<td>
						<h2>
							<a href="${contextPath}/goods/goodsDetail.do?goods_id=${item.goods_id }">${item.goods_title }</a>
						</h2>
					</td>
					<td class="price"><span>${item.goods_price }원</span></td>
					<td>
						<strong>
							<fmt:formatNumber value="${item.goods_sales_price}" type="number" var="sales_price" />
							${sales_price}원

							<c:if test="${item.goods_price > item.goods_sales_price}">
								<c:set var="discountRate"
									value="${(item.goods_price - item.goods_sales_price) * 100 / item.goods_price}" />
								(
								<fmt:formatNumber value="${discountRate}" pattern="0" />% 할인
								)
							</c:if>
						</strong>
					</td>
					<td>
					   <input type="text" name="cart_qty" size=3 value="${cart_goods_qty}"><br>
						<a href="javascript:modify_cart_qty(${item.goods_id },${item.goods_sales_price},${cnt.count-1 });" >
						    <img width=25 alt=""  src="${contextPath}/image/btn_modify_qty.jpg">
						</a>
					</td>
					<td>
					   <strong>
					    <fmt:formatNumber  value="${item.goods_sales_price*cart_goods_qty}" type="number" var="total_sales_price" />
				         ${total_sales_price}원
					</strong> </td>
					<td>
					      <a href="javascript:fn_order_each_goods('${item.goods_id }','${item.goods_title }','${item.goods_sales_price}','${item.goods_fileName}', ${cnt.count-1});">
					       	<img width="75" alt=""  src="${contextPath}/image/btn_order.jpg">
							</a><br>
					 	<a href="#"> 
					 	   <img width="75" alt=""
							src="${contextPath}/image/btn_order_later.jpg">
						</a><br> 
						<a href="#"> 
						   <img width="75" alt=""
							src="${contextPath}/image/btn_add_list.jpg">
						</A><br> 
						<a href="javascript:delete_cart_goods('${cart_id}');""> 
						   <img width="75" alt=""
							   src="${contextPath}/image/btn_delete.jpg">
					   </a>
					</td>
			</tr>
				<c:set  var="totalGoodsPrice" value="${totalGoodsPrice+item.goods_sales_price*cart_goods_qty }" />
				<c:set  var="totalGoodsNum" value="${totalGoodsNum+1 }" />
			   </c:forEach>
		    
		</tbody>
	</table>
     	
	<div class="clear"></div>
	 </c:otherwise>
	</c:choose> 
	<br>
	<br>
	
	<table  width=80%   class="list_view" style="background:#cacaff">
	<tbody>
	     <tr  align=center  class="fixed" >
	       <td class="fixed">총 상품수 </td>
	       <td>총 상품금액</td>
	       <td>  </td>
	       <td>총 배송비</td>
	       <td>  </td>
	       <td>총 할인 금액 </td>
	       <td>  </td>
	       <td>최종 결제금액</td>
	     </tr>
		<tr cellpadding=40  align=center >
			<td id="">
			  <p id="p_totalGoodsNum">${totalGoodsNum}개 </p>
			  <input id="h_totalGoodsNum"type="hidden" value="${totalGoodsNum}"  />
			</td>
	       <td>
	          <p id="p_totalGoodsPrice">
	          <fmt:formatNumber  value="${totalGoodsPrice}" type="number" var="total_goods_price" />
				         ${total_goods_price}원
	          </p>
	          <input id="h_totalGoodsPrice"type="hidden" value="${totalGoodsPrice}" />
	       </td>
	       <td> 
	          <img width="25" alt="" src="${contextPath}/image/plus.jpg">  
	       </td>
	       <td>
	         <p id="p_totalDeliveryPrice">${totalDeliveryPrice }원  </p>
	         <input id="h_totalDeliveryPrice"type="hidden" value="${totalDeliveryPrice}" />
	       </td>
	       <td> 
	         <img width="25" alt="" src="${contextPath}/image/minus.jpg"> 
	       </td>
	       <td>  
	         <p id="p_totalSalesPrice"> 
				         ${totalDiscountedPrice}원
	         </p>
	         <input id="h_totalSalesPrice"type="hidden" value="${totalSalesPrice}" />
	       </td>
	       <td>  
	         <img width="25" alt="" src="${contextPath}/image/equal.jpg">
	       </td>
	       <td>
	          <p id="p_final_totalPrice">
	          <fmt:formatNumber  value="${totalGoodsPrice+totalDeliveryPrice-totalDiscountedPrice}" type="number" var="total_price" />
	            ${total_price}원
	          </p>
	          <input id="h_final_totalPrice" type="hidden" value="${totalGoodsPrice+totalDeliveryPrice-totalDiscountedPrice}" />
	       </td>
		</tr>
		</tbody>
	</table>
	<center>
    <br><br>	
		 <a href="javascript:fn_order_all_cart_goods()">
		 	<img width="75" alt="" src="${contextPath}/image/btn_order_final.jpg">
		 </a>
		 <a href="#">
		 	<img width="75" alt="" src="${contextPath}/image/btn_shoping_continue.jpg">
		 </a>
	<center>
</form>	