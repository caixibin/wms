package com._520it.wms.query;

import com._520it.wms.util.DateUtil;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class BaseAuditQueryObject extends QueryObject{
    private Date beginDate;//开始日期
    private Date endDate;//最后日期
    private Integer status = -1;//状态 审核:1 未审核:0

    public Date getEndDate() {
        return DateUtil.getEndDate(endDate);
    }
}
