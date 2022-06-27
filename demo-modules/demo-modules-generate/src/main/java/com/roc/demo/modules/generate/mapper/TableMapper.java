package com.roc.demo.modules.generate.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.roc.demo.common.core.dto.generate.db.DbListDTO;
import com.roc.demo.common.core.vo.generate.db.DbListVO;
import com.roc.demo.modules.generate.domain.Table;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Description TableMapper 数据层
 * @Author dongp
 * @Date 2022/6/27 0027 14:30
 */
@Mapper
public interface TableMapper extends BaseMapper<Table> {

    /**
     * 查询据库列表
     *
     * @param page 分页条件
     * @param dto  查询条件
     * @return 数据库表集合
     */
    Page<DbListVO> selectDbTableList(Page<DbListVO> page, @Param("dto") DbListDTO dto);


}
