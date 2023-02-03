package io.entframework.kernel.file.modular.mapper;

import io.entframework.kernel.db.api.annotation.Entity;
import io.entframework.kernel.db.mds.mapper.BaseMapper;
import io.entframework.kernel.file.modular.entity.SysFileStorage;
import org.apache.ibatis.annotations.Mapper;

@Mapper
@Entity(SysFileStorage.class)
public interface SysFileStorageMapper extends BaseMapper<SysFileStorage> {
}