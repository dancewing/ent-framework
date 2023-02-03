package io.entframework.kernel.file.modular.mapper;

import io.entframework.kernel.db.api.annotation.Entity;
import io.entframework.kernel.db.mds.mapper.BaseMapper;
import io.entframework.kernel.file.modular.entity.SysFileInfo;
import org.apache.ibatis.annotations.Mapper;

@Mapper
@Entity(SysFileInfo.class)
public interface SysFileInfoMapper extends BaseMapper<SysFileInfo> {
}