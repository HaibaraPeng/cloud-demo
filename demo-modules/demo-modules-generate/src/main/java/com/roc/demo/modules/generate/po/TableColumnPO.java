package com.roc.demo.modules.generate.po;

import com.roc.demo.common.core.base.BasePO;
import lombok.Getter;
import lombok.Setter;

/**
 * @Description 业务表 gen_table_column
 * @Author dongp
 * @Date 2022/6/27 0027 14:00
 */
@Getter
@Setter
public class TableColumnPO extends BasePO {

    private static final long serialVersionUID = 1L;

    /**
     * 编号
     */
    private Long columnId;

    /**
     * 归属表编号
     */
    private Long tableId;

    /**
     * 列名称
     */
    private String columnName;

    /**
     * 列描述
     */
    private String columnComment;

    /**
     * 列类型
     */
    private String columnType;

    /**
     * JAVA类型
     */
    private String javaType;

    /**
     * JAVA字段名
     */
    private String javaField;

    /**
     * 是否主键（1是）
     */
    private Boolean isPk;

    /**
     * 是否自增（1是）
     */
    private Boolean isIncrement;

    /**
     * 是否必填（1是）
     */
    private Boolean isRequired;

    /**
     * 是否为插入字段（1是）
     */
    private Boolean isInsert;

    /**
     * 是否编辑字段（1是）
     */
    private Boolean isEdit;

    /**
     * 是否列表字段（1是）
     */
    private Boolean isList;

    /**
     * 是否查询字段（1是）
     */
    private Boolean isQuery;

    /**
     * 查询方式（EQ等于、NE不等于、GT大于、LT小于、LIKE模糊、BETWEEN范围）
     */
    private String queryType;

    /**
     * 显示类型（input文本框、textarea文本域、select下拉框、checkbox复选框、radio单选框、datetime日期控件、image图片上传控件、upload文件上传控件、editor富文本控件）
     */
    private String htmlType;

    /**
     * 字典类型
     */
    private String dictType;

    /**
     * 排序
     */
    private Integer sort;

}
