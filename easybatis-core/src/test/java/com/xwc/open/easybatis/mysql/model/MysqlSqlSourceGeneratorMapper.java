package com.xwc.open.easybatis.mysql.model;

import com.xwc.open.easybatis.core.anno.SelectSql;
import com.xwc.open.easybatis.core.anno.condition.PrimaryKey;
import com.xwc.open.easybatis.core.interfaces.EasyMapper;
import com.xwc.open.easybatis.model.User;

/**
 * 作者：徐卫超 cc
 * 时间：2020/12/18
 * 描述：
 */
public interface MysqlSqlSourceGeneratorMapper extends EasyMapper<MysqlSqlSource, String> {

    @SelectSql
    void findAll();

    @SelectSql("id")
    void findAll1();

    @SelectSql
    @PrimaryKey
    void selectKey(String key);

}
