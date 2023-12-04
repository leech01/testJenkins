package com.springboot.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.springboot.dao.ReplyMapper;
import com.springboot.service.BoardDTO;
import com.springboot.service.ReplyDTO;

@RestController
@RequestMapping(value="/reply")
public class ReplyController {

	@Autowired
	private ReplyMapper mapper;
	
	/**
	 * get:: select replyList
	 */
	@RequestMapping(value="/{board_seq}", method=RequestMethod.GET)
	public Map<String, Object> getReplyList(@PathVariable("board_seq") final String bSeq) throws Exception {
		System.out.println("call method :: getReplyList()");
		Map<String, Object> jsonObject = new HashMap<String, Object>();
		
		try {
			
			System.out.println("search replyList board seq :: " + bSeq);
			ReplyDTO param = new ReplyDTO();
			param.setBoard_seq(bSeq);
			List<ReplyDTO> replyList = mapper.getReplyList(param);
			
			if(replyList.size() > 0) {
				System.out.println("["+bSeq+"]board replyList is exist");
				jsonObject.put("resultCd", "0000");
				jsonObject.put("resultMsg", "[SUCCESS]"+"["+bSeq+"]board replyList is exist");
				jsonObject.put("data", replyList);
			} else {
				System.out.println("["+bSeq+"]board replyList is not exist");
				jsonObject.put("resultCd", "9999");
				jsonObject.put("resultMsg", "[FAILURE]"+"["+bSeq+"]board replyList is not exist");
			}
		} catch (Exception e) {
			jsonObject.put("resultCd", "9999");
			jsonObject.put("resultMsg", "[FAILURE]getReplyList()");
		}
		
		return jsonObject;			
	}
	
	/**
	 * post:: insert reply
	 * 
	 * {
			"board_seq" : "1",		// 화면에서 입력하는 값 _ 댓글의 게시글번호
			"parent" : "0",			// 화면에서 입력하는 값 _ 대댓글의 경우, 상위댓글 seq, 본댓글은 0
			"content" : "REPLY2", 	// 화면에서 입력하는 값
			
			
			"origin_seq" : "parent_value",	// 대댓글은 상위댓글 seq 받아서 저장, 댓글의 경우 insert 후에 origin_seq update해야할 듯
			"reg_dt" : now(),		// 화면에서 받는값 아님
			"reg_id" : loginId		// 화면에서 받는값 아님
		}
	 * 
	 */
	@RequestMapping(value="/{board_seq}", method=RequestMethod.POST)
	public Map<String, Object> insertReply(@PathVariable("board_seq") final String bSeq, @RequestBody final ReplyDTO reply) throws Exception {
		System.out.println("call method :: insertReply()");
		Map<String, Object> jsonObject = new HashMap<String, Object>();
		
		if(reply == null) {
			System.out.println("No send data");
			jsonObject.put("resultCd", "9999");
			jsonObject.put("resultMsg", "[FAILURE]No send data");
		} else {
			try {
				// 세션에 있는 로그인아이디로 변경예정
				reply.setBoard_seq(bSeq);
				reply.setReg_id("hjkim@gtsoft.co.kr");
				int addCnt = mapper.insertReply(reply);
				System.out.println("insertReply cnt :: " + addCnt);
				System.out.println("insertReply pk :: " + reply.getSeq());
				
				//상위댓글이 없는 본 댓글의 경우, ORIGIN_SEQ 값을 본인의 SEQ값으로 업데이트
				if("0".equals(reply.getParent())) {
					reply.setOrigin_seq(reply.getSeq());
					mapper.updateOriginSeq(reply);
				}
				
				jsonObject.put("resultCd", "0000");
				jsonObject.put("resultMsg", "[SUCCESS]insertReply()");
			} catch (Exception e) {
				jsonObject.put("resultCd", "9999");
				jsonObject.put("resultMsg", "[FAILURE]insertReply()");
			}
		}
		
		return jsonObject;		
	}
	
	
	
}
