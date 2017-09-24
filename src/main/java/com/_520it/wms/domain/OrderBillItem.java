package com._520it.wms.domain;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
@Setter@Getter
public class OrderBillItem extends BaseDomain {

    private BigDecimal costPrice;

    private BigDecimal number;

    private BigDecimal amount;

    private String remark;

    private Product product;

    private Long billId;
}