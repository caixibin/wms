package com._520it.wms.query;

import com._520it.wms.util.DateUtil;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * Created by Administrator on 2017/9/9.
 */
@Setter@Getter
public class OrderBillQueryObject extends QueryObject{
    private Date beginDate;
    private Date endDate;
    private Long supplierId = -1L;
    private Integer status = -1;

    public Date getEndDate(){
        return DateUtil.getEndDate(endDate);
    }
}
