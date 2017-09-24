package com._520it.wms.service.impl;

import com._520it.wms.domain.Product;
import com._520it.wms.mapper.ProductMapper;
import com._520it.wms.page.PageResult;
import com._520it.wms.query.ProductQueryObject;
import com._520it.wms.service.IProductService;
import lombok.Setter;

import java.util.List;


public class ProductServiceImpl implements IProductService {
	@Setter
	private ProductMapper productMapper;
	
	public void save(Product product) {
		productMapper.insert(product);
	}

	
	public void update(Product product) {
		productMapper.updateByPrimaryKey(product);
	}

	
	public void delete(Long id) {
		productMapper.deleteByPrimaryKey(id);
	}

	
	public Product get(Long id) {
		Product d = productMapper.selectByPrimaryKey(id);
		return d;
	}

	@Override
	public PageResult query(ProductQueryObject qo) {
		Integer count = productMapper.queryForCount(qo);
		if (count==0){
			return PageResult.EMPTY_PAGE;
		}
		List<Product> result = productMapper.queryForList(qo);
		return new PageResult(count,result,qo.getCurrentPage(),qo.getPageSize());
	}

	@Override
	public void batchDelete(List<Long> ids) {
		productMapper.batchDelete(ids);
	}

}
