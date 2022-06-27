package com.roc.demo.modules.generate.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.roc.demo.common.core.dto.generate.db.DbListDTO;
import com.roc.demo.common.core.vo.generate.db.DbListVO;
import com.roc.demo.modules.generate.domain.Table;
import com.roc.demo.modules.generate.domain.TableColumn;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Description TableMapper 数据层
 * @Author dongp
 * @Date 2022/6/27 0027 14:30
 */
@Mapper
public interface TableColumnMapper extends BaseMapper<TableColumn> {

    /**
     * 根据表名称查询列信息
     *
     * @param tableName 表名称
     * @return 列信息
     */
    List<TableColumn> selectDbTableColumnsByName(String tableName);
}
