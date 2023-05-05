package cn.onetozero.easybatis;

import cn.onetozero.easy.parse.supports.EasyMapper;
import cn.onetozero.easybatis.annotaions.DeleteSql;
import cn.onetozero.easybatis.annotaions.InsertSql;
import cn.onetozero.easybatis.annotaions.SelectSql;
import cn.onetozero.easybatis.annotaions.UpdateSql;
import cn.onetozero.easybatis.annotaions.conditions.In;
import cn.onetozero.easybatis.annotaions.other.Dynamic;

import java.util.Collection;
import java.util.List;

/**
 * 类描述：通用Mapper
 * 作者：徐卫超 (cc)
 * 时间 2023/2/13 9:52
 */
public interface BaseMapper<E, K> extends EasyMapper<E, K> {

    /**
     * 根据主键查询一条数据
     *
     * @param key 主键数据
     * @return 查询的结果
     */
    @SelectSql
    E selectKey(K key);

    /**
     * 根据主键批量查询数据
     *
     * @param keys 主键数据集合
     * @return 查询的结果
     */
    List<E> selectKeys(@In Collection<K> keys);

    /**
     * 写入一条数据到数据库中
     *
     * @param entity 写入数据的目标数据
     * @return 写入的结果
     */
    @InsertSql
    int insert(E entity);

    /**
     * 写入多套数据到数据库中
     *
     * @param entities 写入数据的目标数据
     * @return 写入的结果
     */
    @InsertSql
    int insertBatch(Collection<E> entities);

    /**
     * 根据主键更新一条数据到数据库中
     *
     * @param entity 更新的数据
     * @return 更新结果
     */
    @UpdateSql
    int update(E entity);


    @UpdateSql
    int updateBatch(Collection<E> entities);

    /**
     * 根据主键动态更新一条数据到数据库中
     *
     * @param entity 更新的数据
     * @return 更新结果
     */
    @UpdateSql
    @Dynamic
    int updateActivity(E entity);

    /**
     * 根据主键删除一条数据
     *
     * @param key 主键数据
     * @return 删除的结果
     */
    @DeleteSql
    int deleteKey(K key);

    /**
     * 根据主键删除多条数据
     *
     * @param keys 删除
     * @return 删除的结果
     */
    @DeleteSql
    int deleteKeys(@In List<K> keys);
}
