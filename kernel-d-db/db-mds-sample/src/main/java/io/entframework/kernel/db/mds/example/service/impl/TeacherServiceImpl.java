package io.entframework.kernel.db.mds.example.service.impl;

import io.entframework.kernel.db.dao.service.BaseServiceImpl;
import io.entframework.kernel.db.mds.example.entity.Teacher;
import io.entframework.kernel.db.mds.example.pojo.request.TeacherRequest;
import io.entframework.kernel.db.mds.example.pojo.response.TeacherResponse;
import io.entframework.kernel.db.mds.example.service.TeacherService;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class TeacherServiceImpl extends BaseServiceImpl<TeacherRequest, TeacherResponse, Teacher>
        implements TeacherService {

    public TeacherServiceImpl() {
        super(TeacherRequest.class, TeacherResponse.class, Teacher.class);
    }

    public TeacherServiceImpl(Class<? extends TeacherRequest> requestClass,
            Class<? extends TeacherResponse> responseClass, Class<? extends Teacher> entityClass) {
        super(requestClass, responseClass, entityClass);
    }

}