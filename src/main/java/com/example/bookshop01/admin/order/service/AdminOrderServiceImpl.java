package com.example.bookshop01.admin.order.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.example.bookshop01.admin.order.dao.AdminOrderDAO;
import com.example.bookshop01.member.vo.MemberVO;
import com.example.bookshop01.order.vo.OrderVO;


@Service("adminOrderService")
@Transactional(propagation=Propagation.REQUIRED)
public class AdminOrderServiceImpl implements AdminOrderService {
	
	private final AdminOrderDAO adminOrderDAO;
	
	public AdminOrderServiceImpl(@Qualifier("adminOrderDAO") AdminOrderDAO adminOrderDAO) {
		this.adminOrderDAO = adminOrderDAO;
	}
	
	public List<OrderVO>listNewOrder(Map<String, Object> condMap) throws Exception{
		return adminOrderDAO.selectNewOrderList(condMap);
	}
	@Override
	public void  modifyDeliveryState(Map<String, Object> deliveryMap) throws Exception{
		adminOrderDAO.updateDeliveryState(deliveryMap);
	}
	@Override
	public Map<String, Object> orderDetail(int order_id) throws Exception{
		Map<String, Object> orderMap=new HashMap<>();
		List<OrderVO> orderList =adminOrderDAO.selectOrderDetail(order_id);
		OrderVO deliveryInfo=(OrderVO)orderList.get(0);
		String member_id=(String)deliveryInfo.getMember_id();
		MemberVO orderer=adminOrderDAO.selectOrderer(member_id);
		orderMap.put("orderList",orderList);
		orderMap.put("deliveryInfo",deliveryInfo);
		orderMap.put("orderer", orderer);
		return orderMap;
	}

	
	

}
