package com.xwc.open.easybatis.mysql.mybatis.auto;

import com.xwc.open.easybatis.core.anno.InsertSql;
import com.xwc.open.easybatis.core.support.BaseMapper;
import com.xwc.open.easybatis.mysql.mybatis.auto.AutoRole;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.SelectKey;

/**
 * 作者：Clay(徐卫超 )
 * 时间：2021/3/25
 * 描述：自增长主键测试
 */
@Mapper
public interface AutoIdMapper extends BaseMapper<AutoRole,Long> {
}
