package com.springboot.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.springboot.dao.BoardMapper;
import com.springboot.service.BoardDTO;
import com.springboot.service.UserDTO;

@SpringBootApplication
@RestController
@RequestMapping(value="/board")
public class BoardController {
	
	@Autowired
	private BoardMapper mapper;
	
	String returnMsg = "";
	
	/**
	 *	get:: select boardList 
	 */
	@RequestMapping(value="/", method=RequestMethod.GET)
	public Map<String, Object> getBoardList(){
		System.out.println("call method :: getBoardList()");
		Map<String, Object> jsonObject = new HashMap<String, Object>();
		
		try {
			
			List<BoardDTO> boardList = mapper.getBoardList();
			
			if(boardList.size() > 0) {
				System.out.println("board count is " + boardList.size());
				returnMsg = "board count is " + boardList.size();
			} else {
				System.out.println("board is empty");
				returnMsg = "board is empty";
			}
			
			jsonObject.put("resultCd", "0000");
			jsonObject.put("resultMsg", "[SUCCESS]"+returnMsg);
			jsonObject.put("data", boardList);
			
		} catch (Exception e) {
			jsonObject.put("resultCd", "9999");
			jsonObject.put("resultMsg", "[FAILURE]getBoardList()");
		}
		
		return jsonObject;
	}	
	
	/**
	 * get:: select boardOne
	 */
	@RequestMapping(value="/{seq}", method=RequestMethod.GET)
	public Map<String, Object> getBoardOne(@PathVariable("seq") final String seq) throws Exception {
		System.out.println("call method :: getBoardOne()");
		Map<String, Object> jsonObject = new HashMap<String, Object>();
		
		try {
			
			System.out.println("search board seq :: " + seq);
			BoardDTO param = new BoardDTO();
			param.setSeq(seq);
			List<BoardDTO> boardList = mapper.getBoardOne(param);
			
			if(boardList.size() > 0) {
				System.out.println("["+seq+"]board is exist");
				returnMsg = "["+seq+"]board is exist";
				jsonObject.put("resultCd", "0000");
				jsonObject.put("resultMsg", "[SUCCESS]"+returnMsg);
				jsonObject.put("data", boardList);
				
				// 해당 게시글 조회수 1 증가
				int viewsCnt = Integer.parseInt(boardList.get(0).getViews_cnt());
				param.setViews_cnt(Integer.toString(viewsCnt+1));
				mapper.addViewsCount(param);
			} else {
				System.out.println("["+seq+"]board is not exist");
				returnMsg = "["+seq+"]board is not exist";
				jsonObject.put("resultCd", "9999");
				jsonObject.put("resultMsg", "[FAILURE]"+returnMsg);
			}
		} catch (Exception e) {
			jsonObject.put("resultCd", "9999");
			jsonObject.put("resultMsg", "[FAILURE]getBoardOne()");
		}
		
		return jsonObject;		
	}	
	
	/**
	 * post:: insert board
	 * 
	 * {
			"title" : "title",		// 화면에서 입력하는 값
			"content" : "content",	// 화면에서 입력하는 값
			"views_cnt" : 1, 		// 화면에서 받는값 아님
			"sts" : "Y", 			// 화면에서 받는값 아님
			"reg_dt" : now(),		// 화면에서 받는값 아님
			"reg_id" : loginId		// 화면에서 받는값 아님
		}
	 * 
	 * 개선사항 :: 헤더에 Content-Type = application/json 추가
	 * 
	 */
	@RequestMapping(value="/", method=RequestMethod.POST)
	public Map<String, Object> insertBoard(@RequestBody final BoardDTO board) throws Exception {
		System.out.println("call method :: insertBoard()");
		Map<String, Object> jsonObject = new HashMap<String, Object>();
		
		if(board == null) {
			System.out.println("No send data");
			returnMsg = "No send data";
			jsonObject.put("resultCd", "9999");
			jsonObject.put("resultMsg", "[FAILURE]"+returnMsg);
		} else {
			try {
				// 세션에 있는 로그인아이디로 변경예정
				board.setReg_id("hjkim@gtsoft.co.kr");
				int addCnt = mapper.insertBoard(board);
				System.out.println("insertBoard cnt :: " + addCnt);
				jsonObject.put("resultCd", "0000");
				jsonObject.put("resultMsg", "[SUCCESS]insertBoard()");
			} catch (Exception e) {
				jsonObject.put("resultCd", "9999");
				jsonObject.put("resultMsg", "[FAILURE]insertBoard()");
			}
		}
		
		return jsonObject;
	}	
	
	/**
	 * put:: update board
	 * 
	 * {
			"title" : "TITLE3",		// 화면에서 입력하는 값
			"content" : "CONTENT3",	// 화면에서 입력하는 값
			"upd_id" : "hjkim@gtsoft.co.kr"		// 본인이 게시한 글인지 확인 위해 입력받음
			
			"views_cnt" : 1, 		// 화면에서 받는값 아님
			"sts" : "Y", 			// 화면에서 받는값 아님
			"upd_dt" : now(),		// 화면에서 받는값 아님
			"upd_id" : loginId		// 화면에서 받는값 아님
		}
	 * 
	 */
	@RequestMapping(value="/{seq}", method=RequestMethod.PUT)
	public Map<String, Object> updateBoard(@PathVariable("seq") final String seq, @RequestBody final BoardDTO board) throws Exception {
		System.out.println("call method :: updateBoard()");
		Map<String, Object> jsonObject = new HashMap<String, Object>();

		if(board == null) {
			System.out.println("no send Data");
			returnMsg = "no send Data";
			jsonObject.put("resultCd", "9999");
			jsonObject.put("resultMsg", "[FAILURE]"+returnMsg);
		} else {
			try {
				// 수정할 게시글 seq
				board.setSeq(seq);
				List<BoardDTO> boardList = mapper.getBoardOne(board);
				if(boardList.size() == 0) {
					System.out.println("[updateBoard] boardSeq " + seq + " is not exists");
					returnMsg = "[updateBoard] boardSeq " + seq + " is not exists";
					jsonObject.put("resultCd", "9999");
					jsonObject.put("resultMsg", "[FAILURE]"+returnMsg);
				} else {
					// 로그인한 세션에서 아이디 가져올 것
//					board.setUpt_id("hjkim@gtsoft.co.kr");
					
					if(!board.getUpd_id().equals(boardList.get(0).getReg_id())) {
						System.out.println("게시글은 등록자만 수정/삭제 할 수 있습니다.");
						returnMsg = "게시글은 등록자만 수정/삭제 할 수 있습니다.";
						jsonObject.put("resultCd", "9999");
						jsonObject.put("resultMsg", "[FAILURE]"+returnMsg);
					} else {
						int updateCnt = mapper.updateBoard(board);
						System.out.println("updateBoard cnt :: " + updateCnt);
						jsonObject.put("resultCd", "0000");
						jsonObject.put("resultMsg", "[SUCCESS]updateBoard()");
					}
				}
			} catch (Exception e) {
				jsonObject.put("resultCd", "9999");
				jsonObject.put("resultMsg", "[FAILURE]updateBoard()");
			}
		}

		return jsonObject;
	}	
	
	/**
	 * delete:: delete board
	 */
	@RequestMapping(value="/{seq}", method=RequestMethod.DELETE)
	public Map<String, Object> deleteBoard(@PathVariable("seq") final String seq) throws Exception {
		System.out.println("call method :: deleteBoard()");
		Map<String, Object> jsonObject = new HashMap<String, Object>();
		
		try {
			System.out.println("delete board seq :: " + seq);
			BoardDTO param = new BoardDTO();
			param.setSeq(seq);
			List<BoardDTO> boardList = mapper.getBoardOne(param);			
			if(boardList.size() > 0) {
				int delCnt = mapper.deleteBoard(param);
				System.out.println("deleteBoard cnt :: " + delCnt);
				jsonObject.put("resultCd", "0000");
				jsonObject.put("resultMsg", "[SUCCESS]deleteBoard()");
			} else {
				System.out.println("[deleteBoard] boardSeq " + seq + " is not exists");
				returnMsg = "[deleteBoard] boardSeq " + seq + " is not exists";
				jsonObject.put("resultCd", "9999");
				jsonObject.put("resultMsg", "[FAILURE]"+returnMsg);
			}
		} catch (Exception e) {
			jsonObject.put("resultCd", "9999");
			jsonObject.put("resultMsg", "[FAILURE]deleteBoard()");
		}
		
		return jsonObject;
	}	
	
}
