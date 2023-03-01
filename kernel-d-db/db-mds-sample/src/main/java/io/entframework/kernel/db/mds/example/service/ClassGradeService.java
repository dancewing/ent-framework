package io.entframework.kernel.db.mds.example.service;

import io.entframework.kernel.db.dao.service.BaseService;
import io.entframework.kernel.db.mds.example.entity.ClassGrade;
import io.entframework.kernel.db.mds.example.pojo.request.ClassGradeRequest;
import io.entframework.kernel.db.mds.example.pojo.response.ClassGradeResponse;

public interface ClassGradeService extends BaseService<ClassGradeRequest, ClassGradeResponse, ClassGrade> {

}