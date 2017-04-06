package com.maoba.facade.convert;

import java.util.ArrayList;
import java.util.List;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.BeanUtils;
import com.maoba.facade.dto.UserRoleDto;
import com.maoba.system.domain.UserRoleEntity;
/**
 * @author kitty daddy
 * 用户角色转换器
 */
public class UserRoleConvert {
    
	/**
	 * 用户角色实体类转换成dto
	 * @param entities
	 * @return
	 */
	public static List<UserRoleDto> convertEntity2Dto(List<UserRoleEntity> entities) {
		List<UserRoleDto> userRoleDtos = null;
		if(CollectionUtils.isNotEmpty(entities)){
			userRoleDtos = new ArrayList<UserRoleDto>();
			for(UserRoleEntity entity : entities){
				UserRoleDto userRoleDto = new UserRoleDto();
				BeanUtils.copyProperties(entity, userRoleDto);
				userRoleDtos.add(userRoleDto);
			}
		}
		return userRoleDtos;
	}

}
