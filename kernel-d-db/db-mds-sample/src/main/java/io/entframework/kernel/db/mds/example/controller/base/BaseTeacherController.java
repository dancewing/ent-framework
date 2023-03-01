package io.entframework.kernel.db.mds.example.controller.base;

import io.entframework.kernel.db.api.pojo.page.PageResult;
import io.entframework.kernel.db.mds.example.pojo.request.TeacherRequest;
import io.entframework.kernel.db.mds.example.pojo.response.TeacherResponse;
import io.entframework.kernel.db.mds.example.service.TeacherService;
import io.entframework.kernel.rule.pojo.request.BaseRequest;
import io.entframework.kernel.rule.pojo.response.ResponseData;
import io.entframework.kernel.scanner.api.annotation.GetResource;
import io.entframework.kernel.scanner.api.annotation.PostResource;
import jakarta.annotation.Resource;
import java.util.List;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;

public abstract class BaseTeacherController {

    @Resource
    protected TeacherService teacherService;

    @PostResource(name = "教师-新增", path = "/teacher/create")
    public ResponseData<TeacherResponse> insert(@RequestBody @Validated(BaseRequest.add.class) TeacherRequest request) {
        return ResponseData.ok(teacherService.insert(request));
    }

    @PostResource(name = "教师-批量新增", path = "/teacher/batch-create")
    public ResponseData<List<TeacherResponse>> insertMultiple(@RequestBody List<TeacherRequest> request) {
        return ResponseData.ok(teacherService.insertMultiple(request));
    }

    @PostResource(name = "教师-更新-by PK", path = "/teacher/update")
    public ResponseData<TeacherResponse> update(
            @RequestBody @Validated(BaseRequest.update.class) TeacherRequest request) {
        return ResponseData.ok(teacherService.update(request));
    }

    @PostResource(name = "教师-列表", path = "/teacher/list")
    public ResponseData<List<TeacherResponse>> list(@RequestBody TeacherRequest request) {
        return ResponseData.ok(teacherService.list(request));
    }

    @PostResource(name = "教师-分页查询", path = "/teacher/page")
    public ResponseData<PageResult<TeacherResponse>> page(@RequestBody TeacherRequest request) {
        return ResponseData.ok(teacherService.page(request));
    }

    @PostResource(name = "教师-删除-by PK", path = "/teacher/delete")
    public ResponseData<Integer> delete(@RequestBody @Validated(BaseRequest.delete.class) TeacherRequest request) {
        return ResponseData.ok(teacherService.delete(request));
    }

    @PostResource(name = "教师-批量删除-by PK", path = "/teacher/batch-delete")
    public ResponseData<Integer> batchDelete(
            @RequestBody @Validated(BaseRequest.batchDelete.class) TeacherRequest request) {
        return ResponseData.ok(teacherService.batchDelete(request));
    }

    @GetResource(name = "教师-获取记录-by PK", path = "/teacher/detail")
    public ResponseData<TeacherResponse> load(@Validated(BaseRequest.detail.class) TeacherRequest request) {
        return ResponseData.ok(teacherService.load(request));
    }

}