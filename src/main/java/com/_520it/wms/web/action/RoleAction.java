package com._520it.wms.web.action;


import com._520it.wms.domain.Role;
import com._520it.wms.query.QueryObject;
import com._520it.wms.service.IPermissionService;
import com._520it.wms.service.IRoleService;
import com._520it.wms.service.ISystemMenuService;
import com._520it.wms.util.RequirePermission;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

public class RoleAction extends BaseAction{
	private static final long serialVersionUID = 1L;
	@Setter
	private IRoleService roleService;
	@Setter
	private IPermissionService permissionService;
	@Setter
	private ISystemMenuService systemMenuService;
	@Getter
	Role role = new Role();
    @Setter@Getter
    List<Long> ids = new ArrayList<>();
	@Getter
	QueryObject qo = new QueryObject();

	@RequirePermission("角色列表")
	public String execute() throws Exception {
		putContext("pageResult", roleService.query(qo));
		return LIST;
	}
	@RequirePermission("角色删除")
	public String delete() throws Exception {
		try {
			if(role.getId() != null){
                roleService.delete(role.getId());
            }
			putJson("删除成功");
		} catch (Exception e) {
			e.printStackTrace();
			putJson("删除失败");
		}
		return NONE;
	}
	@RequirePermission("角色编辑")
	public String input() throws Exception {
		try {
			putContext("permissions", permissionService.listAll());
			putContext("menus", systemMenuService.listAll());
			if(role.getId() != null){
                role = roleService.get(role.getId());
            }
		} catch (Exception e) {
			e.printStackTrace();
			addActionError(e.getMessage());
		}
		return INPUT;
	}
	@RequirePermission("角色保存或者更新")
	public String saveOrUpdate() throws Exception {
		try {
			if(role.getId() != null){
                roleService.update(role);
				addActionMessage("更新成功");
            }else{
                roleService.save(role);
				addActionMessage("保存成功");
            }
		} catch (Exception e) {
			e.printStackTrace();
			addActionError(e.getMessage());
		}
		return SUCCESS;
	}
	@RequirePermission("角色批量删除")
	public String batchDelete() throws Exception {
        try {
            if(ids.size()>0){
                roleService.batchDelete(ids);
            }
            putJson("批量删除成功");
        } catch (Exception e) {
            e.printStackTrace();
            putJson("批量删除失败");
        }
        return NONE;
	}
}
