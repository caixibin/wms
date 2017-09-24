package com._520it.wms.domain;

import com.alibaba.fastjson.JSON;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

@Setter@Getter
public class Product extends BaseDomain {

    private String name;
    private String sn;

    private BigDecimal costPrice;

    private BigDecimal salePrice;

    private String imagePath;

    private String intro;

    private Brand brand;

    public String getSmallImagePath(){
        if (StringUtils.isEmpty(imagePath)){
            return "";
        }
        int indexOf = imagePath.lastIndexOf(".");
        String smallImagePath = imagePath.substring(0, indexOf) + "_small" + imagePath.substring(indexOf);
        return smallImagePath;
    }

    //创建一个get方法将页面需要的信息进行封装
    public String getJsonString(){
        Map<String,Object> map = new HashMap<>();
        map.put("id",super.getId());
        map.put("name",this.getName());
        map.put("brandName",this.brand.getName()==null?"":brand.getName());
        map.put("costPrice",this.getCostPrice());
        map.put("salePrice",this.getSalePrice());
        return JSON.toJSONString(map);
    }
}