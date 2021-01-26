package com.xwc.open.easybatis.mysql.mybatis.auditor;

import com.xwc.open.easybatis.core.support.BaseTableMapper;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface AuditorTableUserMapper extends BaseTableMapper<AuditorTableUser, String> {
}
