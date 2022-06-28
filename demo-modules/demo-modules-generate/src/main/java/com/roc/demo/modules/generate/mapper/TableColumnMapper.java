package com.roc.demo.modules.generate.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.roc.demo.modules.generate.domain.TableColumnDO;
import com.roc.demo.modules.generate.po.TableColumnPO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @Description TableMapper 数据层
 * @Author dongp
 * @Date 2022/6/27 0027 14:30
 */
@Mapper
public interface TableColumnMapper extends BaseMapper<TableColumnPO> {

    /**
     * 根据表名称查询列信息
     *
     * @param tableName 表名称
     * @return 列信息
     */
    List<TableColumnPO> selectDbTableColumnsByName(String tableName);
}
