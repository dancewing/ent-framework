package io.entframework.kernel.db.mds.example.service;

import io.entframework.kernel.db.mds.example.entity.AutoIncrement;
import io.entframework.kernel.db.mds.example.pojo.request.AutoIncrementRequest;
import io.entframework.kernel.db.mds.example.pojo.response.AutoIncrementResponse;
import io.entframework.kernel.db.mds.service.BaseService;

public interface AutoIncrementService extends BaseService<AutoIncrementRequest, AutoIncrementResponse, AutoIncrement> {
}