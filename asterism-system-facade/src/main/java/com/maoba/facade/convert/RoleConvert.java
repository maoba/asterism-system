package com.maoba.facade.convert;
import org.springframework.beans.BeanUtils;
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
    
}
