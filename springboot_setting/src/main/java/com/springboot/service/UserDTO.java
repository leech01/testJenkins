package com.springboot.service;

public class UserDTO {
//	USER_ID	varchar(40)	NO	PRI		
//	USER_NM	varchar(50)	NO			
//	PASSWD	varchar(255)	NO			
//	STS	char(1)	NO		0	
//	ADDRESS	varchar(150)	YES			
//	PHONE	varchar(11)	YES			
//	MAIL	varchar(45)	YES			
//	REG_DT	char(8)	NO			
//	REG_ID	varchar(40)	NO			
//	UDP_DT	char(8)	YES			
//	UDP_ID	varchar(40)	YES
	
	//DB에서 컬럼명에 '_' 가 들어갈 경우, DTO에서 해당 컬럼을 어떻게 정의해야할지..
	private String user_id;
	private String user_nm;
	private String passwd;
	private String sts;
	private String address;
	private String phone;
	private String mail;
	private String reg_dt;
	private String reg_id;
	private String upd_dt;
	private String upd_id;

	
	public String getPasswd() {
		return passwd;
	}

	public void setPasswd(String passwd) {
		this.passwd = passwd;
	}

	public String getSts() {
		return sts;
	}

	public void setSts(String sts) {
		this.sts = sts;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getMail() {
		return mail;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}

	public String getUser_id() {
		return user_id;
	}

	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}

	public String getUser_nm() {
		return user_nm;
	}

	public void setUser_nm(String user_nm) {
		this.user_nm = user_nm;
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
