package org.mybatis.dynamic.sql.relation;

import org.mybatis.dynamic.sql.BasicColumn;

public class JoinDetail {
    private final BasicColumn leftTableJoinColumn;
    private final Class<?> rightJoinTable;
    private final BasicColumn rightTableJoinColumn;

    private JoinDetail(BasicColumn leftTableJoinColumn, Class<?> rightJoinTable, BasicColumn rightTableJoinColumn) {
        this.leftTableJoinColumn = leftTableJoinColumn;
        this.rightJoinTable = rightJoinTable;
        this.rightTableJoinColumn = rightTableJoinColumn;
    }

    public static JoinDetail of(BasicColumn leftTableJoinColumn, Class<?> rightJoinTable, BasicColumn rightTableJoinColumn) {
        return JoinDetail.builder()
                .leftTableJoinColumn(leftTableJoinColumn)
                .rightJoinTable(rightJoinTable)
                .rightTableJoinColumn(rightTableJoinColumn)
                .build();
    }

    public BasicColumn getLeftTableJoinColumn() {
        return leftTableJoinColumn;
    }

    public Class<?> getRightJoinTable() {
        return rightJoinTable;
    }

    public BasicColumn getRightTableJoinColumn() {
        return rightTableJoinColumn;
    }

    public static JoinDetailBuilder builder() {
        return new JoinDetailBuilder();
    }

    private static class JoinDetailBuilder {
        private BasicColumn leftTableJoinColumn;
        private Class<?> rightJoinTable;
        private BasicColumn rightTableJoinColumn;

        JoinDetailBuilder() {
        }

        public JoinDetailBuilder leftTableJoinColumn(BasicColumn leftTableJoinColumn) {
            this.leftTableJoinColumn = leftTableJoinColumn;
            return this;
        }

        public JoinDetailBuilder rightJoinTable(Class<?> rightJoinTable) {
            this.rightJoinTable = rightJoinTable;
            return this;
        }

        public JoinDetailBuilder rightTableJoinColumn(BasicColumn rightTableJoinColumn) {
            this.rightTableJoinColumn = rightTableJoinColumn;
            return this;
        }

        public JoinDetail build() {
            return new JoinDetail(this.leftTableJoinColumn, this.rightJoinTable, this.rightTableJoinColumn);
        }

        public String toString() {
            return "JoinDetail.JoinDetailBuilder(leftTableJoinColumn=" + this.leftTableJoinColumn + ", rightJoinTable=" + this.rightJoinTable + ", rightTableJoinColumn=" + this.rightTableJoinColumn + ")";
        }
    }
}
