package com.xwc.open.easybatis.mysql.mybatis.auto;

import com.xwc.open.easybatis.core.support.BaseTableMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 作者：Clay(徐卫超 )
 * 时间：2021/3/25
 * 描述：自增长主键测试
 */
@Mapper
public interface AutoIdTableMapper extends BaseTableMapper<AutoTableRole,Long> {
}
