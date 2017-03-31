package com.maoba.service.impl;
import java.util.Date;

import org.springframework.stereotype.Service;

import com.maoba.facade.convert.RoleConvert;
import com.maoba.facade.dto.requestdto.RoleRequest;
import com.maoba.service.RoleService;
import com.maoba.system.dao.RoleEntityMapper;
import com.maoba.system.domain.RoleEntity;

@Service
public class RoleServiceImpl implements RoleService{
      
	private RoleEntityMapper roleEntityMapper;

	@Override
	public void saveRole(RoleRequest request) {
        RoleEntity entity = RoleConvert.convertRequest2Entity(request);		
        entity.setCreateTime(new Date());
        roleEntityMapper.insert(entity);
	}
	
	
}
