package com.example.demo.util;

import java.io.File;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Component;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;

@Component
public class FileManager {
	//역활: resource안에 잇는 경로를 가지고 오는 객체
	//classpath 경로를 받아오기 위해 사용, 없는 경로를 쓰면 에러남
	@Autowired
	private ResourceLoader resourceLoader;
	
	public String save(MultipartFile mf, String filePath) throws Exception{
		//filePath : 정적인 파일은 static에 저장될 것 /resources/static/를 제외한 하위경로
		/**
		 * 만약 저장할 경로가 고정되어있다면 받아올 필요 없이 바로 File객체 생성
		 *	String path = "c:/files";
		 *	File file = new File(path, filePath);
		 */
		
		
		//1 경로 설정 resourceLoader
/**		
 * 		String path="classpath:/static/";
 *		File file = new File(resourceLoader.getResource(path).getFile(),filePath);
 *							---------------------------------까지의 경로를 파일로 치환 getFile()
 *																		--------나머지 경로 넣기 
*/		
		//1-1 경로설정 두번째 방법 classpath 경로를 받아오기 위해 사용
		//ClassPathResource 자체적으로 classpath를 가져오기때문에 그 밑의 static만 쓴다
		String path="static";
		ClassPathResource classPathResource = new ClassPathResource(path);
		File file = new File(classPathResource.getFile(), filePath);
		
		
		System.out.println(file.getAbsolutePath());
		//1-1. 경로가 없을 경우 만들기
		if(!file.exists()) {
			file.mkdirs();
		}
		
		//2 저장할 파일명을 생성// 랜덤이름에 오리지널 이름 더하기
		String fileName = UUID.randomUUID().toString()+"_"+mf.getOriginalFilename();
		
		//3. 저장하기 위에 파일 객체는 폴더만 만드는 객체이고 그 자체를 생성자에 넣어서 새로운 객체로 뒤바꿈
		file = new File(file, fileName);
		
		//4. 저장 transferTo(목적지)사용(
		mf.transferTo(file);
		//4-1. FileCopyUtils사용
		//FileCopyUtils.copy(mf.getBytes(), file);
		
		//리턴은 저장한 파일명
		return fileName;
	}
}
