package cn.onetozero.easybatis.samples.mapper;

import cn.onetozero.easy.parse.supports.EasyMapper;
import cn.onetozero.easybatis.annotaions.SelectSql;
import cn.onetozero.easybatis.samples.entity.User;
import org.apache.ibatis.annotations.Mapper;

/**
 * 类描述：
 * 作者：徐卫超 (cc)
 * 时间 2023/2/10 16:36
 */
@Mapper
public interface UserMapper extends EasyMapper<User, String> {

    @SelectSql
    User findOne(String id);
}
