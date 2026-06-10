package com.example.bookshop01.admin.order.dao;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.example.bookshop01.member.vo.MemberVO;
import com.example.bookshop01.order.vo.OrderVO;

public interface AdminOrderDAO {
	public List<OrderVO> selectNewOrderList(Map<String, Object> condMap) throws DataAccessException;
	public void  updateDeliveryState(Map<String, Object> deliveryMap) throws DataAccessException;
	public List<OrderVO> selectOrderDetail(int order_id) throws DataAccessException;
	public MemberVO selectOrderer(String member_id) throws DataAccessException;
}
