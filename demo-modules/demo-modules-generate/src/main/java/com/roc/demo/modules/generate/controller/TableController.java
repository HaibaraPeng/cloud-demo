package com.roc.demo.modules.generate.controller;

import com.roc.demo.common.core.api.CommonResult;
import com.roc.demo.common.core.dto.generate.table.TableImportDTO;
import com.roc.demo.common.core.validated.ValidatedList;
import com.roc.demo.modules.generate.service.TableService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.List;

/**
 * @Description TableController
 * @Author dongp
 * @Date 2022/6/27 0027 16:08
 */
@RequestMapping("/table")
@RestController
public class TableController {

    @Autowired
    private TableService tableService;

    /**
     * 导入表结构
     */
    @PostMapping("/import")
    public CommonResult importTable(@RequestBody @Validated ValidatedList<TableImportDTO> dto) {
        tableService.importTable(dto.getList());
        return new CommonResult().success();
    }
}
