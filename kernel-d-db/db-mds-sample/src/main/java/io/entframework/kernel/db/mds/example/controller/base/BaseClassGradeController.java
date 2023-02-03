package io.entframework.kernel.db.mds.example.controller.base;

import io.entframework.kernel.db.api.pojo.page.PageResult;
import io.entframework.kernel.db.mds.example.pojo.request.ClassGradeRequest;
import io.entframework.kernel.db.mds.example.pojo.response.ClassGradeResponse;
import io.entframework.kernel.db.mds.example.service.ClassGradeService;
import io.entframework.kernel.rule.pojo.request.BaseRequest;
import io.entframework.kernel.rule.pojo.response.ResponseData;
import io.entframework.kernel.scanner.api.annotation.GetResource;
import io.entframework.kernel.scanner.api.annotation.PostResource;
import jakarta.annotation.Resource;
import java.util.List;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;

public abstract class BaseClassGradeController {
    @Resource
    protected ClassGradeService classGradeService;

    @PostResource(name = "班级-新增", path = "/class-grade/create")
    public ResponseData<ClassGradeResponse> insert(@RequestBody @Validated(BaseRequest.add.class) ClassGradeRequest request) {
        return ResponseData.ok(classGradeService.insert(request));
    }

    @PostResource(name = "班级-批量新增", path = "/class-grade/batch-create")
    public ResponseData<List<ClassGradeResponse>> insertMultiple(@RequestBody List<ClassGradeRequest> request) {
        return ResponseData.ok(classGradeService.insertMultiple(request));
    }

    @PostResource(name = "班级-更新-by PK", path = "/class-grade/update")
    public ResponseData<ClassGradeResponse> update(@RequestBody @Validated(BaseRequest.update.class) ClassGradeRequest request) {
        return ResponseData.ok(classGradeService.update(request));
    }

    @PostResource(name = "班级-列表", path = "/class-grade/list")
    public ResponseData<List<ClassGradeResponse>> list(@RequestBody ClassGradeRequest request) {
        return ResponseData.ok(classGradeService.list(request));
    }

    @PostResource(name = "班级-分页查询", path = "/class-grade/page")
    public ResponseData<PageResult<ClassGradeResponse>> page(@RequestBody ClassGradeRequest request) {
        return ResponseData.ok(classGradeService.page(request));
    }

    @PostResource(name = "班级-删除-by PK", path = "/class-grade/delete")
    public ResponseData<Integer> delete(@RequestBody @Validated(BaseRequest.delete.class) ClassGradeRequest request) {
        return ResponseData.ok(classGradeService.delete(request));
    }

    @PostResource(name = "班级-批量删除-by PK", path = "/class-grade/batch-delete")
    public ResponseData<Integer> batchDelete(@RequestBody @Validated(BaseRequest.batchDelete.class) ClassGradeRequest request) {
        return ResponseData.ok(classGradeService.batchDelete(request));
    }

    @GetResource(name = "班级-获取记录-by PK", path = "/class-grade/detail")
    public ResponseData<ClassGradeResponse> load(@Validated(BaseRequest.detail.class) ClassGradeRequest request) {
        return ResponseData.ok(classGradeService.load(request));
    }
}