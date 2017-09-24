package com._520it.wms.mapper;

import com._520it.wms.domain.StockOutcomeBill;
import com._520it.wms.query.QueryObject;

import java.util.List;

public interface StockOutcomeBillMapper {
    int deleteByPrimaryKey(Long id);

    int insert(StockOutcomeBill record);

    StockOutcomeBill selectByPrimaryKey(Long id);

    int updateByPrimaryKey(StockOutcomeBill record);

    Integer queryForCount(QueryObject qo);
    List<StockOutcomeBill> queryForList(QueryObject qo);

    void audit(StockOutcomeBill bill);

}