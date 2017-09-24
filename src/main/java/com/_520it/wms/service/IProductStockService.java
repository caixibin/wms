package com._520it.wms.service;

import com._520it.wms.domain.StockIncomeBill;
import com._520it.wms.domain.StockOutcomeBill;
import com._520it.wms.page.PageResult;
import com._520it.wms.query.QueryObject;

public interface IProductStockService {
    PageResult query(QueryObject qo);
    public void stockIncome(StockIncomeBill bill);
    public void stockOutcome(StockOutcomeBill bill);
}
