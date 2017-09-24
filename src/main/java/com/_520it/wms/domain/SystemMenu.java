package com._520it.wms.domain;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
public class SystemMenu extends BaseDomain {
    private String name;

    private String url;

    private String sn;

    private SystemMenu parent;

    private List<SystemMenu> childrens = new ArrayList<>();

    public Object getParentName() {
        if (getParent() != null) {
            return getParent().getName();
        } else {
            return "根目录";
        }
    }
}