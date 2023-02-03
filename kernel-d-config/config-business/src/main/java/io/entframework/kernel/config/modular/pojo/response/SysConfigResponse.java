package io.entframework.kernel.config.modular.pojo.response;

import io.entframework.kernel.db.api.pojo.response.BaseResponse;
import io.entframework.kernel.rule.annotation.ChineseDescription;
import io.entframework.kernel.rule.enums.StatusEnum;
import io.entframework.kernel.rule.enums.YesOrNotEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * 参数配置 服务响应类
 */
@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
public class SysConfigResponse extends BaseResponse {
    /**
     * 主键
     */
    @ChineseDescription("主键")
    private Long configId;

    /**
     * 名称
     */
    @ChineseDescription("名称")
    private String configName;

    /**
     * 属性编码
     */
    @ChineseDescription("属性编码")
    private String configCode;

    /**
     * 属性值
     */
    @ChineseDescription("属性值")
    private String configValue;

    /**
     * 是否是系统参数：Y-是，N-否
     */
    @ChineseDescription("是否是系统参数：Y-是，N-否")
    private YesOrNotEnum sysFlag;

    /**
     * 备注
     */
    @ChineseDescription("备注")
    private String remark;

    /**
     * 状态：1-正常，2-停用
     */
    @ChineseDescription("状态：1-正常，2-停用")
    private StatusEnum statusFlag;

    /**
     * 常量所属分类的编码，来自于“常量的分类”字典
     */
    @ChineseDescription("常量所属分类的编码，来自于“常量的分类”字典")
    private String groupCode;
}