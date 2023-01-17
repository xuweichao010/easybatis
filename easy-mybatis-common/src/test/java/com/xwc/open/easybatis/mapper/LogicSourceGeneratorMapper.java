package com.xwc.open.easybatis.mapper;

import com.xwc.open.easy.parse.annotations.Ignore;
import com.xwc.open.easy.parse.supports.EasyMapper;
import com.xwc.open.easybatis.annotaions.InsertSql;
import com.xwc.open.easybatis.entity.FillLogicUser;
import com.xwc.open.easybatis.entity.FillUser;
import com.xwc.open.easybatis.entity.LogicUser;
import com.xwc.open.easybatis.entity.NormalUser;

import java.util.List;

/**
 * 类描述：
 * 作者：徐卫超 (cc)
 * 时间 2023/1/14 11:28
 */
@SuppressWarnings("unused")
public interface LogicSourceGeneratorMapper extends EasyMapper<LogicUser, String> {

    @InsertSql
    int insert(LogicUser user);

    @InsertSql
    int insertIgnore(@Ignore String tableName, LogicUser user);

    @InsertSql
    int insertBatch(List<LogicUser> users);

    @InsertSql
    int insertBatchIgnore(@Ignore String tableName, List<LogicUser> users);
}
