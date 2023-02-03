package io.entframework.kernel.file.modular.entity;

import io.entframework.kernel.db.api.annotation.Column;
import io.entframework.kernel.db.api.annotation.Id;
import io.entframework.kernel.db.api.annotation.Table;
import io.entframework.kernel.db.api.pojo.entity.BaseEntity;
import java.sql.JDBCType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * 文件存储信息
 */
@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Table("sys_file_storage")
public class SysFileStorage extends BaseEntity {
    /**
     * 文件主键id，关联file_info表的主键
     */
    @Id
    @Column(name = "file_id", jdbcType = JDBCType.BIGINT)
    private Long fileId;

    /**
     * 具体文件的字节信息
     */
    @Column(name = "file_bytes", jdbcType = JDBCType.LONGVARBINARY)
    private byte[] fileBytes;
}