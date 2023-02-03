package io.entframework.kernel.system.modular.notice.mapper;

import io.entframework.kernel.db.api.annotation.Entity;
import io.entframework.kernel.db.mds.mapper.BaseMapper;
import io.entframework.kernel.system.modular.notice.entity.SysNotice;
import org.apache.ibatis.annotations.Mapper;

@Mapper
@Entity(SysNotice.class)
public interface SysNoticeMapper extends BaseMapper<SysNotice> {
}