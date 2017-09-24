package com._520it.wms.service;

import com._520it.wms.domain.SystemMenu;
import com._520it.wms.page.PageResult;
import com._520it.wms.query.SystemMenuQueryObject;
import com._520it.wms.vo.SystemMenuVO;

import java.util.List;
import java.util.Map;

public interface ISystemMenuService {
	void save(SystemMenu systemMenu);
	void update(SystemMenu systemMenu);
	void delete(Long id);
	
	SystemMenu get(Long id);
	List<SystemMenu> listAll();

	PageResult query(SystemMenuQueryObject qo);

	List<SystemMenuVO> queryParentMenus(Long parentId);

	List<Map<String,Object>> queryMenusByParentSn(String parentSn);
}
