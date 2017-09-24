package com._520it.wms.service;

import com._520it.wms.domain.StockOutcomeBill;
import com._520it.wms.page.PageResult;
import com._520it.wms.query.QueryObject;

public interface IStockOutcomeBillService {
	void save(StockOutcomeBill stockOutcomeBill);
	void update(StockOutcomeBill stockOutcomeBill);
	void delete(Long id);
	
	StockOutcomeBill get(Long id);

	PageResult query(QueryObject qo);

	//void batchDelete(List<Long> ids);

	void audit(Long id);
}
