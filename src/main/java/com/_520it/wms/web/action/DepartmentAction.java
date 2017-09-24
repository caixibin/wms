package com._520it.wms.web.action;

import com._520it.wms.domain.Department;
import com._520it.wms.query.DepartmentQueryObject;
import com._520it.wms.query.QueryObject;
import com._520it.wms.service.IDepartmentService;
import lombok.Getter;
import lombok.Setter;
import org.apache.shiro.authz.annotation.RequiresPermissions;

import java.util.ArrayList;
import java.util.List;

public class DepartmentAction extends BaseAction {
    private static final long serialVersionUID = 1L;
    @Setter
    private IDepartmentService departmentService;
    @Getter
    Department department = new Department();

    @Getter
    @Setter
    private List<Long> ids = new ArrayList<>();

    @Getter
    QueryObject qo = new DepartmentQueryObject();

    @RequiresPermissions("department:execute")
    public String execute() throws Exception {
        putContext("pageResult", departmentService.query(qo));
        return LIST;
    }

    @RequiresPermissions("department:delete")
    public String delete() throws Exception {
        try {
            if (department.getId() != null) {
                departmentService.delete(department.getId());
            }
            //int i = 1/0;
            putJson("删除成功");
        } catch (Exception e) {
            e.printStackTrace();
           putJson("删除失败");
        }
        return NONE;
    }

    @RequiresPermissions("department:input")
    public String input() throws Exception {
        putContext("depts", departmentService.listAll());
        if (department.getId() != null) {
            department = departmentService.get(department.getId());
        }
        return INPUT;
    }

    @RequiresPermissions("department:saveOrUpdate")
    public String saveOrUpdate() throws Exception {
        try {
            //int b = 1 / 0;
            if (department.getId() != null) {
                departmentService.update(department);
                addActionMessage("更新成功");
            } else {
                departmentService.save(department);
                addActionMessage("保存成功");
            }
        } catch (Exception e) {
            e.printStackTrace();
            addActionMessage(e.getMessage());
        }
        return SUCCESS;
    }

    @RequiresPermissions("department:batchDelete")
    public String batchDelete() throws Exception {
        try {
            //int i = 1/0;
            if (ids.size() > 0) {
                departmentService.batchDelete(ids);
            }
            putJson("批量删除成功");
        } catch (Exception e) {
            e.printStackTrace();
            putJson("批量删除失败");
        }
        return NONE;
    }
}
