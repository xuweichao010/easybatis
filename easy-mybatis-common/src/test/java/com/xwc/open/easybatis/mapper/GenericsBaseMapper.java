package com.xwc.open.easybatis.mapper;

import com.xwc.open.easy.parse.annotations.Ignore;
import com.xwc.open.easy.parse.supports.EasyMapper;
import com.xwc.open.easybatis.annotaions.InsertSql;
import com.xwc.open.easybatis.entity.NormalUser;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 类描述：
 * 作者：徐卫超 (cc)
 * 时间 2023/1/17 13:22
 */

@Mapper
public interface GenericsBaseMapper extends BaseMapper<NormalUser, String> {


}
