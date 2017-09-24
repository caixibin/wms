package com._520it.wms.domain;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
@Setter@Getter
public class StockIncomeBillItem extends BaseDomain {

    private BigDecimal costPrice;//成本价

    private BigDecimal number;//数量

    private BigDecimal amount;//小计

    private String remark;//备注

    private Product product;//产品

    private Long billId;//订单id

}