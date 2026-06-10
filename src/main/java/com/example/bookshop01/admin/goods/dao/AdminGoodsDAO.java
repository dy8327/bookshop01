package com.example.bookshop01.admin.goods.dao;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.example.bookshop01.goods.vo.GoodsVO;
import com.example.bookshop01.goods.vo.ImageFileVO;
import com.example.bookshop01.order.vo.OrderVO;

public interface AdminGoodsDAO {
	public int insertNewGoods(Map<String, Object> newGoodsMap) throws DataAccessException;
	public List<GoodsVO>selectNewGoodsList(Map<String, Object> condMap) throws DataAccessException;
	public GoodsVO selectGoodsDetail(int goods_id) throws DataAccessException;
	public List<ImageFileVO> selectGoodsImageFileList(int goods_id) throws DataAccessException;
	public void insertGoodsImageFile(List<ImageFileVO> fileList)  throws DataAccessException;
	public void updateGoodsInfo(Map<String, String> goodsMap) throws DataAccessException;
	public void updateGoodsImage(List<ImageFileVO> imageFileList) throws DataAccessException;
	public void deleteGoodsImage(int image_id) throws DataAccessException;
	public void deleteGoodsImage(List<ImageFileVO> fileList) throws DataAccessException;
	public List<OrderVO> selectOrderGoodsList(Map<String, Object> condMap) throws DataAccessException;
	public void updateOrderGoods(Map<String, Object> orderMap) throws DataAccessException;
	
}
