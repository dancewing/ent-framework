package io.entframework.kernel.db.mds.example.service.impl;

import io.entframework.kernel.db.mds.example.entity.Student;
import io.entframework.kernel.db.mds.example.pojo.request.StudentRequest;
import io.entframework.kernel.db.mds.example.pojo.response.StudentResponse;
import io.entframework.kernel.db.mds.example.repository.StudentRepository;
import io.entframework.kernel.db.mds.example.service.StudentService;
import io.entframework.kernel.db.mds.service.BaseServiceImpl;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class StudentServiceImpl extends BaseServiceImpl<StudentRequest, StudentResponse, Student> implements StudentService {
    public StudentServiceImpl(StudentRepository studentRepository) {
        super(studentRepository, StudentRequest.class, StudentResponse.class);
    }

    public StudentServiceImpl(StudentRepository studentRepository, Class<? extends StudentRequest> requestClass, Class<? extends StudentResponse> responseClass) {
        super(studentRepository, requestClass, responseClass);
    }
}