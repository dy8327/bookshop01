package com.example.bookshop01.admin.goods.dao;


import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

import com.example.bookshop01.goods.vo.GoodsVO;
import com.example.bookshop01.goods.vo.ImageFileVO;
import com.example.bookshop01.order.vo.OrderVO;

@Repository("adminGoodsDAO")
public class AdminGoodsDAOImpl  implements AdminGoodsDAO{
	@Autowired
	private SqlSession sqlSession;
	
	@Override
	public int insertNewGoods(Map<String, Object> newGoodsMap) throws DataAccessException {
		sqlSession.insert("mapper.admin.goods.insertNewGoods",newGoodsMap);
		return Integer.parseInt((String)newGoodsMap.get("goods_id"));
	}
	
	@Override
	public void insertGoodsImageFile(List<ImageFileVO> fileList)  throws DataAccessException {
		for(int i=0; i<fileList.size();i++){
			ImageFileVO imageFileVO=(ImageFileVO)fileList.get(i);
			sqlSession.insert("mapper.admin.goods.insertGoodsImageFile",imageFileVO);
		}
	}
		
	@Override
	public List<GoodsVO>selectNewGoodsList(Map<String, Object> condMap) throws DataAccessException {
		return sqlSession.selectList("mapper.admin.goods.selectNewGoodsList",condMap);
	}
	
	@Override
	public GoodsVO selectGoodsDetail(int goods_id) throws DataAccessException{
		
		return sqlSession.selectOne("mapper.admin.goods.selectGoodsDetail",goods_id);
	}
	
	@Override
	public List<ImageFileVO> selectGoodsImageFileList(int goods_id) throws DataAccessException {
		
		return sqlSession.selectList("mapper.admin.goods.selectGoodsImageFileList",goods_id);
	}
	
	@Override
	public void updateGoodsInfo(Map<String, String> goodsMap) throws DataAccessException{
		sqlSession.update("mapper.admin.goods.updateGoodsInfo",goodsMap);
	}
	
	@Override
	public void deleteGoodsImage(int image_id) throws DataAccessException{
		sqlSession.delete("mapper.admin.goods.deleteGoodsImage",image_id);
	}
	
	@Override
	public void deleteGoodsImage(List<ImageFileVO> fileList) throws DataAccessException{
		int image_id;
		for(int i=0; i<fileList.size();i++){
			ImageFileVO bean=(ImageFileVO) fileList.get(i);
			image_id=bean.getImage_id();
			sqlSession.delete("mapper.admin.goods.deleteGoodsImage",image_id);	
		}
	}

	@Override
	public List<OrderVO> selectOrderGoodsList(Map<String, Object> condMap) throws DataAccessException{
		return sqlSession.selectList("mapper.admin.selectOrderGoodsList",condMap);
	}	
	
	@Override
	public void updateOrderGoods(Map<String, Object> orderMap) throws DataAccessException{
		sqlSession.update("mapper.admin.goods.updateOrderGoods",orderMap);
		
	}

	@Override
	public void updateGoodsImage(List<ImageFileVO> imageFileList) throws DataAccessException {
		
		for(int i=0; i<imageFileList.size();i++){
			ImageFileVO imageFileVO = imageFileList.get(i);
			sqlSession.update("mapper.admin.goods.updateGoodsImage",imageFileVO);	
		}
		
	}
	@Override
	public void deleteGoods(int goods_id) throws Exception {
		sqlSession.delete("mapper.admin.goods.deleteGoodsImage", goods_id);
		sqlSession.delete("mapper.admin.goods.deleteGoods", goods_id);
}





	

}
