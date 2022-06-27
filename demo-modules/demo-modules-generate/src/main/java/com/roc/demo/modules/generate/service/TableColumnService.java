package com.roc.demo.modules.generate.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.roc.demo.common.core.dto.generate.db.DbListDTO;
import com.roc.demo.common.core.dto.generate.table.TableImportDTO;
import com.roc.demo.common.core.vo.generate.db.DbListVO;
import com.roc.demo.modules.generate.domain.Table;
import com.roc.demo.modules.generate.domain.TableColumn;

import java.util.List;

/**
 * @Description TableColumnService
 * @Author dongp
 * @Date 2022/6/27 0027 13:59
 */
public interface TableColumnService extends IService<TableColumn> {


    /**
     * 根据表名称查询列信息
     *
     * @param tableName 表名称
     * @return 列信息
     */
    List<TableColumn> selectDbTableColumnsByName(String tableName);
}
