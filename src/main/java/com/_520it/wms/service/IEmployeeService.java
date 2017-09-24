package com._520it.wms.service;

import com._520it.wms.domain.Employee;
import com._520it.wms.page.PageResult;
import com._520it.wms.query.QueryObject;

import java.util.List;

public interface IEmployeeService {
	void save(Employee d);
	void update(Employee d);
	void delete(Long id);
	void batchDelete(List<Long> ids);
	Employee get(Long id);
	//List<Employee> listAll ();
	
	PageResult query(QueryObject qo);
	void login(String username, String password);

	Employee queryByName(String username);
}
