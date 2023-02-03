package io.entframework.kernel.system.modular.mapper;

import io.entframework.kernel.db.api.annotation.Entity;
import io.entframework.kernel.db.mds.mapper.BaseMapper;
import io.entframework.kernel.system.modular.entity.SysRoleDataScope;
import org.apache.ibatis.annotations.Mapper;

@Mapper
@Entity(SysRoleDataScope.class)
public interface SysRoleDataScopeMapper extends BaseMapper<SysRoleDataScope> {
}