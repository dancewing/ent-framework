package io.entframework.kernel.email.api.config;

import cn.hutool.extra.mail.MailAccount;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * 邮件相关的配置
 */
@Data
@ConfigurationProperties("kernel.email")
public class EmailProperties {

    private MailAccount account = new MailAccount();

}
