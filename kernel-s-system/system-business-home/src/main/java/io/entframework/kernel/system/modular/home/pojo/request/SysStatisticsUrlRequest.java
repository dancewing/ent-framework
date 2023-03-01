package io.entframework.kernel.system.modular.home.pojo.request;

import io.entframework.kernel.rule.annotation.ChineseDescription;
import io.entframework.kernel.rule.enums.YesOrNotEnum;
import io.entframework.kernel.rule.pojo.request.BaseRequest;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * 常用功能列表 服务请求类
 */
@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
public class SysStatisticsUrlRequest extends BaseRequest {

    /**
     * 主键ID
     */
    @NotNull(message = "主键ID不能为空", groups = { update.class, delete.class, detail.class, updateStatus.class })
    @ChineseDescription("主键ID")
    private Long statUrlId;

    /**
     * 被统计名称
     */
    @ChineseDescription("被统计名称")
    private String statName;

    /**
     * 被统计菜单ID
     */
    @ChineseDescription("被统计菜单ID")
    private Long statMenuId;

    /**
     * 被统计的URL
     */
    @ChineseDescription("被统计的URL")
    private String statUrl;

    /**
     * 是否常驻显示，Y-是，N-否
     */
    @ChineseDescription("是否常驻显示，Y-是，N-否")
    private YesOrNotEnum alwaysShow;

    @NotNull(message = "ID集合不能为空", groups = { batchDelete.class })
    @ChineseDescription("ID集合")
    private java.util.List<Long> statUrlIds;

}