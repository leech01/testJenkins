package com.springboot.dao;

import java.util.List;

import com.springboot.service.ReplyDTO;

public interface ReplyMapper {

	List<ReplyDTO> getReplyList(ReplyDTO param) throws Exception;

	int insertReply(ReplyDTO reply) throws Exception;
	
	int updateOriginSeq(ReplyDTO reply) throws Exception;
	
//	public List<BoardDTO> getBoardOne(BoardDTO param) throws Exception;
//
//	public int insertBoard(BoardDTO board) throws Exception;
//
//	public int updateBoard(BoardDTO board) throws Exception;
//
//	public int addViewsCount(BoardDTO board) throws Exception;
//	
//	public int deleteBoard(BoardDTO board) throws Exception;

	
}
