package com.xwc.open.easybatis.mysql.mybatis.auditor;

import com.xwc.open.easybatis.core.support.BaseMapper;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface AuditorUserMapper extends BaseMapper<AuditorUser, String> {

    @Delete("DELETE FROM t_user WHERE valid = #{valid}")
    int deleteByValid(Integer valid);

}
