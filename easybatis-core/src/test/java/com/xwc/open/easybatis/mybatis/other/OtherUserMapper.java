package com.xwc.open.easybatis.mybatis.other;

import com.xwc.open.easybatis.core.anno.SelectSql;
import com.xwc.open.easybatis.core.anno.condition.filter.DESC;
import com.xwc.open.easybatis.core.interfaces.EasyMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 作者：徐卫超 cc
 * 时间：2020/12/17
 * 描述：用户数据读取
 */
@Mapper
public interface OtherUserMapper extends EasyMapper<OtherUser, String> {

    @SelectSql("job")
    List<Integer> groupJob();

    @SelectSql
    List<OtherUser> orderBy(@DESC Boolean age);
}
