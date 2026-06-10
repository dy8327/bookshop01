package com.example.bookshop01.admin.order.service;

import java.util.List;
import java.util.Map;

import com.example.bookshop01.order.vo.OrderVO;

public interface AdminOrderService {
	public List<OrderVO>listNewOrder(Map<String, Object> condMap) throws Exception;
	public void  modifyDeliveryState(Map<String, Object> deliveryMap) throws Exception;
	public Map<String, Object> orderDetail(int order_id) throws Exception;
}
