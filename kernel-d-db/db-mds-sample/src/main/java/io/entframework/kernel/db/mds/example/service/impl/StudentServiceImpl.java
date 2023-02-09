package io.entframework.kernel.db.mds.example.service.impl;

import io.entframework.kernel.db.mds.example.entity.Student;
import io.entframework.kernel.db.mds.example.pojo.request.StudentRequest;
import io.entframework.kernel.db.mds.example.pojo.response.StudentResponse;
import io.entframework.kernel.db.mds.example.service.StudentService;
import io.entframework.kernel.db.mds.service.BaseServiceImpl;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class StudentServiceImpl extends BaseServiceImpl<StudentRequest, StudentResponse, Student> implements StudentService {
    public StudentServiceImpl() {
        super(StudentRequest.class, StudentResponse.class, Student.class);
    }

    public StudentServiceImpl(Class<? extends StudentRequest> requestClass, Class<? extends StudentResponse> responseClass, Class<? extends Student> entityClass) {
        super(requestClass, responseClass, entityClass);
    }
}