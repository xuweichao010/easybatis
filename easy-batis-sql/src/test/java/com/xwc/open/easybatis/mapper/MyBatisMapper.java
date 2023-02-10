package com.xwc.open.easybatis.mapper;

import com.xwc.open.easybatis.entity.NormalUser;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * 类描述：
 * 作者：徐卫超 (cc)
 * 时间 2023/1/12 17:30
 */
@Mapper
public interface MyBatisMapper {

    @Select("SELECT * FROM t_user")
    List<NormalUser> listByUser();
}
