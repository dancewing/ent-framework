package io.entframework.kernel.db.mds.example.controller.base;

import io.entframework.kernel.db.api.pojo.page.PageResult;
import io.entframework.kernel.db.mds.example.pojo.request.HistoryScoreRequest;
import io.entframework.kernel.db.mds.example.pojo.response.HistoryScoreResponse;
import io.entframework.kernel.db.mds.example.service.HistoryScoreService;
import io.entframework.kernel.rule.pojo.request.BaseRequest;
import io.entframework.kernel.rule.pojo.response.ResponseData;
import io.entframework.kernel.scanner.api.annotation.GetResource;
import io.entframework.kernel.scanner.api.annotation.PostResource;
import jakarta.annotation.Resource;
import java.util.List;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;

public abstract class BaseHistoryScoreController {
    @Resource
    protected HistoryScoreService historyScoreService;

    @PostResource(name = "考试记录-新增", path = "/history-score/create")
    public ResponseData<HistoryScoreResponse> insert(@RequestBody @Validated(BaseRequest.add.class) HistoryScoreRequest request) {
        return ResponseData.ok(historyScoreService.insert(request));
    }

    @PostResource(name = "考试记录-批量新增", path = "/history-score/batch-create")
    public ResponseData<List<HistoryScoreResponse>> insertMultiple(@RequestBody List<HistoryScoreRequest> request) {
        return ResponseData.ok(historyScoreService.insertMultiple(request));
    }

    @PostResource(name = "考试记录-更新-by PK", path = "/history-score/update")
    public ResponseData<HistoryScoreResponse> update(@RequestBody @Validated(BaseRequest.update.class) HistoryScoreRequest request) {
        return ResponseData.ok(historyScoreService.update(request));
    }

    @PostResource(name = "考试记录-列表", path = "/history-score/list")
    public ResponseData<List<HistoryScoreResponse>> list(@RequestBody HistoryScoreRequest request) {
        return ResponseData.ok(historyScoreService.list(request));
    }

    @PostResource(name = "考试记录-分页查询", path = "/history-score/page")
    public ResponseData<PageResult<HistoryScoreResponse>> page(@RequestBody HistoryScoreRequest request) {
        return ResponseData.ok(historyScoreService.page(request));
    }

    @PostResource(name = "考试记录-删除-by PK", path = "/history-score/delete")
    public ResponseData<Integer> delete(@RequestBody @Validated(BaseRequest.delete.class) HistoryScoreRequest request) {
        return ResponseData.ok(historyScoreService.delete(request));
    }

    @PostResource(name = "考试记录-批量删除-by PK", path = "/history-score/batch-delete")
    public ResponseData<Integer> batchDelete(@RequestBody @Validated(BaseRequest.batchDelete.class) HistoryScoreRequest request) {
        return ResponseData.ok(historyScoreService.batchDelete(request));
    }

    @GetResource(name = "考试记录-获取记录-by PK", path = "/history-score/detail")
    public ResponseData<HistoryScoreResponse> load(@Validated(BaseRequest.detail.class) HistoryScoreRequest request) {
        return ResponseData.ok(historyScoreService.load(request));
    }
}