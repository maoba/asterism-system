package com.maoba.service.impl;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.maoba.facade.convert.RoleConvert;
import com.maoba.facade.convert.UserConvert;
import com.maoba.facade.dto.RoleDto;
import com.maoba.facade.dto.UserDto;
import com.maoba.facade.dto.requestdto.RoleRequest;
import com.maoba.service.RoleService;
import com.maoba.system.dao.RoleEntityMapper;
import com.maoba.system.domain.RoleEntity;
import com.maoba.system.domain.UserEntity;

@Service
public class RoleServiceImpl implements RoleService{
      
	private RoleEntityMapper roleEntityMapper;

	@Override
	public void saveRole(RoleRequest request) {
        RoleEntity entity = RoleConvert.convertRequest2Entity(request);		
        entity.setCreateTime(new Date());
        roleEntityMapper.insert(entity);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public PageInfo<RoleDto> queryRolesByPage(String name, Long tenantId,
			Integer pageIndex, Integer pageSize) {
		PageHelper.startPage(pageIndex, pageSize, true, null, true);
		List<RoleEntity> entitys = roleEntityMapper.queryRolesByName(name,tenantId);
		PageInfo pageInfo = new PageInfo(entitys);
		List<RoleDto> roleDtos = RoleConvert.convertEntitys2Dtos(entitys);
		pageInfo.setList(roleDtos);
		return pageInfo;
	}
	
	
}
