package com.example.bookshop01.mypage.dao;


import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

import com.example.bookshop01.member.vo.MemberVO;
import com.example.bookshop01.order.vo.OrderVO;

@Repository("myPageDAO")
public class MyPageDAOImpl implements MyPageDAO{
	
	private final SqlSession sqlSession;
	
	@Autowired
	public MyPageDAOImpl(SqlSession sqlSession) {
		this.sqlSession = sqlSession;
	}
	
	public List<OrderVO> selectMyOrderGoodsList(String member_id) throws DataAccessException{
		return sqlSession.selectList("mapper.mypage.selectMyOrderGoodsList",member_id);
	}
	
	public List<OrderVO> selectMyOrderInfo(String order_id) throws DataAccessException{
		return sqlSession.selectList("mapper.mypage.selectMyOrderInfo",order_id);
	}	

	public List<OrderVO> selectMyOrderHistoryList(Map<String, String> dateMap) throws DataAccessException{
		return sqlSession.selectList("mapper.mypage.selectMyOrderHistoryList",dateMap);
	}
	
	public void updateMyInfo(Map<String, String> memberMap) throws DataAccessException{
		sqlSession.update("mapper.mypage.updateMyInfo",memberMap);
	}
	
	public MemberVO selectMyDetailInfo(String member_id) throws DataAccessException{
		MemberVO memberVO=(MemberVO)sqlSession.selectOne("mapper.mypage.selectMyDetailInfo",member_id);
		return memberVO;
		
	}
	
	public void updateMyOrderCancel(String order_id) throws DataAccessException{
		sqlSession.update("mapper.mypage.updateMyOrderCancel",order_id);
	}
}
