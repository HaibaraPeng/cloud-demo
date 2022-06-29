package com.roc.demo.modules.generate.utils;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSON;
import com.roc.demo.common.core.constant.GenerateConstants;
import com.roc.demo.modules.generate.domain.TableColumnDO;
import com.roc.demo.modules.generate.domain.TableDO;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @Description 模板工具类
 * @Author dongp
 * @Date 2022/6/28 0028 14:43
 */
public class VelocityUtils {

    /**
     * 项目空间路径
     */
    private static final String PROJECT_PATH = "main/java";

    /**
     * mybatis空间路径
     */
    private static final String MYBATIS_PATH = "main/resources/mapper";

    /**
     * 默认上级菜单，系统工具
     */
    private static final String DEFAULT_PARENT_MENU_ID = "3";

    /**
     * 设置模板变量信息
     *
     * @return 模板列表
     */
    public static VelocityContext prepareContext(TableDO table) {
        String moduleName = table.getModuleName();
        String businessName = table.getBusinessName();
        String packageName = table.getPackageName();
        String tplCategory = table.getTplCategory();
        String functionName = table.getFunctionName();

        VelocityContext velocityContext = new VelocityContext();
        velocityContext.put("tplCategory", table.getTplCategory());
        velocityContext.put("tableName", table.getTableName());
        velocityContext.put("functionName", StrUtil.isNotBlank(functionName) ? functionName : "【请填写功能名称】");
        velocityContext.put("ClassName", table.getClassName());
        velocityContext.put("className", StringUtils.uncapitalize(table.getClassName()));
        velocityContext.put("moduleName", table.getModuleName());
        velocityContext.put("BusinessName", StringUtils.capitalize(table.getBusinessName()));
        velocityContext.put("businessName", table.getBusinessName());
        velocityContext.put("basePackage", getPackagePrefix(packageName));
        velocityContext.put("packageName", packageName);
        velocityContext.put("author", table.getFunctionAuthor());
        velocityContext.put("datetime", DateUtil.now());
        velocityContext.put("pkColumn", table.getPkColumn());
        velocityContext.put("importList", getImportList(table));
        velocityContext.put("permissionPrefix", getPermissionPrefix(moduleName, businessName));
        velocityContext.put("columns", table.getColumns());
        velocityContext.put("table", table);
        velocityContext.put("dicts", getDicts(table));
        setMenuVelocityContext(velocityContext, table);
        if (GenerateConstants.TPL_TREE.equals(tplCategory)) {
            setTreeVelocityContext(velocityContext, table);
        }
        if (GenerateConstants.TPL_SUB.equals(tplCategory)) {
            setSubVelocityContext(velocityContext, table);
        }
        return velocityContext;
    }

    /**
     * 获取包前缀
     *
     * @param packageName 包名称
     * @return 包前缀名称
     */
    public static String getPackagePrefix(String packageName) {
        int lastIndex = packageName.lastIndexOf(".");
        return StrUtil.sub(packageName, 0, lastIndex);
    }

    /**
     * 根据列类型获取导入包
     *
     * @param table
     * @return 返回需要导入的包列表
     */
    public static HashSet<String> getImportList(TableDO table) {
        List<TableColumnDO> columns = table.getColumns();
        TableDO subTable = table.getSubTable();
        HashSet<String> importList = new HashSet<>();
        if (subTable != null) {
            importList.add("java.util.List");
        }
        for (TableColumnDO column : columns) {
            if (!column.isSuperColumn() && GenerateConstants.TYPE_DATE.equals(column.getJavaType())) {
                importList.add("java.util.Date");
                importList.add("com.fasterxml.jackson.annotation.JsonFormat");
            } else if (!column.isSuperColumn() && GenerateConstants.TYPE_BIGDECIMAL.equals(column.getJavaType())) {
                importList.add("java.math.BigDecimal");
            }
        }
        return importList;
    }

    /**
     * 获取权限前缀
     *
     * @param moduleName   模块名称
     * @param businessName 业务名称
     * @return 返回权限前缀
     */
    public static String getPermissionPrefix(String moduleName, String businessName) {
        return StrUtil.format("{}:{}", moduleName, businessName);
    }

    /**
     * 根据列类型获取字典组
     *
     * @param table
     * @return 返回字典组
     */
    public static String getDicts(TableDO table) {
        List<TableColumnDO> columns = table.getColumns();
        Set<String> dicts = new HashSet<>();
        addDicts(dicts, columns);
        if (table.getSubTable() != null) {
            List<TableColumnDO> subColumns = table.getSubTable().getColumns();
            addDicts(dicts, subColumns);
        }
        return StringUtils.join(dicts, ", ");
    }

    /**
     * 添加字典列表
     *
     * @param dicts   字典列表
     * @param columns 列集合
     */
    public static void addDicts(Set<String> dicts, List<TableColumnDO> columns) {
        for (TableColumnDO column : columns) {
            if (!column.isSuperColumn() && StringUtils.isNotEmpty(column.getDictType()) && StringUtils.equalsAny(
                    column.getHtmlType(),
                    new String[]{GenerateConstants.HTML_SELECT, GenerateConstants.HTML_RADIO, GenerateConstants.HTML_CHECKBOX})) {
                dicts.add("'" + column.getDictType() + "'");
            }
        }
    }

    public static void setMenuVelocityContext(VelocityContext context, TableDO table) {
        String options = table.getOptions();
        JSONObject paramsObj = JSON.parseObject(options);
        String parentMenuId = getParentMenuId(paramsObj);
        context.put("parentMenuId", parentMenuId);
    }

    /**
     * 获取上级菜单ID字段
     *
     * @param paramsObj 生成其他选项
     * @return 上级菜单ID字段
     */
    public static String getParentMenuId(JSONObject paramsObj) {
        if (MapUtil.isNotEmpty(paramsObj) && paramsObj.containsKey(GenerateConstants.PARENT_MENU_ID)
                && StringUtils.isNotEmpty(paramsObj.getString(GenerateConstants.PARENT_MENU_ID))) {
            return paramsObj.getString(GenerateConstants.PARENT_MENU_ID);
        }
        return DEFAULT_PARENT_MENU_ID;
    }

    public static void setTreeVelocityContext(VelocityContext context, TableDO table) {
        String options = table.getOptions();
        JSONObject paramsObj = JSON.parseObject(options);
        String treeCode = getTreecode(paramsObj);
        String treeParentCode = getTreeParentCode(paramsObj);
        String treeName = getTreeName(paramsObj);

        context.put("treeCode", treeCode);
        context.put("treeParentCode", treeParentCode);
        context.put("treeName", treeName);
        context.put("expandColumn", getExpandColumn(table));
        if (paramsObj.containsKey(GenerateConstants.TREE_PARENT_CODE)) {
            context.put("tree_parent_code", paramsObj.getString(GenerateConstants.TREE_PARENT_CODE));
        }
        if (paramsObj.containsKey(GenerateConstants.TREE_NAME)) {
            context.put("tree_name", paramsObj.getString(GenerateConstants.TREE_NAME));
        }
    }

    /**
     * 获取树编码
     *
     * @param paramsObj 生成其他选项
     * @return 树编码
     */
    public static String getTreecode(JSONObject paramsObj) {
        if (paramsObj.containsKey(GenerateConstants.TREE_CODE)) {
            return StrUtil.toCamelCase(paramsObj.getString(GenerateConstants.TREE_CODE));
        }
        return StrUtil.EMPTY;
    }

    /**
     * 获取树父编码
     *
     * @param paramsObj 生成其他选项
     * @return 树父编码
     */
    public static String getTreeParentCode(JSONObject paramsObj) {
        if (paramsObj.containsKey(GenerateConstants.TREE_PARENT_CODE)) {
            return StrUtil.toCamelCase(paramsObj.getString(GenerateConstants.TREE_PARENT_CODE));
        }
        return StrUtil.EMPTY;
    }

    /**
     * 获取树名称
     *
     * @param paramsObj 生成其他选项
     * @return 树名称
     */
    public static String getTreeName(JSONObject paramsObj) {
        if (paramsObj.containsKey(GenerateConstants.TREE_NAME)) {
            return StrUtil.toCamelCase(paramsObj.getString(GenerateConstants.TREE_NAME));
        }
        return StrUtil.EMPTY;
    }

    /**
     * 获取需要在哪一列上面显示展开按钮
     *
     * @param table 业务表对象
     * @return 展开按钮列序号
     */
    public static int getExpandColumn(TableDO table) {
        String options = table.getOptions();
        JSONObject paramsObj = JSON.parseObject(options);
        String treeName = paramsObj.getString(GenerateConstants.TREE_NAME);
        int num = 0;
        for (TableColumnDO column : table.getColumns()) {
            if (column.getIsList()) {
                num++;
                String columnName = column.getColumnName();
                if (columnName.equals(treeName)) {
                    break;
                }
            }
        }
        return num;
    }

    public static void setSubVelocityContext(VelocityContext context, TableDO table) {
        TableDO subTable = table.getSubTable();
        String subTableName = table.getSubTableName();
        String subTableFkName = table.getSubTableFkName();
        String subClassName = table.getSubTable().getClassName();
        String subTableFkClassName = StrUtil.toCamelCase(subTableFkName);

        context.put("subTable", subTable);
        context.put("subTableName", subTableName);
        context.put("subTableFkName", subTableFkName);
        context.put("subTableFkClassName", subTableFkClassName);
        context.put("subTableFkclassName", StringUtils.uncapitalize(subTableFkClassName));
        context.put("subClassName", subClassName);
        context.put("subclassName", StringUtils.uncapitalize(subClassName));
        context.put("subImportList", getImportList(table.getSubTable()));
    }

    /**
     * 获取模板信息
     *
     * @return 模板列表
     */
    public static List<String> getTemplateList(String tplCategory) {
        List<String> templates = new ArrayList<String>();
        templates.add("vm/java/domain.java.vm");
        templates.add("vm/java/mapper.java.vm");
        templates.add("vm/java/service.java.vm");
        templates.add("vm/java/serviceImpl.java.vm");
        templates.add("vm/java/controller.java.vm");
        templates.add("vm/xml/mapper.xml.vm");
        templates.add("vm/sql/sql.vm");
        templates.add("vm/js/api.js.vm");
        if (GenerateConstants.TPL_CRUD.equals(tplCategory)) {
            templates.add("vm/vue/index.vue.vm");
        } else if (GenerateConstants.TPL_TREE.equals(tplCategory)) {
            templates.add("vm/vue/index-tree.vue.vm");
        } else if (GenerateConstants.TPL_SUB.equals(tplCategory)) {
            templates.add("vm/vue/index.vue.vm");
            templates.add("vm/java/sub-domain.java.vm");
        }
        return templates;
    }

    /**
     * 获取文件名
     */
    public static String getFileName(String template, TableDO table) {
        // 文件名称
        String fileName = "";
        // 包路径
        String packageName = table.getPackageName();
        // 模块名
        String moduleName = table.getModuleName();
        // 大写类名
        String className = table.getClassName();
        // 业务名称
        String businessName = table.getBusinessName();

        String javaPath = PROJECT_PATH + "/" + StringUtils.replace(packageName, ".", "/");
        String mybatisPath = MYBATIS_PATH + "/" + moduleName;
        String vuePath = "vue";

        if (template.contains("domain.java.vm")) {
            fileName = StrUtil.format("{}/domain/{}.java", javaPath, className);
        }
        if (template.contains("sub-domain.java.vm") && StringUtils.equals(GenerateConstants.TPL_SUB, table.getTplCategory())) {
            fileName = StrUtil.format("{}/domain/{}.java", javaPath, table.getSubTable().getClassName());
        } else if (template.contains("mapper.java.vm")) {
            fileName = StrUtil.format("{}/mapper/{}Mapper.java", javaPath, className);
        } else if (template.contains("service.java.vm")) {
            fileName = StrUtil.format("{}/service/I{}Service.java", javaPath, className);
        } else if (template.contains("serviceImpl.java.vm")) {
            fileName = StrUtil.format("{}/service/impl/{}ServiceImpl.java", javaPath, className);
        } else if (template.contains("controller.java.vm")) {
            fileName = StrUtil.format("{}/controller/{}Controller.java", javaPath, className);
        } else if (template.contains("mapper.xml.vm")) {
            fileName = StrUtil.format("{}/{}Mapper.xml", mybatisPath, className);
        } else if (template.contains("sql.vm")) {
            fileName = businessName + "Menu.sql";
        } else if (template.contains("api.js.vm")) {
            fileName = StrUtil.format("{}/api/{}/{}.js", vuePath, moduleName, businessName);
        } else if (template.contains("index.vue.vm")) {
            fileName = StrUtil.format("{}/views/{}/{}/index.vue", vuePath, moduleName, businessName);
        } else if (template.contains("index-tree.vue.vm")) {
            fileName = StrUtil.format("{}/views/{}/{}/index.vue", vuePath, moduleName, businessName);
        }
        return fileName;
    }
}
