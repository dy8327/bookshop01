package com.example.bookshop01.admin.member.service;

import java.util.List;
import java.util.Map;

import com.example.bookshop01.member.vo.MemberVO;

public interface AdminMemberService {
	public List<MemberVO> listMember(Map<String, String> condMap) throws Exception;
	public MemberVO memberDetail(String member_id) throws Exception;
	public void  modifyMemberInfo(Map<String, String> memberMap) throws Exception;
}
