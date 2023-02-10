package io.entframework.kernel.db.mds.example.pojo.request;

import io.entframework.kernel.rule.annotation.ChineseDescription;
import io.entframework.kernel.rule.pojo.request.BaseRequest;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * AutoTest 服务请求类
 */
@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
public class AutoIncrementRequest extends BaseRequest {
    /**
     * Id
     */
    @NotNull(message = "Id不能为空", groups = {update.class, delete.class, detail.class, updateStatus.class})
    @ChineseDescription("Id")
    private Integer id;

    /**
     * 姓名
     */
    @ChineseDescription("姓名")
    private String username;

    @NotNull(message = "ID集合不能为空", groups = {batchDelete.class})
    @ChineseDescription("ID集合")
    private java.util.List<Integer> ids;
}