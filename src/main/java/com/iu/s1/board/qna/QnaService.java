package com.iu.s1.board.qna;

import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.error.MyException;
import com.example.demo.util.FileManager;
import com.example.demo.util.Pager;
import com.iu.s1.board.BoardFileVO;
import com.iu.s1.board.BoardMapper;
import com.iu.s1.board.BoardService;
import com.iu.s1.board.BoardVO;
@Service
public class QnaService implements BoardService{
	@Autowired
	private QnaMapper qnaMapper;
	@Autowired
	private FileManager fileManager;
	@Value("board.qna.filePath")
	private String filePath;
	
	@Override
	public List<BoardVO> getList(Pager pager) throws Exception {
		// TODO Auto-generated method stub
		pager.makeRow();
		pager.makeNum(qnaMapper.getTotalCount(pager));
		return qnaMapper.getList(pager);
	}
	@Override
	public BoardVO getSelect(BoardVO boardVO) throws Exception {
		qnaMapper.setHitUpdate(boardVO);
		return qnaMapper.getSelect(boardVO);
	}
	@Override
	@Transactional(rollbackFor = Exception.class)
	public Long setInsert(BoardVO boardVO, MultipartFile[] files) throws Exception {
		//1. insert
		Long result = qnaMapper.setInsert(boardVO);
		//2. qna ref update
		result = qnaMapper.setRefUpdate(boardVO);

		if(result<1) {
			throw new Exception();//강제로 이셉션 발생시키기
		}
		//3. file save
		String filePath = this.filePath;
		for(MultipartFile f: files) {
			if(f.getSize()==0) { continue; }
			String fileName = fileManager.save(f, filePath);
			BoardFileVO boardFileVO = new BoardFileVO();
			boardFileVO.setFileName(fileName);
			boardFileVO.setOriName(f.getOriginalFilename());
			boardFileVO.setNum(boardVO.getNum());
			System.out.println(fileName);
			qnaMapper.setFileInsert(boardFileVO);
		}
		return result;
	}
	
	public Long setReplyInsert(BoardVO boardVO, MultipartFile [] files)throws Exception{
		//부모 글번호만 있음
		//1.step update
		qnaMapper.setReplyUpdate(boardVO);
		//2.reply insert
		Long result = qnaMapper.setReplyInsert(boardVO);
		//3.첨부파일 받을거면
		String filePath = this.filePath;
		for(MultipartFile f: files) {
			if(f.getSize()==0) { continue; }
			String fileName = fileManager.save(f, filePath);
			BoardFileVO boardFileVO = new BoardFileVO();
			boardFileVO.setFileName(fileName);
			boardFileVO.setOriName(f.getOriginalFilename());
			boardFileVO.setNum(boardVO.getNum());
			System.out.println(fileName);
			qnaMapper.setFileInsert(boardFileVO);
		}
		return result;
	}
	
	@Override
	public Long setUpdate(BoardVO boardVO) throws Exception {
		// 파일 조회 글 수정 성공시 파일 수정
		
//		
//		Random random = new Random();
//		int result = random.nextInt(1);
//		System.out.println(result);
//		if(result==0) {
//			//throw new 예외클래스 생성자();
//			throw new MyException("Update Failed");
//		}
		
		return qnaMapper.setUpdate(boardVO);//(long)result; 
	}
	@Override
	public Long setDelete(BoardVO boardVO) throws Exception {
		// TODO Auto-generated method stub
		//1. fileName 조회
		//2. 테이블에서 글 삭제
		//3. 2번 성공시 HDD파일 삭제
		//지금은 생략
		return qnaMapper.setDelete(boardVO);
	}
}
