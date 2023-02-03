package io.entframework.kernel.system.modular.theme.mapper;

import io.entframework.kernel.db.api.annotation.Entity;
import io.entframework.kernel.db.mds.mapper.BaseMapper;
import io.entframework.kernel.system.modular.theme.entity.SysThemeTemplateField;
import org.apache.ibatis.annotations.Mapper;

@Mapper
@Entity(SysThemeTemplateField.class)
public interface SysThemeTemplateFieldMapper extends BaseMapper<SysThemeTemplateField> {
}