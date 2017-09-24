package com._520it.wms.web.action;

import com._520it.wms.domain.StockOutcomeBill;
import com._520it.wms.query.QueryObject;
import com._520it.wms.query.StockOutcomeBillQueryObject;
import com._520it.wms.service.IClientService;
import com._520it.wms.service.IDepotService;
import com._520it.wms.service.IStockOutcomeBillService;
import com._520it.wms.util.RequirePermission;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

class StockOutcomeBillAction extends BaseAction{
	private static final long serialVersionUID = 1L;
	@Setter
	private IStockOutcomeBillService stockOutcomeBillService;
	@Setter
	private IDepotService depotService;
	@Setter
	private IClientService clientService;
	@Getter
	StockOutcomeBill stockOutcomeBill = new StockOutcomeBill();
	@Getter@Setter
	List<Long> ids = new ArrayList<>();
	@Getter
	StockOutcomeBillQueryObject qo = new StockOutcomeBillQueryObject();
	@RequirePermission("出库列表")
	public String execute() throws Exception {
		try {
			QueryObject dqo = new QueryObject();
			dqo.setPageSize(-1);
			super.putContext("depots", depotService.query(dqo).getData());
			super.putContext("clients", clientService.query(dqo).getData());
			super.putContext("pageResult", stockOutcomeBillService.query(qo));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return LIST;
	}
	@RequirePermission("出库删除")
	public String delete() throws Exception {
		try {
			if(stockOutcomeBill.getId() != null){
                stockOutcomeBillService.delete(stockOutcomeBill.getId());
            }
			putJson("删除成功");
		} catch (Exception e) {
			e.printStackTrace();
			putJson("删除失败");
		}
		return NONE;
	}
	@RequirePermission("出库编辑")
	public String input() throws Exception {
		try {
			QueryObject dqo = new QueryObject();
			dqo.setPageSize(-1);
			super.putContext("depots", depotService.query(dqo).getData());
			super.putContext("clients", clientService.query(dqo).getData());
			if(stockOutcomeBill.getId() != null){
                stockOutcomeBill = stockOutcomeBillService.get(stockOutcomeBill.getId());
            }
		} catch (Exception e) {
			e.printStackTrace();
		}
		return INPUT;
	}

	public String view() throws Exception {
		if (stockOutcomeBill.getId() != null) {
			stockOutcomeBill = stockOutcomeBillService.get(stockOutcomeBill.getId());
		}
		return "view";
	}
	@RequirePermission("出库修改或保存")
	public String saveOrUpdate() throws Exception {
		try {
			if(stockOutcomeBill.getId() != null){
                stockOutcomeBillService.update(stockOutcomeBill);
				addActionMessage("更新成功");
            }else{
                stockOutcomeBillService.save(stockOutcomeBill);
				addActionMessage("保存成功");
            }
		} catch (Exception e) {
			e.printStackTrace();
			addActionError(e.getMessage());
		}
		return SUCCESS;
	}

	/*@RequirePermission("出库批量删除")
	public String batchDelete() throws Exception {
		try {
			if(ids.size()>0) {
				stockOutcomeBillService.batchDelete(ids);
			}
			putJson("批量删除成功");
		} catch (Exception e) {
			e.printStackTrace();
			putJson("批量删除失败");
		}
		return NONE;
	}
*/
	@RequirePermission("出库审核")
	public String audit() throws Exception {
		try {
			if (stockOutcomeBill.getId() != null) {
				stockOutcomeBillService.audit(stockOutcomeBill.getId());
			}
			putJson("审核成功");
		} catch (Exception e) {
			e.printStackTrace();
			putJson(e.getMessage());
		}
		return NONE;
	}
}
