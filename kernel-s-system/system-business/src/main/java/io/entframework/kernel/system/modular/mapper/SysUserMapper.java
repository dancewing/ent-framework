package io.entframework.kernel.system.modular.mapper;

import io.entframework.kernel.db.api.annotation.Entity;
import io.entframework.kernel.db.mds.mapper.BaseMapper;
import io.entframework.kernel.system.modular.entity.SysUser;
import org.apache.ibatis.annotations.Mapper;

@Mapper
@Entity(SysUser.class)
public interface SysUserMapper extends BaseMapper<SysUser> {
}