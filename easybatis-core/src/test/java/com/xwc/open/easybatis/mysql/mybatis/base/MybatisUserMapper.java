package com.xwc.open.easybatis.mysql.mybatis.base;

import com.xwc.open.easybatis.core.anno.DeleteSql;
import com.xwc.open.easybatis.core.anno.SelectSql;
import com.xwc.open.easybatis.core.anno.UpdateSql;
import com.xwc.open.easybatis.core.anno.condition.filter.Equal;
import com.xwc.open.easybatis.core.anno.condition.filter.SetParam;
import com.xwc.open.easybatis.core.support.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 作者：徐卫超 cc
 * 时间：2020/12/17
 * 描述：用户数据读取
 */
@Mapper
public interface MybatisUserMapper extends BaseMapper<MybatisUser, String> {


    @SelectSql(dynamic = true)
    List<MybatisUser> methodGlobalDynamic(String orgName, String orgCode);

    @SelectSql
    List<MybatisUser> methodParamDynamic(@Equal(dynamic = true) String orgName, @Equal(dynamic = true) String orgCode);

    @SelectSql
    List<MybatisUser> methodCustom(MybatisUserOne one);

    @SelectSql
    List<MybatisUser> methodMultiCustom(MybatisUserOne one, MybatisUserTwo two);

    @SelectSql
    List<MybatisUser> methodMixture(@Equal(dynamic = true) String name, @Equal String orgCode, MybatisUserTwo two);

    @DeleteSql
    Integer clearTest(int valid);

    @UpdateSql
    Integer updateParamCondition(@SetParam Integer job, @SetParam Integer age, String id);

    @UpdateSql
    Integer updateParam(@SetParam Integer job, @SetParam Integer age);

    @DeleteSql
    Integer deleteParam( String name, Integer age);

}
