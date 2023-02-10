package com.xwc.open.easybatis.samples.mapper;

import com.xwc.open.easy.parse.supports.EasyMapper;
import com.xwc.open.easybatis.annotaions.SelectSql;
import com.xwc.open.easybatis.annotaions.conditions.Equal;
import com.xwc.open.easybatis.samples.entity.User;
import org.apache.ibatis.annotations.Mapper;

/**
 * 类描述：
 * 作者：徐卫超 (cc)
 * 时间 2023/2/10 16:36
 */
@Mapper
public interface UserMapper extends EasyMapper<User, String> {

    @SelectSql
    User findOne(String id);
}
