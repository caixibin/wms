package com._520it.wms.domain;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter@Setter
public class BaseAuditDomain extends BaseDomain{
	public static final int NORMAL = 0;
	public static final int AUDITED = 1;

	private Integer status;//审核状态
	private Date auditTime;//审核时间
	private Date inputTime;//录入时间
	private Employee inputUser;
	private Employee auditor;
}
