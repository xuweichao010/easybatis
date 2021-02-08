package com.xwc.open.easybatis.boot.test.mapper;

import com.xwc.open.easybatis.boot.test.entity.User;
import com.xwc.open.easybatis.core.anno.SelectSql;
import com.xwc.open.easybatis.core.support.BaseMapper;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper extends BaseMapper<User, String> {

    @SelectSql
    User findByName(String name);
}
