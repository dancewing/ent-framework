package io.entframework.kernel.db.mds.example.service;

import io.entframework.kernel.db.dao.service.BaseService;
import io.entframework.kernel.db.mds.example.entity.Teacher;
import io.entframework.kernel.db.mds.example.pojo.request.TeacherRequest;
import io.entframework.kernel.db.mds.example.pojo.response.TeacherResponse;

public interface TeacherService extends BaseService<TeacherRequest, TeacherResponse, Teacher> {
}