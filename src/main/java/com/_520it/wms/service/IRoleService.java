package com._520it.wms.service;

import com._520it.wms.domain.Role;
import com._520it.wms.page.PageResult;
import com._520it.wms.query.QueryObject;

import java.util.List;

public interface IRoleService {
	void save(Role role);
	void update(Role role);
	void delete(Long id);
	
	Role get(Long id);
	PageResult query(QueryObject qo);
	List<Role> listAll();

	void batchDelete(List<Long> ids);

	List<String> queryByEmployeeId(Long id);
}
