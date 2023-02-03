package io.entframework.kernel.db.mds.example.converter;

import io.entframework.kernel.converter.support.ObjectConverter;
import io.entframework.kernel.db.mds.example.entity.Student;
import io.entframework.kernel.db.mds.example.pojo.request.StudentRequest;
import io.entframework.kernel.db.mds.example.pojo.response.StudentResponse;
import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;

public interface StudentConverter {
    @Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    interface RequestConverter extends ObjectConverter<StudentRequest, Student> {
    }

    @Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    interface ResponseConverter extends ObjectConverter<Student, StudentResponse> {
    }
}