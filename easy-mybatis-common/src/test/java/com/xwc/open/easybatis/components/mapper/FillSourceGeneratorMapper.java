package com.xwc.open.easybatis.components.mapper;

import com.xwc.open.easy.parse.annotations.Ignore;
import com.xwc.open.easy.parse.supports.EasyMapper;
import com.xwc.open.easybatis.annotaions.InsertSql;
import com.xwc.open.easybatis.entity.FillLogicUser;
import com.xwc.open.easybatis.entity.FillUser;
import com.xwc.open.easybatis.entity.NormalUser;

import java.util.List;

/**
 * 类描述：
 * 作者：徐卫超 (cc)
 * 时间 2023/1/14 11:28
 */
@SuppressWarnings("unused")
public interface FillSourceGeneratorMapper extends EasyMapper<FillUser, String> {

    @InsertSql
    int insert(FillUser normalUser);

    @InsertSql
    int insertIgnore(@Ignore String tableName, FillUser user);

    @InsertSql
    int insertBatch(List<FillUser> users);

    @InsertSql
    int insertBatchIgnore(@Ignore String tableName, List<FillUser> users);
}
