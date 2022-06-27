package com.roc.demo.common.core.dto.generate.db;

import com.roc.demo.common.core.base.BaseDTO;
import lombok.Getter;
import lombok.Setter;

/**
 * @Description DbListDTO
 * @Author dongp
 * @Date 2022/6/27 0027 11:43
 */
@Getter
@Setter
public class DbListDTO extends BaseDTO {

    private static final long serialVersionUID = 1L;

    /**
     * 表名称
     */
    private String tableName;

    /**
     * 表描述
     */
    private String tableComment;

}
