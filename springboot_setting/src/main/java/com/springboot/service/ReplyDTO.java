package com.springboot.service;

public class ReplyDTO {
//	SEQ	int	NO	PRI	1
//	BOARD_SEQ	int	NO		
//	PARENT	int	NO		0
//	CONTENT	varchar(140)	NO		
//	STS	varchar(1)	NO		0
//	REG_DT	datetime	NO		
//	REG_ID	varchar(40)	NO		
//	UPD_DT	datetime	YES		
	
	private String seq;
	private String board_seq;
	private String parent;
	private String content;
	private String sts;
	private String reg_dt;
	private String reg_id;
	private String upd_dt;
	private String origin_seq;	// 댓글정렬을 위한 컬럼 (댓글은 본인 seq, 대댓글은 댓글의 seq)
	private String fullpath;	// 댓글정렬을 위한 컬럼 (대댓글의 정렬에 사용)

	public String getSeq() {
		return seq;
	}

	public void setSeq(String seq) {
		this.seq = seq;
	}

	public String getBoard_seq() {
		return board_seq;
	}

	public void setBoard_seq(String board_seq) {
		this.board_seq = board_seq;
	}

	public String getParent() {
		return parent;
	}

	public void setParent(String parent) {
		this.parent = parent;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getSts() {
		return sts;
	}

	public void setSts(String sts) {
		this.sts = sts;
	}

	public String getReg_dt() {
		return reg_dt;
	}

	public void setReg_dt(String reg_dt) {
		this.reg_dt = reg_dt;
	}

	public String getReg_id() {
		return reg_id;
	}

	public void setReg_id(String reg_id) {
		this.reg_id = reg_id;
	}

	public String getUpd_dt() {
		return upd_dt;
	}

	public void setUpd_dt(String upd_dt) {
		this.upd_dt = upd_dt;
	}

	public String getFullpath() {
		return fullpath;
	}

	public void setFullpath(String fullpath) {
		this.fullpath = fullpath;
	}

	public String getOrigin_seq() {
		return origin_seq;
	}

	public void setOrigin_seq(String origin_seq) {
		this.origin_seq = origin_seq;
	}
	
	
}
