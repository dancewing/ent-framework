package io.entframework.kernel.db.mds.example.service;

import io.entframework.kernel.db.dao.service.BaseService;
import io.entframework.kernel.db.mds.example.entity.AutoIncrement;
import io.entframework.kernel.db.mds.example.pojo.request.AutoIncrementRequest;
import io.entframework.kernel.db.mds.example.pojo.response.AutoIncrementResponse;

public interface AutoIncrementService extends BaseService<AutoIncrementRequest, AutoIncrementResponse, AutoIncrement> {

}