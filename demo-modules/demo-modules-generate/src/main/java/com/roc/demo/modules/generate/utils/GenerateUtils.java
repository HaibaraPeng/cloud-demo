package com.roc.demo.modules.generate.utils;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ReUtil;
import cn.hutool.core.util.StrUtil;
import com.roc.demo.common.core.dto.generate.table.TableImportDTO;
import com.roc.demo.modules.generate.config.GenerateConfig;
import com.roc.demo.modules.generate.domain.Table;
import org.apache.commons.lang3.RegExUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

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
    public Table initTable(TableImportDTO.ItemDTO item, String userId) {
        Table table = BeanUtil.copyProperties(item, Table.class);
        table.setClassName(convertClassName(item.getTableName()));
        table.setPackageName(generateConfig.getPackageName());
        table.setModuleName(getModuleName(generateConfig.getPackageName()));
        table.setBusinessName(getBusinessName(item.getTableName()));
        table.setFunctionName(replaceText(item.getTableComment()));
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
}
