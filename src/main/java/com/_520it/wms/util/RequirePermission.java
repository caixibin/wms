package com._520it.wms.util;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)//运行在jvm上
@Target(ElementType.METHOD)//只能贴在方法上
public @interface RequirePermission {
	String value();
}
