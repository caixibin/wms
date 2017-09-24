package com._520it.wms.domain;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
@Setter
@Getter
public class Employee extends BaseDomain {
	private String name;
	private String password;//密码
	private String email;//邮箱
	private Integer age;//年龄
	private boolean admin;//是否为超级管理员
	private Department dept;//所属部门
	private List<Role> roles = new ArrayList<>();//员工所有的角色

}
