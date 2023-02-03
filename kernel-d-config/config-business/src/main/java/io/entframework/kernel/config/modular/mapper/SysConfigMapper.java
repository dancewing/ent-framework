package io.entframework.kernel.config.modular.mapper;

import io.entframework.kernel.config.modular.entity.SysConfig;
import io.entframework.kernel.db.api.annotation.Entity;
import io.entframework.kernel.db.mds.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

@Mapper
@Entity(SysConfig.class)
public interface SysConfigMapper extends BaseMapper<SysConfig> {
}