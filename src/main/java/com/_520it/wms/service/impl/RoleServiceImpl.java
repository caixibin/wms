package com._520it.wms.service.impl;

import com._520it.wms.domain.Permission;
import com._520it.wms.domain.Role;
import com._520it.wms.domain.SystemMenu;
import com._520it.wms.mapper.RoleMapper;
import com._520it.wms.page.PageResult;
import com._520it.wms.query.QueryObject;
import com._520it.wms.service.IRoleService;
import lombok.Setter;

import java.util.List;


public class RoleServiceImpl implements IRoleService {
	@Setter
	private RoleMapper roleMapper;
	public void save(Role role) {
		roleMapper.save(role);
		//维护中间表,将保存在集合中的权限拿出来存放到中间表中
		for (Permission permission : role.getPermissions()) {
			roleMapper.insertRelation(role.getId(),permission.getId());
		}
		//维护中间表,将保存在集合中的菜单拿出来存放到中间表中
		for (SystemMenu systemMenu : role.getMenus()) {
			roleMapper.insertMenuRelation(role.getId(),systemMenu.getId());
		}
	}
	public void update(Role role) {
		//更新操作,先删除该角色在中间表所有的对应信息,再进行中间表更新
		roleMapper.deleteRelation(role.getId());
		roleMapper.deleteMenuRelation(role.getId());
		roleMapper.update(role);
		for (Permission permission : role.getPermissions()) {
			roleMapper.insertRelation(role.getId(),permission.getId());
		}
		for (SystemMenu systemMenu : role.getMenus()) {
			roleMapper.insertMenuRelation(role.getId(),systemMenu.getId());
		}
		/*for (SystemMenu systemMenu : role.getMenus()) {
			roleMapper.insertMenuRelation(role.getId(),systemMenu.getId());
		}*/
	}

	public void delete(Long id) {
		roleMapper.deleteRelation(id);//先删除中间表
		roleMapper.deleteMenuRelation(id);
		roleMapper.delete(id);

	}

	public Role get(Long id) {
		Role role = roleMapper.get(id);
		return role;
	}

	public PageResult query(QueryObject qo) {
		Integer totalCount = roleMapper.queryForCount(qo);
		if(totalCount == 0){
			return PageResult.EMPTY_PAGE;
		}
		List<Role> data = roleMapper.queryForList(qo);
		return new PageResult(totalCount, data, qo.getCurrentPage(), qo.getPageSize());
	}
	public List<Role> listAll() {
		return roleMapper.listAll();
	}

	@Override
	public void batchDelete(List<Long> ids) {
		roleMapper.batchDelete(ids);
	}

	@Override
	public List<String> queryByEmployeeId(Long id) {
		return roleMapper.queryByEmployeeId(id);
	}
}
