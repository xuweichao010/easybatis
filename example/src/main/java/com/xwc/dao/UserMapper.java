package com.xwc.dao;

import com.xwc.entity.User;
        import com.xwc.esbatis.interfaces.BaseMapper;
        import org.apache.ibatis.annotations.Mapper;

/**
 * 创建人：徐卫超
 * 创建时间：2019/4/24  13:39
 * 业务：
 * 功能：
 */
@Mapper
public interface UserMapper extends BaseMapper<User, Long> {
}
