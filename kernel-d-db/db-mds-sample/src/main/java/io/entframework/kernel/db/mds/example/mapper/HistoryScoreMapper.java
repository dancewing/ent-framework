package io.entframework.kernel.db.mds.example.mapper;

import io.entframework.kernel.db.api.annotation.Entity;
import io.entframework.kernel.db.mds.example.entity.HistoryScore;
import io.entframework.kernel.db.mds.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

@Mapper
@Entity(HistoryScore.class)
public interface HistoryScoreMapper extends BaseMapper<HistoryScore> {
}