package com.roc.demo.common.core.base;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

/**
 * @Description BaseDTO
 * @Author dongp
 * @Date 2022/4/22 0022 18:47
 */
@Setter
@Getter
public class BaseDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * pageSize
     */
    private Long pageSize = 10L;

    /**
     * pageNum
     */
    private Long pageNum = 1L;

    /**
     * 查询关键字
     */
    private String searchKey;

    /**
     * 排序字段列表
     */
    private String orderByColumn;

    /**
     * 是否正序
     */
    private Boolean asc = false;
}
