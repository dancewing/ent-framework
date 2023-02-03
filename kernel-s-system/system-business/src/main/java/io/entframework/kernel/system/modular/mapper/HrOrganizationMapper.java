package io.entframework.kernel.system.modular.mapper;

import io.entframework.kernel.db.api.annotation.Entity;
import io.entframework.kernel.db.mds.mapper.BaseMapper;
import io.entframework.kernel.system.modular.entity.HrOrganization;
import org.apache.ibatis.annotations.Mapper;

@Mapper
@Entity(HrOrganization.class)
public interface HrOrganizationMapper extends BaseMapper<HrOrganization> {
}