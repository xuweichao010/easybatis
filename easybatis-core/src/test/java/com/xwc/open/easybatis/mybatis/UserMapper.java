package com.xwc.open.easybatis.mybatis;

import com.xwc.open.easybatis.core.anno.SelectSql;
import com.xwc.open.easybatis.core.interfaces.EasyMapper;
import com.xwc.open.easybatis.model.User;
import org.apache.ibatis.annotations.Mapper;

/**
 * 作者：徐卫超 cc
 * 时间：2020/12/17
 * 描述：用户数据读取
 */
@Mapper
public interface UserMapper extends EasyMapper<User, String> {

//    @Select(" SELECT * FROM t_user WHERE id = #{userId} ")
//    User find(String userId);
//
//    @Select("<script> SELECT * FROM t_user WHERE id = #{userId} AND org_code = #{user.orgCode} AND age >= #{user.age} </script>")
//    User find1(String userId, User user);

    @SelectSql
    User get(String id);
}
