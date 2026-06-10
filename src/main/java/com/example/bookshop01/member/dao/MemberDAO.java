package com.example.bookshop01.member.dao;

import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.example.bookshop01.member.vo.MemberVO;

public interface MemberDAO {
	public MemberVO login(Map<String, String> loginMap) throws DataAccessException;
	public void insertNewMember(MemberVO memberVO) throws DataAccessException;
	public String selectOverlappedID(String id) throws DataAccessException;
}
