package com.xwc.dao;

import com.xwc.entity.User;
import com.xwc.entity.UserQuery;
import com.xwc.esbatis.anno.Count;
import com.xwc.esbatis.anno.Distinct;
import com.xwc.esbatis.anno.GenerateSelectQuery;
import com.xwc.esbatis.anno.GenerateSelectSql;
import com.xwc.esbatis.anno.condition.Equal;
import com.xwc.esbatis.anno.condition.FilterBody;
import com.xwc.esbatis.interfaces.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 创建人：徐卫超
 * 创建时间：2019/4/24  13:39
 * 业务：
 * 功能：
 */
@Mapper
public interface BaseUserMapper extends BaseMapper<User, String> {

    @GenerateSelectQuery
    List<User> list(@FilterBody UserQuery filter);

    @Count
    @GenerateSelectQuery
    Long count(@FilterBody UserQuery filter);

    @GenerateSelectSql
    @Count(colums = "name")
    @Distinct()
    List<User> byName(@Equal String name);
}
