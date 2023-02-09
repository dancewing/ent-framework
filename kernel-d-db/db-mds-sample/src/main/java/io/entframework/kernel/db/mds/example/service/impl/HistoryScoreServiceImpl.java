package io.entframework.kernel.db.mds.example.service.impl;

import io.entframework.kernel.db.mds.example.entity.HistoryScore;
import io.entframework.kernel.db.mds.example.pojo.request.HistoryScoreRequest;
import io.entframework.kernel.db.mds.example.pojo.response.HistoryScoreResponse;
import io.entframework.kernel.db.mds.example.service.HistoryScoreService;
import io.entframework.kernel.db.mds.service.BaseServiceImpl;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class HistoryScoreServiceImpl extends BaseServiceImpl<HistoryScoreRequest, HistoryScoreResponse, HistoryScore> implements HistoryScoreService {
    public HistoryScoreServiceImpl() {
        super(HistoryScoreRequest.class, HistoryScoreResponse.class, HistoryScore.class);
    }

    public HistoryScoreServiceImpl(Class<? extends HistoryScoreRequest> requestClass, Class<? extends HistoryScoreResponse> responseClass, Class<? extends HistoryScore> entityClass) {
        super(requestClass, responseClass, entityClass);
    }
}