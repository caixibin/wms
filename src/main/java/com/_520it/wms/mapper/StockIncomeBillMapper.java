package com._520it.wms.mapper;

import com._520it.wms.domain.StockIncomeBill;
import com._520it.wms.query.QueryObject;

import java.util.List;

public interface StockIncomeBillMapper {
    int deleteByPrimaryKey(Long id);

    int insert(StockIncomeBill record);

    StockIncomeBill selectByPrimaryKey(Long id);

    int updateByPrimaryKey(StockIncomeBill record);

    Integer queryForCount(QueryObject qo);
    List<StockIncomeBill> queryForList(QueryObject qo);

    void batchDelete(List<Long> ids);


    void audit(StockIncomeBill bill);
}