package com.xwc.open.easybatis.supports;

import com.xwc.open.easy.parse.model.*;
import com.xwc.open.easy.parse.model.parameter.CollectionEntityParameterAttribute;
import com.xwc.open.easy.parse.model.parameter.EntityParameterAttribute;
import com.xwc.open.easy.parse.model.parameter.MapParameterAttribute;
import com.xwc.open.easybatis.EasyBatisConfiguration;
import com.xwc.open.easybatis.MyBatisSnippetUtils;
import com.xwc.open.easybatis.EasyBatisSourceGenerator;
import com.xwc.open.easybatis.binding.BatisColumnAttribute;
import com.xwc.open.easybatis.exceptions.ParamCheckException;
import com.xwc.open.easybatis.snippet.column.DefaultInsertColumn;
import com.xwc.open.easybatis.snippet.column.InsertColumnSnippet;
import com.xwc.open.easybatis.snippet.from.DefaultInsertFrom;
import com.xwc.open.easybatis.snippet.from.SqlFromSnippet;
import com.xwc.open.easybatis.snippet.values.DefaultInsertValues;
import com.xwc.open.easybatis.snippet.values.InsertValuesSnippet;
import org.apache.ibatis.mapping.SqlCommandType;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * 类描述：
 * 作者：徐卫超 (cc)
 * 时间 2023/1/12 15:05
 */
public class DefaultEasyBatisSourceGenerator implements EasyBatisSourceGenerator {

    private final EasyBatisConfiguration easyBatisConfiguration;

    private SqlFromSnippet insertSqlFrom = new DefaultInsertFrom();

    private InsertColumnSnippet insertColumnSnippet = new DefaultInsertColumn(new DefaultColumnPlaceholder());

    private InsertValuesSnippet insertValuesSnippet = new DefaultInsertValues(new MybatisPlaceholder());


    public DefaultEasyBatisSourceGenerator(EasyBatisConfiguration easyMyBatisConfiguration) {
        this.easyBatisConfiguration = easyMyBatisConfiguration;
    }

    @Override
    public String select(OperateMethodMeta operateMethodMeta) {
        return null;
    }

    @Override
    public String insert(OperateMethodMeta operateMethodMeta) {
        boolean multi = isMulti(operateMethodMeta, SqlCommandType.INSERT);
        List<BatisColumnAttribute> batisColumnAttributes = null;
        EntityParameterAttribute entityParameterAttribute = null;
        for (ParameterAttribute parameterAttribute : operateMethodMeta.getParameterAttributes()) {
            if (entityParameterAttribute == null && parameterAttribute instanceof EntityParameterAttribute) {
                batisColumnAttributes = flatEntityParameterAttribute(parameterAttribute,
                        operateMethodMeta.getDatabaseMeta(), multi, SqlCommandType.INSERT);
                entityParameterAttribute = (EntityParameterAttribute) parameterAttribute;
                entityParameterAttribute.setMulti(multi);
            } else {
                throw new ParamCheckException("INSERT 语句不支持该类型的参数：" + parameterAttribute.getParameterName());
            }
        }
        if (batisColumnAttributes == null) {
            throw new ParamCheckException("INSERT 语句没有写入数据的类型");
        }
        return MyBatisSnippetUtils.script(doInsert(operateMethodMeta.getDatabaseMeta(), batisColumnAttributes,
                entityParameterAttribute));
    }

    private String doInsert(TableMeta tableMeta, List<BatisColumnAttribute> batisColumnAttributes,
                            EntityParameterAttribute entityParameterAttribute) {
        return insertSqlFrom.from(tableMeta) +
                insertColumnSnippet.columns(batisColumnAttributes) + " VALUES " +
                insertValuesSnippet.values(entityParameterAttribute, batisColumnAttributes);
    }

    @Override
    public String update(OperateMethodMeta operateMethodMeta) {
        return null;
    }

    @Override
    public String delete(OperateMethodMeta operateMethodMeta) {
        return null;
    }


    public boolean isMulti(OperateMethodMeta operateMethodMeta, SqlCommandType sqlCommandType) {
        // 当参数是多个值的话一定是多参数
        if (operateMethodMeta.paramSize() > 1) {
            return true;
        }
        if (sqlCommandType == SqlCommandType.SELECT) {
            return operateMethodMeta.getDatabaseMeta().getLogic() != null;
        } else if (SqlCommandType.INSERT == sqlCommandType) {
            if (operateMethodMeta.getParameterAttributes().size() != 1) {
                throw new ParamCheckException("构建的INSERT语句时 参数列表错误");
            }
            ParameterAttribute parameterAttribute = operateMethodMeta.getParameterAttributes().get(0);
            if (parameterAttribute instanceof EntityParameterAttribute) {
                return false;
            } else {
                throw new ParamCheckException("构建的INSERT语句时 不支持的参数类型：" + parameterAttribute.getParameterName());
            }
        }
        return false;
    }


    public List<BatisColumnAttribute> flatEntityParameterAttribute(
            ParameterAttribute parameterAttribute,
            TableMeta tableMeta,
            boolean isMultiParam,
            SqlCommandType sqlCommandType) {

        List<BatisColumnAttribute> list = new ArrayList<>();
        int paramIndex = parameterAttribute.getIndex() * 1000;
        // 主键只有在插入的时候可以被放入到SQL中
        if (!isModelAttributeIgnore(tableMeta.getPrimaryKey(), sqlCommandType)) {
            list.add(convertModelAttribute(parameterAttribute,
                    tableMeta.getPrimaryKey(),
                    paramIndex,
                    isMultiParam));
        }
        List<ModelAttribute> normalAttr = tableMeta.getNormalAttr();
        for (int i = 0; i < normalAttr.size(); i++) {
            ModelAttribute modelAttribute = normalAttr.get(i);
            if (!this.isModelAttributeIgnore(modelAttribute, sqlCommandType)) {
                list.add(convertModelAttribute(parameterAttribute, modelAttribute, paramIndex + 200 + i, isMultiParam));
            }
        }
        List<FillAttribute> fillAttr = tableMeta.getFills();
        for (int i = 0; i < fillAttr.size(); i++) {
            ModelAttribute modelAttribute = fillAttr.get(i);
            if (!this.isModelAttributeIgnore(modelAttribute, sqlCommandType)) {
                list.add(convertModelAttribute(parameterAttribute, modelAttribute, paramIndex + 200 + i, isMultiParam));
            }
        }

        LogicAttribute logic = tableMeta.getLogic();
        if (logic != null && !isModelAttributeIgnore(logic, sqlCommandType)) {
            list.add(convertModelAttribute(parameterAttribute, logic, paramIndex + 300, isMultiParam));
        }
        return list;
    }

    public boolean isModelAttributeIgnore(ModelAttribute modelAttribute, SqlCommandType sqlCommandType) {
        if (sqlCommandType == SqlCommandType.INSERT && modelAttribute.isInsertIgnore()) {
            return true;
        } else {
            return false;
        }
    }

    private BatisColumnAttribute convertModelAttribute(ParameterAttribute parameterAttribute,
                                                       ModelAttribute modelAttribute,
                                                       int modelAttributeIndex,
                                                       boolean isMultiParam) {
        BatisColumnAttribute attribute = new BatisColumnAttribute();
        attribute.setIndex(modelAttributeIndex);
        attribute.setColumn(modelAttribute.getColumn());
        attribute.setParameterName(modelAttribute.getField());
        if (isMultiParam || parameterAttribute instanceof CollectionEntityParameterAttribute) {
            attribute.setPath(new String[]{parameterAttribute.getParameterName(), modelAttribute.getField()});
        } else {
            attribute.setPath(new String[]{modelAttribute.getField()});
        }
        attribute.addAnnotations(modelAttribute.getAnnotations());
        attribute.setMulti(isMultiParam || parameterAttribute instanceof CollectionEntityParameterAttribute);
        return attribute;
    }

    private boolean hasMapParameterAttribute(Set<ParameterAttribute> parameterAttributes) {
        return null != parameterAttributes.stream()
                .filter(parameterAttribute -> parameterAttribute instanceof MapParameterAttribute)
                .findAny()
                .orElse(null);
    }


}
