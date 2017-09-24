package com._520it.wms.web.action;

import com.alibaba.fastjson.JSON;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import org.apache.struts2.ServletActionContext;

public class BaseAction extends ActionSupport{
	public static final String LIST = "list";
	
	private static final long serialVersionUID = 1L;

	protected void putContext (String str,Object obj){
		ActionContext.getContext().put(str, obj);
	}

	protected void putJson(Object obj) throws Exception {
		ServletActionContext.getResponse().setContentType("text/json;charset=utf-8");
		ServletActionContext.getResponse().getWriter().print(JSON.toJSONString(obj));
	}
}
