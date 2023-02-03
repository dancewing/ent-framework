package io.entframework.kernel.system.api.pojo.theme;

import io.entframework.kernel.db.api.pojo.response.BaseResponse;
import io.entframework.kernel.rule.annotation.ChineseDescription;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 系统主题-模板配置关联关系 服务响应类
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SysThemeTemplateRelResponse extends BaseResponse {
    /**
     * 主键
     */
    @ChineseDescription("主键")
    private Long relationId;

    /**
     * 模板主键id
     */
    @ChineseDescription("模板主键id")
    private Long templateId;

    /**
     * 属性编码
     */
    @ChineseDescription("属性编码")
    private String fieldCode;
}