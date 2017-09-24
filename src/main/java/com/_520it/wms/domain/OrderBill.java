package com._520it.wms.domain;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Setter@Getter
public class OrderBill extends BaseDomain {
    public static final Integer NORMAL = 0;
    public static final Integer AUDITED = 1;

    private String sn;

    private Date vdate;//业务时间

    private Integer status;//审核状态

    private BigDecimal totalAmount;//总金额

    private BigDecimal totalNumber;//总数

    private Date auditTime;//审核时间

    private Date inputTime;//输入时间

    private Employee inputUser;//输入人员

    private Employee auditor;//审核人

    private Supplier supplier;//供应商

    private List<OrderBillItem> items = new ArrayList<>();
}