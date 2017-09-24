package com._520it.wms.web.action;

import com._520it.wms.domain.SystemMenu;
import com._520it.wms.query.SystemMenuQueryObject;
import com._520it.wms.service.ISystemMenuService;
import com._520it.wms.util.RequirePermission;
import com._520it.wms.vo.SystemMenuVO;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Map;

public class SystemMenuAction extends BaseAction {
    private static final long serialVersionUID = 1L;
    @Setter
    private ISystemMenuService systemMenuService;
    @Getter
    SystemMenu systemMenu = new SystemMenu();
    @Getter
    SystemMenuQueryObject qo = new SystemMenuQueryObject();

    @RequirePermission("菜单列表")
    public String execute() throws Exception {
        //根据parentId查出所有的上级目录,共享到context区域
        List<SystemMenuVO> parentMenus = systemMenuService.queryParentMenus(qo.getParentId());
        putContext("parentMenus", parentMenus);
        putContext("pageResult", systemMenuService.query(qo));
        return LIST;
    }

    @RequirePermission("菜单删除")
    public String delete() throws Exception {
        try {
            if (systemMenu.getId() != null) {
                systemMenuService.delete(systemMenu.getId());
            }
            putJson("删除成功");
        } catch (Exception e) {
            e.printStackTrace();
            putJson("删除失败");
        }
        return NONE;
    }

    @RequirePermission("菜单编辑")
    public String input() throws Exception {
        if (qo.getParentId() == null) {
            putContext("parentName", "根目录");
        } else {
            SystemMenu parent = systemMenuService.get(qo.getParentId());
            putContext("parentName", parent.getName());
        }
        if (this.systemMenu.getId() != null) {
            this.systemMenu = systemMenuService.get(this.systemMenu.getId());
        }
        return INPUT;
    }

    @RequirePermission("菜单修改或保存")
    public String saveOrUpdate() throws Exception {
        try {
            if (qo.getParentId() != null) {
                SystemMenu parent = systemMenuService.get(qo.getParentId());
                systemMenu.setParent(parent);
            }
            if (this.systemMenu.getId() != null) {
                systemMenuService.update(this.systemMenu);
                addActionMessage("更新成功");
            } else {
                systemMenuService.save(this.systemMenu);
                addActionMessage("保存成功");
            }
        } catch (Exception e) {
            e.printStackTrace();
            addActionError(e.getMessage());
        }
        return SUCCESS;
    }

    //更新菜单,根据根目录加载
    public String loadMenusBySn() throws Exception {
        System.out.println(qo.getParentSn());
        //通过根目录的sn查出底下的所有子目录
        List<Map<String, Object>> list = systemMenuService.queryMenusByParentSn(qo.getParentSn());
        /*List<Map<String, Object>> list = new ArrayList<>();
        Map<String, Object> map = new HashMap<>();
        map.put("id", 11);
        list.add(map);*/
        putJson(list);
        return NONE;
    }

}
