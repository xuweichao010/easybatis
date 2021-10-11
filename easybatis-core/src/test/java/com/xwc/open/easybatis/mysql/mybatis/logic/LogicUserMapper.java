package com.xwc.open.easybatis.mysql.mybatis.logic;

import com.xwc.open.easybatis.core.anno.SelectSql;
import com.xwc.open.easybatis.core.support.BaseMapper;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * 作者：徐卫超 cc
 * 时间：2020/12/17
 * 描述：用户数据读取
 */
@Mapper
public interface LogicUserMapper extends BaseMapper<LogicUser, String> {

    @Delete("DELETE FROM t_user WHERE valid = #{valid}")
    int deleteByValid(Integer valid);

    @Select(" SELECT `id`, `org_code`, `org_name`, `name`, `age`, `job` FROM t_user WHERE `id` = #{id} AND `valid` = #{valid} ")
    LogicUser selectKey1(String id, Integer valid);

    @SelectSql
    List<LogicUser> list(UserFilter filter);

    @SelectSql
    List<LogicUser> findAll();
}
