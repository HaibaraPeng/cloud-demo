package com.roc.demo.common.core.base;

import cn.hutool.core.date.DatePattern;
import com.alibaba.fastjson2.annotation.JSONField;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;

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

    /**
     * 创建开始时间
     */
    @JSONField(format = DatePattern.NORM_DATE_PATTERN)
    private Date createStartDate;

    /**
     * 创建结束时间
     */
    @JSONField(format = DatePattern.NORM_DATE_PATTERN)
    private Date createEndDate;
}
