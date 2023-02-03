package io.entframework.kernel.sms.modular.entity;

import io.entframework.kernel.db.api.annotation.Column;
import io.entframework.kernel.db.api.annotation.Id;
import io.entframework.kernel.db.api.annotation.Table;
import io.entframework.kernel.db.api.pojo.entity.BaseEntity;
import io.entframework.kernel.sms.modular.enums.SmsSendSourceEnum;
import io.entframework.kernel.sms.modular.enums.SmsSendStatusEnum;
import java.sql.JDBCType;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * 短信发送记录
 */
@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Table("sys_sms")
public class SysSms extends BaseEntity {
    /**
     * 主键
     */
    @Id
    @Column(name = "sms_id", jdbcType = JDBCType.BIGINT)
    private Long smsId;

    /**
     * 手机号
     */
    @Column(name = "phone_number", jdbcType = JDBCType.VARCHAR)
    private String phoneNumber;

    /**
     * 短信验证码
     */
    @Column(name = "validate_code", jdbcType = JDBCType.VARCHAR)
    private String validateCode;

    /**
     * 短信模板编号
     */
    @Column(name = "template_code", jdbcType = JDBCType.VARCHAR)
    private String templateCode;

    /**
     * 业务id
     */
    @Column(name = "biz_id", jdbcType = JDBCType.VARCHAR)
    private String bizId;

    /**
     * 发送状态：1-未发送，2-发送成功，3-发送失败，4-失效
     */
    @Column(name = "status_flag", jdbcType = JDBCType.TINYINT)
    private SmsSendStatusEnum statusFlag;

    /**
     * 来源：1-app，2-pc，3-其他
     */
    @Column(name = "source", jdbcType = JDBCType.INTEGER)
    private SmsSendSourceEnum source;

    /**
     * 短信失效截止时间
     */
    @Column(name = "invalid_time", jdbcType = JDBCType.TIMESTAMP)
    private LocalDateTime invalidTime;
}