package com.roc.demo.common.datasource.config;

import com.baomidou.dynamic.datasource.provider.AbstractJdbcDataSourceProvider;
import com.baomidou.dynamic.datasource.spring.boot.autoconfigure.DataSourceProperty;
import org.jasypt.encryption.StringEncryptor;

import java.sql.SQLException;
import java.sql.Statement;
import java.util.Map;

/**
 * @Description 从数据源中获取 配置信息
 * @Author dongp
 * @Date 2022/6/29 0029 15:33
 */
public class JdbcDynamicDataSourceProvider extends AbstractJdbcDataSourceProvider {

    private final DataSourceProperties properties;

    private final StringEncryptor stringEncryptor;

    public JdbcDynamicDataSourceProvider(StringEncryptor stringEncryptor, DataSourceProperties properties) {
        super(properties.getDriverClassName(), properties.getUrl(), properties.getUsername(), properties.getPassword());
        this.stringEncryptor = stringEncryptor;
        this.properties = properties;
    }

    @Override
    protected Map<String, DataSourceProperty> executeStmt(Statement statement) throws SQLException {
        return null;
    }
}
