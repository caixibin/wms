package com._520it.wms.util;

import java.util.Calendar;
import java.util.Date;
public class DateUtil {
    public static Date getEndDate(Date endDate) {
        if(endDate == null){return endDate;}
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(endDate);

        calendar.set(Calendar.HOUR_OF_DAY,23);
        calendar.set(Calendar.MINUTE,59);
        calendar.set(Calendar.SECOND,59);
        return calendar.getTime();
    }
}
