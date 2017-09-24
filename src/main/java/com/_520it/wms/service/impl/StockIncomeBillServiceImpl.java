package com._520it.wms.service.impl;

import com._520it.wms.domain.StockIncomeBill;
import com._520it.wms.domain.StockIncomeBillItem;
import com._520it.wms.mapper.StockIncomeBillItemMapper;
import com._520it.wms.mapper.StockIncomeBillMapper;
import com._520it.wms.page.PageResult;
import com._520it.wms.query.QueryObject;
import com._520it.wms.service.IProductStockService;
import com._520it.wms.service.IStockIncomeBillService;
import com._520it.wms.util.UserContext;
import lombok.Setter;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Date;
import java.util.List;


public class StockIncomeBillServiceImpl implements IStockIncomeBillService {
    @Setter
    private StockIncomeBillItemMapper stockIncomeBillItemMapper;
    @Setter
    private StockIncomeBillMapper stockIncomeBillMapper;
    @Setter
    private IProductStockService productStockService;

    public void save(StockIncomeBill stockIncomeBill) {
        //1.设置录入信息
        stockIncomeBill.setInputTime(new Date());//录入时间
        stockIncomeBill.setInputUser(UserContext.getCurrentUser());//输入人员
        stockIncomeBill.setStatus(StockIncomeBill.NORMAL);//未审核状态
        //2.计算总金额和总数量
        BigDecimal totalAmount = BigDecimal.ZERO;
        BigDecimal totalNumber = BigDecimal.ZERO;
        //循环遍历每一个产品的明细item
        for (StockIncomeBillItem item : stockIncomeBill.getItems()) {
            //计算单个产品的总价
            BigDecimal amount = item.getCostPrice().multiply(item.getNumber()).setScale(2, RoundingMode.HALF_UP);
            //设置每一产品的总价
            item.setAmount(amount);
            //将每一产品的总价和总数量添加到总数中去
            totalAmount = totalAmount.add(amount);
            totalNumber = totalNumber.add(item.getNumber());
        }
        //3.设置总金额和总数量
        stockIncomeBill.setTotalAmount(totalAmount);
        stockIncomeBill.setTotalNumber(totalNumber);
        //4.保存采购订单
        stockIncomeBillMapper.insert(stockIncomeBill);
        //5.为明细设置订单id
        for (StockIncomeBillItem item : stockIncomeBill.getItems()) {
            item.setBillId(stockIncomeBill.getId());
            //6.保存明细
            stockIncomeBillItemMapper.insert(item);
        }
    }

    public void update(StockIncomeBill stockIncomeBill) {
        //查询出订单对象,并判断是否为审核状态
        StockIncomeBill bill = stockIncomeBillMapper.selectByPrimaryKey(stockIncomeBill.getId());
        if (bill != null && bill.getStatus()== StockIncomeBill.NORMAL){
            //操作人员有可能修改,增加,删除明细,故将之前所有的明细(数据库中)删除,再将修改之后的明细保存到数据库中去
            stockIncomeBillItemMapper.deleteByBillId(stockIncomeBill.getId());
            //计算入库单总金额和总数
            BigDecimal totalAmount = BigDecimal.ZERO;
            BigDecimal totalNumber = BigDecimal.ZERO;
            //循环遍历明细
            for (StockIncomeBillItem item : stockIncomeBill.getItems()) {
                //计算明细小计
                BigDecimal amount = item.getCostPrice().multiply(item.getNumber()).setScale(2, RoundingMode.HALF_UP);
                totalAmount = totalAmount.add(amount);
                totalAmount = totalNumber.add(item.getNumber());
                //由于是编辑,直接知道了图库单的billid
                item.setBillId(stockIncomeBill.getId());
                stockIncomeBillItemMapper.insert(item);
            }
            stockIncomeBill.setTotalAmount(totalAmount);
            stockIncomeBill.setTotalNumber(totalNumber);
            stockIncomeBillMapper.insert(stockIncomeBill);
        }
    }

    public void delete(Long id) {
        stockIncomeBillItemMapper.deleteByBillId(id);
        stockIncomeBillMapper.deleteByPrimaryKey(id);
    }

    public StockIncomeBill get(Long id) {
        return stockIncomeBillMapper.selectByPrimaryKey(id);
    }

    public PageResult query(QueryObject qo) {
        Integer count = stockIncomeBillMapper.queryForCount(qo);
        if (count == 0) {
            return PageResult.EMPTY_PAGE;
        }
        List<StockIncomeBill> result = stockIncomeBillMapper.queryForList(qo);
        return new PageResult(count, result, qo.getCurrentPage(), qo.getPageSize());
    }

    public void batchDelete(List<Long> ids) {
        stockIncomeBillMapper.batchDelete(ids);
    }
    public void audit(Long id) {
        //1.查询出入库单,判断是否为审核状态
        StockIncomeBill bill = stockIncomeBillMapper.selectByPrimaryKey(id);
        if(bill != null && bill.getStatus()==StockIncomeBill.NORMAL){
            //2设置审核信息审核人/审核时间/审核状态
            bill.setAuditor(UserContext.getCurrentUser());
            bill.setAuditTime(new Date());
            bill.setStatus(StockIncomeBill.AUDITED);
            //3更新订单审核信息
            stockIncomeBillMapper.audit(bill);
            //        -----库存操作------
            productStockService.stockIncome(bill);
        }
    }
}
