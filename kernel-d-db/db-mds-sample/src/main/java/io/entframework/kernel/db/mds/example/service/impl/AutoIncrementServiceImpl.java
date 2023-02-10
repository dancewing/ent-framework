package io.entframework.kernel.db.mds.example.service.impl;

import io.entframework.kernel.db.mds.example.entity.AutoIncrement;
import io.entframework.kernel.db.mds.example.pojo.request.AutoIncrementRequest;
import io.entframework.kernel.db.mds.example.pojo.response.AutoIncrementResponse;
import io.entframework.kernel.db.mds.example.service.AutoIncrementService;
import io.entframework.kernel.db.mds.service.BaseServiceImpl;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class AutoIncrementServiceImpl extends BaseServiceImpl<AutoIncrementRequest, AutoIncrementResponse, AutoIncrement> implements AutoIncrementService {
    public AutoIncrementServiceImpl() {
        super(AutoIncrementRequest.class, AutoIncrementResponse.class, AutoIncrement.class);
    }

    public AutoIncrementServiceImpl(Class<? extends AutoIncrementRequest> requestClass, Class<? extends AutoIncrementResponse> responseClass, Class<? extends AutoIncrement> entityClass) {
        super(requestClass, responseClass, entityClass);
    }
}