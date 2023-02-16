package cn.onetozero.easybatis.mapper;

import cn.onetozero.easy.parse.annotations.Ignore;
import cn.onetozero.easy.parse.supports.EasyMapper;
import cn.onetozero.easybatis.annotaions.InsertSql;
import cn.onetozero.easybatis.entity.FillLogicUser;

import java.util.List;

/**
 * 类描述：
 * 作者：徐卫超 (cc)
 * 时间 2023/1/14 11:28
 */
@SuppressWarnings("unused")
public interface FillAndLogicSourceGeneratorMapper extends EasyMapper<FillLogicUser, String> {

    @InsertSql
    int insert(FillLogicUser user);

    @InsertSql
    int insertIgnore(@Ignore String tableName, FillLogicUser user);

    @InsertSql
    int insertBatch(List<FillLogicUser> users);

    @InsertSql
    int insertBatchIgnore(@Ignore String tableName, List<FillLogicUser> users);
}
