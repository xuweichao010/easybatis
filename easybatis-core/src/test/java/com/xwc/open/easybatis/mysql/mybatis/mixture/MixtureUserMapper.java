package com.xwc.open.easybatis.mysql.mybatis.mixture;

import com.xwc.open.easybatis.core.support.BaseMapper;
import com.xwc.open.easybatis.core.support.BaseTableMapper;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;


@Mapper
public interface MixtureUserMapper extends BaseMapper<MixtureUser, String> {
    @Delete("DELETE FROM t_user WHERE valid = #{valid}")
    int deleteByValid(Integer valid);

}
