package cn.onetozero.easybatis.snippet.from;

import cn.onetozero.easy.parse.model.OperateMethodMeta;
import cn.onetozero.easybatis.annotaions.SelectJoinSql;
import cn.onetozero.easybatis.exceptions.EasyMybatisException;
import cn.onetozero.easybatis.supports.AbstractBatisSourceGenerator;

/**
 * 作者： 徐卫超
 * 时间： 2023/5/10 16:05
 * 描述：
 */
public class DefaultSelectJoinFrom implements SelectFromSnippet {
    private final AbstractBatisSourceGenerator sourceGenerator;

    public DefaultSelectJoinFrom(AbstractBatisSourceGenerator sourceGenerator) {
        this.sourceGenerator = sourceGenerator;
    }

    @Override
    public String from(OperateMethodMeta operateMethodMeta) {
        SelectJoinSql selectJoinSql = operateMethodMeta.findAnnotation(SelectJoinSql.class);
        if (selectJoinSql == null) {
            throw new EasyMybatisException("找不到 @SelectJoinSql 注解信息");
        }
        return "SELECT " + selectJoinSql.value() + " " +
                "FROM " + selectJoinSql.from();
    }
}
