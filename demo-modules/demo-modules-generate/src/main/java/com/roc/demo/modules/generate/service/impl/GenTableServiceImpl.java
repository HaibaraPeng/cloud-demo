package com.roc.demo.modules.generate.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.roc.demo.common.core.dto.generate.db.GenDbListDTO;
import com.roc.demo.common.core.vo.generate.db.GenDbListVO;
import com.roc.demo.modules.generate.domain.GenTable;
import com.roc.demo.modules.generate.mapper.GenTableMapper;
import com.roc.demo.modules.generate.service.GenTableService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Description admin用户 服务实现类
 * @Author dongp
 * @Date 2022/6/27 0027 14:29
 */
@Service
public class GenTableServiceImpl extends ServiceImpl<GenTableMapper, GenTable> implements GenTableService {


    @Override
    public Page<GenDbListVO> getDbList(GenDbListDTO dto) {
        Page<GenDbListVO> page = new Page<>(dto.getPageNum(), dto.getPageSize());
        return this.baseMapper.selectDbTableList(page, dto);
    }
}
