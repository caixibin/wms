package com._520it.wms.domain;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@Setter@Getter
public class StockIncomeBill extends BaseAuditDomain {


    private String sn;//订单号

    private Date vdate;//业务时间

    private BigDecimal totalAmount;//总金额

    private BigDecimal totalNumber;//总数量

    private Depot depot;

    private List<StockIncomeBillItem> items = new ArrayList<>();

}