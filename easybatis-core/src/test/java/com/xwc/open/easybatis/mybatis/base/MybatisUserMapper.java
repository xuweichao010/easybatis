package com.xwc.open.easybatis.mybatis.base;

import com.xwc.open.easybatis.core.anno.SelectSql;
import com.xwc.open.easybatis.core.anno.condition.filter.Equal;
import com.xwc.open.easybatis.core.interfaces.BaseMapper;
import com.xwc.open.easybatis.model.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 作者：徐卫超 cc
 * 时间：2020/12/17
 * 描述：用户数据读取
 */
@Mapper
public interface MybatisUserMapper extends BaseMapper<MybatisUser, String> {

    @SelectSql
    User get(String id);

    @SelectSql(dynamic = true)
    List<MybatisUser> methodGlobalDynamic(String orgName, String orgCode);

    @SelectSql
    List<MybatisUser> methodParamDynamic(@Equal(dynamic = true) String name, @Equal(dynamic = true) String orgCode);

    @SelectSql
    List<MybatisUser> methodCustom(MybatisUserOne one);


    @SelectSql
    List<MybatisUser> methodMultiCustom(MybatisUserOne one, MybatisUserTwo two);

    @SelectSql
    List<MybatisUser> methodMixture(@Equal(dynamic = true) String name, @Equal String orgCode, MybatisUserTwo two);
}
