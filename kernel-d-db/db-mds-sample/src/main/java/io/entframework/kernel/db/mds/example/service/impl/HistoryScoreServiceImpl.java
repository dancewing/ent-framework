package io.entframework.kernel.db.mds.example.service.impl;

import io.entframework.kernel.db.mds.example.entity.HistoryScore;
import io.entframework.kernel.db.mds.example.pojo.request.HistoryScoreRequest;
import io.entframework.kernel.db.mds.example.pojo.response.HistoryScoreResponse;
import io.entframework.kernel.db.mds.example.repository.HistoryScoreRepository;
import io.entframework.kernel.db.mds.example.service.HistoryScoreService;
import io.entframework.kernel.db.mds.service.BaseServiceImpl;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class HistoryScoreServiceImpl extends BaseServiceImpl<HistoryScoreRequest, HistoryScoreResponse, HistoryScore> implements HistoryScoreService {
    public HistoryScoreServiceImpl(HistoryScoreRepository historyScoreRepository) {
        super(historyScoreRepository, HistoryScoreRequest.class, HistoryScoreResponse.class);
    }

    public HistoryScoreServiceImpl(HistoryScoreRepository historyScoreRepository, Class<? extends HistoryScoreRequest> requestClass, Class<? extends HistoryScoreResponse> responseClass) {
        super(historyScoreRepository, requestClass, responseClass);
    }
}