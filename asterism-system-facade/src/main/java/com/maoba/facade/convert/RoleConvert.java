package com.maoba.facade.convert;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.BeanUtils;

import com.maoba.facade.dto.RoleDto;
import com.maoba.facade.dto.requestdto.RoleRequest;
import com.maoba.system.domain.RoleEntity;

/**
 * @author kitty daddy
 *  角色转换器
 */
public class RoleConvert {
    
	/**
	 * request转换成角色实体类
	 * @param request
	 * @return
	 */
	public static RoleEntity convertRequest2Entity(RoleRequest request) {
		RoleEntity entity = null;
		if(request != null){
			entity = new RoleEntity();
			BeanUtils.copyProperties(request, entity);
		}
		return entity;
	}
     
	/**
	 * 实体类转换成dto
	 * @param entitys
	 * @return
	 */
	public static List<RoleDto> convertEntitys2Dtos(List<RoleEntity> entitys) {
		List<RoleDto> roleDtos = null;
		if(CollectionUtils.isNotEmpty(entitys)){
			roleDtos = new ArrayList<RoleDto>();
			for(RoleEntity entity : entitys){
				RoleDto roleDto = new RoleDto();
				BeanUtils.copyProperties(entity, roleDto);
				roleDtos.add(roleDto);
			}
		}
		return roleDtos;
	}
    
	/**
	 * 将部分request转换成实体类信息
	 * @param request
	 * @param oldEntity
	 * @return
	 */
	public static RoleEntity convertRequest2Entity(RoleRequest request,RoleEntity oldEntity) {
		if(oldEntity!= null){
			oldEntity.setUpdateTime(new Date());
			oldEntity.setRoleCode(request.getRoleCode());
			oldEntity.setRoleName(request.getRoleName());
			oldEntity.setDescription(request.getDescription());
		}
		return oldEntity;
	}
    
}
