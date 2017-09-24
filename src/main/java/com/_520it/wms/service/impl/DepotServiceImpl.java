package com._520it.wms.service.impl;

import com._520it.wms.domain.Depot;
import com._520it.wms.mapper.DepotMapper;
import com._520it.wms.page.PageResult;
import com._520it.wms.query.QueryObject;
import com._520it.wms.service.IDepotService;
import lombok.Setter;

import java.util.List;


public class DepotServiceImpl implements IDepotService {
	@Setter
	private DepotMapper depotMapper;
	
	public void save(Depot depot) {
		depotMapper.insert(depot);
	}

	public void update(Depot depot) {
		depotMapper.updateByPrimaryKey(depot);
	}

	public void delete(Long id) {
		depotMapper.deleteByPrimaryKey(id);

	}

	public Depot get(Long id) {
		Depot depot = depotMapper.selectByPrimaryKey(id);
		return depot;
	}

	public PageResult query(QueryObject qo) {
		Integer count = depotMapper.queryForCount(qo);
		if (count==0){
			return PageResult.EMPTY_PAGE;
		}
		List<Depot> result = depotMapper.queryForList(qo);
		return new PageResult(count,result,qo.getCurrentPage(),qo.getPageSize());
	}

	public void batchDelete(List<Long> ids) {
		depotMapper.batchDelete(ids);
	}

}
