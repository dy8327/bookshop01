package com.example.bookshop01.admin.member.dao;

import java.util.Map;
import java.util.List;

import org.springframework.dao.DataAccessException;

import com.example.bookshop01.member.vo.MemberVO;

public interface AdminMemberDAO {
	public List<MemberVO> listMember(Map<String, String> condMap) throws DataAccessException;
	public MemberVO memberDetail(String member_id) throws DataAccessException;
	public void modifyMemberInfo(Map<String, String> memberMap) throws DataAccessException;
}
