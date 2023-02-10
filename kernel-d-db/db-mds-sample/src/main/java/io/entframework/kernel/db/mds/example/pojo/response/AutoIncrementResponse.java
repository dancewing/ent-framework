package io.entframework.kernel.db.mds.example.pojo.response;

import io.entframework.kernel.db.api.pojo.response.BaseResponse;
import io.entframework.kernel.rule.annotation.ChineseDescription;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * AutoTest 服务响应类
 */
@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
public class AutoIncrementResponse extends BaseResponse {
    /**
     * Id
     */
    @ChineseDescription("Id")
    private Long id;

    /**
     * 姓名
     */
    @ChineseDescription("姓名")
    private String username;
}