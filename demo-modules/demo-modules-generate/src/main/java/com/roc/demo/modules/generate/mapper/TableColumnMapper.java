package com.roc.demo.modules.generate.mapper;

import com.roc.demo.modules.generate.po.TableColumnPO;

import java.util.List;

/**
 * @Description TableMapper 数据层
 * @Author dongp
 * @Date 2022/6/27 0027 14:30
 */
public interface TableColumnMapper {

    /**
     * 根据表名称查询列信息
     *
     * @param tableName 表名称
     * @return 列信息
     */
    List<TableColumnPO> selectDbTableColumnsByName(String tableName);
}
