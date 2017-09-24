package com._520it.wms.domain;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;
@Setter@Getter
public class ProductStock extends BaseDomain {

    private BigDecimal price;//库存价格

    private BigDecimal storeNumber;//库存数量

    private BigDecimal amount;//库存金额

    private Date incomeDate;//入库时间

    private Date outcomeDate;//出库时间

    private Product product;//货品

    private Depot depot;//仓库

}