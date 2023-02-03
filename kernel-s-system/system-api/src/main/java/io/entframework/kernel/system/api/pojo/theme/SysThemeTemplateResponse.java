package io.entframework.kernel.system.api.pojo.theme;

import io.entframework.kernel.db.api.pojo.response.BaseResponse;
import io.entframework.kernel.rule.annotation.ChineseDescription;
import io.entframework.kernel.rule.enums.YesOrNotEnum;
import io.entframework.kernel.system.api.enums.ThemeFieldTypeEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 系统主题-模板 服务响应类
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SysThemeTemplateResponse extends BaseResponse {
    /**
     * 主键
     */
    @ChineseDescription("主键")
    private Long templateId;

    /**
     * 主题名称
     */
    @ChineseDescription("主题名称")
    private String templateName;

    /**
     * 主题编码
     */
    @ChineseDescription("主题编码")
    private String templateCode;

    /**
     * 主题类型：1-系统类型，2-业务类型
     */
    @ChineseDescription("主题类型：1-系统类型，2-业务类型")
    private ThemeFieldTypeEnum templateType;

    /**
     * 启用状态：Y-启用，N-禁用
     */
    @ChineseDescription("启用状态：Y-启用，N-禁用")
    private YesOrNotEnum statusFlag;
}