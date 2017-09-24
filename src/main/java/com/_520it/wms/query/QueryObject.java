package com._520it.wms.query;


import lombok.Getter;
import lombok.Setter;

public class QueryObject {
	@Setter@Getter
	private Integer currentPage = 1;
	@Setter@Getter
	private Integer pageSize = 5;
	public Integer getStart(){
		return (currentPage - 1) * pageSize;
	}
	
	protected String empty2Null(String str) {
		return hasLength(str)? str:null;
	}
	private boolean hasLength(String str){
		return str != null && !"".equals(str.trim());
	}
}
