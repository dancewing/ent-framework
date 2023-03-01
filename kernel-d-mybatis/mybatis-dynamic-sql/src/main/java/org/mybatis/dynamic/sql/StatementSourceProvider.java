package org.mybatis.dynamic.sql;

public interface StatementSourceProvider<S> extends StatementProvider {

    S getSource();

}
