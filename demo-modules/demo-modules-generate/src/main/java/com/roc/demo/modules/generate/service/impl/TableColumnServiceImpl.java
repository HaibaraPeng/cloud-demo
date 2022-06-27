package com.roc.demo.modules.generate.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.roc.demo.common.core.base.BaseException;
import com.roc.demo.common.core.dto.generate.db.DbListDTO;
import com.roc.demo.common.core.dto.generate.table.TableImportDTO;
import com.roc.demo.common.core.vo.generate.db.DbListVO;
import com.roc.demo.modules.generate.domain.Table;
import com.roc.demo.modules.generate.domain.TableColumn;
import com.roc.demo.modules.generate.mapper.TableColumnMapper;
import com.roc.demo.modules.generate.mapper.TableMapper;
import com.roc.demo.modules.generate.service.TableColumnService;
import com.roc.demo.modules.generate.service.TableService;
import com.roc.demo.modules.generate.utils.GenerateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description TableColumnServiceImpl 服务实现类
 * @Author dongp
 * @Date 2022/6/27 0027 14:29
 */
@Service
public class TableColumnServiceImpl extends ServiceImpl<TableColumnMapper, TableColumn> implements TableColumnService {

    @Override
    public List<TableColumn> selectDbTableColumnsByName(String tableName) {
        return this.baseMapper.selectDbTableColumnsByName(tableName);
    }
}
