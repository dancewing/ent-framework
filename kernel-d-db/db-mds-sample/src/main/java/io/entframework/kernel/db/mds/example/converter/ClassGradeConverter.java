package io.entframework.kernel.db.mds.example.converter;

import cn.hutool.extra.spring.SpringUtil;
import io.entframework.kernel.converter.support.ObjectConversionService;
import io.entframework.kernel.converter.support.ObjectConverter;
import io.entframework.kernel.db.mds.example.entity.ClassGrade;
import io.entframework.kernel.db.mds.example.entity.Student;
import io.entframework.kernel.db.mds.example.entity.Teacher;
import io.entframework.kernel.db.mds.example.pojo.request.ClassGradeRequest;
import io.entframework.kernel.db.mds.example.pojo.request.StudentRequest;
import io.entframework.kernel.db.mds.example.pojo.request.TeacherRequest;
import io.entframework.kernel.db.mds.example.pojo.response.ClassGradeResponse;
import io.entframework.kernel.db.mds.example.pojo.response.StudentResponse;
import io.entframework.kernel.db.mds.example.pojo.response.TeacherResponse;
import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;

public interface ClassGradeConverter {
    @Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    interface RequestConverter extends ObjectConverter<ClassGradeRequest, ClassGrade> {
        default Student mapStudentRequestToStudent(StudentRequest studentRequest) {
            ObjectConversionService converterService = SpringUtil.getBean(ObjectConversionService.class);
            return converterService.convert(studentRequest, Student.class);
        }

        default Teacher mapTeacherRequestToTeacher(TeacherRequest teacherRequest) {
            ObjectConversionService converterService = SpringUtil.getBean(ObjectConversionService.class);
            return converterService.convert(teacherRequest, Teacher.class);
        }
    }

    @Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    interface ResponseConverter extends ObjectConverter<ClassGrade, ClassGradeResponse> {
        default StudentResponse mapStudentToStudentResponse(Student student) {
            ObjectConversionService converterService = SpringUtil.getBean(ObjectConversionService.class);
            return converterService.convert(student, StudentResponse.class);
        }

        default TeacherResponse mapTeacherToTeacherResponse(Teacher teacher) {
            ObjectConversionService converterService = SpringUtil.getBean(ObjectConversionService.class);
            return converterService.convert(teacher, TeacherResponse.class);
        }
    }
}