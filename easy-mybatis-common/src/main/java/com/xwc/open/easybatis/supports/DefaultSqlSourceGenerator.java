package com.xwc.open.easybatis.supports;

import com.xwc.open.easy.parse.annotations.Ignore;
import com.xwc.open.easy.parse.annotations.Syntax;
import com.xwc.open.easy.parse.model.*;
import com.xwc.open.easy.parse.model.parameter.*;
import com.xwc.open.easy.parse.utils.AnnotationUtils;
import com.xwc.open.easy.parse.utils.Reflection;
import com.xwc.open.easybatis.EasyBatisConfiguration;
import com.xwc.open.easybatis.MyBatisSnippetUtils;
import com.xwc.open.easybatis.annotaions.conditions.*;
import com.xwc.open.easybatis.annotaions.other.Count;
import com.xwc.open.easybatis.binding.BatisColumnAttribute;
import com.xwc.open.easybatis.exceptions.ParamCheckException;
import com.xwc.open.easybatis.snippet.column.DefaultInsertColumn;
import com.xwc.open.easybatis.snippet.column.DefaultSelectColumnSnippet;
import com.xwc.open.easybatis.snippet.column.InsertColumnSnippet;
import com.xwc.open.easybatis.snippet.column.SelectColumnSnippet;
import com.xwc.open.easybatis.snippet.conditional.*;
import com.xwc.open.easybatis.snippet.from.*;
import com.xwc.open.easybatis.snippet.order.DefaultOrderSnippet;
import com.xwc.open.easybatis.snippet.order.OrderSnippet;
import com.xwc.open.easybatis.snippet.page.DefaultPageSnippet;
import com.xwc.open.easybatis.snippet.page.PageSnippet;
import com.xwc.open.easybatis.snippet.set.DefaultSetSnippet;
import com.xwc.open.easybatis.snippet.set.SetSnippet;
import com.xwc.open.easybatis.snippet.values.DefaultInsertValues;
import com.xwc.open.easybatis.snippet.values.InsertValuesSnippet;
import com.xwc.open.easybatis.snippet.where.DefaultWhereSnippet;
import com.xwc.open.easybatis.snippet.where.WhereSnippet;
import org.apache.ibatis.mapping.SqlCommandType;

import java.lang.reflect.Field;
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

    private ColumnPlaceholder columnPlaceholder = new DefaultColumnPlaceholder();

    private BatisPlaceholder batisPlaceholder = new MybatisPlaceholder();

    private InsertFromSnippet insertSqlFrom;

    private InsertColumnSnippet insertColumnSnippet;

    private InsertValuesSnippet insertValuesSnippet;

    private ConditionalRegistry conditionalRegistry;

    private SelectColumnSnippet selectColumnSnippet;

    private SelectFromSnippet selectSqlFrom;

    private WhereSnippet whereSnippet;

    private OrderSnippet orderSnippet;

    private PageSnippet pageSnippet;

    private UpdateFromSnippet updateFromSnippet;

    private SetSnippet setSnippet;

    private DeleteFromSnippet deleteFromSnippet;


    public DefaultSqlSourceGenerator(EasyBatisConfiguration easyMyBatisConfiguration) {
        this.easyBatisConfiguration = easyMyBatisConfiguration;
        this.insertSqlFrom = new DefaultInsertFrom();
        this.insertColumnSnippet = new DefaultInsertColumn(new DefaultColumnPlaceholder());
        this.insertValuesSnippet = new DefaultInsertValues(new MybatisPlaceholder());
        this.selectColumnSnippet = new DefaultSelectColumnSnippet(new DefaultColumnPlaceholder());
        this.selectSqlFrom = new DefaultSelectFrom(this.selectColumnSnippet);
        this.conditionalRegistry = new DefaultConditionalRegistry();
        this.whereSnippet = new DefaultWhereSnippet(this.conditionalRegistry, new MybatisPlaceholder());
        this.orderSnippet = new DefaultOrderSnippet(new MybatisPlaceholder());
        this.pageSnippet = new DefaultPageSnippet(new MybatisPlaceholder());
        this.updateFromSnippet = new DefaultUpdateFromSnippet();
        this.setSnippet = new DefaultSetSnippet(batisPlaceholder, columnPlaceholder);
        this.deleteFromSnippet = new DefaultDeleteFromSnippet();
        this.registryDefaultConditional();
    }

    public void registryDefaultConditional() {
        // 比较查询
        this.conditionalRegistry.register(Equal.class,
                new EqualConditional(batisPlaceholder, columnPlaceholder));
        this.conditionalRegistry.register(NotEqual.class,
                new NotEqualsConditional(batisPlaceholder, columnPlaceholder));
        this.conditionalRegistry.register(GreaterThan.class,
                new GreaterThanConditional(batisPlaceholder, columnPlaceholder));
        this.conditionalRegistry.register(GreaterThanEqual.class,
                new GreaterThanEqualConditional(batisPlaceholder, columnPlaceholder));
        this.conditionalRegistry.register(LessThan.class,
                new LessThanConditional(batisPlaceholder, columnPlaceholder));
        this.conditionalRegistry.register(LessThanEqual.class,
                new LessThanEqualConditional(batisPlaceholder, columnPlaceholder));

        // 模糊查询
        this.conditionalRegistry.register(Like.class,
                new LikeConditional(batisPlaceholder, columnPlaceholder));
        this.conditionalRegistry.register(LikeLeft.class,
                new LikeLeftConditional(batisPlaceholder, columnPlaceholder));
        this.conditionalRegistry.register(LikeRight.class,
                new LikeRightConditional(batisPlaceholder, columnPlaceholder));

        // 空判断
        this.conditionalRegistry.register(IsNull.class,
                new IsNullConditional(batisPlaceholder, columnPlaceholder));
        this.conditionalRegistry.register(IsNotNull.class,
                new IsNotNullConditional(batisPlaceholder, columnPlaceholder));

        // 范围查询
        this.conditionalRegistry.register(Between.class,
                new BetweenConditional(batisPlaceholder, columnPlaceholder));
        this.conditionalRegistry.register(In.class,
                new InConditional(batisPlaceholder, columnPlaceholder));

    }

    @Override
    public String select(OperateMethodMeta operateMethodMeta) {
        return MyBatisSnippetUtils.script(doSelect(operateMethodMeta));
    }

    private String doSelect(OperateMethodMeta operateMethodMeta) {
        boolean multi = SqlSourceGenerator.isMulti(operateMethodMeta, SqlCommandType.SELECT);
        boolean methodDynamic = SqlSourceGenerator.isMethodDynamic(operateMethodMeta, SqlCommandType.SELECT);
        List<BatisColumnAttribute> batisColumnAttributes = new ArrayList<>();
        for (ParameterAttribute parameterAttribute : operateMethodMeta.getParameterAttributes()) {
            if (parameterAttribute instanceof BaseParameterAttribute) {
                batisColumnAttributes.add(convertParameterAttribute(parameterAttribute, multi, methodDynamic, SqlCommandType.SELECT));
            } else if (parameterAttribute instanceof PrimaryKeyParameterAttribute) {
                batisColumnAttributes.add(convertPrimaryKeyParameterAttribute((PrimaryKeyParameterAttribute) parameterAttribute, multi, methodDynamic, SqlCommandType.SELECT));
            } else if (parameterAttribute instanceof ObjectParameterAttribute) {
                List<BatisColumnAttribute> objectAttributes =
                        analysisObjectAttribute((ObjectParameterAttribute) parameterAttribute, multi, methodDynamic, SqlCommandType.SELECT);
                parameterAttribute.setMulti(multi);
                batisColumnAttributes.addAll(objectAttributes);
            } else {
                throw new ParamCheckException(operateMethodMeta.getMethodName() + "查询语句不支持该类型的参数：" + parameterAttribute.getParameterName());
            }
        }
        int index = operateMethodMeta.getParameterAttributes().size();
        LogicAttribute logic = operateMethodMeta.getDatabaseMeta().getLogic();
        if (logic != null) {
            batisColumnAttributes.add(convertModelAttribute(logic, ++index, multi, false,
                    SqlCommandType.SELECT));
        }
        StringBuilder sql = new StringBuilder(this.selectSqlFrom.from(operateMethodMeta))
                .append(this.whereSnippet.where(batisColumnAttributes));
        if (!operateMethodMeta.containsAnnotation(Count.class)) {
            sql.append(this.orderSnippet.order(operateMethodMeta, batisColumnAttributes))
                    .append(this.pageSnippet.page(batisColumnAttributes));
        }
        return sql.toString();
    }

    @Override
    public String insert(OperateMethodMeta operateMethodMeta) {
        boolean multi = SqlSourceGenerator.isMulti(operateMethodMeta, SqlCommandType.INSERT);
        boolean methodDynamic = SqlSourceGenerator.isMethodDynamic(operateMethodMeta, SqlCommandType.INSERT);
        List<BatisColumnAttribute> batisColumnAttributes = null;
        EntityParameterAttribute entityParameterAttribute = null;
        for (ParameterAttribute parameterAttribute : operateMethodMeta.getParameterAttributes()) {
            if (parameterAttribute instanceof EntityParameterAttribute) {
                entityParameterAttribute = (EntityParameterAttribute) parameterAttribute;
                batisColumnAttributes = analysisEntityParameterAttribute(entityParameterAttribute, multi, methodDynamic, SqlCommandType.INSERT);
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
        return MyBatisSnippetUtils.script(doUpdate(operateMethodMeta));
    }

    public String doUpdate(OperateMethodMeta operateMethodMeta) {
        boolean multi = SqlSourceGenerator.isMulti(operateMethodMeta, SqlCommandType.UPDATE);
        boolean methodDynamic = SqlSourceGenerator.isMethodDynamic(operateMethodMeta, SqlCommandType.UPDATE);
        List<BatisColumnAttribute> batisColumnAttributes = new ArrayList<>();
        boolean isSetFill = false;
        boolean isSetLogic = false;
        int index = operateMethodMeta.getParameterAttributes().size();
        for (ParameterAttribute parameterAttribute : operateMethodMeta.getParameterAttributes()) {
            if (parameterAttribute instanceof EntityParameterAttribute) {
                EntityParameterAttribute entityParameterAttribute = (EntityParameterAttribute) parameterAttribute;
                List<BatisColumnAttribute> entityBatisColumnAttributes =
                        analysisEntityParameterAttribute((EntityParameterAttribute) parameterAttribute,
                                multi, methodDynamic, SqlCommandType.UPDATE);
                parameterAttribute.setMulti(multi);
                batisColumnAttributes.addAll(entityBatisColumnAttributes);
                BatisColumnAttribute condition = convertModelAttribute(entityParameterAttribute,
                        entityParameterAttribute.getDatabaseMeta().getPrimaryKey(),
                        0, multi, false, SqlCommandType.SELECT);
                batisColumnAttributes.add(condition);
                if (entityParameterAttribute.getDatabaseMeta().getLogic() != null) {
                    BatisColumnAttribute logicCondition = convertModelAttribute(entityParameterAttribute,
                            entityParameterAttribute.getDatabaseMeta().getLogic(), ++index
                            , multi, false, SqlCommandType.SELECT);
                    batisColumnAttributes.add(logicCondition);
                    isSetLogic = true;
                }
                isSetFill = true;
            } else if (parameterAttribute instanceof BaseParameterAttribute) {
                batisColumnAttributes.add(convertParameterAttribute(parameterAttribute, multi, methodDynamic,
                        SqlCommandType.UPDATE));
            } else if (parameterAttribute instanceof PrimaryKeyParameterAttribute) {
                batisColumnAttributes.add(convertPrimaryKeyParameterAttribute((PrimaryKeyParameterAttribute) parameterAttribute, multi, methodDynamic, SqlCommandType.UPDATE));
            } else if (parameterAttribute instanceof ObjectParameterAttribute) {
                List<BatisColumnAttribute> objectAttributes =
                        analysisObjectAttribute((ObjectParameterAttribute) parameterAttribute, multi, methodDynamic, SqlCommandType.UPDATE);
                parameterAttribute.setMulti(multi);
                batisColumnAttributes.addAll(objectAttributes);
            } else {
                throw new ParamCheckException("UPDATE 语句不支持该类型的参数：" + parameterAttribute.getParameterName());
            }
        }
        if (!isSetFill) {
            List<FillAttribute> fillAttributes = operateMethodMeta.getDatabaseMeta().updateFillAttributes();
            for (FillAttribute fillAttribute : fillAttributes) {
                batisColumnAttributes.add(convertModelAttribute(fillAttribute, ++index, multi, false,
                        SqlCommandType.UPDATE));
            }
        }
        LogicAttribute logic = operateMethodMeta.getDatabaseMeta().getLogic();
        if (!isSetLogic && logic != null) {
            batisColumnAttributes.add(convertModelAttribute(logic, ++index, multi, false,
                    SqlCommandType.SELECT));
        }
        return updateFromSnippet.from(operateMethodMeta) + setSnippet.set(batisColumnAttributes) + whereSnippet.where(batisColumnAttributes);
    }


    @Override
    public String delete(OperateMethodMeta operateMethodMeta) {
        LogicAttribute logic = operateMethodMeta.getDatabaseMeta().getLogic();
        if (operateMethodMeta.getDatabaseMeta().getLogic() == null) {
            return MyBatisSnippetUtils.script(doDelete(operateMethodMeta));
        } else {
            // 当对象实体中有逻辑删除标志位的时候 del语句会构建为Update语句
            // del语句中没有update语句中的Set片段  所以要应用虚拟来参数来把这个参数加入到方法中
            BatisColumnAttribute batisColumnAttribute = convertVirtualModelAttribute(logic.getColumn(),
                    logic.getField() + "0", logic.getInvalid(), 1
                    , false,
                    false,
                    SqlCommandType.UPDATE);
            operateMethodMeta.addVirtualParameterAttribute(batisColumnAttribute);
            return MyBatisSnippetUtils.script(doLogicDelete(operateMethodMeta, logic));
        }

    }

    private String doLogicDelete(OperateMethodMeta operateMethodMeta, LogicAttribute logic) {
        boolean multi = SqlSourceGenerator.isMulti(operateMethodMeta, SqlCommandType.UPDATE);
        boolean methodDynamic = SqlSourceGenerator.isMethodDynamic(operateMethodMeta, SqlCommandType.UPDATE);
        List<BatisColumnAttribute> batisColumnAttributes = new ArrayList<>();
        for (ParameterAttribute parameterAttribute : operateMethodMeta.getParameterAttributes()) {
            if (parameterAttribute instanceof BaseParameterAttribute) {
                batisColumnAttributes.add(convertParameterAttribute(parameterAttribute, multi, methodDynamic,
                        SqlCommandType.SELECT));
            } else if (parameterAttribute instanceof PrimaryKeyParameterAttribute) {
                batisColumnAttributes.add(convertPrimaryKeyParameterAttribute((PrimaryKeyParameterAttribute) parameterAttribute, multi, methodDynamic, SqlCommandType.SELECT));
            } else if (parameterAttribute instanceof ObjectParameterAttribute) {
                List<BatisColumnAttribute> objectAttributes =
                        analysisObjectAttribute((ObjectParameterAttribute) parameterAttribute, multi, methodDynamic, SqlCommandType.SELECT);
                parameterAttribute.setMulti(multi);
                batisColumnAttributes.addAll(objectAttributes);
            } else {
                throw new ParamCheckException("DELETE 语句不支持该类型的参数：" + parameterAttribute.getParameterName());
            }
        }
        int index = operateMethodMeta.getParameterAttributes().size();
        batisColumnAttributes.add(convertModelAttribute(logic, ++index, multi, false,
                SqlCommandType.SELECT));
        for (ParameterAttribute virtualParameterAttribute : operateMethodMeta.getVirtualParameterAttributes()) {
            if (virtualParameterAttribute instanceof BatisColumnAttribute) {
                batisColumnAttributes.add((BatisColumnAttribute) virtualParameterAttribute);
            }
        }
        return updateFromSnippet.from(operateMethodMeta) + setSnippet.set(batisColumnAttributes) + whereSnippet.where(batisColumnAttributes);
    }

    private String doDelete(OperateMethodMeta operateMethodMeta) {
        boolean multi = SqlSourceGenerator.isMulti(operateMethodMeta, SqlCommandType.UPDATE);
        boolean methodDynamic = SqlSourceGenerator.isMethodDynamic(operateMethodMeta, SqlCommandType.UPDATE);
        List<BatisColumnAttribute> batisColumnAttributes = new ArrayList<>();
        for (ParameterAttribute parameterAttribute : operateMethodMeta.getParameterAttributes()) {
            if (parameterAttribute instanceof BaseParameterAttribute) {
                batisColumnAttributes.add(convertParameterAttribute(parameterAttribute, multi, methodDynamic,
                        SqlCommandType.SELECT));
            } else if (parameterAttribute instanceof PrimaryKeyParameterAttribute) {
                batisColumnAttributes.add(convertPrimaryKeyParameterAttribute((PrimaryKeyParameterAttribute) parameterAttribute, multi, methodDynamic, SqlCommandType.SELECT));
            } else if (parameterAttribute instanceof ObjectParameterAttribute) {
                List<BatisColumnAttribute> objectAttributes =
                        analysisObjectAttribute((ObjectParameterAttribute) parameterAttribute, multi, methodDynamic, SqlCommandType.SELECT);
                parameterAttribute.setMulti(multi);
                batisColumnAttributes.addAll(objectAttributes);
            } else {
                throw new ParamCheckException("DELETE 语句不支持该类型的参数：" + parameterAttribute.getParameterName());
            }
        }
        return deleteFromSnippet.from(operateMethodMeta) + whereSnippet.where(batisColumnAttributes);
    }


    /**
     * 分析非实体对象的参数
     *
     * @param parameterAttribute 对象的参数源信息
     * @param multi              方法是否是多个参数
     * @param methodDynamic      方法是否具有动态属性
     * @return 分析的结果
     */
    private List<BatisColumnAttribute> analysisObjectAttribute(ObjectParameterAttribute parameterAttribute, boolean multi,
                                                               boolean methodDynamic, SqlCommandType sqlCommandType) {
        List<Field> fields = Reflection.getField(parameterAttribute.getObjectClass());
        ArrayList<BatisColumnAttribute> attributes = new ArrayList<>();
        for (int i = 0; i < fields.size(); i++) {
            BatisColumnAttribute attribute = convertObjectAttribute(parameterAttribute, fields.get(i),
                    parameterAttribute.getIndex() * 1000 + i, multi,
                    methodDynamic, sqlCommandType);
            if (attribute != null) {
                attributes.add(attribute);
            }
        }
        return attributes;

    }

    /**
     * 分析实体对象的参数
     *
     * @param parameterAttribute 实体对象的参数源信息
     * @param isMultiParam       方法是否是多个参数
     * @param dynamic            方法是否具有动态属性
     * @param sqlCommandType     操作类型
     * @return 返回分析的结果
     */
    public List<BatisColumnAttribute> analysisEntityParameterAttribute(
            EntityParameterAttribute parameterAttribute,
            boolean isMultiParam,
            boolean dynamic,
            SqlCommandType sqlCommandType) {
        TableMeta tableMeta = parameterAttribute.getDatabaseMeta();
        List<BatisColumnAttribute> list = new ArrayList<>();
        int paramIndex = parameterAttribute.getIndex() * 1000;
        // 主键只有在插入的时候可以被放入到SQL中
        if (!isModelAttributeIgnore(tableMeta.getPrimaryKey(), sqlCommandType)) {
            list.add(convertModelAttribute(parameterAttribute,
                    tableMeta.getPrimaryKey(),
                    paramIndex,
                    isMultiParam, dynamic, sqlCommandType));
        }
        List<ModelAttribute> normalAttr = tableMeta.getNormalAttr();
        for (int i = 0; i < normalAttr.size(); i++) {
            ModelAttribute modelAttribute = normalAttr.get(i);
            if (!this.isModelAttributeIgnore(modelAttribute, sqlCommandType)) {
                list.add(convertModelAttribute(parameterAttribute, modelAttribute, paramIndex + 200 + i, isMultiParam
                        , dynamic, sqlCommandType));
            }
        }
        List<FillAttribute> fillAttr = tableMeta.getFills();
        for (int i = 0; i < fillAttr.size(); i++) {
            ModelAttribute modelAttribute = fillAttr.get(i);
            if (!this.isModelAttributeIgnore(modelAttribute, sqlCommandType)) {
                list.add(convertModelAttribute(parameterAttribute, modelAttribute, paramIndex + 300 + i, isMultiParam
                        , false, sqlCommandType));
            }
        }
        LogicAttribute logic = tableMeta.getLogic();
        if (logic != null && !isModelAttributeIgnore(logic, sqlCommandType)) {
            list.add(convertModelAttribute(parameterAttribute, logic, paramIndex + 400, isMultiParam, dynamic, sqlCommandType));
        }
        return list.stream().sorted(Comparator.comparingInt(BatisColumnAttribute::getIndex)).collect(Collectors.toList());
    }

    /**
     * 判断 ModelAttribute 属性是在SqlCommandType情况进行忽略
     *
     * @param modelAttribute 模型类型
     * @param sqlCommandType sql操作类型
     * @return 是否需要忽略
     */
    public boolean isModelAttributeIgnore(ModelAttribute modelAttribute, SqlCommandType sqlCommandType) {
        if (sqlCommandType == SqlCommandType.INSERT && modelAttribute.isInsertIgnore()) {
            return true;
        } else if (sqlCommandType == SqlCommandType.SELECT && modelAttribute.isSelectIgnore()) {
            return true;
        } else if (sqlCommandType == SqlCommandType.UPDATE && modelAttribute.isUpdateIgnore()) {
            return true;
        }
        return false;
    }

    private BatisColumnAttribute convertVirtualModelAttribute(String column, String paramName, Object virtualValue,
                                                              int index,
                                                              boolean isMultiParam,
                                                              boolean methodDynamic, SqlCommandType sqlCommandType) {
        BatisColumnAttribute attribute = new BatisColumnAttribute();
        attribute.setIndex(index * 1000);
        attribute.setColumn(column);
        attribute.setVirtualValue(virtualValue);
        attribute.setParameterName(paramName);
        attribute.setPath(new String[]{paramName});
        attribute.setMulti(isMultiParam);
        attribute.setMethodDynamic(methodDynamic);
        attribute.setSqlCommandType(sqlCommandType);
        return attribute;
    }

    private BatisColumnAttribute convertModelAttribute(ModelAttribute modelAttribute,
                                                       int index,
                                                       boolean isMultiParam,
                                                       boolean methodDynamic, SqlCommandType sqlCommandType) {
        BatisColumnAttribute attribute = new BatisColumnAttribute();
        attribute.setIndex(index * 1000);
        attribute.setColumn(modelAttribute.getColumn());
        attribute.setParameterName(modelAttribute.getField());
        attribute.setPath(new String[]{modelAttribute.getField()});
        attribute.addAnnotations(modelAttribute.getAnnotations());
        attribute.setMulti(isMultiParam);
        attribute.setMethodDynamic(methodDynamic);
        attribute.setSqlCommandType(sqlCommandType);
        return attribute;
    }

    private BatisColumnAttribute convertModelAttribute(ParameterAttribute parameterAttribute,
                                                       ModelAttribute modelAttribute,
                                                       int modelAttributeIndex,
                                                       boolean isMultiParam,
                                                       boolean methodDynamic, SqlCommandType sqlCommandType) {
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
        attribute.setSqlCommandType(sqlCommandType);
        return attribute;
    }


    private BatisColumnAttribute convertParameterAttribute(ParameterAttribute parameterAttribute,
                                                           boolean isMultiParam, boolean methodDynamic,
                                                           SqlCommandType sqlCommandType) {
        BatisColumnAttribute attribute = new BatisColumnAttribute();
        attribute.setIndex(parameterAttribute.getIndex() * 1000);
        attribute.setColumn(easyBatisConfiguration.getColumnNameConverter().convert(parameterAttribute.getParameterName()));
        attribute.setParameterName(parameterAttribute.getParameterName());
        attribute.setPath(new String[]{parameterAttribute.getParameterName()});
        attribute.addAnnotations(parameterAttribute.annotations());
        attribute.setMulti(isMultiParam);
        attribute.setMethodDynamic(methodDynamic);
        attribute.setSqlCommandType(sqlCommandType);
        return attribute;
    }

    private BatisColumnAttribute convertObjectAttribute(ObjectParameterAttribute objectParameterAttribute, Field field, int index,
                                                        boolean isMultiParam, boolean methodDynamic, SqlCommandType sqlCommandType) {
        if (field.getAnnotation(Ignore.class) != null) {
            return null;
        }
        BatisColumnAttribute attribute = new BatisColumnAttribute();
        attribute.setIndex(index);
        attribute.setColumn(easyBatisConfiguration.getColumnNameConverter().convert(field.getName()));
        attribute.setParameterName(field.getName());
        attribute.setPath(new String[]{objectParameterAttribute.getParameterName(), field.getName()});
        attribute.addAnnotations(AnnotationUtils.registerAnnotation(field.getAnnotations(), Syntax.class));
        attribute.setMulti(isMultiParam);
        attribute.setMethodDynamic(methodDynamic);
        attribute.setSqlCommandType(sqlCommandType);
        return attribute;
    }

    private BatisColumnAttribute convertPrimaryKeyParameterAttribute(PrimaryKeyParameterAttribute parameterAttribute,
                                                                     boolean multi, boolean methodDynamic, SqlCommandType sqlCommandType) {
        BatisColumnAttribute attribute = convertParameterAttribute(parameterAttribute, multi, methodDynamic, sqlCommandType);
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
