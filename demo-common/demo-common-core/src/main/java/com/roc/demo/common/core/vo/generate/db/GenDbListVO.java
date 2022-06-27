package com.roc.demo.common.core.vo.generate.db;

import com.roc.demo.common.core.base.BaseVO;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * @Description GenDbListVO
 * @Author dongp
 * @Date 2022/6/27 0027 14:38
 */
@Getter
@Setter
public class GenDbListVO extends BaseVO {

    private static final long serialVersionUID = 1L;

    /**
     * 表名称
     */
    private String tableName;

    /**
     * 表描述
     */
    private String tableComment;

//    /**
//     * 创建时间
//     */
//    private LocalDateTime createTime;
//
//    /**
//     * 更新时间
//     */
//    private LocalDateTime updateTime;
}
