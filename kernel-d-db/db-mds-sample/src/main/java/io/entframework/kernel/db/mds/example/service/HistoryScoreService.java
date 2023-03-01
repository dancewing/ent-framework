package io.entframework.kernel.db.mds.example.service;

import io.entframework.kernel.db.dao.service.BaseService;
import io.entframework.kernel.db.mds.example.entity.HistoryScore;
import io.entframework.kernel.db.mds.example.pojo.request.HistoryScoreRequest;
import io.entframework.kernel.db.mds.example.pojo.response.HistoryScoreResponse;

public interface HistoryScoreService extends BaseService<HistoryScoreRequest, HistoryScoreResponse, HistoryScore> {

}