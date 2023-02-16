package cn.onetozero.easybatis.mapper;

import cn.onetozero.easy.parse.annotations.Ignore;
import cn.onetozero.easy.parse.supports.EasyMapper;
import cn.onetozero.easybatis.annotaions.InsertSql;
import cn.onetozero.easybatis.annotaions.SelectSql;
import cn.onetozero.easybatis.annotaions.UpdateSql;
import cn.onetozero.easybatis.annotaions.conditions.Equal;
import cn.onetozero.easybatis.annotaions.other.Dynamic;
import cn.onetozero.easybatis.annotaions.set.SetParam;
import cn.onetozero.easybatis.model.NormalUserUpdateObject;
import cn.onetozero.easybatis.entity.FillUser;
import org.apache.ibatis.annotations.Delete;

import java.util.List;

/**
 * 类描述：用于填充的单元测试程序
 * 作者：徐卫超 (cc)
 * 时间 2023/1/14 11:28
 */
@SuppressWarnings("unused")
public interface FillSourceGeneratorMapper extends EasyMapper<FillUser, String> {

    @SelectSql
    FillUser findOne(String id);

    @InsertSql
    int insert(FillUser normalUser);

    @InsertSql
    int insertIgnore(@Ignore String tableName, FillUser user);

    @InsertSql
    int insertBatch(List<FillUser> users);

    @InsertSql
    int insertBatchIgnore(@Ignore String tableName, List<FillUser> users);


    @UpdateSql
    int update(FillUser normalUser);

    @UpdateSql
    @Dynamic
    int updateDynamic(FillUser normalUser);

    @UpdateSql
    int updateParam(@Equal() String id, String name);

    @UpdateSql
    int updateParamDynamic(@Equal() String id, @SetParam(dynamic = true) String name,
                           @SetParam(dynamic = true, value = "age") Integer ageAlias);


    @UpdateSql
    @Dynamic
    int dynamicUpdateParam(@Equal String id, @SetParam() String name,
                           @SetParam(value = "age") Integer ageAlias);

    @UpdateSql
    int updateObject(NormalUserUpdateObject object);

    @UpdateSql
    int updateObjectIgnore(@Ignore String tableName, NormalUserUpdateObject object);


    @UpdateSql
    @Dynamic
    int dynamicUpdateObject(NormalUserUpdateObject object);

    @UpdateSql
    @Dynamic
    int dynamicUpdateMixture(@Equal String name, NormalUserUpdateObject object);


    @Delete("DELETE FROM t_user WHERE data_type =2")
    int delTestData();

}
