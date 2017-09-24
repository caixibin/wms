package com._520it.wms.domain;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;
@Setter@Getter
public class SaleAccount extends BaseDomain {
    private Date vdate;//业务时间
    private BigDecimal number;//销售数量
    private BigDecimal costPrice;//成本价
    private BigDecimal costAmount;//成本总额
    private BigDecimal salePrice;//销售价
    private BigDecimal saleAmount;//销售总额
    private Long productId;//销售产品
    private Long salemanId;//销售人员
    private Long clientId;//顾客
}