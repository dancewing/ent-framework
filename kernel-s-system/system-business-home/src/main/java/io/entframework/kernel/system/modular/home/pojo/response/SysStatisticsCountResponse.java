package io.entframework.kernel.system.modular.home.pojo.response;

import io.entframework.kernel.db.api.pojo.response.BaseResponse;
import io.entframework.kernel.rule.annotation.ChineseDescription;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * 常用功能的统计次数 服务响应类
 */
@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
public class SysStatisticsCountResponse extends BaseResponse {
    /**
     * 主键ID
     */
    @ChineseDescription("主键ID")
    private Long statCountId;

    /**
     * 用户id
     */
    @ChineseDescription("用户id")
    private Long userId;

    /**
     * 访问的地址
     */
    @ChineseDescription("访问的地址")
    private Long statUrlId;

    /**
     * 访问的次数
     */
    @ChineseDescription("访问的次数")
    private Integer statCount;
}