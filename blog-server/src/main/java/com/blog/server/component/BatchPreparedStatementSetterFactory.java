package com.blog.server.component;

import org.jetbrains.annotations.NotNull;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

/**
 * @author yanzhihao
 */
public class BatchPreparedStatementSetterFactory {

    public static BatchPreparedStatementSetter newInstance(List<Object[]> batchArgs) {
        return new BatchPreparedStatementSetter() {
            @Override
            public void setValues(@NotNull PreparedStatement preparedStatement, int i) throws SQLException {
                Object[] args = batchArgs.get(i);
                for (int j = 0; j < args.length; j++) {
                    preparedStatement.setObject(j + 1, args[j]);
                }
            }

            @Override
            public int getBatchSize() {
                return batchArgs.size();
            }
        };
    }
}
