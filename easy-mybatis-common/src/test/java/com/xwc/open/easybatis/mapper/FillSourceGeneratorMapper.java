package com.xwc.open.easybatis.mapper;

import com.xwc.open.easy.parse.annotations.Ignore;
import com.xwc.open.easy.parse.supports.EasyMapper;
import com.xwc.open.easybatis.annotaions.InsertSql;
import com.xwc.open.easybatis.annotaions.SelectSql;
import com.xwc.open.easybatis.entity.FillUser;
import com.xwc.open.easybatis.entity.NormalUser;
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
    NormalUser findOne(String id);


    @InsertSql
    int insert(FillUser normalUser);

    @InsertSql
    int insertIgnore(@Ignore String tableName, FillUser user);

    @InsertSql
    int insertBatch(List<FillUser> users);

    @InsertSql
    int insertBatchIgnore(@Ignore String tableName, List<FillUser> users);



    @Delete("DELETE FROM t_user WHERE data_type =2")
    int delTestData();

}
