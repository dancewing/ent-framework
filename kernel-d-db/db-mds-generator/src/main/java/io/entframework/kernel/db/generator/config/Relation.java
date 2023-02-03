package io.entframework.kernel.db.generator.config;

import lombok.Builder;
import lombok.Data;
import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.dom.java.Field;
import org.mybatis.generator.config.JoinTarget;

@Data
@Builder(builderClassName = "Builder")
public class Relation {

    private Field bindField;
    /**
     * 关系类型
     */
    private JoinTarget.JoinType joinType;
    /**
     * 源字段，只有在One时存在，如果关联字段为teacher, 则源字段teacher_id为source field
     */
    private Field sourceField;

    /**
     * 关联的目标表
     */
    private IntrospectedTable targetTable;

    /**
     * 关联的目标column
     */
    private IntrospectedColumn targetColumn;

    private String displayField;

}
