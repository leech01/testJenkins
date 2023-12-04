package com.springboot.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import com.springboot.dao.UserMapper;
import com.springboot.service.UserDTO;

@SpringBootApplication
@RestController
@RequestMapping(value="/user")
public class UserController {

	@Autowired
	private UserMapper mapper;
	
	String returnMsg = "";
	
	/**
	 * get:: loginCheck
	 * 
	 * {
			"user_id" : "test2@gtsoft.co.kr",
			"passwd" : "1234"
		}
	 * 
	 */
	@RequestMapping(value="/login", method=RequestMethod.GET)
	public Map<String, Object> login(@RequestBody final UserDTO user) throws Exception {
		System.out.println("call method :: login()");
		Map<String, Object> jsonObject = new HashMap<String, Object>();
		
		try {
			PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
			System.out.println("login user id :: " + user.getUser_id());
			System.out.println("login user pw :: " + user.getPasswd());
			List<UserDTO> userList = mapper.getUserOne(user);
			
			if(userList.size() > 0) {
				System.out.println("["+user.getUser_id()+"]user is exist");
				String dbPassWord = userList.get(0).getPasswd();
				String inputPassword = user.getPasswd();
				if(passwordEncoder.matches(inputPassword, dbPassWord )){
					System.out.println("계정정보 일치");
					returnMsg = "계정정보 일치";
					jsonObject.put("resultCd", "0000");
					jsonObject.put("resultMsg", "[SUCCESS]"+returnMsg);
					jsonObject.put("data", userList);
				}else{
					System.out.println("계정정보 불일치");
					returnMsg = "계정정보 불일치";
					jsonObject.put("resultCd", "9999");
					jsonObject.put("resultMsg", "[FAILURE]"+returnMsg);
				}						
			} else {
				System.out.println("["+user.getUser_id()+"]user is not exist");
				returnMsg = "["+user.getUser_id()+"]user is not exist";
				jsonObject.put("resultCd", "9999");
				jsonObject.put("resultMsg", "[FAILURE]"+returnMsg);
			}
		} catch (Exception e) {
			jsonObject.put("resultCd", "9999");
			jsonObject.put("resultMsg", "[FAILURE]login()");
		}
		
		return jsonObject;		
	}	
	
	/**
	 *	get:: select userList 
	 */
	@RequestMapping(value="/", method=RequestMethod.GET)
	public Map<String, Object> getUserList(){
		System.out.println("call method :: getUserList()");
		Map<String, Object> jsonObject = new HashMap<String, Object>();
		
		try {
			
			List<UserDTO> userList = mapper.getUserList();
			
			if(userList.size() > 0) {
				System.out.println("user count is " + userList.size());
				returnMsg = "user count is " + userList.size();
			} else {
				System.out.println("user is empty");
				returnMsg = "user is empty";
			}
			
			jsonObject.put("resultCd", "0000");
			jsonObject.put("resultMsg", "[SUCCESS]"+returnMsg);
			jsonObject.put("data", userList);
			
		} catch (Exception e) {
			jsonObject.put("resultCd", "9999");
			jsonObject.put("resultMsg", "[FAILURE]getUserList()");
		}
		
		return jsonObject;
	}
	
	
	/**
	 * get:: select userOne
	 */
	@RequestMapping(value="/{id}", method=RequestMethod.GET)
	public Map<String, Object> getUserOne(@PathVariable("id") final String id) throws Exception {
		System.out.println("call method :: getUserOne()");
		Map<String, Object> jsonObject = new HashMap<String, Object>();
		
		try {
			
			System.out.println("search user id :: " + id);
			UserDTO param = new UserDTO();
			param.setUser_id(id);
			List<UserDTO> userList = mapper.getUserOne(param);
			
			if(userList.size() > 0) {
				System.out.println("["+id+"]user is exist");
				returnMsg = "["+id+"]user is exist";
				jsonObject.put("resultCd", "0000");
				jsonObject.put("resultMsg", "[SUCCESS]"+returnMsg);
				jsonObject.put("data", userList);
			} else {
				System.out.println("["+id+"]user is not exist");
				returnMsg = "["+id+"]user is not exist";
				jsonObject.put("resultCd", "9999");
				jsonObject.put("resultMsg", "[FAILURE]"+returnMsg);
			}
		} catch (Exception e) {
			jsonObject.put("resultCd", "9999");
			jsonObject.put("resultMsg", "[FAILURE]getUserOne()");
		}
		
		return jsonObject;		
	}
	
	/**
	 * post:: insert user
	 * 
	 * {
			"user_id" : "test2@gtsoft.co.kr",
			"user_nm" : "testNm",
			"passwd" : "1234",
			"address" : "testAddress",
			"phone" : "01012345678",
			"mail" : "test2@gtsoft.co.kr"
		}
	 * 
	 * 개선사항 :: 헤더에 Content-Type = application/json 추가
	 * 
	 */
	@RequestMapping(value="/", method=RequestMethod.POST)
	public Map<String, Object> insertUser(@RequestBody final UserDTO user) throws Exception {
		System.out.println("call method :: insertUser()");
		Map<String, Object> jsonObject = new HashMap<String, Object>();
		
		if(user == null) {
			System.out.println("No send data");
			returnMsg = "No send data";
			jsonObject.put("resultCd", "9999");
			jsonObject.put("resultMsg", "[FAILURE]"+returnMsg);
		} else {
			try {
				List<UserDTO> userList = mapper.getUserOne(user);
				if(userList.size() > 0) {
					System.out.println("[insertUser] userId " + user.getUser_id() + " is already exists");
					returnMsg = "[insertUser] userId " + user.getUser_id() + " is already exists";
					jsonObject.put("resultCd", "9999");
					jsonObject.put("resultMsg", "[FAILURE]"+returnMsg);
				} else {
					//비밀번호 암호화
					BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
					String password = user.getPasswd();
					String encodePwd = passwordEncoder.encode(password);
					user.setPasswd(encodePwd);
					int addCnt = mapper.insertUser(user);
					System.out.println("insertUser cnt :: " + addCnt);
					jsonObject.put("resultCd", "0000");
					jsonObject.put("resultMsg", "[SUCCESS]insertUser()");
//					userList = mapper.getUserOne(user);
//					jsonObject.put("data", userList);
				}
			} catch (Exception e) {
				jsonObject.put("resultCd", "9999");
				jsonObject.put("resultMsg", "[FAILURE]insertUser()");
			}
		}
		
		return jsonObject;
	}
	
	/**
	 * put:: update user
	 * 
	 * {
			"user_id" : "test2@gtsoft.co.kr",
			"user_nm" : "testNm",
			"passwd" : "1234",
			"address" : "testAddress",
			"phone" : "01012345678",
			"mail" : "test2@gtsoft.co.kr"
		}
	 * 
	 */
	@RequestMapping(value="/{id}", method=RequestMethod.PUT)
	public Map<String, Object> updateUser(@PathVariable("id") final String id, @RequestBody final UserDTO user) throws Exception {
		System.out.println("call method :: updateUser()");
		Map<String, Object> jsonObject = new HashMap<String, Object>();

		if(user == null) {
			System.out.println("no send Data");
			returnMsg = "no send Data";
			jsonObject.put("resultCd", "9999");
			jsonObject.put("resultMsg", "[FAILURE]"+returnMsg);
		} else {
			try {
				List<UserDTO> userList = mapper.getUserOne(user);
				if(userList.size() == 0) {
					System.out.println("[updateUser] userId " + user.getUser_id() + " is not exists");
					returnMsg = "[updateUser] userId " + user.getUser_id() + " is not exists";
					jsonObject.put("resultCd", "9999");
					jsonObject.put("resultMsg", "[FAILURE]"+returnMsg);
				} else {
					user.setUpd_id("admin");
					System.out.println("updateUserData s :: ");
					System.out.println("userid : "+user.getUser_id());
					System.out.println("usernm : "+user.getUser_nm());
					System.out.println("addr : "+user.getAddress());
					System.out.println("mail : "+user.getMail());
					System.out.println("passwd : "+user.getPasswd());
					System.out.println("phone : "+user.getPhone());
					System.out.println("updid : "+user.getUpd_id());
					System.out.println("updateUserData e :: ");
					int updateCnt = mapper.updateUser(user);
					System.out.println("updateUser cnt :: " + updateCnt);
					
					jsonObject.put("resultCd", "0000");
					jsonObject.put("resultMsg", "[SUCCESS]updateUser()");
				}
			} catch (Exception e) {
				jsonObject.put("resultCd", "9999");
				jsonObject.put("resultMsg", "[FAILURE]updateUser()");
			}
		}

		return jsonObject;
	}
	
	/**
	 * delete:: delete user
	 */
	@RequestMapping(value="/{id}", method=RequestMethod.DELETE)
	public Map<String, Object> deleteUser(@PathVariable("id") final String id) throws Exception {
		System.out.println("call method :: deleteUser()");
		Map<String, Object> jsonObject = new HashMap<String, Object>();
		
		try {
			System.out.println("delete user id :: " + id);
			UserDTO param = new UserDTO();
			param.setUser_id(id);
			List<UserDTO> userList = mapper.getUserOne(param);			
			if(userList.size() > 0) {
				int delCnt = mapper.deleteUser(param);
				System.out.println("deleteUser cnt :: " + delCnt);
				jsonObject.put("resultCd", "0000");
				jsonObject.put("resultMsg", "[SUCCESS]deleteUser()");
			} else {
				System.out.println("[deleteUser] userId " + id + " is not exists");
				returnMsg = "[deleteUser] userId " + id + " is not exists";
				jsonObject.put("resultCd", "9999");
				jsonObject.put("resultMsg", "[FAILURE]"+returnMsg);
			}
		} catch (Exception e) {
			jsonObject.put("resultCd", "9999");
			jsonObject.put("resultMsg", "[FAILURE]deleteUser()");
		}
		
		return jsonObject;
	}
	
}
