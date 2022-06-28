package com.roc.demo.modules.generate.controller;

import cn.hutool.core.convert.Convert;
import com.roc.demo.common.core.api.CommonResult;
import com.roc.demo.common.core.dto.generate.table.TableBatchGenerateDTO;
import com.roc.demo.common.core.dto.generate.table.TableImportDTO;
import com.roc.demo.common.core.validated.ValidatedList;
import com.roc.demo.modules.generate.service.TableService;
import lombok.extern.java.Log;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

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

    /**
     * 生成代码（下载方式）
     */
    @GetMapping("/generateCode/{tableName}")
    public void generateCode(HttpServletResponse response, @PathVariable("tableName") String tableName) throws IOException {
        byte[] data = tableService.generateCode(tableName);
        genCode(response, data);
    }

    /**
     * 生成zip文件
     */
    private void genCode(HttpServletResponse response, byte[] data) throws IOException {
        response.reset();
        response.setHeader("Content-Disposition", "attachment; filename=\"ruoyi.zip\"");
        response.addHeader("Content-Length", "" + data.length);
        response.setContentType("application/octet-stream; charset=UTF-8");
        IOUtils.write(data, response.getOutputStream());
    }
}
