package org.mybatis.generator.config;

public class JoinTarget {

	private String rightTable;

	private String fieldName;

	private String joinColumn;

	private JoinType type;

	public enum JoinType {

		ONE, MORE,;

	}

	public JoinTarget() {
	}

	public JoinTarget(String rightTable, String fieldName, String joinColumn, JoinType type) {
		this.rightTable = rightTable;
		this.fieldName = fieldName;
		this.joinColumn = joinColumn;
		this.type = type;
	}

	public void validate() {
		if (isEmpty(rightTable)) {
			throw new RuntimeException("The right table participating in join operation cannot be empty");
		}
		if (isEmpty(fieldName)) {
			throw new RuntimeException("The filed name of join result cannot be empty");
		}
		if (isEmpty(joinColumn)) {
			throw new RuntimeException("The column of right table participating in join operation cannot be empty");
		}
		if (type == null) {
			throw new RuntimeException("Join type[ONE/MORE] cannot be empty");
		}
	}

	private boolean isEmpty(String str) {
		return str == null || str.trim().isEmpty();
	}

	public String getRightTable() {
		return rightTable;
	}

	public String getFieldName() {
		return fieldName;
	}

	public String getJoinColumn() {
		return joinColumn;
	}

	public JoinType getType() {
		return type;
	}

}
