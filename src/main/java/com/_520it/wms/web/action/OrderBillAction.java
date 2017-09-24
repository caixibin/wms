package com._520it.wms.web.action;

import com._520it.wms.domain.OrderBill;
import com._520it.wms.query.OrderBillQueryObject;
import com._520it.wms.service.IOrderBillService;
import com._520it.wms.service.ISupplierService;
import com._520it.wms.util.RequirePermission;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

public class OrderBillAction extends BaseAction {
    private static final long serialVersionUID = 1L;
    @Setter
    private IOrderBillService orderBillService;
    @Setter
    private ISupplierService supplierService;
    @Getter
    private OrderBill orderBill = new OrderBill();
    @Getter
    @Setter
    private List<Long> ids = new ArrayList<>();
    @Getter
    private OrderBillQueryObject qo = new OrderBillQueryObject();

    @RequirePermission("订单列表")
    public String execute() throws Exception {
        try {
            putContext("suppliers", supplierService.listAll());
            putContext("pageResult", orderBillService.query(qo));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return LIST;
    }

    @RequirePermission("订单删除")
    public String delete() throws Exception {
        try {
            if (orderBill.getId() != null) {
                orderBillService.delete(orderBill.getId());
            }
            putJson("删除成功");
        } catch (Exception e) {
            e.printStackTrace();
            putJson("删除失败");
        }
        return NONE;
    }

    @RequirePermission("订单编辑")
    public String input() throws Exception {
        try {
            putContext("suppliers",supplierService.listAll());
            if (orderBill.getId() != null) {
                orderBill = orderBillService.get(orderBill.getId());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return INPUT;
    }

    public String view() throws Exception {
            if (orderBill.getId() != null) {
                orderBill = orderBillService.get(orderBill.getId());
            }
        return "view";
    }

    @RequirePermission("订单修改或保存")
    public String saveOrUpdate() throws Exception {
        try {
            if (orderBill.getId() != null) {
                orderBillService.update(orderBill);
                addActionMessage("更新成功");
            } else {
                orderBillService.save(orderBill);
                addActionMessage("保存成功");
            }
        } catch (Exception e) {
            e.printStackTrace();
            addActionError(e.getMessage());
        }
        return SUCCESS;
    }

   @RequirePermission("订单批量删除")
    public String batchDelete() throws Exception {
        try {
            if (ids.size() > 0) {
                orderBillService.batchDelete(ids);
            }
            putJson("批量删除成功");
        } catch (Exception e) {
            e.printStackTrace();
            putJson("批量删除失败");
        }
        return NONE;
    }
    @RequirePermission("订单审核")
    public String audit() throws Exception {
        try {
            if (orderBill.getId() != null) {
                orderBillService.audit(orderBill.getId());
            }
            putJson("审核成功");
        } catch (Exception e) {
            e.printStackTrace();
            putJson("审核失败");
        }
        return NONE;
    }
}
