package io.entframework.kernel.db.mds.example.service;

import io.entframework.kernel.db.mds.example.entity.Student;
import io.entframework.kernel.db.mds.example.pojo.request.StudentRequest;
import io.entframework.kernel.db.mds.example.pojo.response.StudentResponse;
import io.entframework.kernel.db.mds.service.BaseService;

public interface StudentService extends BaseService<StudentRequest, StudentResponse, Student> {
}