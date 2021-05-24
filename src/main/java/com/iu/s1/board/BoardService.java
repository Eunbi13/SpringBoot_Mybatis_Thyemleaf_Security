package com.iu.s1.board;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.example.demo.util.Pager;

public interface BoardService {
		//list
		public List<BoardVO> getList(Pager pager)throws Exception;
		//select
		public BoardVO getSelect(BoardVO boardVO)throws Exception;
		//insert
		public Long setInsert(BoardVO boardVO, MultipartFile [] files)throws Exception;
		//update
		public Long setUpdate(BoardVO boardVO)throws Exception;
		//delete
		public Long setDelete(BoardVO boardVO)throws Exception;
}
