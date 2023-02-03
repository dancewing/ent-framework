package io.entframework.kernel.log.db.mapper;

import io.entframework.kernel.db.api.annotation.Entity;
import io.entframework.kernel.db.mds.mapper.BaseMapper;
import io.entframework.kernel.log.db.entity.SysLog;
import org.apache.ibatis.annotations.Mapper;

@Mapper
@Entity(SysLog.class)
public interface SysLogMapper extends BaseMapper<SysLog> {
}