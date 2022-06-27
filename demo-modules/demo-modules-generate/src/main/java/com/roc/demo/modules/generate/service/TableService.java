package com.roc.demo.modules.generate.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.roc.demo.common.core.dto.generate.db.DbListDTO;
import com.roc.demo.common.core.dto.generate.table.TableImportDTO;
import com.roc.demo.common.core.vo.generate.db.DbListVO;
import com.roc.demo.modules.generate.domain.Table;

import java.util.List;

/**
 * @Description TableService
 * @Author dongp
 * @Date 2022/6/27 0027 13:59
 */
public interface TableService extends IService<Table> {

    /**
     * 查询据库列表
     *
     * @param dto 查询条件
     * @return 数据库表集合
     */
    Page<DbListVO> getDbList(DbListDTO dto);

    /**
     * 导入表结构
     *
     * @param itemList 导入表列表
     */
    void importTable(List<TableImportDTO.ItemDTO> itemList);
}
