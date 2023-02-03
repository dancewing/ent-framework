package io.entframework.kernel.db.mds.example.service;

import io.entframework.kernel.db.mds.example.entity.Teacher;
import io.entframework.kernel.db.mds.example.pojo.request.TeacherRequest;
import io.entframework.kernel.db.mds.example.pojo.response.TeacherResponse;
import io.entframework.kernel.db.mds.service.BaseService;

public interface TeacherService extends BaseService<TeacherRequest, TeacherResponse, Teacher> {
}