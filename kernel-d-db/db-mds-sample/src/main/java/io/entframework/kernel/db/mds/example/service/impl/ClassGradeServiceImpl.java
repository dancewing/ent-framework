package io.entframework.kernel.db.mds.example.service.impl;

import io.entframework.kernel.db.dao.service.BaseServiceImpl;
import io.entframework.kernel.db.mds.example.entity.ClassGrade;
import io.entframework.kernel.db.mds.example.pojo.request.ClassGradeRequest;
import io.entframework.kernel.db.mds.example.pojo.response.ClassGradeResponse;
import io.entframework.kernel.db.mds.example.service.ClassGradeService;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ClassGradeServiceImpl extends BaseServiceImpl<ClassGradeRequest, ClassGradeResponse, ClassGrade> implements ClassGradeService {
    public ClassGradeServiceImpl() {
        super(ClassGradeRequest.class, ClassGradeResponse.class, ClassGrade.class);
    }

    public ClassGradeServiceImpl(Class<? extends ClassGradeRequest> requestClass, Class<? extends ClassGradeResponse> responseClass, Class<? extends ClassGrade> entityClass) {
        super(requestClass, responseClass, entityClass);
    }
}