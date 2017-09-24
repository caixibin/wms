package com._520it.wms.mapper;

import com._520it.wms.domain.Supplier;
import com._520it.wms.query.SupplierQueryObject;

import java.util.List;

public interface SupplierMapper {
    int deleteByPrimaryKey(Long id);

    int insert(Supplier record);

    Supplier selectByPrimaryKey(Long id);

    List<Supplier> selectAll();

    int updateByPrimaryKey(Supplier record);

    Integer queryForCount(SupplierQueryObject qo);
    List<Supplier> queryForList(SupplierQueryObject qo);

    void batchDelete(List<Long> ids);
}