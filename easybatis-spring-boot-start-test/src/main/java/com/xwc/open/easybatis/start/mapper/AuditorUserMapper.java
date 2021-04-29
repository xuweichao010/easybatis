package com.xwc.open.easybatis.start.mapper;

import com.xwc.open.easybatis.core.support.BaseMapper;
import com.xwc.open.easybatis.start.entity.AuditorUser;
import org.apache.ibatis.annotations.Mapper;

/**
 * 作者：Clay(徐卫超 )
 * 时间：2021/4/23
 * 描述：审计功能演示
 */
@Mapper
public interface AuditorUserMapper extends BaseMapper<AuditorUser, String> {
}