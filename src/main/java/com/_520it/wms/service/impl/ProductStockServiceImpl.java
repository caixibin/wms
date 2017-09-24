package com._520it.wms.service.impl;

import com._520it.wms.domain.*;
import com._520it.wms.mapper.ProductStockMapper;
import com._520it.wms.mapper.SaleAccountMapper;
import com._520it.wms.page.PageResult;
import com._520it.wms.query.QueryObject;
import com._520it.wms.service.IProductStockService;
import lombok.Setter;

import java.math.RoundingMode;
import java.util.Date;
import java.util.List;

public class ProductStockServiceImpl implements IProductStockService {
    @Setter
    private ProductStockMapper productStockMapper;
    @Setter
    private SaleAccountMapper saleAccountMapper;
    @Override
    public PageResult query(QueryObject qo) {
        Integer count = productStockMapper.queryForCount(qo);
        if (count == 0) {
            return PageResult.EMPTY_PAGE;
        }
        List<StockOutcomeBill> result = productStockMapper.queryForList(qo);
        return new PageResult(count, result, qo.getCurrentPage(), qo.getPageSize());
    }

    @Override
    public void stockIncome(StockIncomeBill bill) {
        for (StockIncomeBillItem item : bill.getItems()) {
            //根据仓库id和产品的id获取一条即时库存
            ProductStock ps = productStockMapper.selectByProductIdAndDepotId(bill.getDepot().getId(), item.getProduct().getId());
            if(ps == null){
                //创建一条库存保存
                ps = new ProductStock();
                ps.setDepot(bill.getDepot());
                ps.setProduct(item.getProduct());
                ps.setStoreNumber(item.getNumber());
                ps.setPrice(item.getCostPrice());
                ps.setAmount(item.getAmount());
                ps.setIncomeDate(new Date());
                productStockMapper.insert(ps);
            }else{
                //如果有,则修改库存金额,库存数量,库存进价
                ps.setAmount(ps.getAmount().add(item.getAmount()));
                ps.setStoreNumber(ps.getStoreNumber().add(item.getNumber()));
                ps.setPrice(ps.getAmount().divide(ps.getStoreNumber()).setScale(2, RoundingMode.HALF_UP));
                productStockMapper.updateByPrimaryKey(ps);
            }
        }
    }

    @Override
    public void stockOutcome(StockOutcomeBill bill) {
        for (StockOutcomeBillItem item : bill.getItems()) {
            //5.根据产品id+仓库id获取库存明细
            ProductStock ps = productStockMapper.selectByProductIdAndDepotId(item.getProduct().getId(), bill.getDepot().getId());
            //6.判断销售的货品是否在仓库有货,三种状态,没有该产品
            if (ps == null){
                throw new RuntimeException("在"+bill.getDepot().getName()+"中没有"+item.getProduct().getName()+"产品");
            }else if (ps.getStoreNumber().compareTo(item.getNumber())<0){
                //7.有,但数量不够
                throw new RuntimeException("在"+bill.getDepot().getName()+"中"+item.getProduct().getName()+"产品的数量"+ps.getStoreNumber()+"不足出货数量"+item.getNumber());
            }else {
                //8.有且数量足够,设置库存数量,计算库存总金额
                ps.setStoreNumber(ps.getStoreNumber().subtract(item.getNumber()));
                ps.setAmount(ps.getPrice().multiply(ps.getStoreNumber()).setScale(2,RoundingMode.HALF_UP));
                ps.setOutcomeDate(new Date());
                //9.更新库存
                productStockMapper.updateByPrimaryKey(ps);

                //===============记账===================
                SaleAccount sa = new SaleAccount();
                sa.setClientId(bill.getClient().getId());
                sa.setSalemanId(bill.getInputUser().getId());
                sa.setProductId(item.getProduct().getId());
                sa.setVdate(bill.getVdate());

                sa.setNumber(item.getNumber());//获取销售数量
                sa.setCostPrice(ps.getPrice());//获取成本价
                sa.setCostAmount(sa.getNumber().multiply(sa.getCostPrice().setScale(2,RoundingMode.HALF_UP)));//计算成本总额
                sa.setSalePrice(item.getSalePrice());//销售价格
                sa.setSaleAmount(sa.getSalePrice().multiply(sa.getNumber()).setScale(2,RoundingMode.HALF_UP));//销售总额

                saleAccountMapper.insert(sa);
            }
        }
    }
}
