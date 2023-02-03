package io.entframework.kernel.i18n.modular.mapper;

import io.entframework.kernel.db.api.annotation.Entity;
import io.entframework.kernel.db.mds.mapper.BaseMapper;
import io.entframework.kernel.i18n.modular.entity.Translation;
import org.apache.ibatis.annotations.Mapper;

@Mapper
@Entity(Translation.class)
public interface TranslationMapper extends BaseMapper<Translation> {
}