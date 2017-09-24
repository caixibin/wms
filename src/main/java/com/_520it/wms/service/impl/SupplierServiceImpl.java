package com._520it.wms.service.impl;

import com._520it.wms.domain.Supplier;
import com._520it.wms.mapper.SupplierMapper;
import com._520it.wms.page.PageResult;
import com._520it.wms.query.SupplierQueryObject;
import com._520it.wms.service.ISupplierService;
import lombok.Setter;

import java.util.List;


public class SupplierServiceImpl implements ISupplierService {
	@Setter
	private SupplierMapper supplierMapper;
	
	public void save(Supplier supplier) {
		supplierMapper.insert(supplier);
	}

	
	public void update(Supplier supplier) {
		supplierMapper.updateByPrimaryKey(supplier);
	}

	
	public void delete(Long id) {
		supplierMapper.deleteByPrimaryKey(id);

	}

	
	public Supplier get(Long id) {
		Supplier d = supplierMapper.selectByPrimaryKey(id);
		return d;
	}

	
	public List<Supplier> listAll() {
		List<Supplier> list = supplierMapper.selectAll();
		return list;
	}

	@Override
	public PageResult query(SupplierQueryObject qo) {
		Integer count = supplierMapper.queryForCount(qo);
		if (count==0){
			return PageResult.EMPTY_PAGE;
		}
		List<Supplier> result = supplierMapper.queryForList(qo);
		return new PageResult(count,result,qo.getCurrentPage(),qo.getPageSize());
	}

	@Override
	public void batchDelete(List<Long> ids) {
		supplierMapper.batchDelete(ids);
	}

}
