package cn.onetozero.easybatis.mapper;

import cn.onetozero.easy.parse.annotations.Ignore;
import cn.onetozero.easy.parse.supports.EasyMapper;
import cn.onetozero.easybatis.annotaions.DeleteSql;
import cn.onetozero.easybatis.annotaions.InsertSql;
import cn.onetozero.easybatis.annotaions.SelectSql;
import cn.onetozero.easybatis.annotaions.UpdateSql;
import cn.onetozero.easybatis.annotaions.conditions.*;
import cn.onetozero.easybatis.annotaions.other.Count;
import cn.onetozero.easybatis.annotaions.other.Distinct;
import cn.onetozero.easybatis.annotaions.other.Dynamic;
import cn.onetozero.easybatis.annotaions.page.Limit;
import cn.onetozero.easybatis.annotaions.page.Offset;
import cn.onetozero.easybatis.annotaions.set.SetParam;
import cn.onetozero.easybatis.model.*;
import com.xwc.open.easybatis.annotaions.conditions.*;
import cn.onetozero.easybatis.annotaions.order.Asc;
import cn.onetozero.easybatis.annotaions.order.Desc;
import cn.onetozero.easybatis.annotaions.order.OrderBy;
import cn.onetozero.easybatis.entity.NormalUser;
import cn.onetozero.easybatis.entity.UserObject;
import com.xwc.open.easybatis.model.*;
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
    NormalUser findOneIgnore(@Ignore String tableName, String id);

    @SelectSql
    NormalUser findOneDynamic(@Equal(dynamic = true) String id);

    @SelectSql
    NormalUser findOneDynamicIgnore(@Ignore String tableName, @Equal(dynamic = true) String id);

    @SelectSql
    List<NormalUser> findAll();

    @SelectSql
    List<NormalUser> between(@Between(of = "ageTo") int age, int ageTo);

    @SelectSql
    List<NormalUser> betweenIgnore(@Ignore String tableName, @Between(of = "ageTo") int age, int ageTo);

    @SelectSql
    List<NormalUser> betweenDynamic(@Between(of = "ageTo", dynamic = true) Integer age, Integer ageTo);

    @SelectSql
    List<NormalUser> in(@In List<String> id);

    @SelectSql
    List<NormalUser> inIgnore(@Ignore String tableName, @In List<String> id);

    @SelectSql
    List<NormalUser> inObject(QueryInObject query);

    @SelectSql()
    @OrderBy("age desc")
    List<NormalUser> methodOrder();

    @SelectSql
    List<NormalUser> orderDesc(@Desc boolean age);

    @SelectSql
    List<NormalUser> orderAsc(@Asc boolean age);

    @SelectSql
    @Dynamic
    List<NormalUser> dynamicOrderDesc(@Desc boolean age);

    @SelectSql
    List<NormalUser> orderDescDynamic(@Desc(dynamic = true) Boolean age);

    @SelectSql
    List<NormalUser> orderAscDynamic(@Asc(dynamic = true) Boolean age);

    @SelectSql
    List<NormalUser> orderMixture(@Asc(dynamic = true) Boolean age, @Desc boolean orgCode,
                                  @Asc(dynamic = true) Boolean job);

    @SelectSql
    List<NormalUser> page(@Limit Integer limit, @Offset Integer offset);


    @SelectSql
    List<NormalUser> limit(@Limit Integer limit);

    @SelectSql
    List<NormalUser> queryObject(NormalUserPageQueryDto query);

    @SelectSql
    List<NormalUser> queryObjectIgnore(@Ignore String tableName, NormalUserPageQueryDto query);

    @SelectSql
    @Dynamic
    List<NormalUser> dynamicQueryObject(NormalUserPageQueryDto query);

    @SelectSql
    @Dynamic
    List<NormalUser> dynamicQueryObjectIgnore(@Ignore String tableName, NormalUserPageQueryDto query);

    @SelectSql
    @Count
    Long queryObjectCount(NormalUserPageQueryDto query);


    @SelectSql("age")
    @Distinct
    List<Integer> queryObjectDistinct(NormalUserPageQueryDto query);


    @SelectSql("age")
    @Distinct
    @Count
    Long queryObjectCountDistinct(NormalUserPageQueryDto query);

    @SelectSql
    List<NormalUser> queryMultiObject(NormalUseQueryDto query, PageQueryDto page);

    @UpdateSql
    int update(NormalUser normalUser);

    @SelectSql
    List<NormalUser> notEqual(@NotEqual Integer age);

    @SelectSql
    List<NormalUser> isNull(@IsNull Integer age);

    @SelectSql
    List<NormalUser> isNotNull(@IsNotNull Integer age);

    @SelectSql
    List<NormalUser> like(@Like String orgCode);

    @SelectSql
    List<NormalUser> likeLeft(@LikeLeft String orgCode);

    @SelectSql
    List<NormalUser> likeRight(@LikeRight String orgCode);

    @SelectSql
    List<NormalUser> greaterThan(@GreaterThan Integer age);

    @SelectSql
    List<NormalUser> greaterThanEqual(@GreaterThanEqual Integer age);

    @SelectSql
    List<NormalUser> lessThan(@LessThan Integer age);

    @SelectSql
    List<NormalUser> lessThanEqual(@LessThanEqual Integer age);


    @UpdateSql
    @Dynamic
    int updateDynamic(NormalUser normalUser);

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

    @DeleteSql
    int del(String id);

    @DeleteSql
    @Dynamic
    int dynamicDel(String id, String name);

    @DeleteSql
    int delObject(NormalUserDeleteObject object);

    @DeleteSql
    @Dynamic
    int delDynamicObjectIgnore(@Ignore String tableName, NormalUserDeleteObject object);


    @Delete(databaseId = "", value = "DELETE FROM t_user WHERE data_type =2")
    int delTestData();


}
