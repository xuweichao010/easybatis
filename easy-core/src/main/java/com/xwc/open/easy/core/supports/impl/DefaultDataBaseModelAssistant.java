package com.xwc.open.easy.core.supports.impl;

import com.xwc.open.easy.core.EasyConfiguration;
import com.xwc.open.easy.core.annotations.*;
import com.xwc.open.easy.core.enums.IdType;
import com.xwc.open.easy.core.exceptions.CheckDatabaseModelException;
import com.xwc.open.easy.core.model.*;
import com.xwc.open.easy.core.supports.DataBaseModelAssistant;
import com.xwc.open.easy.core.utils.AnnotationUtils;
import com.xwc.open.easy.core.utils.Reflection;
import com.xwc.open.easy.core.utils.StringUtils;

import java.lang.reflect.Field;
import java.util.List;

/**
 * 类描述：
 * 作者：徐卫超 (cc)
 * 时间 2022/11/24 15:20
 */
public class DefaultDataBaseModelAssistant implements DataBaseModelAssistant {
    private EasyConfiguration configuration;

    public DefaultDataBaseModelAssistant(EasyConfiguration configuration) {
        this.configuration = configuration;
    }

    @Override
    public DatabaseModel getResultModel(Class<?> clazz) {
        DatabaseModel model = new DatabaseModel();
        model.setTableName(tableName(clazz));
        List<Field> fields = Reflection.getField(clazz);
        for (Field field : fields) {
            ModelAttribute modelAttribute;
            if ((modelAttribute = primaryKeyAttribute(clazz, field)) != null) {
                model.setPrimaryKey((PrimaryKeyAttribute) modelAttribute);
            } else if ((modelAttribute = logicAttribute(clazz, field)) != null) {
                model.setLogic((LogicAttribute) modelAttribute);
            } else if ((modelAttribute = fillAttribute(clazz, field)) != null) {
                model.addFillAttribute((FillAttribute) modelAttribute);
            } else if ((modelAttribute = modelAttribute(clazz, field)) != null) {
                model.addModelAttribute(modelAttribute);
            }
        }
        return model;
    }


    /**
     * 获取实体类上面的表名
     *
     * @param clazz 需要获取的class文件
     * @return 返回解析出来的表名
     */
    public String tableName(Class<?> clazz) {
        Table table = AnnotationUtils.findAnnotation(clazz, Table.class);
        if (table == null) {
            throw new CheckDatabaseModelException(clazz.getName() + "没有找到 @Table 注解信息");
        }
        if (StringUtils.hasText(table.value())) {
            return table.value();
        } else if (configuration.isAutoTableName()) {
            String name = configuration.getTableNameConverter().convert(clazz.getSimpleName());
            if (StringUtils.hasText(configuration.getUseTableNamePrefix())) {
                return configuration.getUseTableNamePrefix() + name;
            }
            return name;
        } else {
            throw new CheckDatabaseModelException(clazz.getName() + " @Table注解信息中的value属性无效");
        }
    }

    /**
     * 获取数据模型中的主键信息
     *
     * @param clazz 实体类的Class文件
     * @param field 实体属性Field 属性
     * @return 返回一个主键属性封装对象
     */
    public PrimaryKeyAttribute primaryKeyAttribute(Class<?> clazz, Field field) {
        Id id = AnnotationUtils.findAnnotation(field, Id.class);
        if (id == null) {
            return null;
        }
        PrimaryKeyAttribute primaryKeyAttribute = (PrimaryKeyAttribute) this.process(new PrimaryKeyAttribute(), clazz, field);
        if (primaryKeyAttribute == null) {
            return null;
        }
        IdType idType = id.type() == IdType.GLOBAL ? configuration.getGlobalIdType() : id.type();
        if (idType == null) {
            throw new CheckDatabaseModelException("请填写有效的IdType类型");
        }
        primaryKeyAttribute.setUpdateIgnore(true);
        primaryKeyAttribute.setInsertIgnore(idType == IdType.AUTO);
        primaryKeyAttribute.setIdType(idType);
        if (StringUtils.hasText(id.value())) {
            primaryKeyAttribute.setColumn(id.value());
        }
        return primaryKeyAttribute;
    }

    /**
     * 获取数据模型中的逻辑删除字段
     *
     * @param clazz 实体类的Class文件
     * @param field 实体属性Field 属性
     * @return 返回一个逻辑删除属性封装对象
     */
    public LogicAttribute logicAttribute(Class<?> clazz, Field field) {
        Logic logic = AnnotationUtils.findAnnotation(field, Logic.class);
        if (logic == null) {
            return null;
        }
        LogicAttribute logicAttribute = (LogicAttribute) this.process(new LogicAttribute(), clazz, field);
        if (logicAttribute == null) {
            return null;
        }
        logicAttribute.setUpdateIgnore(false);
        logicAttribute.setSelectIgnore(logic.selectIgnore());
        logicAttribute.setValid(logic.valid());
        logicAttribute.setInvalid(logic.invalid());
        if (StringUtils.hasText(logic.value())) {
            logicAttribute.setColumn(logic.value());
        }
        return logicAttribute;
    }


    /**
     * 获取数据模型中的自动填充字段
     *
     * @param clazz 实体类的Class文件
     * @param field 实体属性Field 属性
     * @return 返回一个自动填充属性封装对象
     */
    public FillAttribute fillAttribute(Class<?> clazz, Field field) {
        FillColumn fillColumn = AnnotationUtils.findAnnotation(field, FillColumn.class);
        if (fillColumn == null) {
            return null;
        }
        FillAttribute fillAttribute = (FillAttribute) this.process(new FillAttribute(), clazz, field);
        if (fillAttribute == null) {
            return null;
        }
        if (StringUtils.hasText(fillColumn.identification())) {

        }
        // 处理填充标识
        String identification = StringUtils.hasText(fillColumn.identification()) ? fillColumn.identification() : fillAttribute.getField();
        fillAttribute.setIdentification(identification);
        // 填充类型处理
        fillAttribute.setType(fillAttribute.getType());
        fillAttribute.setSelectIgnore(fillColumn.selectIgnore());
        if (StringUtils.hasText(fillColumn.value())) {
            fillAttribute.setColumn(fillColumn.value());
        }
        if (StringUtils.hasText(fillColumn.value())) {
            fillAttribute.setColumn(fillColumn.value());
        }
        return fillAttribute;
    }

    /**
     * 获取数据模型中的普通的属性字段
     *
     * @param clazz 实体类的Class文件
     * @param field 实体属性Field 属性
     * @return 返回一个普通的属性字段属性封装
     */
    public ModelAttribute modelAttribute(Class<?> clazz, Field field) {
        Column column = AnnotationUtils.findAnnotation(field, Column.class);
        if (column != null && column.ignore()) {
            return null;
        }
        ModelAttribute modelAttribute = process(new ModelAttribute(), clazz, field);
        if (modelAttribute == null) {
            return null;
        }
        if (column != null) {
            if (StringUtils.hasText(column.value())) {
                modelAttribute.setColumn(column.value());
            }
            modelAttribute.setInsertIgnore(column.insertIgnore());
            modelAttribute.setUpdateIgnore(column.updateIgnore());
            modelAttribute.setSelectIgnore(column.selectIgnore());
        }
        return modelAttribute;
    }

    /**
     * @param modelAttribute 需要填充的属性模型
     * @param clazz          实体类
     * @param field          需要解析的属性
     * @return 返回解析属性模型 如果解析错误返回null
     */
    private ModelAttribute process(ModelAttribute modelAttribute, Class<?> clazz, Field field) {
        try {
            modelAttribute.setSetter(Reflection.setter(field, clazz));
            modelAttribute.setGetter(Reflection.getter(field, clazz));
        } catch (NoSuchMethodException e) {
            return null;
        }
        modelAttribute.setField(field.getName());
        modelAttribute.setColumn(configuration.getColumnNameConverter().convert(field.getName()));
        return modelAttribute;
    }

}