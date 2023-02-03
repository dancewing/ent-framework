package io.entframework.kernel.system.modular.loginlog.mapper;

import io.entframework.kernel.db.api.annotation.Entity;
import io.entframework.kernel.db.mds.mapper.BaseMapper;
import io.entframework.kernel.system.modular.loginlog.entity.SysLoginLog;
import org.apache.ibatis.annotations.Mapper;

@Mapper
@Entity(SysLoginLog.class)
public interface SysLoginLogMapper extends BaseMapper<SysLoginLog> {
}