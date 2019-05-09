package com.xwc.dao;

import com.xwc.dto.OrgQuery;
import com.xwc.entity.Org;
import com.xwc.entity.User;
import com.xwc.esbatis.anno.Count;
import com.xwc.esbatis.anno.GenerateSelectQuery;
import com.xwc.esbatis.interfaces.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 创建人：徐卫超
 * 创建时间：2019/4/24  13:39
 * 业务：
 * 功能：
 */
@Mapper
public interface OrgMapper extends BaseMapper<Org, String> {

    @GenerateSelectQuery
    List<Org> list(OrgQuery query);


    @GenerateSelectQuery
    @Count
    Long count(OrgQuery query);


}
