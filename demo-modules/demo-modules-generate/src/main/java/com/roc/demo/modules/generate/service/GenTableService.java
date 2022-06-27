package com.roc.demo.modules.generate.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.roc.demo.common.core.dto.generate.db.GenDbListDTO;
import com.roc.demo.common.core.vo.generate.db.GenDbListVO;
import com.roc.demo.modules.generate.domain.GenTable;

import java.util.List;

/**
 * @Description GenTableService
 * @Author dongp
 * @Date 2022/6/27 0027 13:59
 */
public interface GenTableService extends IService<GenTable> {

    /**
     * 查询据库列表
     *
     * @param dto 查询条件
     * @return 数据库表集合
     */
    Page<GenDbListVO> getDbList(GenDbListDTO dto);
}
