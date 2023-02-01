package com.xwc.open.easybatis.mapper;

import com.xwc.open.easy.parse.annotations.Ignore;
import com.xwc.open.easy.parse.supports.EasyMapper;
import com.xwc.open.easybatis.annotaions.InsertSql;
import com.xwc.open.easybatis.annotaions.SelectSql;
import com.xwc.open.easybatis.annotaions.conditions.Between;
import com.xwc.open.easybatis.annotaions.conditions.Equal;
import com.xwc.open.easybatis.annotaions.order.Asc;
import com.xwc.open.easybatis.annotaions.order.Desc;
import com.xwc.open.easybatis.annotaions.order.OrderBy;
import com.xwc.open.easybatis.annotaions.other.Count;
import com.xwc.open.easybatis.annotaions.other.Distinct;
import com.xwc.open.easybatis.annotaions.other.Dynamic;
import com.xwc.open.easybatis.annotaions.page.Limit;
import com.xwc.open.easybatis.annotaions.page.Offset;
import com.xwc.open.easybatis.dto.NormalUserQueryDto;
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
    List<NormalUser> queryObject(NormalUserQueryDto query);

    @SelectSql
    List<NormalUser> queryObjectIgnore(@Ignore String tableName, NormalUserQueryDto query);

    @SelectSql
    @Dynamic
    List<NormalUser> dynamicQueryObject(NormalUserQueryDto query);

    @SelectSql
    @Dynamic
    List<NormalUser> dynamicQueryObjectIgnore(@Ignore String tableName, NormalUserQueryDto query);

    @SelectSql
    @Count
    Long queryObjectCount(NormalUserQueryDto query);


    @SelectSql("age")
    @Distinct
    List<Integer> queryObjectDistinct(NormalUserQueryDto query);


    @SelectSql("age")
    @Distinct
    @Count
    Long queryObjectCountDistinct(NormalUserQueryDto query);


    @Delete("DELETE FROM t_user WHERE data_type =2")
    int delTestData();


}
