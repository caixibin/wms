package com._520it.wms.service.impl;

import com._520it.wms.domain.StockIncomeBill;
import com._520it.wms.domain.StockOutcomeBill;
import com._520it.wms.domain.StockOutcomeBillItem;
import com._520it.wms.mapper.StockOutcomeBillItemMapper;
import com._520it.wms.mapper.StockOutcomeBillMapper;
import com._520it.wms.page.PageResult;
import com._520it.wms.query.QueryObject;
import com._520it.wms.service.IProductStockService;
import com._520it.wms.service.IStockOutcomeBillService;
import com._520it.wms.util.UserContext;
import lombok.Setter;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Date;
import java.util.List;


public class StockOutcomeBillServiceImpl implements IStockOutcomeBillService {
    @Setter
    private StockOutcomeBillItemMapper stockOutcomeBillItemMapper;
    @Setter
    private StockOutcomeBillMapper stockOutcomeBillMapper;
    @Setter
    private IProductStockService productStockService;

    public void save(StockOutcomeBill stockOutcomeBill) {
        //1.设置录入信息
        stockOutcomeBill.setInputTime(new Date());//录入时间
        stockOutcomeBill.setInputUser(UserContext.getCurrentUser());//输入人员
        stockOutcomeBill.setStatus(StockOutcomeBill.NORMAL);//未审核状态
        //2.计算总金额和总数量
        BigDecimal totalAmount = BigDecimal.ZERO;
        BigDecimal totalNumber = BigDecimal.ZERO;
        //循环遍历每一个产品的明细item
        for (StockOutcomeBillItem item : stockOutcomeBill.getItems()) {
            //计算单个产品的总价
            BigDecimal amount = item.getSalePrice().multiply(item.getNumber()).setScale(2, RoundingMode.HALF_UP);
            //设置每一产品的总价
            item.setAmount(amount);
            //将每一产品的总价和总数量添加到总数中去
            totalAmount = totalAmount.add(amount);
            totalNumber = totalNumber.add(item.getNumber());
        }
        //3.设置总金额和总数量
        stockOutcomeBill.setTotalAmount(totalAmount);
        stockOutcomeBill.setTotalNumber(totalNumber);
        //4.保存销售订单
        stockOutcomeBillMapper.insert(stockOutcomeBill);
        //5.为明细设置订单id
        for (StockOutcomeBillItem item : stockOutcomeBill.getItems()) {
            item.setBillId(stockOutcomeBill.getId());
            //6.保存明细
            stockOutcomeBillItemMapper.insert(item);
        }
    }

    public void update(StockOutcomeBill stockOutcomeBill) {
        //查询出订单对象,并判断是否为审核状态
        StockOutcomeBill bill = stockOutcomeBillMapper.selectByPrimaryKey(stockOutcomeBill.getId());
        if (bill != null && bill.getStatus()== StockOutcomeBill.NORMAL){
            //操作人员有可能修改,增加,删除明细,故将之前所有的明细(数据库中)删除,再将修改之后的明细保存到数据库中去
            stockOutcomeBillItemMapper.deleteByBillId(stockOutcomeBill.getId());
            //计算出库单总金额和总数
            BigDecimal totalAmount = BigDecimal.ZERO;
            BigDecimal totalNumber = BigDecimal.ZERO;
            //循环遍历明细
            for (StockOutcomeBillItem item : stockOutcomeBill.getItems()) {
                //计算明细小计
                BigDecimal amount = item.getSalePrice().multiply(item.getNumber()).setScale(2, RoundingMode.HALF_UP);
                totalAmount = totalAmount.add(amount);
                totalAmount = totalNumber.add(item.getNumber());
                //由于是编辑,直接知道了图库单的billid
                item.setBillId(stockOutcomeBill.getId());
                stockOutcomeBillItemMapper.insert(item);
            }
            stockOutcomeBill.setTotalAmount(totalAmount);
            stockOutcomeBill.setTotalNumber(totalNumber);
            stockOutcomeBillMapper.insert(stockOutcomeBill);
        }
    }

    public void delete(Long id) {
        stockOutcomeBillItemMapper.deleteByBillId(id);
        stockOutcomeBillMapper.deleteByPrimaryKey(id);
    }

    public StockOutcomeBill get(Long id) {
        return stockOutcomeBillMapper.selectByPrimaryKey(id);
    }

    public PageResult query(QueryObject qo) {
        Integer count = stockOutcomeBillMapper.queryForCount(qo);
        if (count == 0) {
            return PageResult.EMPTY_PAGE;
        }
        List<StockOutcomeBill> result = stockOutcomeBillMapper.queryForList(qo);
        return new PageResult(count, result, qo.getCurrentPage(), qo.getPageSize());
    }

   /* public void batchDelete(List<Long> ids) {
        stockOutcomeBillMapper.batchDelete(ids);
    }*/
    public void audit(Long id) {
        //1.查询出库单
        StockOutcomeBill bill = stockOutcomeBillMapper.selectByPrimaryKey(id);
        //2.判断出库单是否为未审核状态
        if (bill != null && bill.getStatus() == StockOutcomeBill.NORMAL){
            //3.如未审核,设置审核时间,审核人,审核状态
            bill.setAuditor(UserContext.getCurrentUser());
            bill.setAuditTime(new Date());
            bill.setStatus(StockIncomeBill.AUDITED);
            //4.更新订单
            stockOutcomeBillMapper.audit(bill);
            //==========更新库存操作=============
            productStockService.stockOutcome(bill);
        }
    }
}
