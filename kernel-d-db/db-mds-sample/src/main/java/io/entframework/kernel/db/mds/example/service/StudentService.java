package io.entframework.kernel.db.mds.example.service;

import io.entframework.kernel.db.dao.service.BaseService;
import io.entframework.kernel.db.mds.example.entity.Student;
import io.entframework.kernel.db.mds.example.pojo.request.StudentRequest;
import io.entframework.kernel.db.mds.example.pojo.response.StudentResponse;

public interface StudentService extends BaseService<StudentRequest, StudentResponse, Student> {

}