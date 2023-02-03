package io.entframework.kernel.db.mds.example.service;

import io.entframework.kernel.db.mds.example.entity.HistoryScore;
import io.entframework.kernel.db.mds.example.pojo.request.HistoryScoreRequest;
import io.entframework.kernel.db.mds.example.pojo.response.HistoryScoreResponse;
import io.entframework.kernel.db.mds.service.BaseService;

public interface HistoryScoreService extends BaseService<HistoryScoreRequest, HistoryScoreResponse, HistoryScore> {
}