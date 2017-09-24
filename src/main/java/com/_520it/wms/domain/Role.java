package com._520it.wms.domain;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Setter@Getter
public class Role extends BaseDomain{
	private String name;
	private String sn;
	//把中间表交给role管理,many2many
	private List<Permission> permissions = new ArrayList<>();
	//角色和系统菜单也是mang2mang
	private List<SystemMenu> menus = new ArrayList<>();
}
