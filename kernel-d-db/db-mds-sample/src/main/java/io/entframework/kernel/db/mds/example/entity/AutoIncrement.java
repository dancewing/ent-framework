package io.entframework.kernel.db.mds.example.entity;

import io.entframework.kernel.db.api.pojo.entity.BaseEntity;
import java.io.Serializable;
import java.sql.JDBCType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.mybatis.dynamic.sql.annotation.Column;
import org.mybatis.dynamic.sql.annotation.Entity;
import org.mybatis.dynamic.sql.annotation.GeneratedValue;
import org.mybatis.dynamic.sql.annotation.Id;
import org.mybatis.dynamic.sql.annotation.Table;

/**
 * AutoTest
 */
@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(value = "exam_auto_increment", sqlSupport = AutoIncrementDynamicSqlSupport.class,
        tableProperty = "autoIncrement")
public class AutoIncrement extends BaseEntity implements Serializable {

    /**
     * Id
     */
    @Id
    @GeneratedValue
    @Column(name = "id", jdbcType = JDBCType.BIGINT)
    private Long id;

    /**
     * 姓名
     */
    @Column(name = "username", jdbcType = JDBCType.VARCHAR)
    private String username;

    private static final long serialVersionUID = 1L;

    public AutoIncrement id(Long id) {
        this.id = id;
        return this;
    }

    public AutoIncrement username(String username) {
        this.username = username;
        return this;
    }

}