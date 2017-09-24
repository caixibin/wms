package com._520it.wms.web.action;

import com._520it.wms.domain.Employee;
import com._520it.wms.query.EmployeeQueryObject;
import com._520it.wms.query.QueryObject;
import com._520it.wms.service.IDepartmentService;
import com._520it.wms.service.IEmployeeService;
import com._520it.wms.service.IRoleService;
import com._520it.wms.util.RequirePermission;
import com.opensymphony.xwork2.interceptor.annotations.InputConfig;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

public class EmployeeAction extends BaseAction {
    private static final long serialVersionUID = 1L;
    @Setter
    private IEmployeeService employeeService;
    @Setter
    private IDepartmentService departmentService;
    @Setter
    private IRoleService roleService;
    @Getter
    @Setter
    private List<Long> ids = new ArrayList<>();
    @Getter
    Employee employee = new Employee();
    @Getter
    QueryObject qo = new EmployeeQueryObject();
    @InputConfig(methodName = "input")
    @RequirePermission("员工列表")
    public String execute() throws Exception {
        try {
            putContext("depts", departmentService.listAll());
            putContext("roles", roleService.listAll());
            putContext("pageResult", employeeService.query(qo));
           // int a = 1 / 0;
        } catch (Exception e) {
            e.printStackTrace();
            addActionError(e.getMessage());
        }
        return LIST;
    }

    @RequirePermission("员工删除")
    public String delete() throws Exception {
        try {
            if (employee.getId() != null) {
                employeeService.delete(employee.getId());
            }
            putJson("删除成功");
        } catch (Exception e) {
            e.printStackTrace();
            putJson("删除失败");
        }
        return NONE;
    }

    @RequirePermission("员工编辑")
    public String input() throws Exception {
        try {
            putContext("depts", departmentService.listAll());
            putContext("roles", roleService.listAll());
            if (employee.getId() != null) {
                employee = employeeService.get(employee.getId());
            }
            //int a = 1 / 0;
        } catch (Exception e) {
            e.printStackTrace();
            addActionError(e.getMessage());
        }
        return INPUT;
    }

    @RequirePermission("员工保存或者更新")
    public String saveOrUpdate() throws Exception {
        try {
            //int a = 1 / 0;
            if (employee.getId() != null) {
                employeeService.update(employee);
                addActionMessage("更新成功");
            } else {
                employeeService.save(employee);
                addActionMessage("保存成功");
}
        } catch (Exception e) {
            e.printStackTrace();
            addActionError(e.getMessage());
        }
        return SUCCESS;
    }

    public String batchDelete() throws Exception {
        try {
            if (ids.size() > 0) {
                employeeService.batchDelete(ids);
            }
            putJson("批量删除成功");
        } catch (Exception e) {
            e.printStackTrace();
            putJson("批量删除失败");
        }
        return NONE;
    }
}
