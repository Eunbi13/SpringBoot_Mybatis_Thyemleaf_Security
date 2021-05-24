package com.iu.s1.board.notice;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Configuration
@RequestMapping("/notice/**")
public class NoticeController {
	@GetMapping("list")
	public void getList()throws Exception{
		System.out.println("notice list page");
	}
	
	@GetMapping("insert")
	public void setInsert() throws Exception{
		System.out.println("notice insert page");
	}
}
