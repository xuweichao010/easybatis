package com.xwc.open.easybatis.mapper;

import com.xwc.open.easy.parse.annotations.Ignore;
import com.xwc.open.easy.parse.supports.EasyMapper;
import com.xwc.open.easybatis.annotaions.DeleteSql;
import com.xwc.open.easybatis.annotaions.InsertSql;
import com.xwc.open.easybatis.annotaions.SelectSql;
import com.xwc.open.easybatis.annotaions.UpdateSql;
import com.xwc.open.easybatis.annotaions.conditions.Equal;
import com.xwc.open.easybatis.annotaions.other.Dynamic;
import com.xwc.open.easybatis.entity.LogicUser;
import com.xwc.open.easybatis.entity.NormalUser;
import com.xwc.open.easybatis.model.NormalUserDeleteObject;
import com.xwc.open.easybatis.model.NormalUserPageQueryDto;
import com.xwc.open.easybatis.model.NormalUserUpdateObject;
import org.apache.ibatis.annotations.Delete;

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

    @UpdateSql
    int update(LogicUser normalUser);

    @UpdateSql
    int updateParam(@Equal() String id, String name);

    @UpdateSql
    int updateObject(NormalUserUpdateObject object);


    @SelectSql
    NormalUser findOne(String id);


    @SelectSql
    NormalUser findOne2(String id, Integer age);


    @SelectSql
    NormalUser findOneDynamicIgnore(@Ignore String tableName, @Equal(dynamic = true) String id);

    @SelectSql
    List<NormalUser> findAll();

    @SelectSql
    List<NormalUser> queryObject(NormalUserPageQueryDto query);


    @DeleteSql
    int del(String id);

    @DeleteSql
    int delObject(NormalUserDeleteObject object);

    @DeleteSql
    @Dynamic
    int delDynamicObjectIgnore(@Ignore String tableName, NormalUserDeleteObject object);

    @Delete("DELETE FROM t_user WHERE data_type =2")
    int delTestData();


}
