package com.roc.demo.modules.generate.mapper;

import com.roc.demo.common.core.dto.generate.db.DbListDTO;
import com.roc.demo.common.core.vo.generate.db.DbListVO;
import com.roc.demo.modules.generate.po.TablePO;
import org.springframework.data.domain.Page;

/**
 * @Description TableMapper 数据层
 * @Author dongp
 * @Date 2022/6/27 0027 14:30
 */
public interface TableMapper {

    /**
     * 查询据库列表
     *
     * @param page 分页条件
     * @param dto  查询条件
     * @return 数据库表集合
     */
    Page<DbListVO> selectDbTableList(Page<DbListVO> page, DbListDTO dto);


}
