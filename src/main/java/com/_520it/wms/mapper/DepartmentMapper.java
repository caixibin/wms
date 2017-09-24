 package com._520it.wms.mapper;

import com._520it.wms.domain.Department;
import com._520it.wms.query.QueryObject;

import java.util.List;

public interface DepartmentMapper {
	void save(Department d);
	void update(Department d);
	void delete(Long id);
	
	Department get(Long id);
	List<Department> listAll ();

	Integer queryForCount(QueryObject qo);
	List<Department> queryForList(QueryObject qo);

    //批量删除操作
    void batchDelete(List<Long> ids);
}
