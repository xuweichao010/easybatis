package cn.onetozero.easybatis.mapper;

import cn.onetozero.easy.parse.supports.EasyMapper;
import cn.onetozero.easy.annotations.DeleteSql;
import cn.onetozero.easy.annotations.InsertSql;
import cn.onetozero.easy.annotations.SelectSql;
import cn.onetozero.easy.annotations.UpdateSql;
import cn.onetozero.easy.annotations.conditions.Between;
import cn.onetozero.easy.annotations.conditions.In;
import cn.onetozero.easy.annotations.conditions.LikeRight;
import cn.onetozero.easy.annotations.order.Asc;
import cn.onetozero.easy.annotations.order.Desc;
import cn.onetozero.easy.annotations.other.Dynamic;
import cn.onetozero.easybatis.entity.NormalUser;
import org.apache.ibatis.annotations.Mapper;

import java.util.Collection;
import java.util.List;

/**
 * 作者： 徐卫超
 * @since  2023/9/25 14:52
 * 描述：
 */
@Mapper
public interface NormalUserMapper extends EasyMapper<NormalUser, String> {

    /**
     * 根据条件查询数据并排序
     */
    @SelectSql(databaseId = "db2")
    List<NormalUser> query(
            @In List<String> id,
            @Between(of = "ageTo") int age, int ageTo,
            @LikeRight(value = "org_code") String orgCodes,
            @Desc boolean orgCode, @Asc(dynamic = true) Boolean job);

    /**
     * 根据主键查询一条数据
     */
    @SelectSql
    NormalUser selectKey(String key);

    /**
     * 根据主键批量查询数据
     */
    List<NormalUser> selectKeys(@In Collection<String> keys);

    /**
     * 写入一条数据到数据库中
     */
    @InsertSql
    int insert(NormalUser entity);

    /**
     * 写入多套数据到数据库中
     */
    @InsertSql
    int insertBatch(Collection<NormalUser> entities);

    /**
     * 根据主键更新一条数据到数据库中
     */
    @UpdateSql
    int update(NormalUser entity);

    /**
     * 批量修改数据库
     */
    @UpdateSql
    int updateBatch(Collection<NormalUser> entities);

    /**
     * 根据主键动态更新一条数据到数据库中
     */
    @UpdateSql
    @Dynamic
    int updateActivity(NormalUser entity);

    /**
     * 根据主键删除一条数据
     */
    @DeleteSql
    int deleteKey(String key);

    /**
     * 根据主键删除多条数据
     */
    @DeleteSql
    int deleteKeys(@In List<String> keys);
}
