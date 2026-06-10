package com.example.bookshop01.cart.service;

import java.util.Map;

import com.example.bookshop01.cart.vo.CartVO;

public interface CartService {
	public Map<String ,Object> myCartList(CartVO cartVO) throws Exception;
	public boolean findCartGoods(CartVO cartVO) throws Exception;
	public void addGoodsInCart(CartVO cartVO) throws Exception;
	public boolean modifyCartQty(CartVO cartVO) throws Exception;
	public void removeCartGoods(int cart_id) throws Exception;
}
