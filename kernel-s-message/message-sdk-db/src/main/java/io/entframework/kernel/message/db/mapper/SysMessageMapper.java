package io.entframework.kernel.message.db.mapper;

import io.entframework.kernel.db.api.annotation.Entity;
import io.entframework.kernel.db.mds.mapper.BaseMapper;
import io.entframework.kernel.message.db.entity.SysMessage;
import org.apache.ibatis.annotations.Mapper;

@Mapper
@Entity(SysMessage.class)
public interface SysMessageMapper extends BaseMapper<SysMessage> {
}