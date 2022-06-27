package com.roc.demo.modules.generate.utils;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ReUtil;
import cn.hutool.core.util.StrUtil;
import com.roc.demo.common.core.constant.GenerateConstants;
import com.roc.demo.common.core.dto.generate.table.TableImportDTO;
import com.roc.demo.modules.generate.config.GenerateConfig;
import com.roc.demo.modules.generate.domain.Table;
import com.roc.demo.modules.generate.domain.TableColumn;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

/**
 * @Description 代码生成器 工具类
 * @Author dongp
 * @Date 2022/6/27 0027 16:51
 */
@Component
public class GenerateUtils {

    @Autowired
    private GenerateConfig generateConfig;

    /**
     * 初始化表信息
     */
    public Table initTable(TableImportDTO dto, String userId) {
        Table table = BeanUtil.copyProperties(dto, Table.class);
        table.setClassName(convertClassName(dto.getTableName()));
        table.setPackageName(generateConfig.getPackageName());
        table.setModuleName(getModuleName(generateConfig.getPackageName()));
        table.setBusinessName(getBusinessName(dto.getTableName()));
        table.setFunctionName(replaceText(dto.getTableComment()));
        table.setFunctionAuthor(generateConfig.getAuthor());
        table.setCreateBy(userId);
        return table;
    }

    /**
     * 表名转换成Java类名
     *
     * @param tableName 表名称
     * @return 类名
     */
    public String convertClassName(String tableName) {
        Boolean autoRemovePre = generateConfig.getAutoRemovePre();
        String tablePrefix = generateConfig.getTablePrefix();
        if (autoRemovePre && StrUtil.isNotBlank(tablePrefix)) {
            List<String> searchList = StrUtil.split(tablePrefix, ",");
            tableName = replaceFirst(tableName, searchList);
        }
        return StrUtil.toCamelCase(tableName);
    }

    /**
     * 批量替换前缀
     *
     * @param replacement 替换值
     * @param searchList  替换列表
     * @return
     */
    public String replaceFirst(String replacement, List<String> searchList) {
        String text = replacement;
        for (String searchString : searchList) {
            if (replacement.startsWith(searchString)) {
                text = replacement.replaceFirst(searchString, "");
                break;
            }
        }
        return text;
    }

    /**
     * 获取模块名
     *
     * @param packageName 包名
     * @return 模块名
     */
    public String getModuleName(String packageName) {
        int lastIndex = packageName.lastIndexOf(".");
        int nameLength = packageName.length();
        return StrUtil.sub(packageName, lastIndex + 1, nameLength);
    }

    /**
     * 获取业务名
     *
     * @param tableName 表名
     * @return 业务名
     */
    public String getBusinessName(String tableName) {
        int lastIndex = tableName.lastIndexOf("_");
        int nameLength = tableName.length();
        return StrUtil.sub(tableName, lastIndex + 1, nameLength);
    }

    /**
     * 关键字替换
     *
     * @param text 需要被替换的名字
     * @return 替换后的名字
     */
    public static String replaceText(String text) {
        return ReUtil.replaceAll(text, "(?:表)", "");
    }

    /**
     * 初始化列属性字段
     */
    public void initColumnField(TableColumn column, Table table) {
        String dataType = getDbType(column.getColumnType());
        String columnName = column.getColumnName();
        column.setTableId(table.getTableId());
        column.setCreateBy(table.getCreateBy());
        // 设置java字段名
        column.setJavaField(StrUtil.toCamelCase(columnName));
        // 设置默认类型
        column.setJavaType(GenerateConstants.TYPE_STRING);
        column.setQueryType(GenerateConstants.QUERY_EQ);

        if (arraysContains(GenerateConstants.COLUMNTYPE_STR, dataType) || arraysContains(GenerateConstants.COLUMNTYPE_TEXT, dataType)) {
            // 字符串长度超过500设置为文本域
            Integer columnLength = getColumnLength(column.getColumnType());
            String htmlType = columnLength >= 500 || arraysContains(GenerateConstants.COLUMNTYPE_TEXT, dataType) ? GenerateConstants.HTML_TEXTAREA : GenConstants.HTML_INPUT;
            column.setHtmlType(htmlType);
        } else if (arraysContains(GenerateConstants.COLUMNTYPE_TIME, dataType)) {
            column.setJavaType(GenerateConstants.TYPE_DATE);
            column.setHtmlType(GenerateConstants.HTML_DATETIME);
        } else if (arraysContains(GenerateConstants.COLUMNTYPE_NUMBER, dataType)) {
            column.setHtmlType(GenerateConstants.HTML_INPUT);

            // 如果是浮点型 统一用BigDecimal
            List<String> str = StrUtil.split(StrUtil.subBetween(column.getColumnType(), "(", ")"), ",");
            if (str != null && str.size() == 2 && Integer.parseInt(str.get(1)) > 0) {
                column.setJavaType(GenerateConstants.TYPE_BIGDECIMAL);
            }
            // 如果是整形
            else if (str != null && str.size() == 1 && Integer.parseInt(str.get(0)) <= 10) {
                column.setJavaType(GenerateConstants.TYPE_INTEGER);
            }
            // 长整形
            else {
                column.setJavaType(GenerateConstants.TYPE_LONG);
            }
        }

        // 插入字段（默认所有字段都需要插入）
        column.setIsInsert(GenerateConstants.REQUIRE);

        // 编辑字段
        if (!arraysContains(GenerateConstants.COLUMNNAME_NOT_EDIT, columnName) && !column.isPk()) {
            column.setIsEdit(GenerateConstants.REQUIRE);
        }
        // 列表字段
        if (!arraysContains(GenConstants.COLUMNNAME_NOT_LIST, columnName) && !column.isPk()) {
            column.setIsList(GenConstants.REQUIRE);
        }
        // 查询字段
        if (!arraysContains(GenConstants.COLUMNNAME_NOT_QUERY, columnName) && !column.isPk()) {
            column.setIsQuery(GenConstants.REQUIRE);
        }

        // 查询字段类型
        if (StringUtils.endsWithIgnoreCase(columnName, "name")) {
            column.setQueryType(GenConstants.QUERY_LIKE);
        }
        // 状态字段设置单选框
        if (StringUtils.endsWithIgnoreCase(columnName, "status")) {
            column.setHtmlType(GenConstants.HTML_RADIO);
        }
        // 类型&性别字段设置下拉框
        else if (StringUtils.endsWithIgnoreCase(columnName, "type")
                || StringUtils.endsWithIgnoreCase(columnName, "sex")) {
            column.setHtmlType(GenConstants.HTML_SELECT);
        }
        // 图片字段设置图片上传控件
        else if (StringUtils.endsWithIgnoreCase(columnName, "image")) {
            column.setHtmlType(GenConstants.HTML_IMAGE_UPLOAD);
        }
        // 文件字段设置文件上传控件
        else if (StringUtils.endsWithIgnoreCase(columnName, "file")) {
            column.setHtmlType(GenConstants.HTML_FILE_UPLOAD);
        }
        // 内容字段设置富文本控件
        else if (StringUtils.endsWithIgnoreCase(columnName, "content")) {
            column.setHtmlType(GenConstants.HTML_EDITOR);
        }
    }

    /**
     * 获取数据库类型字段
     *
     * @param columnType 列类型
     * @return 截取后的列类型
     */
    public String getDbType(String columnType) {
        if (StrUtil.indexOf(columnType, '(') > 0) {
            return StrUtil.subBefore(columnType, '(', true);
        } else {
            return columnType;
        }
    }

    /**
     * 校验数组是否包含指定值
     *
     * @param arr         数组
     * @param targetValue 值
     * @return 是否包含
     */
    public static boolean arraysContains(String[] arr, String targetValue) {
        return Arrays.asList(arr).contains(targetValue);
    }

    /**
     * 获取字段长度
     *
     * @param columnType 列类型
     * @return 截取后的列类型
     */
    public static Integer getColumnLength(String columnType) {
        if (StrUtil.indexOf(columnType, '(') > 0) {
            String length = StrUtil.subBetween(columnType, "(", ")");
            return Integer.valueOf(length);
        } else {
            return 0;
        }
    }
}
