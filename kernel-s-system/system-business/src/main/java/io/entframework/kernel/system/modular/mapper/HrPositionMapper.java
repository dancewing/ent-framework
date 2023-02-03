package io.entframework.kernel.system.modular.mapper;

import io.entframework.kernel.db.api.annotation.Entity;
import io.entframework.kernel.db.mds.mapper.BaseMapper;
import io.entframework.kernel.system.modular.entity.HrPosition;
import org.apache.ibatis.annotations.Mapper;

@Mapper
@Entity(HrPosition.class)
public interface HrPositionMapper extends BaseMapper<HrPosition> {
}