package io.entframework.kernel.db.mds.example.controller.base;

import io.entframework.kernel.db.api.pojo.page.PageResult;
import io.entframework.kernel.db.mds.example.pojo.request.AutoIncrementRequest;
import io.entframework.kernel.db.mds.example.pojo.response.AutoIncrementResponse;
import io.entframework.kernel.db.mds.example.service.AutoIncrementService;
import io.entframework.kernel.rule.pojo.request.BaseRequest;
import io.entframework.kernel.rule.pojo.response.ResponseData;
import io.entframework.kernel.scanner.api.annotation.GetResource;
import io.entframework.kernel.scanner.api.annotation.PostResource;
import jakarta.annotation.Resource;
import java.util.List;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;

public abstract class BaseAutoIncrementController {

    @Resource
    protected AutoIncrementService autoIncrementService;

    @PostResource(name = "AutoTest-新增", path = "/auto-increment/create")
    public ResponseData<AutoIncrementResponse> insert(
            @RequestBody @Validated(BaseRequest.add.class) AutoIncrementRequest request) {
        return ResponseData.ok(autoIncrementService.insert(request));
    }

    @PostResource(name = "AutoTest-批量新增", path = "/auto-increment/batch-create")
    public ResponseData<List<AutoIncrementResponse>> insertMultiple(@RequestBody List<AutoIncrementRequest> request) {
        return ResponseData.ok(autoIncrementService.insertMultiple(request));
    }

    @PostResource(name = "AutoTest-更新-by PK", path = "/auto-increment/update")
    public ResponseData<AutoIncrementResponse> update(
            @RequestBody @Validated(BaseRequest.update.class) AutoIncrementRequest request) {
        return ResponseData.ok(autoIncrementService.update(request));
    }

    @PostResource(name = "AutoTest-列表", path = "/auto-increment/list")
    public ResponseData<List<AutoIncrementResponse>> list(@RequestBody AutoIncrementRequest request) {
        return ResponseData.ok(autoIncrementService.list(request));
    }

    @PostResource(name = "AutoTest-分页查询", path = "/auto-increment/page")
    public ResponseData<PageResult<AutoIncrementResponse>> page(@RequestBody AutoIncrementRequest request) {
        return ResponseData.ok(autoIncrementService.page(request));
    }

    @PostResource(name = "AutoTest-删除-by PK", path = "/auto-increment/delete")
    public ResponseData<Integer> delete(
            @RequestBody @Validated(BaseRequest.delete.class) AutoIncrementRequest request) {
        return ResponseData.ok(autoIncrementService.delete(request));
    }

    @PostResource(name = "AutoTest-批量删除-by PK", path = "/auto-increment/batch-delete")
    public ResponseData<Integer> batchDelete(
            @RequestBody @Validated(BaseRequest.batchDelete.class) AutoIncrementRequest request) {
        return ResponseData.ok(autoIncrementService.batchDelete(request));
    }

    @GetResource(name = "AutoTest-获取记录-by PK", path = "/auto-increment/detail")
    public ResponseData<AutoIncrementResponse> load(@Validated(BaseRequest.detail.class) AutoIncrementRequest request) {
        return ResponseData.ok(autoIncrementService.load(request));
    }

}