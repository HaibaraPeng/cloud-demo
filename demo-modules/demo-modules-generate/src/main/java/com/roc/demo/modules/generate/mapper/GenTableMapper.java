package com.roc.demo.modules.generate.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.roc.demo.common.core.dto.generate.db.GenDbListDTO;
import com.roc.demo.common.core.vo.generate.db.GenDbListVO;
import com.roc.demo.modules.generate.domain.GenTable;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Description 业务 数据层
 * @Author dongp
 * @Date 2022/6/27 0027 14:30
 */
@Mapper
public interface GenTableMapper extends BaseMapper<GenTable> {

    /**
     * 查询据库列表
     *
     * @param page 分页条件
     * @param dto  查询条件
     * @return 数据库表集合
     */
    Page<GenDbListVO> selectDbTableList(Page<GenDbListVO> page, @Param("dto") GenDbListDTO dto);
}
