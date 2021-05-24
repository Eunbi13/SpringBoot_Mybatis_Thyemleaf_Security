package com.example.demo.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Component;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.servlet.view.AbstractView;

//Custom view 생성
//1. AbstractView를 상속 받아야 함

//3. 객체 생성 @Component(참조변수명) -> fileDown 참조변수명 =  new FileDown();
@Component("fileDown")
public class FileDown extends AbstractView{
	//5-1.
	@Autowired
	private ResourceLoader resourceLoader;
	
	
//2, renderMergedOutputModel 오버라이딩
	@Override
	protected void renderMergedOutputModel(Map<String, Object> model, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		//model: ModelAndView Model 객체
		String fileName = (String)model.get("fileName");
		String oriName = (String)model.get("oriName");
		String filePath = (String)model.get("filePath");
		System.out.println(fileName);
		System.out.println(filePath);
		
		//5. 파일을 클라이언트로 다시 전송 ResourceLoader 사용
		String path="classpath:/static/";
		
		File file = new File(resourceLoader.getResource(path).getFile(), filePath);//경로만 설정
		System.out.println(file.getAbsolutePath());
		
		file = new File(file, fileName);//파일이름까지 설정
		
		//한글 처리
		response.setCharacterEncoding("UTF-8");
		
		//총 파일의 크기<<얼마나 남았나 보여주려고
		response.setContentLengthLong(file.length());
		
		//다운로드시 파일 이름을 인코딩 처리
		fileName = URLEncoder.encode(oriName, "UTF-8");
		
		//header 설정
		//다운로드 시 이름 처리
		response.setHeader("Content-Disposition", "attachment;filename=\""+fileName+"\"" );
		//2진코드로 왔다갔다 하는 정보를 웹브라우저한테 제공
		response.setHeader("Content-Transfer-Encoding", "binary");
		
		//HDD에서 파일을 읽고
		FileInputStream fi = new FileInputStream(file);
		//Client로 전송 준비
		OutputStream os = response.getOutputStream();
				
		//전송
		FileCopyUtils.copy(fi, os);
				
		os.close();
		fi.close();
	}
}
