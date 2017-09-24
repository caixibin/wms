package com._520it.wms.web.action;

import com._520it.wms.query.ProductStockQueryObject;
import com._520it.wms.query.QueryObject;
import com._520it.wms.service.IBrandService;
import com._520it.wms.service.IDepotService;
import com._520it.wms.service.IProductStockService;
import com._520it.wms.util.RequirePermission;
import lombok.Getter;
import lombok.Setter;

class ProductStockAction extends BaseAction{
	private static final long serialVersionUID = 1L;
	@Setter
	private IProductStockService productStockService;
	@Setter
	private IDepotService depotService;
	@Setter
	private IBrandService brandService;
	@Getter
	ProductStockQueryObject qo = new ProductStockQueryObject();

	@RequirePermission("出库列表")
	public String execute() throws Exception {
		try {
			QueryObject dqo = new QueryObject();
			dqo.setPageSize(-1);
			super.putContext("pageResult", productStockService.query(qo));
			super.putContext("brands", brandService.query(dqo).getData());
			super.putContext("depots", depotService.query(dqo).getData());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return LIST;
	}
	/*@RequirePermission("出库删除")
	public String delete() throws Exception {
		try {
			if(productStock.getId() != null){
                productStockService.delete(productStock.getId());
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
			if(productStock.getId() != null){
                productStock = productStockService.get(productStock.getId());
            }
		} catch (Exception e) {
			e.printStackTrace();
		}
		return INPUT;
	}

	public String view() throws Exception {
		if (productStock.getId() != null) {
			productStock = productStockService.get(productStock.getId());
		}
		return "view";
	}
	@RequirePermission("出库修改或保存")
	public String saveOrUpdate() throws Exception {
		try {
			if(productStock.getId() != null){
                productStockService.update(productStock);
				addActionMessage("更新成功");
            }else{
                productStockService.save(productStock);
				addActionMessage("保存成功");
            }
		} catch (Exception e) {
			e.printStackTrace();
			addActionError(e.getMessage());
		}
		return SUCCESS;
	}*/

	/*@RequirePermission("出库批量删除")
	public String batchDelete() throws Exception {
		try {
			if(ids.size()>0) {
				productStockService.batchDelete(ids);
			}
			putJson("批量删除成功");
		} catch (Exception e) {
			e.printStackTrace();
			putJson("批量删除失败");
		}
		return NONE;
	}
*/
	/*@RequirePermission("出库审核")
	public String audit() throws Exception {
		try {
			if (productStock.getId() != null) {
				productStockService.audit(productStock.getId());
			}
			putJson("审核成功");
		} catch (Exception e) {
			e.printStackTrace();
			putJson(e.getMessage());
		}
		return NONE;
	}*/
}
