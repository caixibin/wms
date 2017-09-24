package com._520it.wms.service;

import com._520it.wms.query.QueryObject;

import java.util.List;
import java.util.Map;

public interface IChartService {
    List<Map<String,Object>> orderChart(QueryObject qo) ;

    List<Map<String,Object>> saleChart(QueryObject qo);
}
