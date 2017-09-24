package com._520it.wms.service.impl;

import com._520it.wms.domain.Department;
import com._520it.wms.mapper.DepartmentMapper;
import com._520it.wms.page.PageResult;
import com._520it.wms.query.QueryObject;
import com._520it.wms.service.IDepartmentService;
import lombok.Setter;

import java.util.List;


public class DepartmentServiceImpl implements IDepartmentService {
	@Setter
	private DepartmentMapper departmentMapper;
	
	public void save(Department d) {
		departmentMapper.save(d);
	}

	
	public void update(Department d) {
		departmentMapper.update(d);
	}

	
	public void delete(Long id) {
		departmentMapper.delete(id);

	}

	
	public Department get(Long id) {
		Department d = departmentMapper.get(id);
		return d;
	}

	
	public List<Department> listAll() {
		List<Department> list = departmentMapper.listAll();
		return list;
	}

	public PageResult query(QueryObject qo) {
		Integer count = departmentMapper.queryForCount(qo);
		if (count==0){
			return PageResult.EMPTY_PAGE;
		}
		List<Department> result = departmentMapper.queryForList(qo);
		return new PageResult(count,result,qo.getCurrentPage(),qo.getPageSize());
	}

	public void batchDelete(List<Long> ids) {
		departmentMapper.batchDelete(ids);
	}

}
