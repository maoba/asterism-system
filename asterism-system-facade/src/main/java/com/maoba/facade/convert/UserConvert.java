package com.maoba.facade.convert;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.BeanUtils;

import com.maoba.facade.dto.UserDto;
import com.maoba.facade.dto.requestdto.UserRequest;
import com.maoba.facade.dto.responsedto.UserResponse;
import com.maoba.system.domain.UserEntity;
/**
 * @author kitty daddy
 *  用户转换器
 */
public class UserConvert {
	
	/**
	 * 用户实体类换成response
	 * @param userEntity
	 * @return
	 */
	public static UserResponse convertEntity2Response(UserEntity userEntity) {
		UserResponse response = null;
		if(userEntity!=null){
			response = new UserResponse();
			BeanUtils.copyProperties(userEntity, response);
		}
		return response;
	}
    
	/**
	 * 将用户实体类转换成dto
	 * @param userEntities
	 * @return
	 */
	public static List<UserDto> convertEntity2Dto(List<UserEntity> userEntities) {
		List<UserDto> userDtos = null;
		if(CollectionUtils.isNotEmpty(userEntities)){
			userDtos = new ArrayList<UserDto>();
			for(UserEntity entity : userEntities){
				UserDto userDto  = new UserDto();
				BeanUtils.copyProperties(entity, userDto);
				userDtos.add(userDto);
			}
		}
		return userDtos;
	}
    
	/**
	 * 容器request转换成用户实体类
	 * @param request
	 * @return
	 */
	public static UserEntity convertRequest2Entity(UserRequest request) {
		UserEntity entity = null;
		if(request!=null){
			entity = new UserEntity();
			BeanUtils.copyProperties(request, entity);
		}
		return entity;
	}
    
	/**
	 * 更新用户实体类
	 * @param response
	 * @return
	 */
	public static UserEntity converResponse2Entity(UserResponse response) {
		UserEntity entity = null;
		if(response!=null){
			entity = new UserEntity();
			BeanUtils.copyProperties(response, entity);
			entity.setUpdateTime(new Date());
		}
		return entity;
	}
    
	/**
	 * 部分用户信息进行更新
	 * @param request
	 * @return
	 */
	public static UserEntity convertApartInfo2Entity(UserRequest request,UserEntity oldEntity) {
		BeanUtils.copyProperties(request, oldEntity);
		return oldEntity;
	}
}
