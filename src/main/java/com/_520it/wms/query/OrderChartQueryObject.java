package com._520it.wms.query;

import com._520it.wms.util.DateUtil;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;
@Getter@Setter
public class OrderChartQueryObject extends QueryObject{
    public static final Map<String , String>  GROUP_TYPES = new LinkedHashMap<>();
    static {
        GROUP_TYPES.put("iu.name", "定货人员");
        GROUP_TYPES.put("p.name", "货品名称");
        GROUP_TYPES.put("s.name", "供应商");
        GROUP_TYPES.put("b.name", "货品品牌");
        GROUP_TYPES.put("date_format(o.vdate,'%Y-%m')", "订货日期(月)");
        GROUP_TYPES.put("date_format(o.vdate,'%Y-%m-%d')", "订货日期(日)");
    }
    private Date endDate;
    private Date beginDate;
    private String keyword;
    private Long brandId = -1L;
    private Long supplierId = -1L;
    private String groupType = "iu.name";

    public Date getEndDate(){
        return DateUtil.getEndDate(endDate);
    }
}
