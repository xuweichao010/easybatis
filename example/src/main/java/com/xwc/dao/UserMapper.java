package com.xwc.dao;

import com.xwc.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * 创建人：徐卫超
 * 创建时间：2018/8/15  15:23
 * 功能：
 * 业务：
 */
@Mapper
public interface UserMapper{
    @Select("SELECT * FROM t_user WHERE id = #{id}")
    User get(@Param("id") String id);

}
