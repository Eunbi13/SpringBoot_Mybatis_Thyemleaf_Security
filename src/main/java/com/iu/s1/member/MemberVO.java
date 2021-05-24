package com.iu.s1.member;


import javax.validation.constraints.NotEmpty;

import org.hibernate.validator.constraints.Length;

import lombok.Data;

@Data
public class MemberVO {
	private String userName;
	
	@Length(max = 10, min=4)
	private String password;
	
	private String passwordCheck;
	
	@NotEmpty
	private String name;
	private String email;
	private String phone;
	
	private MemberFileVO memberFileVO;

}
