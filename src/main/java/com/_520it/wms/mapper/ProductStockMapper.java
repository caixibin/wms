package com._520it.wms.mapper;

import com._520it.wms.domain.ProductStock;
import com._520it.wms.domain.StockOutcomeBill;
import com._520it.wms.query.QueryObject;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ProductStockMapper {

    int insert(ProductStock record);

    ProductStock selectByProductIdAndDepotId(@Param("productId") Long productId, @Param("depotId") Long depotId);

    int updateByPrimaryKey(ProductStock record);

    Integer queryForCount(QueryObject qo);

    List<StockOutcomeBill> queryForList(QueryObject qo);
}