package com.xwc.open.easybatis.mybatis.group;

import com.xwc.open.easybatis.core.anno.SelectSql;
import com.xwc.open.easybatis.core.interfaces.EasyMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 作者：徐卫超 cc
 * 时间：2020/12/17
 * 描述：用户数据读取
 */
@Mapper
public interface GroupUserMapper extends EasyMapper<GroupUser, String> {

    @SelectSql("job")
    List<Integer> groupJob();
}
