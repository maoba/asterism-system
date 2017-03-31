package com.maoba.facade.convert;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.BeanUtils;

import com.maoba.facade.dto.requestdto.PermissionRequest;
import com.maoba.facade.dto.responsedto.PermissionTreeResponse;
import com.maoba.system.domain.PermissionEntity;

/**
 * @author kitty daddy
 * 权限转换器
 */
public class PermissionConvert {
    
	/**
	 * request转换成实体类
	 * @param request
	 * @return
	 */
	public static PermissionEntity convertRequest2Entity(PermissionRequest request) {
		PermissionEntity entity = null;
		if(request!=null){
			entity = new PermissionEntity();
			BeanUtils.copyProperties(request, entity);
		}
		return entity;
	}

	/**
	 * 将entity转换成response
	 * @param topEntity
	 * @param childEntitys
	 * @return
	 */
	public static PermissionTreeResponse convertEntity2TreeResponse(PermissionEntity topEntity, List<PermissionEntity> childEntitys) {
		PermissionTreeResponse response = new PermissionTreeResponse();
		if(topEntity!=null){
			response.setPermissionICO(topEntity.getPermissionICO());
			response.setPermissionName(topEntity.getModuleName());
			response.setPermissionUrl(topEntity.getPermissionUrl());
		}
		
		//组装子节点
		List<PermissionTreeResponse> childs = null;
		if(CollectionUtils.isNotEmpty(childEntitys)){
			childs = new ArrayList<PermissionTreeResponse>();
			for(PermissionEntity entity : childEntitys){
				PermissionTreeResponse child = new PermissionTreeResponse();
				child.setPermissionICO(entity.getPermissionICO());
				child.setPermissionName(entity.getModuleName());
				child.setPermissionUrl(entity.getPermissionUrl());
				childs.add(child);
			}
		}
		response.setChild(childs);
		return response;
	}
    
}
