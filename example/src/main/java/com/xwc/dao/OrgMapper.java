package com.xwc.dao;

import com.xwc.dto.OrgQuery;
import com.xwc.entity.Org;
import com.xwc.entity.User;
import com.xwc.esbatis.anno.Count;
import com.xwc.esbatis.anno.Distinct;
import com.xwc.esbatis.anno.GenerateSelectQuery;
import com.xwc.esbatis.anno.GenerateSelectSql;
import com.xwc.esbatis.anno.condition.enhance.In;
import com.xwc.esbatis.anno.condition.enhance.RightLike;
import com.xwc.esbatis.interfaces.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.context.annotation.Primary;

import java.util.ArrayList;
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


    @GenerateSelectSql
    List<Org> listByOrg(String name, @RightLike String code, @In ArrayList<Integer> sons);

    @GenerateSelectSql(colums = "name")
    List<Org> listNameByOrg(String name, @RightLike String code, @In ArrayList<Integer> sons);
}
