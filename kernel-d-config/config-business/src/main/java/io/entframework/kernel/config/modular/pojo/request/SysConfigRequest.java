package io.entframework.kernel.config.modular.pojo.request;

import io.entframework.kernel.rule.annotation.ChineseDescription;
import io.entframework.kernel.rule.enums.StatusEnum;
import io.entframework.kernel.rule.enums.YesOrNotEnum;
import io.entframework.kernel.rule.pojo.request.BaseRequest;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * 参数配置 服务请求类
 */
@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
public class SysConfigRequest extends BaseRequest {
    /**
     * 主键
     */
    @NotNull(message = "主键不能为空", groups = {update.class, delete.class, detail.class, updateStatus.class})
    @ChineseDescription("主键")
    private Long configId;

    /**
     * 名称
     */
    @NotBlank(message = "名称不能为空", groups = {add.class, update.class})
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
    @NotBlank(message = "属性值不能为空", groups = {add.class, update.class})
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

    @NotNull(message = "ID集合不能为空", groups = {batchDelete.class})
    @ChineseDescription("ID集合")
    private java.util.List<Long> configIds;
}