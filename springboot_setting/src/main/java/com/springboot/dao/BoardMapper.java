package com.springboot.dao;

import java.util.List;
import com.springboot.service.BoardDTO;

public interface BoardMapper {
	
	public List<BoardDTO> getBoardList() throws Exception;

	public List<BoardDTO> getBoardOne(BoardDTO param) throws Exception;

	public int insertBoard(BoardDTO board) throws Exception;

	public int updateBoard(BoardDTO board) throws Exception;

	public int addViewsCount(BoardDTO board) throws Exception;
	
	public int deleteBoard(BoardDTO board) throws Exception;

}
