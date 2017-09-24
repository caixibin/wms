package com._520it.wms.mapper;

import com._520it.wms.domain.Depot;
import com._520it.wms.query.QueryObject;

import java.util.List;

public interface DepotMapper {
    int deleteByPrimaryKey(Long id);

    int insert(Depot record);

    Depot selectByPrimaryKey(Long id);

    int updateByPrimaryKey(Depot record);

    Integer queryForCount(QueryObject qo);
    List<Depot> queryForList(QueryObject qo);

    void batchDelete(List<Long> ids);
}