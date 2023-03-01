package io.entframework.kernel.file.modular.entity;

import io.entframework.kernel.db.api.pojo.entity.BaseEntity;
import java.sql.JDBCType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.mybatis.dynamic.sql.annotation.Column;
import org.mybatis.dynamic.sql.annotation.Entity;
import org.mybatis.dynamic.sql.annotation.Id;
import org.mybatis.dynamic.sql.annotation.Table;

/**
 * 文件存储信息
 */
@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(value = "sys_file_storage", sqlSupport = SysFileStorageDynamicSqlSupport.class, tableProperty = "sysFileStorage")
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

	public SysFileStorage fileId(Long fileId) {
		this.fileId = fileId;
		return this;
	}

	public SysFileStorage fileBytes(byte[] fileBytes) {
		this.fileBytes = fileBytes;
		return this;
	}

}