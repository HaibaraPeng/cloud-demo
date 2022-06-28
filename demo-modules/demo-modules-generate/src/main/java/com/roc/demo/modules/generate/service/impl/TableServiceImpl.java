package com.roc.demo.modules.generate.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.io.IoUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.roc.demo.common.core.base.BaseException;
import com.roc.demo.common.core.constant.Constants;
import com.roc.demo.common.core.constant.GenerateConstants;
import com.roc.demo.common.core.dto.generate.db.DbListDTO;
import com.roc.demo.common.core.dto.generate.table.TableImportDTO;
import com.roc.demo.common.core.vo.generate.db.DbListVO;
import com.roc.demo.modules.generate.domain.TableDO;
import com.roc.demo.modules.generate.domain.TableColumnDO;
import com.roc.demo.modules.generate.mapper.TableMapper;
import com.roc.demo.modules.generate.po.TableColumnPO;
import com.roc.demo.modules.generate.po.TablePO;
import com.roc.demo.modules.generate.service.TableColumnService;
import com.roc.demo.modules.generate.service.TableService;
import com.roc.demo.modules.generate.utils.GenerateUtils;
import com.roc.demo.modules.generate.utils.VelocityInitializer;
import com.roc.demo.modules.generate.utils.VelocityUtils;
import org.apache.commons.io.IOUtils;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * @Description TableService 服务实现类
 * @Author dongp
 * @Date 2022/6/27 0027 14:29
 */
@Service
public class TableServiceImpl extends ServiceImpl<TableMapper, TablePO> implements TableService {

    @Autowired
    private GenerateUtils generateUtils;

    @Autowired
    private TableColumnService tableColumnService;


    @Override
    public Page<DbListVO> getDbList(DbListDTO dto) {
        Page<DbListVO> page = new Page<>(dto.getPageNum(), dto.getPageSize());
        return this.baseMapper.selectDbTableList(page, dto);
    }

    @Override
    public void importTable(List<TableImportDTO> dtoList) {
        // TODO 获取userId
//        String operateName = SecurityUtils.getUsername();
        String userId = "1";
        try {
            List<TableColumnPO> tableColumnList = new ArrayList<>();
            for (TableImportDTO dto : dtoList) {
                String tableName = dto.getTableName();
                TablePO table = generateUtils.initTable(dto, userId);
                boolean save = save(table);
                if (save) {
                    // 保存列信息
                    List<TableColumnPO> tableColumns = tableColumnService.selectDbTableColumnsByName(tableName);
                    for (TableColumnPO column : tableColumns) {
                        column.setTableId(table.getTableId());
                        generateUtils.initColumnField(column, table);
                        tableColumnList.add(column);
                    }
                }
            }
            tableColumnService.saveBatch(tableColumnList);
        } catch (Exception e) {
            throw new BaseException("导入失败：" + e.getMessage());
        }
    }

    @Override
    public byte[] generateCode(String tableName) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        ZipOutputStream zip = new ZipOutputStream(outputStream);
        generateCode(tableName, zip);
        IoUtil.close(zip);
        return outputStream.toByteArray();
    }

    /**
     * 查询表信息并生成代码
     */
    private void generateCode(String tableName, ZipOutputStream zip) {
        // 查询表信息
        TableDO table = getTableDO(tableName);
        // 设置主子表信息
        setSubTable(table);
        // 设置主键列信息
        setPkColumn(table);

        VelocityInitializer.initVelocity();

        VelocityContext context = VelocityUtils.prepareContext(table);

        // 获取模板列表
        List<String> templates = VelocityUtils.getTemplateList(table.getTplCategory());
        for (String template : templates) {
            // 渲染模板
            StringWriter sw = new StringWriter();
            Template tpl = Velocity.getTemplate(template, Constants.UTF8);
            tpl.merge(context, sw);
            try {
                // 添加到zip
                zip.putNextEntry(new ZipEntry(VelocityUtils.getFileName(template, table)));
                IOUtils.write(sw.toString(), zip, Constants.UTF8);
                IOUtils.closeQuietly(sw);
                zip.flush();
                zip.closeEntry();
            } catch (IOException e) {
                log.error("渲染模板失败，表名：" + table.getTableName(), e);
            }
        }
    }

    /**
     * 根据表名称获取业务表信息
     *
     * @param tableName
     * @return
     */
    private TableDO getTableDO(String tableName) {
        TableDO tableDO = null;
        // 查询表信息
        TablePO tablePO = getOne(Wrappers.<TablePO>lambdaQuery().eq(TablePO::getTableName, tableName));
        if (tablePO != null) {
            tableDO = BeanUtil.copyProperties(tablePO, TableDO.class);
            // 查询表列信息
            List<TableColumnDO> columns = tableColumnService
                    .list(Wrappers.<TableColumnPO>lambdaQuery().eq(TableColumnPO::getTableId, tableDO.getTableId()))
                    .stream()
                    .map(tableColumnPO -> BeanUtil.copyProperties(tableColumnPO, TableColumnDO.class))
                    .collect(Collectors.toList());
            tableDO.setColumns(columns);
        }
        return tableDO;
    }

    /**
     * 设置主子表信息
     *
     * @param table
     */
    private void setSubTable(TableDO table) {
        String subTableName = table.getSubTableName();
        if (StrUtil.isNotBlank(subTableName)) {
            TableDO subTable = getTableDO(subTableName);
            table.setSubTable(subTable);
        }
    }

    /**
     * 设置主键列信息
     *
     * @param tableDO
     */
    public void setPkColumn(TableDO tableDO) {
        for (TableColumnDO column : tableDO.getColumns()) {
            if (column.getIsPk()) {
                tableDO.setPkColumn(column);
                break;
            }
        }
        if (tableDO.getPkColumn() == null) {
            tableDO.setPkColumn(tableDO.getColumns().get(0));
        }
        if (GenerateConstants.TPL_SUB.equals(tableDO.getTplCategory())) {
            for (TableColumnDO column : tableDO.getSubTable().getColumns()) {
                if (column.getIsPk()) {
                    tableDO.getSubTable().setPkColumn(column);
                    break;
                }
            }
            if (tableDO.getSubTable().getPkColumn() == null) {
                tableDO.getSubTable().setPkColumn(tableDO.getSubTable().getColumns().get(0));
            }
        }
    }

}
