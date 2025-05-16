package cn.onetozero.easybatis.mapper;

import cn.onetozero.easy.parse.supports.EasyMapper;
import cn.onetozero.easy.annotations.SelectJoinSql;
import cn.onetozero.easy.annotations.conditions.Equal;
import cn.onetozero.easybatis.entity.NormalUser;

import java.util.List;

/**
 * 类描述：
 * @author  徐卫超 (cc)
 * @since 2023/1/14 11:28
 */
@SuppressWarnings("unused")
public interface JoinSourceGeneratorMapper extends EasyMapper<NormalUser, String> {

    @SelectJoinSql(value = "t.*", from = "t_user t INNER JOIN t_org o ON t.org_code = o.code ")
    List<NormalUser> joinUser();


    @SelectJoinSql(value = "t.*", from = "t_user t INNER JOIN t_org o ON t.org_code = o.code ")
    List<NormalUser> joinUserByCode(@Equal(alias = "o") String code);

}
