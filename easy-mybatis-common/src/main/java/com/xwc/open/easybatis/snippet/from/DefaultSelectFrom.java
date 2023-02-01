package com.xwc.open.easybatis.snippet.from;

import com.xwc.open.easy.parse.model.FillAttribute;
import com.xwc.open.easy.parse.model.ModelAttribute;
import com.xwc.open.easy.parse.model.OperateMethodMeta;
import com.xwc.open.easy.parse.model.TableMeta;
import com.xwc.open.easybatis.snippet.column.SelectColumnSnippet;

import java.util.ArrayList;
import java.util.List;

/**
 * 类描述：用户不配置的时候 框架默认的用于处理 Insert 语句中 INSERT INTO tableName 这部分语句的片段
 * 作者：徐卫超 (cc)
 * 时间 2023/1/16 13:46
 */
public class DefaultSelectFrom implements SelectFromSnippet {

    final SelectColumnSnippet selectColumnSnippet;

    public DefaultSelectFrom(SelectColumnSnippet selectColumnSnippet) {
        this.selectColumnSnippet = selectColumnSnippet;
    }


    @Override
    public String from(OperateMethodMeta operateMethodMeta) {
        List<ModelAttribute> modelAttributes = selectColumnAttribute(operateMethodMeta.getDatabaseMeta());
        return "SELECT " + selectColumnSnippet.columns(operateMethodMeta, modelAttributes) + " FROM " + operateMethodMeta.getDatabaseMeta().getTableName();
    }

    public List<ModelAttribute> selectColumnAttribute(TableMeta tableMeta) {
        List<ModelAttribute> modelAttributes = new ArrayList<>();
        if (!tableMeta.getPrimaryKey().isSelectIgnore()) {
            modelAttributes.add(tableMeta.getPrimaryKey());
        }

        if (tableMeta.getLogic() != null && !tableMeta.getLogic().isSelectIgnore()) {
            modelAttributes.add(tableMeta.getLogic());
        }
        for (FillAttribute fill : tableMeta.getFills()) {
            if (!fill.isSelectIgnore()) {
                modelAttributes.add(fill);
            }
        }
        for (ModelAttribute modelAttribute : tableMeta.getNormalAttr()) {
            if (!modelAttribute.isSelectIgnore()) {
                modelAttributes.add(modelAttribute);
            }
        }
        return modelAttributes;
    }
}
