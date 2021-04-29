package com.xwc.open.easybatis.start.mapper;

import com.xwc.open.easybatis.core.support.BaseMapper;
import com.xwc.open.easybatis.start.entity.LogicUser;
import org.apache.ibatis.annotations.Mapper;

/**
 * 作者：Clay(徐卫超 )
 * 时间：2021/4/21
 * 描述：
 */
@Mapper
public interface LogicUserMapper extends BaseMapper<LogicUser, String> {
}
