package cn.onetozero.easybatis.snippet.from;

import cn.onetozero.easy.parse.model.FillAttribute;
import cn.onetozero.easy.parse.model.ModelAttribute;
import cn.onetozero.easy.parse.model.OperateMethodMeta;
import cn.onetozero.easy.parse.model.TableMeta;
import cn.onetozero.easybatis.supports.AbstractBatisSourceGenerator;

import java.util.ArrayList;
import java.util.List;

/**
 * 类描述：用户不配置的时候 框架默认的用于处理 Insert 语句中 INSERT INTO tableName 这部分语句的片段
 * @author  徐卫超 (cc)
 * @since 2023/1/16 13:46
 */
public class DefaultSelectFrom implements SelectFromSnippet {


    private final AbstractBatisSourceGenerator sourceGenerator;

    public DefaultSelectFrom(AbstractBatisSourceGenerator sourceGenerator) {
        this.sourceGenerator = sourceGenerator;
    }


    @Override
    public String from(OperateMethodMeta operateMethodMeta) {
        List<ModelAttribute> modelAttributes = selectColumnAttribute(operateMethodMeta.getDatabaseMeta());
        return "SELECT " + sourceGenerator.getSelectColumnSnippet().columns(operateMethodMeta, modelAttributes) + " " +
                "FROM " + operateMethodMeta.getDatabaseMeta().getTableName();
    }

    public List<ModelAttribute> selectColumnAttribute(TableMeta tableMeta) {
        List<ModelAttribute> modelAttributes = new ArrayList<>();
        if (!tableMeta.getPrimaryKey().isSelectIgnore()) {
            modelAttributes.add(tableMeta.getPrimaryKey());
        }
        for (ModelAttribute modelAttribute : tableMeta.getNormalAttr()) {
            if (!modelAttribute.isSelectIgnore()) {
                modelAttributes.add(modelAttribute);
            }
        }
        for (FillAttribute fill : tableMeta.getFills()) {
            if (!fill.isSelectIgnore()) {
                modelAttributes.add(fill);
            }
        }
        if (tableMeta.getLogic() != null && !tableMeta.getLogic().isSelectIgnore()) {
            modelAttributes.add(tableMeta.getLogic());
        }
        return modelAttributes;
    }
}
