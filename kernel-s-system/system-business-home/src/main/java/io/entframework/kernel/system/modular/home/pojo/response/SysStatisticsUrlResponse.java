package io.entframework.kernel.system.modular.home.pojo.response;

import io.entframework.kernel.db.api.pojo.response.BaseResponse;
import io.entframework.kernel.rule.annotation.ChineseDescription;
import io.entframework.kernel.rule.enums.YesOrNotEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * 常用功能列表 服务响应类
 */
@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
public class SysStatisticsUrlResponse extends BaseResponse {
    /**
     * 主键ID
     */
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
}