package com.iu.s1.member;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("/member/**")
public class MemberController {
	
	@Autowired
	private MemberService service;
	
	@GetMapping("join")
	public String setJoin(@ModelAttribute MemberVO memberVO)throws Exception{
		return "member/memberJoin";
	}
	
	@PostMapping("join")
	public String setJoin(@Valid MemberVO memberVO,Errors errors, MultipartFile file)throws Exception{
		
//		if(errors.hasErrors()) {
//			return "member/memberJoin";
//		}
		
		if(service.memberError(memberVO, errors)) {
			return "member/memberJoin";
		}
		
		System.out.println(file.getName());
		System.out.println(file.getOriginalFilename());
		
		service.setJoin(memberVO, file);
		return "redirect:../";
	}
	
	@GetMapping("login")
	public String getLogin()throws Exception{
		return "member/memberLogin";
	}
	
	@PostMapping("login")
	public String getLogin(MemberVO memberVO, HttpSession session)throws Exception{
		
		
		
		memberVO = service.getLogin(memberVO);
		session.setAttribute("member", memberVO);
		
		
		return "redirect:/";
	}
	
	@GetMapping("page")
	public String getMyPage() throws Exception{
		return "member/memberPage";
	}
	
	@GetMapping("logout")
	public String logout(HttpSession session) throws Exception{
		session.invalidate();
		return "redirect:/";
	}
	
}
