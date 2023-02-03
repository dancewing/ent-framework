package io.entframework.kernel.sms.modular.mapper;

import io.entframework.kernel.db.api.annotation.Entity;
import io.entframework.kernel.db.mds.mapper.BaseMapper;
import io.entframework.kernel.sms.modular.entity.SysSms;
import org.apache.ibatis.annotations.Mapper;

@Mapper
@Entity(SysSms.class)
public interface SysSmsMapper extends BaseMapper<SysSms> {
}