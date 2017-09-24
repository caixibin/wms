package com._520it.wms.web.interceptor;

import com._520it.wms.domain.Employee;
import com._520it.wms.util.RequirePermission;
import com._520it.wms.util.UserContext;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;

import java.lang.reflect.Method;
import java.util.List;

public class SecruityInterceptor extends AbstractInterceptor{
	private static final long serialVersionUID = 1L;

	public String intercept(ActionInvocation invocation) throws Exception {
		//获取session中对象,判断是否有信息
		Employee employee = UserContext.getCurrentUser();
		//1.判断是否为超级管理员,是放行
		if(employee.isAdmin()){
			return invocation.invoke();
		}
		
		Object actionObject = invocation.getProxy().getAction();//获取action对象
		String actionMethod = invocation.getProxy().getMethod();//请求action的方法名称
		Method method = actionObject.getClass().getMethod(actionMethod);//请求方法的对象
		
		//2.判断请求的action方法是否贴有权限标志,如没有则放行
		RequirePermission rp = method.getAnnotation(RequirePermission.class);
		if(rp == null){
			return invocation.invoke();
		}
		//3.有则获取当前的action表达式
		String exp  = actionObject.getClass().getName()+ ":" + actionMethod;
		//4.判断表达式是否在session中,有则放行
		List<String> exps = UserContext.getCurrentPermission();
		if(exps.contains(exp)){
			return invocation.invoke(); 
		}
		return "nopermission";
	}

}
/*
根据判断session中传过来的信息,是否与点击的action方法包含在其中,有则表示有权限,否则表示没有改权限 
 */

