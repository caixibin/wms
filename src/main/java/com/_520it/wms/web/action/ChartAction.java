package com._520it.wms.web.action;

import com._520it.wms.query.OrderChartQueryObject;
import com._520it.wms.query.QueryObject;
import com._520it.wms.query.SaleChartQueryObject;
import com._520it.wms.service.IBrandService;
import com._520it.wms.service.IChartService;
import com._520it.wms.service.IClientService;
import com._520it.wms.service.ISupplierService;
import com._520it.wms.util.RequirePermission;
import com.alibaba.fastjson.JSON;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.*;

public class ChartAction extends BaseAction{
	private static final long serialVersionUID = 1L;
	@Setter
	private IChartService chartService;
	@Setter
	private IBrandService brandService;
	@Setter
	private ISupplierService supplierService;
	@Setter
	private IClientService clientService;
	@Getter
	private OrderChartQueryObject oqo = new OrderChartQueryObject();
	@Getter
	private SaleChartQueryObject sqo = new SaleChartQueryObject();
	@RequirePermission("订货报表")
	public String orderChart() throws Exception {
		try {
			putContext("pageResult", chartService.orderChart(this.oqo));
			putContext("brands",brandService.listAll());
			putContext("suppliers",supplierService.listAll());
			putContext("groupTypes",OrderChartQueryObject.GROUP_TYPES);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "orderChart";
	}
	@RequirePermission("销售报表")
	public String saleChart() throws Exception {
		try {
			QueryObject qo = new QueryObject();
			qo.setPageSize(-1);
			putContext("pageResult", chartService.saleChart(this.sqo));
			putContext("clients",clientService.query(qo).getData());
			putContext("brands",brandService.query(qo).getData());
			putContext("groupTypes",SaleChartQueryObject.GROUP_TYPES);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "saleChart";
	}
	@RequirePermission("销售报表柱状图")
	public String saleChartByBar() throws Exception {
		List<Map<String, Object>> lists = chartService.saleChart(this.sqo);
		putContext("groupType",SaleChartQueryObject.GROUP_TYPES.get(sqo.getGroupType()));

		List<String> groupList = new ArrayList<>();
		List<BigDecimal> amountList = new ArrayList<>();

		for (Map<String, Object> map : lists) {
			groupList.add((String) map.get("groupType"));
			amountList.add((BigDecimal) map.get("totalAmount"));
		}
		putContext("groupList", JSON.toJSONString(groupList));
		putContext("amountList", JSON.toJSONString(amountList));
		return "saleChartByBar";
	}

	@RequirePermission("销售报表饼图")
	public String saleChartByPie() throws Exception {
		List<Map<String, Object>> lists = chartService.saleChart(this.sqo);
		putContext("groupType",SaleChartQueryObject.GROUP_TYPES.get(sqo.getGroupType()));

		List<String> groupList = new ArrayList<>();
		List<HashMap<Object, Object>> datas = new ArrayList<>();
		BigDecimal amountMax = BigDecimal.ZERO;
		for (Map<String, Object> map : lists) {
			BigDecimal amount = (BigDecimal) map.get("totalAmount");
			String groupType = (String) map.get("groupType");
			if (amount.compareTo(amountMax) > 0){
				amountMax = amount;
			}

			groupList.add(groupType);
			HashMap<Object, Object> data = new HashMap<>();
			data.put("name",groupType);
			data.put("value",amount);
			datas.add(data);
		}
		putContext("amountMax",amountMax);
		putContext("groupList", JSON.toJSONString(groupList));
		putContext("datas", JSON.toJSONString(datas));
		return "saleChartByPie";
	}
}
