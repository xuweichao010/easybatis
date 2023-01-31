package com.xwc.open.easybatis.supports;

import com.xwc.open.easy.parse.model.*;
import com.xwc.open.easy.parse.model.parameter.*;
import com.xwc.open.easybatis.EasyBatisConfiguration;
import com.xwc.open.easybatis.MyBatisSnippetUtils;
import com.xwc.open.easybatis.annotaions.conditions.Between;
import com.xwc.open.easybatis.annotaions.conditions.Equal;
import com.xwc.open.easybatis.annotaions.other.Dynamic;
import com.xwc.open.easybatis.binding.BatisColumnAttribute;
import com.xwc.open.easybatis.exceptions.ParamCheckException;
import com.xwc.open.easybatis.snippet.column.DefaultInsertColumn;
import com.xwc.open.easybatis.snippet.column.DefaultSelectColumnSnippet;
import com.xwc.open.easybatis.snippet.column.InsertColumnSnippet;
import com.xwc.open.easybatis.snippet.column.SelectColumnSnippet;
import com.xwc.open.easybatis.snippet.conditional.BetweenConditionalSnippet;
import com.xwc.open.easybatis.snippet.conditional.EqualsConditionalSnippet;
import com.xwc.open.easybatis.snippet.from.DefaultInsertFrom;
import com.xwc.open.easybatis.snippet.from.DefaultSelectFrom;
import com.xwc.open.easybatis.snippet.from.InsertFromSnippet;
import com.xwc.open.easybatis.snippet.from.SelectFromSnippet;
import com.xwc.open.easybatis.snippet.order.DefaultOrderSnippet;
import com.xwc.open.easybatis.snippet.order.OrderSnippet;
import com.xwc.open.easybatis.snippet.values.DefaultInsertValues;
import com.xwc.open.easybatis.snippet.values.InsertValuesSnippet;
import com.xwc.open.easybatis.snippet.where.DefaultWhereSnippet;
import com.xwc.open.easybatis.snippet.where.WhereSnippet;
import org.apache.ibatis.mapping.SqlCommandType;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 类描述：
 * 作者：徐卫超 (cc)
 * 时间 2023/1/12 15:05
 */
public class DefaultSqlSourceGenerator implements SqlSourceGenerator {

    private EasyBatisConfiguration easyBatisConfiguration;

    private ColumnPlaceholder columnPlaceholder;

    private InsertFromSnippet insertSqlFrom;

    private InsertColumnSnippet insertColumnSnippet;

    private InsertValuesSnippet insertValuesSnippet;

    private ConditionalRegistry conditionalRegistry;

    private SelectColumnSnippet selectColumnSnippet;

    private SelectFromSnippet selectSqlFrom;

    private WhereSnippet whereSnippet;

    private OrderSnippet orderSnippet;


    public DefaultSqlSourceGenerator(EasyBatisConfiguration easyMyBatisConfiguration) {
        this.easyBatisConfiguration = easyMyBatisConfiguration;
        this.insertSqlFrom = new DefaultInsertFrom();
        this.insertColumnSnippet = new DefaultInsertColumn(new DefaultColumnPlaceholder());
        this.insertValuesSnippet = new DefaultInsertValues(new MybatisPlaceholder());
        this.selectColumnSnippet = new DefaultSelectColumnSnippet(new DefaultColumnPlaceholder());
        this.selectSqlFrom = new DefaultSelectFrom(this.selectColumnSnippet);
        this.conditionalRegistry = new DefaultConditionalRegistry();
        this.conditionalRegistry.register(Equal.class, new EqualsConditionalSnippet(new MybatisPlaceholder()));
        this.conditionalRegistry.register(Between.class, new BetweenConditionalSnippet(new MybatisPlaceholder()));
        this.whereSnippet = new DefaultWhereSnippet(this.conditionalRegistry, new MybatisPlaceholder());
        this.orderSnippet = new DefaultOrderSnippet(new MybatisPlaceholder());
    }

    @Override
    public String select(OperateMethodMeta operateMethodMeta) {
        return MyBatisSnippetUtils.script(doSelect(operateMethodMeta));
    }

    private String doSelect(OperateMethodMeta operateMethodMeta) {
        boolean multi = isMulti(operateMethodMeta, SqlCommandType.SELECT);
        boolean methodDynamic = isMethodDynamic(operateMethodMeta, SqlCommandType.SELECT);
        List<BatisColumnAttribute> batisColumnAttributes = new ArrayList<>();
        for (ParameterAttribute parameterAttribute : operateMethodMeta.getParameterAttributes()) {
            if (parameterAttribute instanceof EntityParameterAttribute) {
                List<BatisColumnAttribute> entityParameterAttribute =
                        flatEntityParameterAttribute(parameterAttribute, operateMethodMeta.getDatabaseMeta(),
                                multi, methodDynamic, SqlCommandType.SELECT);
                parameterAttribute.setMulti(multi);
                batisColumnAttributes.addAll(entityParameterAttribute);
            } else if (parameterAttribute instanceof BaseParameterAttribute) {
                batisColumnAttributes.add(convertParameterAttribute(parameterAttribute, multi, methodDynamic));
            } else if (parameterAttribute instanceof PrimaryKeyParameterAttribute) {
                batisColumnAttributes.add(convertPrimaryKeyParameterAttribute((PrimaryKeyParameterAttribute) parameterAttribute, multi, methodDynamic));
            } else if (parameterAttribute instanceof ObjectParameterAttribute) {

            } else {
                throw new ParamCheckException("SELECT 语句不支持该类型的参数：" + parameterAttribute.getParameterName());
            }
        }
        return this.selectSqlFrom.from(operateMethodMeta) +
                this.whereSnippet.where(batisColumnAttributes) +
                this.orderSnippet.order(operateMethodMeta, batisColumnAttributes);
    }


    @Override
    public String insert(OperateMethodMeta operateMethodMeta) {
        boolean multi = isMulti(operateMethodMeta, SqlCommandType.INSERT);
        boolean methodDynamic = isMethodDynamic(operateMethodMeta, SqlCommandType.INSERT);
        List<BatisColumnAttribute> batisColumnAttributes = null;
        EntityParameterAttribute entityParameterAttribute = null;
        for (ParameterAttribute parameterAttribute : operateMethodMeta.getParameterAttributes()) {
            if (parameterAttribute instanceof EntityParameterAttribute) {
                entityParameterAttribute = (EntityParameterAttribute) parameterAttribute;
                batisColumnAttributes = flatEntityParameterAttribute(parameterAttribute,
                        operateMethodMeta.getDatabaseMeta(), multi, methodDynamic, SqlCommandType.INSERT);
                parameterAttribute.setMulti(multi);
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
                insertColumnSnippet.columns(batisColumnAttributes) + " VALUES" +
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
        int paramNum = operateMethodMeta.paramSize();
        if (sqlCommandType == SqlCommandType.SELECT) {
            if (operateMethodMeta.getDatabaseMeta().getLogic() != null) {
                paramNum += 1;
            }
        } else if (SqlCommandType.INSERT == sqlCommandType) {
            if (operateMethodMeta.getParameterAttributes().size() != 1) {
                throw new ParamCheckException("构建的INSERT语句时 参数列表错误");
            }
            ParameterAttribute parameterAttribute = operateMethodMeta.getParameterAttributes().get(0);
            if (parameterAttribute instanceof EntityParameterAttribute) {
                return paramNum > 1;
            } else {
                throw new ParamCheckException("构建的INSERT语句时 不支持的参数类型：" + parameterAttribute.getParameterName());
            }
        }
        return paramNum > 1;
    }


    /**
     * 判断构建语句是否需要进行动态语句构建
     *
     * @param operateMethodMeta
     * @param sqlCommandType
     * @return
     */
    public boolean isMethodDynamic(OperateMethodMeta operateMethodMeta, SqlCommandType sqlCommandType) {
        if (sqlCommandType == SqlCommandType.INSERT || sqlCommandType == SqlCommandType.SELECT) {
            return operateMethodMeta.findAnnotation(Dynamic.class) != null;
        } else {
            return false;
        }
    }


    public List<BatisColumnAttribute> flatEntityParameterAttribute(
            ParameterAttribute parameterAttribute,
            TableMeta tableMeta,
            boolean isMultiParam,
            boolean dynamic,
            SqlCommandType sqlCommandType) {

        List<BatisColumnAttribute> list = new ArrayList<>();
        int paramIndex = parameterAttribute.getIndex() * 1000;
        // 主键只有在插入的时候可以被放入到SQL中
        if (!isModelAttributeIgnore(tableMeta.getPrimaryKey(), sqlCommandType)) {
            list.add(convertModelAttribute(parameterAttribute,
                    tableMeta.getPrimaryKey(),
                    paramIndex,
                    isMultiParam, dynamic));
        }
        List<ModelAttribute> normalAttr = tableMeta.getNormalAttr();
        for (int i = 0; i < normalAttr.size(); i++) {
            ModelAttribute modelAttribute = normalAttr.get(i);
            if (!this.isModelAttributeIgnore(modelAttribute, sqlCommandType)) {
                list.add(convertModelAttribute(parameterAttribute, modelAttribute, paramIndex + 200 + i, isMultiParam
                        , dynamic));
            }
        }
        List<FillAttribute> fillAttr = tableMeta.getFills();
        for (int i = 0; i < fillAttr.size(); i++) {
            ModelAttribute modelAttribute = fillAttr.get(i);
            if (!this.isModelAttributeIgnore(modelAttribute, sqlCommandType)) {
                list.add(convertModelAttribute(parameterAttribute, modelAttribute, paramIndex + 200 + i, isMultiParam
                        , dynamic));
            }
        }
        LogicAttribute logic = tableMeta.getLogic();
        if (logic != null && !isModelAttributeIgnore(logic, sqlCommandType)) {
            list.add(convertModelAttribute(parameterAttribute, logic, paramIndex + 300, isMultiParam, dynamic));
        }
        return list.stream().sorted(Comparator.comparingInt(BatisColumnAttribute::getIndex)).collect(Collectors.toList());
    }


    public boolean isModelAttributeIgnore(ModelAttribute modelAttribute, SqlCommandType sqlCommandType) {
        if (sqlCommandType == SqlCommandType.INSERT && modelAttribute.isInsertIgnore()) {
            return true;
        } else if (sqlCommandType == SqlCommandType.SELECT && modelAttribute.isSelectIgnore()) {
            return true;
        } else {
            return false;
        }
    }

    private BatisColumnAttribute convertModelAttribute(ParameterAttribute parameterAttribute,
                                                       ModelAttribute modelAttribute,
                                                       int modelAttributeIndex,
                                                       boolean isMultiParam,
                                                       boolean methodDynamic) {
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
        attribute.setMethodDynamic(methodDynamic);
        return attribute;
    }

    private BatisColumnAttribute convertParameterAttribute(ParameterAttribute parameterAttribute,
                                                           boolean isMultiParam, boolean methodDynamic) {
        BatisColumnAttribute attribute = new BatisColumnAttribute();
        attribute.setIndex(parameterAttribute.getIndex() * 1000);
        attribute.setColumn(easyBatisConfiguration.getColumnNameConverter().convert(parameterAttribute.getParameterName()));
        attribute.setParameterName(parameterAttribute.getParameterName());
        attribute.setPath(new String[]{parameterAttribute.getParameterName()});
        attribute.addAnnotations(parameterAttribute.annotations());
        attribute.setMulti(isMultiParam);
        attribute.setMethodDynamic(methodDynamic);
        return attribute;
    }

    private BatisColumnAttribute convertPrimaryKeyParameterAttribute(PrimaryKeyParameterAttribute parameterAttribute,
                                                                     boolean multi, boolean methodDynamic) {
        BatisColumnAttribute attribute = convertParameterAttribute(parameterAttribute, multi, methodDynamic);
        attribute.setColumn(parameterAttribute.getPrimaryKey().getColumn());
        return attribute;
    }

    private boolean hasMapParameterAttribute(Set<ParameterAttribute> parameterAttributes) {
        return null != parameterAttributes.stream()
                .filter(parameterAttribute -> parameterAttribute instanceof MapParameterAttribute)
                .findAny()
                .orElse(null);
    }


}
