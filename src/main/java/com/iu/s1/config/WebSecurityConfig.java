package com.iu.s1.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter{
	
	@Override
	public void configure(WebSecurity web) throws Exception {
		// 시큐리티를 걸치지 말아야 하는 경로 설정

		 web.ignoring()
			.antMatchers("/resources/**")//정적 파일 다 무시
			.antMatchers("/images/**")//이런 url이 들어오면 무시하세요
			.antMatchers("/css/**")
			.antMatchers("/js/**")
			.antMatchers("/vendor/**")//외부 css파일 가지고 올떄 cdn방식이 아닐때 많이 사용하는 폴더 명
			.antMatchers("/favicon/**")//아이콘,,?
			;
		 
		 
	}
	//로그인 안한 사람이 들어갈 수 있는 곳 지정
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
			.authorizeRequests()//인증된 요청
				.antMatchers("/")//루트와 같다면(home주소)
				.permitAll()//누구나 허용
//				.antMatchers("/notice/**")//이렇게 하면 이 밑에꺼는 안 먹는다고 
				.antMatchers("/notice/list", "/notice/select")
				.permitAll()
				.antMatchers("/notice/**")//순서는 이렇게 하는 좋다,,,먼저 제한 없는 거 쓰고 제한 하는 거 이케 묶어서 쓰고,,
				.hasRole("ADMIN")
				.anyRequest().authenticated()
//				.antMatchers("/member")
//				.authenticated()//로그인 한 사람만 가능 //로그인 요구
////				.permitAll()
//				.antMatchers("/admin")
//				.hasRole("ADMIN")//로그인 한 사람 중 ADMIN만 가능하다 //권한 요구 //아이디 말하는거 아님
				;
	}
	
	
}
