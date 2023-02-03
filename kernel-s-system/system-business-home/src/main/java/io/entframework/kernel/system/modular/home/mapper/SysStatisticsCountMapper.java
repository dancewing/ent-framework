package io.entframework.kernel.system.modular.home.mapper;

import io.entframework.kernel.db.api.annotation.Entity;
import io.entframework.kernel.db.mds.mapper.BaseMapper;
import io.entframework.kernel.system.modular.home.entity.SysStatisticsCount;
import org.apache.ibatis.annotations.Mapper;

@Mapper
@Entity(SysStatisticsCount.class)
public interface SysStatisticsCountMapper extends BaseMapper<SysStatisticsCount> {
}