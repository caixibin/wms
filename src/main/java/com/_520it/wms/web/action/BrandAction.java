package com._520it.wms.web.action;

import com._520it.wms.domain.Brand;
import com._520it.wms.query.BrandQueryObject;
import com._520it.wms.service.IBrandService;
import com._520it.wms.util.RequirePermission;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

public class BrandAction extends BaseAction{
	private static final long serialVersionUID = 1L;
	@Setter
	private IBrandService brandService;
	@Getter
	Brand brand = new Brand();
	@Getter@Setter
	List<Long> ids = new ArrayList<>();
	@Getter
	BrandQueryObject qo = new BrandQueryObject();
	@RequirePermission("品牌列表")
	public String execute() throws Exception {
		try {
			putContext("pageResult", brandService.query(qo));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return LIST;
	}
	@RequirePermission("品牌删除")
	public String delete() throws Exception {
		try {
			if(brand.getId() != null){
                brandService.delete(brand.getId());
            }
			putJson("删除成功");
		} catch (Exception e) {
			e.printStackTrace();
			putJson("删除失败");
		}
		return NONE;
	}
	@RequirePermission("品牌编辑")
	public String input() throws Exception {
		try {
			if(brand.getId() != null){
                brand = brandService.get(brand.getId());
            }
		} catch (Exception e) {
			e.printStackTrace();
		}
		return INPUT;
	}
	@RequirePermission("品牌修改或保存")
	public String saveOrUpdate() throws Exception {
		try {
			if(brand.getId() != null){
                brandService.update(brand);
				addActionMessage("更新成功");
            }else{
                brandService.save(brand);
				addActionMessage("保存成功");
            }
		} catch (Exception e) {
			e.printStackTrace();
			addActionError(e.getMessage());
		}
		return SUCCESS;
	}

	@RequirePermission("品牌批量删除")
	public String batchDelete() throws Exception {
		try {
			if(ids.size()>0) {
				brandService.batchDelete(ids);
			}
			putJson("批量删除成功");
		} catch (Exception e) {
			e.printStackTrace();
			putJson("批量删除失败");
		}
		return NONE;
	}

}
