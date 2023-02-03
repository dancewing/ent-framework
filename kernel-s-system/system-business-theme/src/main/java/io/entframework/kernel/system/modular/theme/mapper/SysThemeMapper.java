package io.entframework.kernel.system.modular.theme.mapper;

import io.entframework.kernel.db.api.annotation.Entity;
import io.entframework.kernel.db.mds.mapper.BaseMapper;
import io.entframework.kernel.system.modular.theme.entity.SysTheme;
import org.apache.ibatis.annotations.Mapper;

@Mapper
@Entity(SysTheme.class)
public interface SysThemeMapper extends BaseMapper<SysTheme> {
}