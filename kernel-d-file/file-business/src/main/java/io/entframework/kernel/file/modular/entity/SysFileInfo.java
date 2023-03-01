package io.entframework.kernel.file.modular.entity;

import io.entframework.kernel.db.api.annotation.LogicDelete;
import io.entframework.kernel.db.api.pojo.entity.BaseEntity;
import io.entframework.kernel.file.api.enums.FileStatusEnum;
import io.entframework.kernel.file.api.enums.FileStorageEnum;
import io.entframework.kernel.rule.enums.YesOrNotEnum;
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
 * 文件信息
 */
@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(value = "sys_file_info", sqlSupport = SysFileInfoDynamicSqlSupport.class, tableProperty = "sysFileInfo")
public class SysFileInfo extends BaseEntity {

    /**
     * 文件主键id
     */
    @Id
    @Column(name = "file_id", jdbcType = JDBCType.BIGINT)
    private Long fileId;

    /**
     * 文件编码，本号升级的依据，解决一个文件多个版本问题，多次上传文件编码不变
     */
    @Column(name = "file_code", jdbcType = JDBCType.BIGINT)
    private Long fileCode;

    /**
     * 文件版本，从1开始
     */
    @Column(name = "file_version", jdbcType = JDBCType.INTEGER)
    private Integer fileVersion;

    /**
     * 当前状态：0-历史版,1-最新版
     */
    @Column(name = "file_status", jdbcType = JDBCType.CHAR)
    private FileStatusEnum fileStatus;

    /**
     * 文件存储位置：1-阿里云，2-腾讯云，3-minio，4-本地
     */
    @Column(name = "file_location", jdbcType = JDBCType.TINYINT)
    private FileStorageEnum fileLocation;

    /**
     * 文件仓库（文件夹）
     */
    @Column(name = "file_bucket", jdbcType = JDBCType.VARCHAR)
    private String fileBucket;

    /**
     * 文件名称（上传时候的文件全名）
     */
    @Column(name = "file_origin_name", jdbcType = JDBCType.VARCHAR)
    private String fileOriginName;

    /**
     * 文件后缀，例如.txt
     */
    @Column(name = "file_suffix", jdbcType = JDBCType.VARCHAR)
    private String fileSuffix;

    /**
     * 文件大小kb为单位
     */
    @Column(name = "file_size_kb", jdbcType = JDBCType.BIGINT)
    private Long fileSizeKb;

    /**
     * 文件大小信息，计算后的
     */
    @Column(name = "file_size_info", jdbcType = JDBCType.VARCHAR)
    private String fileSizeInfo;

    /**
     * 存储到bucket中的名称，主键id+.后缀
     */
    @Column(name = "file_object_name", jdbcType = JDBCType.VARCHAR)
    private String fileObjectName;

    /**
     * 存储路径
     */
    @Column(name = "file_path", jdbcType = JDBCType.VARCHAR)
    private String filePath;

    /**
     * 是否为机密文件，Y-是机密，N-不是机密
     */
    @Column(name = "secret_flag", jdbcType = JDBCType.CHAR)
    private YesOrNotEnum secretFlag;

    /**
     * 是否删除：Y-被删除，N-未删除
     */
    @Column(name = "del_flag", jdbcType = JDBCType.CHAR)
    @LogicDelete
    private YesOrNotEnum delFlag;

    public SysFileInfo fileId(Long fileId) {
        this.fileId = fileId;
        return this;
    }

    public SysFileInfo fileCode(Long fileCode) {
        this.fileCode = fileCode;
        return this;
    }

    public SysFileInfo fileVersion(Integer fileVersion) {
        this.fileVersion = fileVersion;
        return this;
    }

    public SysFileInfo fileStatus(FileStatusEnum fileStatus) {
        this.fileStatus = fileStatus;
        return this;
    }

    public SysFileInfo fileLocation(FileStorageEnum fileLocation) {
        this.fileLocation = fileLocation;
        return this;
    }

    public SysFileInfo fileBucket(String fileBucket) {
        this.fileBucket = fileBucket;
        return this;
    }

    public SysFileInfo fileOriginName(String fileOriginName) {
        this.fileOriginName = fileOriginName;
        return this;
    }

    public SysFileInfo fileSuffix(String fileSuffix) {
        this.fileSuffix = fileSuffix;
        return this;
    }

    public SysFileInfo fileSizeKb(Long fileSizeKb) {
        this.fileSizeKb = fileSizeKb;
        return this;
    }

    public SysFileInfo fileSizeInfo(String fileSizeInfo) {
        this.fileSizeInfo = fileSizeInfo;
        return this;
    }

    public SysFileInfo fileObjectName(String fileObjectName) {
        this.fileObjectName = fileObjectName;
        return this;
    }

    public SysFileInfo filePath(String filePath) {
        this.filePath = filePath;
        return this;
    }

    public SysFileInfo secretFlag(YesOrNotEnum secretFlag) {
        this.secretFlag = secretFlag;
        return this;
    }

    public SysFileInfo delFlag(YesOrNotEnum delFlag) {
        this.delFlag = delFlag;
        return this;
    }

}