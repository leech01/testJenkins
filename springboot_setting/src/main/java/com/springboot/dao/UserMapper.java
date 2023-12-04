package com.springboot.dao;

import java.util.List;
import com.springboot.service.UserDTO;

public interface UserMapper {

	public int userCheck() throws Exception ;

	public List<UserDTO> getUserList() throws Exception;

	public List<UserDTO> getUserOne(UserDTO param) throws Exception;

	public int insertUser(UserDTO user) throws Exception;

	public int updateUser(UserDTO user) throws Exception;

	public int deleteUser(UserDTO param) throws Exception;

}
