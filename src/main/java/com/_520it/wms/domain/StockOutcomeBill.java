package com._520it.wms.domain;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Getter@Setter
public class StockOutcomeBill extends BaseAuditDomain {

    private String sn;//编码
    private Date vdate;//业务日期
    private BigDecimal totalAmount;//总金额
    private BigDecimal totalNumber;//总数量
    private Depot depot;//仓库
    private Client client;//客户
    private List<StockOutcomeBillItem> items = new ArrayList<>();
}