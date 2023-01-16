package com.xwc.open.easybatis.components.mapper;

import com.xwc.open.easy.parse.annotations.Ignore;
import com.xwc.open.easy.parse.supports.EasyMapper;
import com.xwc.open.easybatis.annotaions.InsertSql;
import com.xwc.open.easybatis.entity.NormalUser;
import com.xwc.open.easybatis.entity.UserObject;

import java.util.List;

/**
 * 类描述：
 * 作者：徐卫超 (cc)
 * 时间 2023/1/14 11:28
 */
@SuppressWarnings("unused")
public interface SimpleSourceGeneratorMapper extends EasyMapper<NormalUser, String> {

    @InsertSql
    int simpleInsert(NormalUser normalUser);

    @InsertSql
    int insertIgnore(@Ignore String tableName, NormalUser user);

    @InsertSql
    int insertBatch(List<NormalUser> users);

    @InsertSql
    int insertBatchIgnore(@Ignore String tableName, List<NormalUser> users);

    @InsertSql
    int insertObject(UserObject userObject);
}
