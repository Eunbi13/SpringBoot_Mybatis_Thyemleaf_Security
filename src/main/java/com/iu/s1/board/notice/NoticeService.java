package com.iu.s1.board.notice;

import java.util.List;

import org.apache.ibatis.session.SqlSessionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.util.FileManager;
import com.example.demo.util.Pager;
import com.iu.s1.board.BoardFileVO;
import com.iu.s1.board.BoardService;
import com.iu.s1.board.BoardVO;
@Service
public class NoticeService implements BoardService {
	@Autowired
	private NoticeMapper noticeMapper;
	@Autowired
	private FileManager fileManager;
	
	@Value("${board.notice.filePath}")
	private String filePath;
	
	
	@Override
	public List<BoardVO> getList(Pager pager) throws Exception {
//		if(pager.getCurPage()%2!=0) {
//			throw new SqlSessionException();
//		}
		pager.makeRow();
		Long totalCount = noticeMapper.getTotalCount(pager);
		pager.makeNum(totalCount);
		return noticeMapper.getList(pager);
	}
	@Override
	public BoardVO getSelect(BoardVO boardVO) throws Exception {
		// TODO Auto-generated method stub
		noticeMapper.setHitUpdate(boardVO);
		return noticeMapper.getSelect(boardVO);
	}
	@Override
	@Transactional(rollbackFor = Exception.class)//에러나면 롤백해라 
	public Long setInsert(BoardVO boardVO, MultipartFile [] files) throws Exception {
		// TODO Auto-generated method stub
		Long result = noticeMapper.setInsert(boardVO);
		//exception이 발생하지 않았어도 등록이 안될 수 도 있음
		if(result<1) {
			throw new Exception();//강제로 이셉션 발생시키기
		}
		
		
		//file
		String filePath = this.filePath;//"upload/notice/";//approperties에서 이 부분을 작업할 수 있다. 
		for(MultipartFile f: files) {
			if(f.getSize()==0) { continue; }
			String fileName = fileManager.save(f, filePath);
			BoardFileVO boardFileVO = new BoardFileVO();
			boardFileVO.setFileName(fileName);
			boardFileVO.setOriName(f.getOriginalFilename());
			boardFileVO.setNum(boardVO.getNum());
			System.out.println(fileName);
			noticeMapper.setFileInsert(boardFileVO);
		}

		return result;
	}
	@Override
	public Long setUpdate(BoardVO boardVO) throws Exception {
		// TODO Auto-generated method stub
		return noticeMapper.setUpdate(boardVO);
	}
	@Override
	public Long setDelete(BoardVO boardVO) throws Exception {
		// TODO Auto-generated method stub
		return noticeMapper.setDelete(boardVO);
	}
}
