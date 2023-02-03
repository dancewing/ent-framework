package io.entframework.kernel.db.mds.example.converter;

import io.entframework.kernel.converter.support.ObjectConverter;
import io.entframework.kernel.db.mds.example.entity.Teacher;
import io.entframework.kernel.db.mds.example.pojo.request.TeacherRequest;
import io.entframework.kernel.db.mds.example.pojo.response.TeacherResponse;
import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;

public interface TeacherConverter {
    @Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    interface RequestConverter extends ObjectConverter<TeacherRequest, Teacher> {
    }

    @Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    interface ResponseConverter extends ObjectConverter<Teacher, TeacherResponse> {
    }
}