package com._520it.wms.mapper;

import com._520it.wms.domain.SystemMenu;
import com._520it.wms.query.SystemMenuQueryObject;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface SystemMenuMapper {
    int deleteByPrimaryKey(Long id);

    int insert(SystemMenu record);

    SystemMenu selectByPrimaryKey(Long id);

    List<SystemMenu> selectAll();

    int updateByPrimaryKey(SystemMenu record);

    Integer queryForCount(SystemMenuQueryObject qo);
    List<SystemMenu> queryForList(SystemMenuQueryObject qo);

    List<Map<String,Object>> queryMenusByParentSn(String parentSn);

    List<Map<String,Object>> selectByPrimaryKeyAndEmployeeId(@Param("parentSn") String parentSn, @Param("employeeId") Long employeeId);
}