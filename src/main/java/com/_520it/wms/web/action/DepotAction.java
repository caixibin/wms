package com._520it.wms.web.action;

import com._520it.wms.domain.Depot;
import com._520it.wms.query.QueryObject;
import com._520it.wms.service.IDepotService;
import com._520it.wms.util.RequirePermission;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

public class DepotAction extends BaseAction{
	private static final long serialVersionUID = 1L;
	@Setter
	private IDepotService depotService;
	@Getter
	Depot depot = new Depot();
	@Getter@Setter
	List<Long> ids = new ArrayList<>();
	@Getter
	QueryObject qo = new QueryObject();
	@RequirePermission("仓库列表")
	public String execute() throws Exception {
		try {
			putContext("pageResult", depotService.query(qo));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return LIST;
	}
	@RequirePermission("仓库删除")
	public String delete() throws Exception {
		try {
			if(depot.getId() != null){
                depotService.delete(depot.getId());
            }
			putJson("删除成功");
		} catch (Exception e) {
			e.printStackTrace();
			putJson("删除失败");
		}
		return NONE;
	}
	@RequirePermission("仓库编辑")
	public String input() throws Exception {
		try {
			if(depot.getId() != null){
                depot = depotService.get(depot.getId());
            }
		} catch (Exception e) {
			e.printStackTrace();
		}
		return INPUT;
	}
	@RequirePermission("仓库修改或保存")
	public String saveOrUpdate() throws Exception {
		try {
			if(depot.getId() != null){
                depotService.update(depot);
				addActionMessage("更新成功");
            }else{
                depotService.save(depot);
				addActionMessage("保存成功");
            }
		} catch (Exception e) {
			e.printStackTrace();
			addActionError(e.getMessage());
		}
		return SUCCESS;
	}

	@RequirePermission("仓库批量删除")
	public String batchDelete() throws Exception {
		try {
			if(ids.size()>0) {
				depotService.batchDelete(ids);
			}
			putJson("批量删除成功");
		} catch (Exception e) {
			e.printStackTrace();
			putJson("批量删除失败");
		}
		return NONE;
	}
}
