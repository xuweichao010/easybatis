package com.xwc.open.easybatis.start.mapper;

import com.xwc.open.easybatis.core.anno.DeleteSql;
import com.xwc.open.easybatis.core.anno.SelectSql;
import com.xwc.open.easybatis.core.anno.UpdateSql;
import com.xwc.open.easybatis.core.anno.condition.ASC;
import com.xwc.open.easybatis.core.anno.condition.Count;
import com.xwc.open.easybatis.core.anno.condition.DESC;
import com.xwc.open.easybatis.core.anno.condition.Distinct;
import com.xwc.open.easybatis.core.anno.condition.filter.*;
import com.xwc.open.easybatis.core.support.BaseMapper;
import com.xwc.open.easybatis.start.entity.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface UserMapper extends BaseMapper<User, String> {

    @SelectSql
    List<User> findBy(String name, String orgCode);

    @SelectSql
    List<User> findByCustom(@Equal("name") String customName, @Equal String orgCode);

    @SelectSql
    List<User> findByMultiCondition(@Like(dynamic = true) String orgCode, @In List<String> id, String name);


    @SelectSql(dynamic = true)
    List<User> findByDynamic(String name, String orgCode);

    @SelectSql
    List<User> findByParamDynamic(@Equal(dynamic = true) String name, @Equal(dynamic = true) String orgCode);

    @SelectSql(" id, org_name")
    List<User> findByColumn(String name, String orgCode);

    @UpdateSql
    Integer updateCode(@SetParam String orgCode);

    @UpdateSql
    Integer updateCodeByName(@SetParam String orgCode, String name);

    @DeleteSql
    Integer deleteByOrgName(String orgName);

    @SelectSql
    List<User> orderUser(@ASC Boolean id);

    @SelectSql
    List<User> orderByCUser(String orgCode, @ASC Boolean id);

    @SelectSql
    List<User> orderByUser(String orgCode, @ASC(dynamic = true) Boolean id, @DESC Boolean name);

    @SelectSql
    @Count
    List<User> count(String name, String orgCode);

    @SelectSql(" org_name ")
    @Distinct
    List<User> distinct(String name, String orgCode);

    @SelectSql
    List<User> limit(String name, String orgCode, @Start Integer start, @Offset Integer offset);

    @UpdateSql
    Integer updateParam(String orgCode, @SetParam String orgName);
}
