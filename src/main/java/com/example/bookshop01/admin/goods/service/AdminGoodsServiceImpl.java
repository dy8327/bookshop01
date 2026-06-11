package com.example.bookshop01.admin.goods.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.example.bookshop01.admin.goods.dao.AdminGoodsDAO;
import com.example.bookshop01.goods.vo.GoodsVO;
import com.example.bookshop01.goods.vo.ImageFileVO;
import com.example.bookshop01.order.vo.OrderVO;


@Service("adminGoodsService")
@Transactional(propagation=Propagation.REQUIRED)
public class AdminGoodsServiceImpl implements AdminGoodsService {
	@Autowired
	private AdminGoodsDAO adminGoodsDAO;
	@SuppressWarnings("unchecked")
	@Override
	public int addNewGoods(Map<String, Object> newGoodsMap) throws Exception{
		int goods_id = adminGoodsDAO.insertNewGoods(newGoodsMap);
		List<ImageFileVO> imageFileList = (List<ImageFileVO>) newGoodsMap.get("imageFileList");
		if (imageFileList != null && !imageFileList.isEmpty()){
		for(ImageFileVO imageFileVO : imageFileList) {
			imageFileVO.setGoods_id(goods_id);
		}
		adminGoodsDAO.insertGoodsImageFile(imageFileList);
	}
		return goods_id;
	}
	
	@Override
	public List<GoodsVO> listNewGoods(Map<String, Object> condMap) throws Exception{
		return adminGoodsDAO.selectNewGoodsList(condMap);
	}
	@Override
	public Map<String, Object> goodsDetail(int goods_id) throws Exception {
		Map<String, Object> goodsMap = new HashMap<>();
		GoodsVO goodsVO=adminGoodsDAO.selectGoodsDetail(goods_id);
		List<ImageFileVO> imageFileList =adminGoodsDAO.selectGoodsImageFileList(goods_id);
		goodsMap.put("goods", goodsVO);
		goodsMap.put("imageFileList", imageFileList);
		return goodsMap;
	}
	@Override
	public List<ImageFileVO> goodsImageFile(int goods_id) throws Exception{
		List<ImageFileVO> imageList =adminGoodsDAO.selectGoodsImageFileList(goods_id);
		return imageList;
	}
	
	@Override
	public void modifyGoodsInfo(Map<String, String> goodsMap) throws Exception{
		adminGoodsDAO.updateGoodsInfo(goodsMap);
		
	}	
	@Override
	public void modifyGoodsImage(List<ImageFileVO> imageFileList) throws Exception{
		adminGoodsDAO.updateGoodsImage(imageFileList); 
	}
	
	@Override
	public List<OrderVO> listOrderGoods(Map<String, Object> condMap) throws Exception{
		return adminGoodsDAO.selectOrderGoodsList(condMap);
	}
	@Override
	public void modifyOrderGoods(Map<String, Object> orderMap) throws Exception{
		adminGoodsDAO.updateOrderGoods(orderMap);
	}
	
	@Override
	@Transactional(rollbackFor = Exception.class)
	public void removeGoods(int goods_id) throws Exception {
		adminGoodsDAO.deleteGoods(goods_id);
	}
	

	@Override
	public void removeGoodsImage(int image_id) throws Exception{
		adminGoodsDAO.deleteGoodsImage(image_id);
	}
	
	@Override
	public void addNewGoodsImage(List<ImageFileVO> imageFileList) throws Exception{
		adminGoodsDAO.insertGoodsImageFile(imageFileList);
	}
	
	
}
