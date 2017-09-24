package com._520it.wms.service.impl;

import com._520it.wms.domain.Brand;
import com._520it.wms.mapper.BrandMapper;
import com._520it.wms.page.PageResult;
import com._520it.wms.query.QueryObject;
import com._520it.wms.service.IBrandService;
import lombok.Setter;

import java.util.List;


public class BrandServiceImpl implements IBrandService {
	@Setter
	private BrandMapper brandMapper;
	
	public void save(Brand brand) {
		brandMapper.insert(brand);
	}

	
	public void update(Brand brand) {
		brandMapper.updateByPrimaryKey(brand);
	}

	
	public void delete(Long id) {
		brandMapper.deleteByPrimaryKey(id);

	}

	
	public Brand get(Long id) {
		Brand d = brandMapper.selectByPrimaryKey(id);
		return d;
	}

	
	public List<Brand> listAll() {
		List<Brand> list = brandMapper.selectAll();
		return list;
	}

	@Override
	public PageResult query(QueryObject qo) {
		Integer count = brandMapper.queryForCount(qo);
		if (count==0){
			return PageResult.EMPTY_PAGE;
		}
		List<Brand> result = brandMapper.queryForList(qo);
		return new PageResult(count,result,qo.getCurrentPage(),qo.getPageSize());
	}

	@Override
	public void batchDelete(List<Long> ids) {
		brandMapper.batchDelete(ids);
	}

}
