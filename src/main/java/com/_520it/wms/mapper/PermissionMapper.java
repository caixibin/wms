 package com._520it.wms.mapper;

import com._520it.wms.domain.Permission;
import com._520it.wms.query.QueryObject;

import java.util.List;

public interface PermissionMapper {
	void save(Permission permission);
	void delete(Long id);
	
	List<Permission> listAll ();
	
	Integer queryForCount(QueryObject qo);
	List<Permission> queryForList(QueryObject qo);
	
	List<String> getExpressions();
	
	List<String> selectByEmployeeId(Long employeeId);

	void batchDelete(List<Long> ids);

	List<String> queryByEmployeeId(Long id);
}
