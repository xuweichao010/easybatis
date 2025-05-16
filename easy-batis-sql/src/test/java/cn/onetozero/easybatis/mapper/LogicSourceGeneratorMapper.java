package cn.onetozero.easybatis.mapper;

import cn.onetozero.easy.annotations.models.Ignore;
import cn.onetozero.easy.parse.supports.EasyMapper;
import cn.onetozero.easy.annotations.DeleteSql;
import cn.onetozero.easy.annotations.InsertSql;
import cn.onetozero.easy.annotations.SelectSql;
import cn.onetozero.easy.annotations.UpdateSql;
import cn.onetozero.easy.annotations.conditions.Equal;
import cn.onetozero.easy.annotations.other.Dynamic;
import cn.onetozero.easybatis.model.NormalUserDeleteObject;
import cn.onetozero.easybatis.model.NormalUserPageQueryDto;
import cn.onetozero.easybatis.model.NormalUserUpdateObject;
import cn.onetozero.easybatis.entity.LogicUser;
import cn.onetozero.easybatis.entity.NormalUser;
import org.apache.ibatis.annotations.Delete;

import java.util.List;

/**
 * 类描述：
 * @author  徐卫超 (cc)
 * @since 2023/1/14 11:28
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
