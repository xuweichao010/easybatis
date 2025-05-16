package cn.onetozero.easybatis.mapper;

import cn.onetozero.easy.annotations.models.Ignore;
import cn.onetozero.easy.parse.supports.EasyMapper;
import cn.onetozero.easy.annotations.InsertSql;
import cn.onetozero.easy.annotations.SelectSql;

import java.util.List;

/**
 * 类描述：
 * @author  徐卫超 (cc)
 * @since 2023/1/17 13:22
 */
public interface BaseMapper<E, K> extends EasyMapper<E, K> {

    @InsertSql
    int insert(E normalUser);

    @InsertSql
    int insertIgnore(@Ignore String tableName, E user);

    @InsertSql
    int insertBatch(List<E> users);

    @InsertSql
    int insertBatchIgnore(@Ignore String tableName, List<E> users);

    @SelectSql
    E selectKey(K key);

    @SelectSql
    E selectKeyIgnore(@Ignore String tableName, K id);
}
