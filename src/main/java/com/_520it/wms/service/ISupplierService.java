package com._520it.wms.service;

import com._520it.wms.domain.Supplier;
import com._520it.wms.page.PageResult;
import com._520it.wms.query.SupplierQueryObject;

import java.util.List;

public interface ISupplierService {
	void save(Supplier supplier);
	void update(Supplier supplier);
	void delete(Long id);
	
	Supplier get(Long id);
	List<Supplier> listAll();

	PageResult query(SupplierQueryObject qo);

	void batchDelete(List<Long> ids);
}
