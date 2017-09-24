package com._520it.wms.domain;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class Permission extends BaseDomain{
	private String expression;//权限表达式
	private String name;
}
