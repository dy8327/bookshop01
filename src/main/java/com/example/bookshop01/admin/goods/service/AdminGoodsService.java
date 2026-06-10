package com.example.bookshop01.admin.goods.service;

import java.util.List;
import java.util.Map;

import com.example.bookshop01.goods.vo.GoodsVO;
import com.example.bookshop01.goods.vo.ImageFileVO;
import com.example.bookshop01.order.vo.OrderVO;

public interface AdminGoodsService {
	public int  addNewGoods(Map<String, Object> newGoodsMap) throws Exception;
	public List<GoodsVO> listNewGoods(Map<String, Object> condMap) throws Exception;
	public Map<String, Object> goodsDetail(int goods_id) throws Exception;
	public List<ImageFileVO> goodsImageFile(int goods_id) throws Exception;
	public void modifyGoodsInfo(Map<String, String> goodsMap) throws Exception;
	public void modifyGoodsImage(List<ImageFileVO> imageFileList) throws Exception;
	public List<OrderVO> listOrderGoods(Map<String, Object> condMap) throws Exception;
	public void modifyOrderGoods(Map<String, Object> orderMap) throws Exception;
	public void removeGoodsImage(int image_id) throws Exception;
	public void addNewGoodsImage(List<ImageFileVO> imageFileList) throws Exception;
	
}
