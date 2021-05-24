package com.iu.s1.board.qna;

import org.apache.ibatis.annotations.Mapper;

import com.iu.s1.board.BoardMapper;
import com.iu.s1.board.BoardVO;
@Mapper
public interface QnaMapper extends BoardMapper{
	public Long setRefUpdate(BoardVO boardVO) throws Exception;
	public Long setReplyUpdate(BoardVO boardVO)throws Exception;
	public Long setReplyInsert(BoardVO boardVO) throws Exception;
}
