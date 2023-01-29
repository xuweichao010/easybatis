package com.xwc.open.easybatis.mapper;

import com.xwc.open.easy.parse.annotations.Ignore;
import com.xwc.open.easy.parse.supports.EasyMapper;
import com.xwc.open.easybatis.annotaions.InsertSql;
import com.xwc.open.easybatis.annotaions.SelectSql;
import com.xwc.open.easybatis.annotaions.conditions.Equal;
import com.xwc.open.easybatis.entity.NormalUser;
import com.xwc.open.easybatis.entity.UserObject;
import org.apache.ibatis.annotations.Delete;

import java.util.List;

/**
 * 类描述：
 * 作者：徐卫超 (cc)
 * 时间 2023/1/14 11:28
 */
@SuppressWarnings("unused")
public interface SimpleSourceGeneratorMapper extends EasyMapper<NormalUser, String> {

    @InsertSql
    int insert(NormalUser normalUser);

    @InsertSql
    int insertIgnore(@Ignore String tableName, NormalUser user);

    @InsertSql
    int insertBatch(List<NormalUser> users);

    @InsertSql
    int insertBatchIgnore(@Ignore String tableName, List<NormalUser> users);

    //@InsertSql
    int insertObject(UserObject userObject);

    @SelectSql
    NormalUser findOne(String id);

    @SelectSql
    NormalUser findOneDynamic(@Equal(dynamic = true) String id);

    @SelectSql
    List<NormalUser> findAll();


    @Delete("DELETE FROM t_user WHERE data_type =2")
    int delTestData();
}
