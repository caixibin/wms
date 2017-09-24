package com._520it.wms.service.impl;

import com._520it.wms.domain.Client;
import com._520it.wms.mapper.ClientMapper;
import com._520it.wms.page.PageResult;
import com._520it.wms.query.QueryObject;
import com._520it.wms.service.IClientService;
import lombok.Setter;

import java.util.List;


public class ClientServiceImpl implements IClientService {
	@Setter
	private ClientMapper clientMapper;
	
	public void save(Client client) {
		clientMapper.insert(client);
	}

	
	public void update(Client client) {
		clientMapper.updateByPrimaryKey(client);
	}

	
	public void delete(Long id) {
		clientMapper.deleteByPrimaryKey(id);

	}

	
	public Client get(Long id) {
		return clientMapper.selectByPrimaryKey(id);
	}

	@Override
	public PageResult query(QueryObject qo) {
		Integer count = clientMapper.queryForCount(qo);
		if (count==0){
			return PageResult.EMPTY_PAGE;
		}
		List<Client> result = clientMapper.queryForList(qo);
		return new PageResult(count,result,qo.getCurrentPage(),qo.getPageSize());
	}

/*	@Override
	public void batchDelete(List<Long> ids) {
		clientMapper.batchDelete(ids);
	}*/

}
