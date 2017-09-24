package com._520it.wms.query;

import lombok.Getter;
import lombok.Setter;

@Getter@Setter
public class StockOutcomeBillQueryObject extends BaseAuditQueryObject{
    private Long depotId = -1L;//仓库的id
    private Long clientId = -1L;//客户的id
}
