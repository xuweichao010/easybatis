package com.xwc.open.easybatis.mapper;

import com.xwc.open.easybatis.entity.NormalUser;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;

/**
 * 类描述：
 * 作者：徐卫超 (cc)
 * 时间 2023/1/17 13:22
 */

@Mapper
public interface GenericsBaseMapper extends BaseMapper<NormalUser, String> {

    @Delete(databaseId = "", value = "DELETE FROM t_user WHERE data_type =2")
    int delTestData();


}
