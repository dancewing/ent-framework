package io.entframework.kernel.sms.modular.pojo.response;

import io.entframework.kernel.db.api.pojo.response.BaseResponse;
import io.entframework.kernel.rule.annotation.ChineseDescription;
import io.entframework.kernel.sms.modular.enums.SmsSendSourceEnum;
import io.entframework.kernel.sms.modular.enums.SmsSendStatusEnum;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * 短信发送记录 服务响应类
 */
@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
public class SysSmsResponse extends BaseResponse {

	/**
	 * 主键
	 */
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

}