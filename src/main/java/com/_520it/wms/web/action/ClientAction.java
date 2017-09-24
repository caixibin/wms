package com._520it.wms.web.action;

import com._520it.wms.domain.Client;
import com._520it.wms.query.QueryObject;
import com._520it.wms.service.IClientService;
import com._520it.wms.util.RequirePermission;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

public class ClientAction extends BaseAction{
	private static final long serialVersionUID = 1L;
	@Setter
	private IClientService clientService;
	@Getter
	Client client = new Client();
	@Getter@Setter
	List<Long> ids = new ArrayList<>();
	@Getter
	QueryObject qo = new QueryObject();
	@RequirePermission("客户列表")
	public String execute() throws Exception {
		try {
			putContext("pageResult", clientService.query(qo));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return LIST;
	}
	@RequirePermission("客户删除")
	public String delete() throws Exception {
		try {
			if(client.getId() != null){
                clientService.delete(client.getId());
            }
			putJson("删除成功");
		} catch (Exception e) {
			e.printStackTrace();
			putJson("删除失败");
		}
		return NONE;
	}
	@RequirePermission("客户编辑")
	public String input() throws Exception {
		try {
			if(client.getId() != null){
                client = clientService.get(client.getId());
            }
		} catch (Exception e) {
			e.printStackTrace();
		}
		return INPUT;
	}
	@RequirePermission("客户修改或保存")
	public String saveOrUpdate() throws Exception {
		try {
			if(client.getId() != null){
                clientService.update(client);
				addActionMessage("更新成功");
            }else{
                clientService.save(client);
				addActionMessage("保存成功");
            }
		} catch (Exception e) {
			e.printStackTrace();
			addActionError(e.getMessage());
		}
		return SUCCESS;
	}

	/*@RequirePermission("客户批量删除")
	public String batchDelete() throws Exception {
		try {
			if(ids.size()>0) {
				clientService.batchDelete(ids);
			}
			putJson("批量删除成功");
		} catch (Exception e) {
			e.printStackTrace();
			putJson("批量删除失败");
		}
		return NONE;
	}*/
}
