package com.xwc.open.easybatis.mysql.parser.select;

import com.xwc.open.easybatis.core.anno.SelectSql;
import com.xwc.open.easybatis.core.anno.condition.filter.Equal;
import com.xwc.open.easybatis.core.support.BaseTableMapper;

/**
 * 作者：徐卫超 cc
 * 时间：2020/12/18
 * 描述：
 */
public interface BaseSelectMapper extends BaseTableMapper<BaseSelectEntity, String> {

    @SelectSql
    void findAll();

    @SelectSql("id")
    void findAll1();

    @SelectSql(dynamic = true)
    void methodGlobalDynamic(String name);

    @SelectSql
    void methodParamDynamic(@Equal(dynamic = true) String name);

    @SelectSql(dynamic = true)
    void methodGlobalMultiDynamic(String name, String orgCode);

    @SelectSql
    void methodParamMultiDynamic(@Equal(dynamic = true) String name, @Equal(dynamic = true) String orgCode);

    @SelectSql
    void methodCustom(BaseSelectFilterThree one);

    @SelectSql
    void methodMultiCustom(BaseSelectFilterOne one, BaseSelectFilterTwo two);

    @SelectSql
    void methodMixture(@Equal String name, @Equal(dynamic = true) String orgCode, BaseSelectFilterTwo two);


}
