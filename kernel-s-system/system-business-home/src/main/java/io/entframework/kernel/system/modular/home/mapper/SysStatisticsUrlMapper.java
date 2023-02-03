package io.entframework.kernel.system.modular.home.mapper;

import io.entframework.kernel.db.api.annotation.Entity;
import io.entframework.kernel.db.mds.mapper.BaseMapper;
import io.entframework.kernel.system.modular.home.entity.SysStatisticsUrl;
import org.apache.ibatis.annotations.Mapper;

@Mapper
@Entity(SysStatisticsUrl.class)
public interface SysStatisticsUrlMapper extends BaseMapper<SysStatisticsUrl> {
}