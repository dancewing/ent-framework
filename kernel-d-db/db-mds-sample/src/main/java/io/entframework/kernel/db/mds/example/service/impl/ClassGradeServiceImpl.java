package io.entframework.kernel.db.mds.example.service.impl;

import io.entframework.kernel.db.mds.example.entity.ClassGrade;
import io.entframework.kernel.db.mds.example.pojo.request.ClassGradeRequest;
import io.entframework.kernel.db.mds.example.pojo.response.ClassGradeResponse;
import io.entframework.kernel.db.mds.example.repository.ClassGradeRepository;
import io.entframework.kernel.db.mds.example.service.ClassGradeService;
import io.entframework.kernel.db.mds.service.BaseServiceImpl;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ClassGradeServiceImpl extends BaseServiceImpl<ClassGradeRequest, ClassGradeResponse, ClassGrade> implements ClassGradeService {
    public ClassGradeServiceImpl(ClassGradeRepository classGradeRepository) {
        super(classGradeRepository, ClassGradeRequest.class, ClassGradeResponse.class);
    }

    public ClassGradeServiceImpl(ClassGradeRepository classGradeRepository, Class<? extends ClassGradeRequest> requestClass, Class<? extends ClassGradeResponse> responseClass) {
        super(classGradeRepository, requestClass, responseClass);
    }
}