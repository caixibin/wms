package com._520it.wms.page;


import java.util.Collections;
import java.util.List;

import lombok.Getter;
@Getter
public class PageResult {
	public static final PageResult EMPTY_PAGE = new PageResult(0, Collections.EMPTY_LIST, 1, 1);
	private Integer endPage;
	private Integer prevPage;
	private Integer nextPage;
	
	private Integer currentPage;
	private Integer pageSize;
	
	private Integer totalCount;
	private List<?> data;
	
	public PageResult(Integer totalCount, List<?> data, Integer currentPage, Integer pageSize) {
		this.totalCount = totalCount;
		this.data = data;
		this.currentPage = currentPage;
		this.pageSize = pageSize;
		
		endPage = totalCount % pageSize == 0 ? totalCount / pageSize : totalCount / pageSize + 1;
		prevPage = currentPage - 1 > 1 ? currentPage - 1 : 1;
		nextPage = currentPage + 1 < endPage ? currentPage + 1 : endPage;
	}
}
