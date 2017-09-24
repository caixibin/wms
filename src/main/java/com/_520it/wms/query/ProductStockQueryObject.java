package com._520it.wms.query;

import lombok.Getter;
import lombok.Setter;

@Getter@Setter
public class ProductStockQueryObject extends QueryObject{
    private String keyword;
    private Long depotId = -1L;
    private Long brandId = -1L;
    private Integer limitNumber;

}
