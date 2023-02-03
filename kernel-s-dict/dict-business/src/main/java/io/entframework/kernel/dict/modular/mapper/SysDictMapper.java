package io.entframework.kernel.dict.modular.mapper;

import io.entframework.kernel.db.api.annotation.Entity;
import io.entframework.kernel.db.mds.mapper.BaseMapper;
import io.entframework.kernel.dict.modular.entity.SysDict;
import org.apache.ibatis.annotations.Mapper;

@Mapper
@Entity(SysDict.class)
public interface SysDictMapper extends BaseMapper<SysDict> {
}