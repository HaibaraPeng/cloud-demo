package com.roc.demo.modules.generate.controller;

import com.roc.demo.modules.generate.service.TableService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @Description DbController
 * @Author penn
 * @Date 2022/6/26 10:37
 */
@RequestMapping("/db")
@RestController
public class DbController {

    @Autowired
    private TableService tableService;

    /**
     * 查询数据库列表
     */
//    @PostMapping(value = "/list")
//    public CommonResult<Page<DbListVO>> list(@RequestBody DbListDTO dto) {
//        Page<DbListVO> page = tableService.getDbList(dto);
//        return new CommonResult<Page<DbListVO>>().success().data(page);
//    }

}
