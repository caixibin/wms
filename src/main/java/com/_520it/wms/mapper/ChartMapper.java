package com._520it.wms.mapper;

import com._520it.wms.query.QueryObject;
import com._520it.wms.query.SaleChartQueryObject;

import javax.management.Query;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public interface ChartMapper {
  List<Map<String,Object>> orderChart(QueryObject qo) ;

  List<Map<String,Object>> saleChart(QueryObject qo);
}