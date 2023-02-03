package io.entframework.kernel.system.modular.theme.mapper;

import io.entframework.kernel.db.api.annotation.Entity;
import io.entframework.kernel.db.mds.mapper.BaseMapper;
import io.entframework.kernel.system.modular.theme.entity.SysThemeTemplateRel;
import org.apache.ibatis.annotations.Mapper;

@Mapper
@Entity(SysThemeTemplateRel.class)
public interface SysThemeTemplateRelMapper extends BaseMapper<SysThemeTemplateRel> {
}