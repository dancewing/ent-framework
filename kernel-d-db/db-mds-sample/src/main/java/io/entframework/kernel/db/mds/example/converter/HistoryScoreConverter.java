package io.entframework.kernel.db.mds.example.converter;

import io.entframework.kernel.converter.support.ObjectConverter;
import io.entframework.kernel.db.mds.example.entity.HistoryScore;
import io.entframework.kernel.db.mds.example.pojo.request.HistoryScoreRequest;
import io.entframework.kernel.db.mds.example.pojo.response.HistoryScoreResponse;
import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;

public interface HistoryScoreConverter {
    @Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    interface RequestConverter extends ObjectConverter<HistoryScoreRequest, HistoryScore> {
    }

    @Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    interface ResponseConverter extends ObjectConverter<HistoryScore, HistoryScoreResponse> {
    }
}