package com.iu.s1.member;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface MemberMapper {
	public Long setJoin(MemberVO memberVO)throws Exception;
	public Long checkUserName(MemberVO memberVO)throws Exception;
	public Long setMemberFile(MemberFileVO memberFileVO)throws Exception;
	public MemberVO getLogin(MemberVO memberVO) throws Exception;
}
