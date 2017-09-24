 package com._520it.wms.mapper;

import com._520it.wms.domain.Role;
import com._520it.wms.query.QueryObject;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface RoleMapper {
	void save(Role role);
	void update(Role role);
	void delete(Long id);
	
	Role get(Long id);
	List<Role> listAll();
	
	Integer queryForCount(QueryObject qo);
	List<Role> queryForList(QueryObject qo);
	
	//维护中间表关系
	void insertRelation(@Param("roleId")Long roleId,@Param("permissionId")Long permissionId);
	void deleteRelation(Long roleId);
	//批量删除
	void batchDelete(List<Long> ids);

	void insertMenuRelation(@Param("roleId") Long roleId, @Param("menuId") Long menuId);

	void deleteMenuRelation(Long roleId);

	List<String> queryByEmployeeId(Long id);
}
