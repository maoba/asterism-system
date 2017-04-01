package com.maoba.service;
import java.util.Set;

import com.github.pagehelper.PageInfo;
import com.maoba.facade.dto.UserDto;
import com.maoba.facade.dto.requestdto.UserRequest;
import com.maoba.facade.dto.responsedto.UserResponse;
/**
 * @author  maoba
 */
public interface UserService {
     /**
      * 根据用户的id进行查询
      * @param userId
      * @return
      */
	 UserResponse queryUserById(long userId);
     
	 /**
	  * 根据email地址以及终端类型进行查询
	  * @param email 邮箱地址
	  * @param terminalType 终端类型
	  * @return
	  */
	 UserResponse queryUserByEmail(String email, Integer terminalType);
     
	 /**
	  * 根据手机号码以及终端类型进行查询
	  * @param cellPhoneNum 手机号码
	  * @param terminalType 终端类型
	  * @return
	  */
	 UserResponse queryUserByCellPhone(String cellPhoneNum, Integer terminalType);
     
	 /**
	  * 分页查询用户
	  * @param name
	  * @param pageIndex
	  * @param pageSize
	  * @return
	  */
	 PageInfo<UserDto> queryUsersByPage(String name,Integer status,Long tenantId, Integer pageIndex,Integer pageSize);

	 /**
	  * 保存用户信息
	  * @param request
	  */
	 void saveUser(UserRequest request);
     
	 /**
	  * 更新用户
	  * @param response
	  */
	 void updateUser(UserResponse response);
     
	 /**
	  * 删除用户
	  * @param ids 
	  */
	 void delete(Set<Long> ids);
     
	 /**
	  * 更新部分用户的信息
	  * @param request
	  */
	 void updateApartUserInfo(UserRequest request);
     
}
