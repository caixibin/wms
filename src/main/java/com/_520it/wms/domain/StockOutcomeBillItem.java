package com._520it.wms.domain;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
@Setter@Getter
public class StockOutcomeBillItem extends BaseDomain {
    private BigDecimal salePrice;//销售价格
    private BigDecimal number;//数量
    private BigDecimal amount;//金额小计
    private String remark;//备注
    private Product product;//产品
    private Long billId;//订单编号
}