package com._520it.wms.service.impl;

import com._520it.wms.domain.OrderBill;
import com._520it.wms.domain.OrderBillItem;
import com._520it.wms.mapper.OrderBillItemMapper;
import com._520it.wms.mapper.OrderBillMapper;
import com._520it.wms.page.PageResult;
import com._520it.wms.query.OrderBillQueryObject;
import com._520it.wms.service.IOrderBillService;
import com._520it.wms.util.UserContext;
import lombok.Setter;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Date;
import java.util.List;


public class OrderBillServiceImpl implements IOrderBillService {
    @Setter
    private OrderBillMapper orderBillMapper;
    @Setter
    private OrderBillItemMapper orderBillItemMapper;

    public void save(OrderBill orderBill) {
        //0.查询出当前订单对象是否为未审核

        //1.设置业务日期(录入时间),录入人员(当前登录用户)
        orderBill.setInputTime(new Date());
        orderBill.setInputUser(UserContext.getCurrentUser());
        //2.设置审核的状态为未审核状态
        orderBill.setStatus(OrderBill.NORMAL);
        //3.计算总金额和总数量
        BigDecimal totalAmount = BigDecimal.ZERO;
        BigDecimal totalNumber = BigDecimal.ZERO;
        for (OrderBillItem billItem : orderBill.getItems()) {
            BigDecimal amount = billItem.getCostPrice().multiply(billItem.getNumber()).setScale(2, RoundingMode.HALF_UP);
            //为明细设置小计
            billItem.setAmount(amount);
            //设置总金额和总数量
            totalAmount = totalAmount.add(amount);
            totalNumber = totalNumber.add(billItem.getNumber());
        }
        orderBill.setTotalAmount(totalAmount);
        orderBill.setTotalNumber(totalNumber);

        orderBillMapper.insert(orderBill);
        for (OrderBillItem billItem : orderBill.getItems()) {
            billItem.setBillId(orderBill.getId());
            orderBillItemMapper.insert(billItem);
        }
    }


    public void update(OrderBill orderBill) {
        OrderBill orderBillDB = orderBillMapper.selectByPrimaryKey(orderBill.getId());
        if (orderBillDB != null && orderBillDB.getStatus() == OrderBill.NORMAL) {
            orderBillItemMapper.deleteByBillId(orderBill.getId());
            //3.计算总金额和总数量
            BigDecimal totalAmount = BigDecimal.ZERO;
            BigDecimal totalNumber = BigDecimal.ZERO;
            for (OrderBillItem billItem : orderBill.getItems()) {
                BigDecimal amount = billItem.getCostPrice().multiply(billItem.getNumber()).setScale(2, RoundingMode.HALF_UP);
                //为明细设置小计
                billItem.setAmount(amount);
                billItem.setBillId(orderBill.getId());

                //设置总金额和总数量
                orderBillItemMapper.insert(billItem);
                totalAmount = totalAmount.add(amount);
                totalNumber = totalNumber.add(billItem.getNumber());
            }
            orderBill.setTotalAmount(totalAmount);
            orderBill.setTotalNumber(totalNumber);
            orderBillMapper.updateByPrimaryKey(orderBill);
        }
    }


    public void delete(Long id) {
        orderBillItemMapper.deleteByBillId(id);
        orderBillMapper.deleteByPrimaryKey(id);

    }


    public OrderBill get(Long id) {
         OrderBill d = orderBillMapper.selectByPrimaryKey(id);
        return d;
    }

    @Override
    public PageResult query(OrderBillQueryObject qo) {
        Integer count = orderBillMapper.queryForCount(qo);
        if (count == 0) {
            return PageResult.EMPTY_PAGE;
        }
        List<OrderBill> result = orderBillMapper.queryForList(qo);
        return new PageResult(count, result, qo.getCurrentPage(), qo.getPageSize());
    }

    @Override
    public void batchDelete(List<Long> ids) {
        orderBillMapper.batchDelete(ids);
    }

    @Override
    public void audit(Long id) {
        OrderBill orderBill = orderBillMapper.selectByPrimaryKey(id);
        if (orderBill != null && orderBill.getStatus() == OrderBill.NORMAL) {
            //设置审核信息
            orderBill.setAuditor(UserContext.getCurrentUser());
            orderBill.setAuditTime(new Date());
            orderBill.setStatus(OrderBill.AUDITED);
            //更新订单
            orderBillMapper.audit(orderBill);
        }
    }

}
