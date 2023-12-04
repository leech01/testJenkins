package com.springboot.service;

public class BoardDTO {
//	SEQ	int	NO	PRI	1
//	TITLE	varchar(100)	NO		
//	CONTENT	varchar(10000)	NO		
//	VIEWS_CNT	varchar(10)	NO		
//	STS	varchar(1)	NO		Y
//	REG_DT	datetime	NO		
//	REG_ID	varchar(40)	NO	
	
	private String seq;
	private String title;
	private String content;
	private String views_cnt;
	private String sts;
	private String reg_dt;
	private String reg_id;
	private String upd_dt;
	private String upd_id;
	
	public String getSeq() {
		return seq;
	}
	public void setSeq(String seq) {
		this.seq = seq;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getViews_cnt() {
		return views_cnt;
	}
	public void setViews_cnt(String views_cnt) {
		this.views_cnt = views_cnt;
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
	public String getUpd_id() {
		return upd_id;
	}
	public void setUpd_id(String upd_id) {
		this.upd_id = upd_id;
	}
	
}
