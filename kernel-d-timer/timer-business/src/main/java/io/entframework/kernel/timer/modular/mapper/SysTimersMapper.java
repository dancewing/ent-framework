package io.entframework.kernel.timer.modular.mapper;

import io.entframework.kernel.db.api.annotation.Entity;
import io.entframework.kernel.db.mds.mapper.BaseMapper;
import io.entframework.kernel.timer.modular.entity.SysTimers;
import org.apache.ibatis.annotations.Mapper;

@Mapper
@Entity(SysTimers.class)
public interface SysTimersMapper extends BaseMapper<SysTimers> {
}