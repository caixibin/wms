 package com._520it.wms.mapper;

import com._520it.wms.domain.Employee;
import com._520it.wms.query.QueryObject;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface EmployeeMapper {
	void save(Employee employee);
	void update(Employee employee);
	void delete(Long id);
	void batchDelete(List<Long> ids);
	
	Employee get(Long id);
	//List<Employee> listAll ();
	
	Integer queryForCount(QueryObject qo);
	List<Employee> queryForList(QueryObject qo);
	
	//维护中间表关系
	void insertRelation(@Param("employeeId")Long employeeId,@Param("roleId")Long roleId);
	void deleteRelation(Long employeeId);
	void batchDeleteRelation(List<Long> ids);

	Employee checkLogin(@Param("username")String username, @Param("password")String password);


	Employee queryForName(String username);
}
