package io.entframework.kernel.sms.modular.pojo.request;

import io.entframework.kernel.rule.annotation.ChineseDescription;
import io.entframework.kernel.rule.pojo.request.BaseRequest;
import io.entframework.kernel.sms.modular.enums.SmsSendSourceEnum;
import io.entframework.kernel.sms.modular.enums.SmsSendStatusEnum;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * 短信发送记录 服务请求类
 */
@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
public class SysSmsRequest extends BaseRequest {
    /**
     * 主键
     */
    @NotNull(message = "主键不能为空", groups = {update.class, delete.class, detail.class, updateStatus.class})
    @ChineseDescription("主键")
    private Long smsId;

    /**
     * 手机号
     */
    @ChineseDescription("手机号")
    private String phoneNumber;

    /**
     * 短信验证码
     */
    @ChineseDescription("短信验证码")
    private String validateCode;

    /**
     * 短信模板编号
     */
    @ChineseDescription("短信模板编号")
    private String templateCode;

    /**
     * 业务id
     */
    @ChineseDescription("业务id")
    private String bizId;

    /**
     * 发送状态：1-未发送，2-发送成功，3-发送失败，4-失效
     */
    @ChineseDescription("发送状态：1-未发送，2-发送成功，3-发送失败，4-失效")
    private SmsSendStatusEnum statusFlag;

    /**
     * 来源：1-app，2-pc，3-其他
     */
    @ChineseDescription("来源：1-app，2-pc，3-其他")
    private SmsSendSourceEnum source;

    /**
     * 短信失效截止时间
     */
    @ChineseDescription("短信失效截止时间")
    private LocalDateTime invalidTime;

    @NotNull(message = "ID集合不能为空", groups = {batchDelete.class})
    @ChineseDescription("ID集合")
    private java.util.List<Long> smsIds;
}