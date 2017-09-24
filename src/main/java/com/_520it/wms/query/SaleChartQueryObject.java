package com._520it.wms.query;

import com._520it.wms.util.DateUtil;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

@Getter
@Setter
public class SaleChartQueryObject extends QueryObject {
     public static final Map<String , String> GROUP_TYPES = new LinkedHashMap<>();
     static {
         GROUP_TYPES.put("sm.name", "销售人员");
         GROUP_TYPES.put("p.name", "货品名称");
         GROUP_TYPES.put("c.name", "客户");
         GROUP_TYPES.put("b.name", "货品品牌");
         GROUP_TYPES.put("date_format(sa.vdate,'%Y-%m')", "销售日期(月)");
         GROUP_TYPES.put("date_format(sa.vdate,'%Y-%m-%d')", "销售日期(日)");
     }
    private Date endDate;
    private Date beginDate;
    private String keyword;
    private Long clientId = -1L;
    private Long brandId = -1L;
    private String groupType = "sm.name";

    public Date getEndDate() {
        return DateUtil.getEndDate(endDate);
    }
}
