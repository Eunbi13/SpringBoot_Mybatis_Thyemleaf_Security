package com.iu.s1.home;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
	@GetMapping("/")
	public String index() {
		return "index";
	}
	
	@GetMapping("/admin")
	public void admin() {
		System.out.println("I'm admin");
	}
	
	@GetMapping("/member")
	public void member() {
		System.out.println("I'm Crew One");
	}
	
}
