package com.roc.demo.modules.generate.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.roc.demo.modules.generate.domain.TableColumnDO;
import com.roc.demo.modules.generate.po.TableColumnPO;

import java.util.List;

/**
 * @Description TableColumnService
 * @Author dongp
 * @Date 2022/6/27 0027 13:59
 */
public interface TableColumnService extends IService<TableColumnPO> {


    /**
     * 根据表名称查询列信息
     *
     * @param tableName 表名称
     * @return 列信息
     */
    List<TableColumnPO> selectDbTableColumnsByName(String tableName);
}
