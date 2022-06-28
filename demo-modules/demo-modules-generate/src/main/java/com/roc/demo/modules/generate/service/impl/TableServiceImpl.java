package com.roc.demo.modules.generate.service.impl;

import com.alibaba.nacos.shaded.com.google.protobuf.ServiceException;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.roc.demo.common.core.base.BaseException;
import com.roc.demo.common.core.dto.generate.db.DbListDTO;
import com.roc.demo.common.core.dto.generate.table.TableImportDTO;
import com.roc.demo.common.core.vo.generate.db.DbListVO;
import com.roc.demo.modules.generate.domain.Table;
import com.roc.demo.modules.generate.domain.TableColumn;
import com.roc.demo.modules.generate.mapper.TableMapper;
import com.roc.demo.modules.generate.service.TableColumnService;
import com.roc.demo.modules.generate.service.TableService;
import com.roc.demo.modules.generate.utils.GenerateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description TableService 服务实现类
 * @Author dongp
 * @Date 2022/6/27 0027 14:29
 */
@Service
public class TableServiceImpl extends ServiceImpl<TableMapper, Table> implements TableService {

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
            List<TableColumn> tableColumnList = new ArrayList<>();
            for (TableImportDTO dto : dtoList) {
                String tableName = dto.getTableName();
                Table table = generateUtils.initTable(dto, userId);
                boolean save = save(table);
                if (save) {
                    // 保存列信息
                    List<TableColumn> tableColumns = tableColumnService.selectDbTableColumnsByName(tableName);
                    for (TableColumn column : tableColumns) {
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

}
