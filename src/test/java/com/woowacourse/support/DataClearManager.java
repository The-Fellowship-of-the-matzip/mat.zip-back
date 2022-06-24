package com.woowacourse.support;

import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.sql.DataSource;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class DataClearManager implements InitializingBean {

    private static final String TRUNCATE_FORMAT = "TRUNCATE TABLE %s";
    private static final String ID_RESET_FORMAT = "ALTER TABLE %s ALTER COLUMN ID RESTART WITH 1";

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private DataSource dataSource;
    private List<String> tableNames;

    @Transactional
    public void clear() {
        truncate(tableNames);
    }

    private void truncate(final List<String> tableNames) {
        entityManager.flush();
        for (final String tableName : tableNames) {
            doTruncate(tableName);
        }
    }

    private void doTruncate(final String tableName) {
        if (Tables.hasName(tableName)) {
            executeTruncateQuery(tableName);
        }
    }

    private void executeTruncateQuery(String tableName) {
        entityManager.createNativeQuery(String.format(TRUNCATE_FORMAT, tableName)).executeUpdate();
        entityManager.createNativeQuery(String.format(ID_RESET_FORMAT, tableName)).executeUpdate();
    }

    @Override
    public void afterPropertiesSet() {
        tableNames = new ArrayList<>();
        try {
            final DatabaseMetaData metaData = dataSource.getConnection().getMetaData();
            final ResultSet tables = metaData.getTables(null, null, null, new String[]{"TABLE"});
            while (tables.next()) {
                tableNames.add(tables.getString("TABLE_NAME"));
            }
        } catch (SQLException e) {
            throw new RuntimeException();
        }
    }
}
