package com.example.bookshop01.admin.member.dao;

import java.util.Map;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

import com.example.bookshop01.member.vo.MemberVO;

@Repository("adminMemberDAO")
public class AdminMemberDAOImpl  implements AdminMemberDAO{
	
	private final SqlSession sqlSession;
	
	public AdminMemberDAOImpl(SqlSession sqlSession) {
		this.sqlSession = sqlSession;
	}
	
	
	public List<MemberVO> listMember(Map<String, String> condMap) throws DataAccessException{
		return sqlSession.selectList("mapper.admin.member.listMember",condMap);
	}
	
	public MemberVO memberDetail(String member_id) throws DataAccessException{
		MemberVO memberBean=(MemberVO)sqlSession.selectOne("mapper.admin.member.memberDetail",member_id);
		return memberBean;
	}
	
	public void modifyMemberInfo(Map<String, String> memberMap) throws DataAccessException{
		sqlSession.update("mapper.admin.member.modifyMemberInfo",memberMap);
	}
	
	

}
