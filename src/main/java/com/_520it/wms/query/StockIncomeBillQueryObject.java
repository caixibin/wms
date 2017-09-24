package com._520it.wms.query;

import lombok.Getter;
import lombok.Setter;
@Getter@Setter
public class StockIncomeBillQueryObject extends BaseAuditQueryObject{
    private Long depotId = -1L;//仓库的id
}
