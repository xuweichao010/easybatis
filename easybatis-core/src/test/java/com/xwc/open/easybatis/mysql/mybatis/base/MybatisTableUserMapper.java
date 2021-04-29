package com.xwc.open.easybatis.mysql.mybatis.base;

import com.xwc.open.easybatis.core.anno.DeleteSql;
import com.xwc.open.easybatis.core.anno.SelectSql;
import com.xwc.open.easybatis.core.anno.UpdateSql;
import com.xwc.open.easybatis.core.anno.condition.filter.Equal;
import com.xwc.open.easybatis.core.anno.condition.filter.SetParam;
import com.xwc.open.easybatis.core.anno.table.Ignore;
import com.xwc.open.easybatis.core.support.BaseTableMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 作者：徐卫超 cc
 * 时间：2020/12/17
 * 描述：用户数据读取
 */
@Mapper
public interface MybatisTableUserMapper extends BaseTableMapper<MybatisTableUser, String> {

    @SelectSql(dynamic = true)
    List<MybatisTableUser> methodGlobalDynamic(@Ignore String tableName, String orgName, String orgCode);

    @SelectSql
    List<MybatisTableUser> methodParamDynamic(@Ignore String tableName, @Equal(dynamic = true) String orgName, @Equal(dynamic = true) String orgCode);

    @SelectSql
    List<MybatisTableUser> methodCustom(@Ignore String tableName, MybatisUserOne mybatisUserOne);

    @SelectSql
    List<MybatisTableUser> methodMultiCustom(@Ignore String tableName, MybatisUserOne one, MybatisUserTwo two);

    @SelectSql
    List<MybatisTableUser> methodMixture(@Ignore String tableName, @Equal(dynamic = true) String name, @Equal String orgCode, MybatisUserTwo two);

    @DeleteSql
    Integer clearTest(@Ignore String tableName, int valid);


    @UpdateSql
    Integer updateParamCondition(@Ignore String tableName, @SetParam Integer job, @SetParam Integer age, String id);

    @UpdateSql
    Integer updateParam(@Ignore String tableName, @SetParam Integer job, @SetParam Integer age);

    @DeleteSql
    Integer deleteParam(@Ignore String tableName, String name, Integer age);
}
