package io.entframework.kernel.db.mds.example.mapper;

import io.entframework.kernel.db.api.annotation.Entity;
import io.entframework.kernel.db.mds.example.entity.Teacher;
import io.entframework.kernel.db.mds.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

@Mapper
@Entity(Teacher.class)
public interface TeacherMapper extends BaseMapper<Teacher> {
}