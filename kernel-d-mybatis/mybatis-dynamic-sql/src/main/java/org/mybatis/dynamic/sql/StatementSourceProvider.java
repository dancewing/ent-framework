package org.mybatis.dynamic.sql;

public interface StatementSourceProvider<S> {
    S getSource();
}
