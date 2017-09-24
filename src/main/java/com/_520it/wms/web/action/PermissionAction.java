package com._520it.wms.web.action;

import lombok.Getter;
import lombok.Setter;

import com._520it.wms.domain.Permission;
import com._520it.wms.query.QueryObject;
import com._520it.wms.service.IPermissionService;
import com._520it.wms.util.RequirePermission;

import java.util.ArrayList;
import java.util.List;

public class PermissionAction extends BaseAction{
	private static final long serialVersionUID = 1L;
	
	@Setter
	private IPermissionService permissionService;
	@Getter
	QueryObject qo = new QueryObject();
	@Getter
	Permission permission = new Permission();
	@Setter@Getter
	private List<Long> ids = new ArrayList<>();
	@Override
	@RequirePermission("权限列表")
	public String execute() throws Exception {
		putContext("pageResult", permissionService.query(qo));
		return LIST;
	}
	@RequirePermission("权限删除")
	public String delete() throws Exception {
		try {
			//int i = 1/0;
			if(permission.getId() != null){
                permissionService.delete(permission.getId());
            }
			putJson("删除成功");
		} catch (Exception e) {
			e.printStackTrace();
			putJson("删除失败");
		}
		return NONE;
	}
	@RequirePermission("权限批量删除")
	public String batchDelete() throws Exception {
		try {
			if(ids.size()>0){
                permissionService.batchDelete(ids);
            }
			putJson("批量删除成功");
		} catch (Exception e) {
			e.printStackTrace();
			putJson("批量删除失败");
		}
		return NONE;
	}

	@RequirePermission("权限加载")
	public String reload() throws Exception {
		try {
			permissionService.reload();
			putJson("加载成功");
		} catch (Exception e) {
			e.printStackTrace();
			putJson("加载失败");
		}
		return NONE;
	}
}
