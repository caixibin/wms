package com._520it.wms.service.impl;

import com._520it.wms.domain.Employee;
import com._520it.wms.domain.SystemMenu;
import com._520it.wms.mapper.SystemMenuMapper;
import com._520it.wms.page.PageResult;
import com._520it.wms.query.SystemMenuQueryObject;
import com._520it.wms.service.ISystemMenuService;
import com._520it.wms.vo.SystemMenuVO;
import lombok.Setter;
import org.apache.shiro.SecurityUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;


public class SystemMenuServiceImpl implements ISystemMenuService {
    @Setter
    private SystemMenuMapper systemMenuMapper;

    public void save(SystemMenu systemMenu) {
        systemMenuMapper.insert(systemMenu);
    }


    public void update(SystemMenu systemMenu) {
        systemMenuMapper.updateByPrimaryKey(systemMenu);
    }


    public void delete(Long id) {
        systemMenuMapper.deleteByPrimaryKey(id);

    }


    public SystemMenu get(Long id) {
        SystemMenu d = systemMenuMapper.selectByPrimaryKey(id);
        return d;
    }


    public List<SystemMenu> listAll() {
        List<SystemMenu> list = systemMenuMapper.selectAll();
        return list;
    }

    @Override
    public PageResult query(SystemMenuQueryObject qo) {
        Integer count = systemMenuMapper.queryForCount(qo);
        if (count == 0) {
            return PageResult.EMPTY_PAGE;
        }
        List<SystemMenu> result = systemMenuMapper.queryForList(qo);
        return new PageResult(count, result, qo.getCurrentPage(), qo.getPageSize());
    }

    @Override
    public List<SystemMenuVO> queryParentMenus(Long parentId) {
        List<SystemMenuVO> menus = new ArrayList<>();
        SystemMenu menu = systemMenuMapper.selectByPrimaryKey(parentId);
        while (menu != null) {
            SystemMenuVO vo = new SystemMenuVO();
            vo.setId(menu.getId());
            vo.setName(menu.getName());
            menus.add(vo);
            menu = menu.getParent();
            if (menu != null) {
                menu = systemMenuMapper.selectByPrimaryKey(menu.getParent().getId());
            } else {
                break;
            }
        }
        Collections.reverse(menus);
        return menus;
    }

    @Override
    public List<Map<String, Object>> queryMenusByParentSn(String parentSn) {
        //如果是管理员直接使用parentSn直接查询
        //Employee currentUser = UserContext.getCurrentUser();
        Employee currentUser = (Employee) SecurityUtils.getSubject().getPrincipal();
        if (currentUser.isAdmin()) {
            return systemMenuMapper.queryMenusByParentSn(parentSn);
        }
        //如果不是管理员,使用parentSn和员工的id进行查询
        return systemMenuMapper.selectByPrimaryKeyAndEmployeeId(parentSn,currentUser.getId());
    }
}
