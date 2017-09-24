package com._520it.wms.web.action;

import com._520it.wms.domain.Supplier;
import com._520it.wms.query.SupplierQueryObject;
import com._520it.wms.service.ISupplierService;
import com._520it.wms.util.RequirePermission;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

public class SupplierAction extends BaseAction{
	private static final long serialVersionUID = 1L;
	@Setter
	private ISupplierService supplierService;
	@Getter
	Supplier supplier = new Supplier();
	@Getter@Setter
	List<Long> ids = new ArrayList<>();
	@Getter
	SupplierQueryObject qo = new SupplierQueryObject();
	@RequirePermission("供应商列表")
	public String execute() throws Exception {
		try {
			putContext("pageResult", supplierService.query(qo));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return LIST;
	}
	@RequirePermission("供应商删除")
	public String delete() throws Exception {
		try {
			if(supplier.getId() != null){
                supplierService.delete(supplier.getId());
            }
			putJson("删除成功");
		} catch (Exception e) {
			e.printStackTrace();
			putJson("删除失败");
		}
		return NONE;
	}
	@RequirePermission("供应商编辑")
	public String input() throws Exception {
		try {
			if(supplier.getId() != null){
                supplier = supplierService.get(supplier.getId());
            }
		} catch (Exception e) {
			e.printStackTrace();
		}
		return INPUT;
	}
	@RequirePermission("供应商修改或保存")
	public String saveOrUpdate() throws Exception {
		try {
			if(supplier.getId() != null){
                supplierService.update(supplier);
				addActionMessage("更新成功");
            }else{
                supplierService.save(supplier);
				addActionMessage("保存成功");
            }
		} catch (Exception e) {
			e.printStackTrace();
			addActionError(e.getMessage());
		}
		return SUCCESS;
	}
	@RequirePermission("供应商批量删除")
	public String batchDelete() throws Exception {
		try {
			if(ids.size()>0) {
                supplierService.batchDelete(ids);
            }
			putJson("批量删除成功");
		} catch (Exception e) {
			e.printStackTrace();
			putJson("批量删除失败");
		}
		return NONE;
	}
}
