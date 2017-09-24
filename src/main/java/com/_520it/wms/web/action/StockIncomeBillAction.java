package com._520it.wms.web.action;

import com._520it.wms.domain.StockIncomeBill;
import com._520it.wms.query.QueryObject;
import com._520it.wms.query.StockIncomeBillQueryObject;
import com._520it.wms.service.IDepotService;
import com._520it.wms.service.IStockIncomeBillService;
import com._520it.wms.util.RequirePermission;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

class StockIncomeBillAction extends BaseAction{
	private static final long serialVersionUID = 1L;
	@Setter
	private IStockIncomeBillService stockIncomeBillService;
	@Setter
	private IDepotService depotService;
	@Getter
	StockIncomeBill stockIncomeBill = new StockIncomeBill();
	@Getter@Setter
	List<Long> ids = new ArrayList<>();
	@Getter
	StockIncomeBillQueryObject qo = new StockIncomeBillQueryObject();
	@RequirePermission("入库列表")
	public String execute() throws Exception {
		try {
			QueryObject dqo = new QueryObject();
			dqo.setPageSize(-1);
			super.putContext("depots", depotService.query(dqo).getData());
			putContext("pageResult", stockIncomeBillService.query(qo));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return LIST;
	}
	@RequirePermission("入库删除")
	public String delete() throws Exception {
		try {
			if(stockIncomeBill.getId() != null){
                stockIncomeBillService.delete(stockIncomeBill.getId());
            }
			putJson("删除成功");
		} catch (Exception e) {
			e.printStackTrace();
			putJson("删除失败");
		}
		return NONE;
	}
	@RequirePermission("入库编辑")
	public String input() throws Exception {
		try {
			QueryObject dqo = new QueryObject();
			dqo.setPageSize(-1);
			super.putContext("depots", depotService.query(dqo).getData());
			if(stockIncomeBill.getId() != null){
                stockIncomeBill = stockIncomeBillService.get(stockIncomeBill.getId());
            }
		} catch (Exception e) {
			e.printStackTrace();
		}
		return INPUT;
	}

	public String view() throws Exception {
		if (stockIncomeBill.getId() != null) {
			stockIncomeBill = stockIncomeBillService.get(stockIncomeBill.getId());
		}
		return "view";
	}
	@RequirePermission("入库修改或保存")
	public String saveOrUpdate() throws Exception {
		try {
			if(stockIncomeBill.getId() != null){
                stockIncomeBillService.update(stockIncomeBill);
				addActionMessage("更新成功");
            }else{
                stockIncomeBillService.save(stockIncomeBill);
				addActionMessage("保存成功");
            }
		} catch (Exception e) {
			e.printStackTrace();
			addActionError(e.getMessage());
		}
		return SUCCESS;
	}

	@RequirePermission("入库批量删除")
	public String batchDelete() throws Exception {
		try {
			if(ids.size()>0) {
				stockIncomeBillService.batchDelete(ids);
			}
			putJson("批量删除成功");
		} catch (Exception e) {
			e.printStackTrace();
			putJson("批量删除失败");
		}
		return NONE;
	}

	@RequirePermission("入库审核")
	public String audit() throws Exception {
		try {
			if (stockIncomeBill.getId() != null) {
				stockIncomeBillService.audit(stockIncomeBill.getId());
			}
			putJson("审核成功");
		} catch (Exception e) {
			e.printStackTrace();
			putJson("审核失败");
		}
		return NONE;
	}
}
