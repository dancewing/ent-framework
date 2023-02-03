package io.entframework.kernel.db.mds.example.controller.base;

import io.entframework.kernel.db.api.pojo.page.PageResult;
import io.entframework.kernel.db.mds.example.pojo.request.StudentRequest;
import io.entframework.kernel.db.mds.example.pojo.response.StudentResponse;
import io.entframework.kernel.db.mds.example.service.StudentService;
import io.entframework.kernel.rule.pojo.request.BaseRequest;
import io.entframework.kernel.rule.pojo.response.ResponseData;
import io.entframework.kernel.scanner.api.annotation.GetResource;
import io.entframework.kernel.scanner.api.annotation.PostResource;
import jakarta.annotation.Resource;
import java.util.List;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;

public abstract class BaseStudentController {
    @Resource
    protected StudentService studentService;

    @PostResource(name = "学生-新增", path = "/student/create")
    public ResponseData<StudentResponse> insert(@RequestBody @Validated(BaseRequest.add.class) StudentRequest request) {
        return ResponseData.ok(studentService.insert(request));
    }

    @PostResource(name = "学生-批量新增", path = "/student/batch-create")
    public ResponseData<List<StudentResponse>> insertMultiple(@RequestBody List<StudentRequest> request) {
        return ResponseData.ok(studentService.insertMultiple(request));
    }

    @PostResource(name = "学生-更新-by PK", path = "/student/update")
    public ResponseData<StudentResponse> update(@RequestBody @Validated(BaseRequest.update.class) StudentRequest request) {
        return ResponseData.ok(studentService.update(request));
    }

    @PostResource(name = "学生-列表", path = "/student/list")
    public ResponseData<List<StudentResponse>> list(@RequestBody StudentRequest request) {
        return ResponseData.ok(studentService.list(request));
    }

    @PostResource(name = "学生-分页查询", path = "/student/page")
    public ResponseData<PageResult<StudentResponse>> page(@RequestBody StudentRequest request) {
        return ResponseData.ok(studentService.page(request));
    }

    @PostResource(name = "学生-删除-by PK", path = "/student/delete")
    public ResponseData<Integer> delete(@RequestBody @Validated(BaseRequest.delete.class) StudentRequest request) {
        return ResponseData.ok(studentService.delete(request));
    }

    @PostResource(name = "学生-批量删除-by PK", path = "/student/batch-delete")
    public ResponseData<Integer> batchDelete(@RequestBody @Validated(BaseRequest.batchDelete.class) StudentRequest request) {
        return ResponseData.ok(studentService.batchDelete(request));
    }

    @GetResource(name = "学生-获取记录-by PK", path = "/student/detail")
    public ResponseData<StudentResponse> load(@Validated(BaseRequest.detail.class) StudentRequest request) {
        return ResponseData.ok(studentService.load(request));
    }
}