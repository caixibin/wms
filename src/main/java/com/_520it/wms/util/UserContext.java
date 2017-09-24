package com._520it.wms.util;

import com._520it.wms.domain.Employee;
import com.opensymphony.xwork2.ActionContext;

import java.util.List;

public class UserContext {

    public static void setCurrentUser(Object obj) {
        ActionContext.getContext().getSession().put("user_in_session", obj);
    }

    public static Employee getCurrentUser() {
        return (Employee) ActionContext.getContext().getSession().get("user_in_session");
    }

    public static void setCurrentPermission(Object obj) {
        ActionContext.getContext().getSession().put("permission_in_session", obj);
    }
    public static List<String> getCurrentPermission(){
        return (List<String>)ActionContext.getContext().getSession().get("permission_in_session");
    }
}
