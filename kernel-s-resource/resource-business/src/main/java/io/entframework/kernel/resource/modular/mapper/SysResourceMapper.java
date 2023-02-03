package io.entframework.kernel.resource.modular.mapper;

import io.entframework.kernel.db.api.annotation.Entity;
import io.entframework.kernel.db.mds.mapper.BaseMapper;
import io.entframework.kernel.resource.modular.entity.SysResource;
import org.apache.ibatis.annotations.Mapper;

@Mapper
@Entity(SysResource.class)
public interface SysResourceMapper extends BaseMapper<SysResource> {
}