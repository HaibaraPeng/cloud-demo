package com.roc.demo.modules.generate.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.roc.demo.common.core.api.CommonResult;
import com.roc.demo.common.core.dto.generate.db.GenDbListDTO;
import com.roc.demo.common.core.vo.generate.db.GenDbListVO;
import com.roc.demo.modules.generate.service.GenTableService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Description GenerateController
 * @Author penn
 * @Date 2022/6/26 10:37
 */
@RequestMapping("/db")
@RestController
public class GenerateController {

    @Autowired
    private GenTableService genTableService;

    /**
     * 查询数据库列表
     */
    @PostMapping(value = "/list")
    public CommonResult<Page<GenDbListVO>> list(GenDbListDTO dto) {
        Page<GenDbListVO> page = genTableService.getDbList(dto);
        return new CommonResult<Page<GenDbListVO>>().success().data(page);
    }

}
