package com._520it.wms.service;

import com._520it.wms.domain.Product;
import com._520it.wms.page.PageResult;
import com._520it.wms.query.ProductQueryObject;

import java.util.List;

public interface IProductService {
	void save(Product product);
	void update(Product product);
	void delete(Long id);
	
	Product get(Long id);

	PageResult query(ProductQueryObject qo);

	void batchDelete(List<Long> ids);
}
