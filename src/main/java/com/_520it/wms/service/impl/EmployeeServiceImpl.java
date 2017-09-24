package com._520it.wms.service.impl;

import com._520it.wms.domain.Employee;
import com._520it.wms.domain.Role;
import com._520it.wms.mapper.EmployeeMapper;
import com._520it.wms.mapper.PermissionMapper;
import com._520it.wms.page.PageResult;
import com._520it.wms.query.QueryObject;
import com._520it.wms.service.IEmployeeService;
import com._520it.wms.util.MD5;
import com._520it.wms.util.UserContext;
import lombok.Setter;

import java.util.List;


public class EmployeeServiceImpl implements IEmployeeService {
	@Setter
	private EmployeeMapper employeeMapper;
	@Setter
	private PermissionMapper permissionMapper;
	
	public void save(Employee employee) {
		employee.setPassword(MD5.encode(employee.getPassword()));
		employeeMapper.save(employee);
		//维护中间表
		for (Role role  : employee.getRoles()) {
			employeeMapper.insertRelation(employee.getId(), role.getId());
		}
	}

	
	public void update(Employee employee) {
		employeeMapper.update(employee);
		employeeMapper.deleteRelation(employee.getId());
		for (Role role  : employee.getRoles()) {
			employeeMapper.insertRelation(employee.getId(), role.getId());
		}
	}

	
	public void delete(Long id) {
		employeeMapper.deleteRelation(id);
		employeeMapper.delete(id);
		
	}

	public void batchDelete(List<Long> ids) {
		employeeMapper.batchDeleteRelation(ids);
		employeeMapper.batchDelete(ids);
	}


	public Employee get(Long id) {
		Employee employee = employeeMapper.get(id);
		return employee;
	}

	
	/*public List<Employee> listAll() {
		List<Employee> list = employeeMapper.listAll();
		return list;
	}*/
	@Override
	public PageResult query(QueryObject qo) {
		Integer totalCount = employeeMapper.queryForCount(qo);
		if(totalCount == 0){
			return PageResult.EMPTY_PAGE;
		}
		List<Employee> data = employeeMapper.queryForList(qo);
		return new PageResult(totalCount, data, qo.getCurrentPage(), qo.getPageSize());
	}
	@Override
	public void login(String username, String password) {
		//1.检查用户是否存在数据库
		Employee employee = employeeMapper.checkLogin(username,MD5.encode(password));
		//2.有用户则将其信息存放在seession ,没有则抛出异常
		if(employee == null){
			throw new RuntimeException();
		}
		UserContext.setCurrentUser(employee);
		//3,有则将用户权限查询出来并放进session
		List<String> expression = permissionMapper.selectByEmployeeId(employee.getId());
		UserContext.setCurrentPermission(expression);
	}

	@Override
	public Employee queryByName(String username) {
		return employeeMapper.queryForName(username);
	}
}
