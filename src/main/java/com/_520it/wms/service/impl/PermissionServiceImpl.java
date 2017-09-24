package com._520it.wms.service.impl;

import com._520it.wms.domain.Permission;
import com._520it.wms.mapper.PermissionMapper;
import com._520it.wms.page.PageResult;
import com._520it.wms.query.QueryObject;
import com._520it.wms.service.IPermissionService;
import com._520it.wms.util.RequirePermission;
import com._520it.wms.web.action.BaseAction;
import lombok.Setter;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;

public class PermissionServiceImpl implements IPermissionService , ApplicationContextAware{
	@Setter
	private PermissionMapper permissionMapper;
	public void save(Permission permission) {
		permissionMapper.save(permission);
	}

	public void delete(Long id) {
		permissionMapper.delete(id);
	}
	
	public List<Permission> listAll() {
		return permissionMapper.listAll();
	}
	
	public PageResult query(QueryObject qo) {
		Integer totalCount = permissionMapper.queryForCount(qo);
		if (totalCount == 0) {
			return PageResult.EMPTY_PAGE;
		}
		List<Permission> data = permissionMapper.queryForList(qo);
		return new PageResult(totalCount, data, qo.getCurrentPage(),qo.getPageSize());
	}

	public void reload() {
		//1.查出数据库中所有的权限,(单列的字符串类型)
		List<String> expressions = permissionMapper.getExpressions();
		//2.查出所有action类
		Map<String, BaseAction> actionMap = ctx.getBeansOfType(BaseAction.class);
		for (BaseAction actionObject  : actionMap.values()) {
			//3.查出所有Action中所有的方法
			Method[] actionMethods = actionObject.getClass().getDeclaredMethods();
			for (Method method : actionMethods) {
				//4.判断所有action中的方法是否贴有权限注解,
				RequirePermission rp = method.getAnnotation(RequirePermission.class);
				String expression = actionObject.getClass().getName()+":"+method.getName();
				//4.判断Action方法是否与数据库查询出来的重复
				if(!expressions.contains(expression)){
					//5有则创建对象并设置参数并保存到数据库中权限表中
					if(rp != null){
						Permission permission = new Permission();
						permission.setName(rp.value());
						permission.setExpression(expression);
						permissionMapper.save(permission);
					}
				}
			}
		}
	}
	private ApplicationContext ctx = null;
	@Override
	public void setApplicationContext(ApplicationContext ctx)throws BeansException {
		 this.ctx = ctx;

	}
	@Override
	public void batchDelete(List<Long> ids) {
		permissionMapper.batchDelete(ids);
	}

	@Override
	public List<String> queryByemployeeId(Long id) {
		return permissionMapper.queryByEmployeeId(id);
	}
}