package io.entframework.kernel.system.modular.mapper;

import io.entframework.kernel.db.api.annotation.Entity;
import io.entframework.kernel.db.mds.mapper.BaseMapper;
import io.entframework.kernel.system.modular.entity.SysMenu;
import org.apache.ibatis.annotations.Mapper;

@Mapper
@Entity(SysMenu.class)
public interface SysMenuMapper extends BaseMapper<SysMenu> {
}