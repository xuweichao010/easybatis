package cn.onetozero.easybatis.mapper;

import cn.onetozero.easy.parse.supports.EasyMapper;
import cn.onetozero.easybatis.annotaions.SelectJoinSql;
import cn.onetozero.easybatis.annotaions.conditions.Equal;
import cn.onetozero.easybatis.entity.NormalUser;

import java.util.List;

/**
 * 类描述：
 * 作者：徐卫超 (cc)
 * 时间 2023/1/14 11:28
 */
@SuppressWarnings("unused")
public interface JoinSourceGeneratorMapper extends EasyMapper<NormalUser, String> {

    @SelectJoinSql(value = "t.*", from = "t_user t INNER JOIN t_org o ON t.org_code = o.code ")
    List<NormalUser> joinUser();


    @SelectJoinSql(value = "t.*", from = "t_user t INNER JOIN t_org o ON t.org_code = o.code ")
    List<NormalUser> joinUserByCode(@Equal(alias = "o") String code);

}
