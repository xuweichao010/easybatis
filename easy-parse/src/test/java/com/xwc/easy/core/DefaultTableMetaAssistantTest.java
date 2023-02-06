package com.xwc.easy.core;

import com.xwc.easy.core.table.column.ColumnModel;
import com.xwc.easy.core.table.column.IgnoreColumnModel;
import com.xwc.easy.core.table.column.NotColumnModel;
import com.xwc.easy.core.table.fill.CustomFillFieldModel;
import com.xwc.easy.core.table.fill.FillFieldModel;
import com.xwc.easy.core.table.key.*;
import com.xwc.easy.core.table.logic.LogicField;
import com.xwc.easy.core.table.name.DataBaseModelAutoTableName;
import com.xwc.easy.core.table.name.DataBaseModelTableName;
import com.xwc.open.easy.parse.DefaultTableMetaAssistant;
import com.xwc.open.easy.parse.EasyConfiguration;
import com.xwc.open.easy.parse.enums.FillType;
import com.xwc.open.easy.parse.enums.IdType;
import com.xwc.open.easy.parse.model.FillAttribute;
import com.xwc.open.easy.parse.model.LogicAttribute;
import com.xwc.open.easy.parse.model.ModelAttribute;
import com.xwc.open.easy.parse.model.PrimaryKeyAttribute;
import com.xwc.open.easy.parse.supports.impl.DefaultUUIDHandler;
import com.xwc.open.easy.parse.supports.impl.NoneIdGenerateHandler;
import com.xwc.open.easy.parse.supports.impl.NoneNameConverter;
import com.xwc.open.easy.parse.utils.Reflection;
import org.junit.Assert;
import org.junit.Test;

import java.lang.reflect.Field;

/**
 * 类描述：测试 DefaultDataBaseModelAssistant 类是否解析数据正常
 * 作者：徐卫超 (cc)
 * 时间 2022/11/25 9:24
 */
public class DefaultTableMetaAssistantTest {


    /**
     * 测试是否能解析出来tableName
     */
    @Test
    public void tableName() {
        EasyConfiguration configuration = new EasyConfiguration();
        DefaultTableMetaAssistant assistant = new DefaultTableMetaAssistant(configuration);
        String name = assistant.tableName(DataBaseModelTableName.class);
        Assert.assertEquals("t_database_table_name", name);
    }

    /**
     * 测试 AutoTableName 等于 true 的时候是否能解析出来正确的表名
     */
    @Test
    public void autoTableName() {
        EasyConfiguration configuration = new EasyConfiguration();
        configuration.setAutoTableName(true);
        DefaultTableMetaAssistant assistant = new DefaultTableMetaAssistant(configuration);
        String name = assistant.tableName(DataBaseModelAutoTableName.class);
        Assert.assertEquals("data_base_model_auto_table_name", name);
    }

    /**
     * 测试 tableNameConverter属性书否正常工作
     */
    @Test
    public void autoTableNameNameConvert() {
        EasyConfiguration configuration = new EasyConfiguration();
        configuration.setAutoTableName(true);
        configuration.setTableNameConverter(new NoneNameConverter());
        DefaultTableMetaAssistant assistant = new DefaultTableMetaAssistant(configuration);
        String name = assistant.tableName(DataBaseModelAutoTableName.class);
        Assert.assertEquals(name, "DataBaseModelAutoTableName");
    }

    /**
     * 测试useTableNamePrefix是否正常能工作
     */
    @Test
    public void prefixAutoTableName() {
        EasyConfiguration configuration = new EasyConfiguration();
        configuration.setAutoTableName(true);
        configuration.setUseTableNamePrefix("t_");
        DefaultTableMetaAssistant assistant = new DefaultTableMetaAssistant(configuration);
        String name = assistant.tableName(DataBaseModelAutoTableName.class);
        Assert.assertEquals("t_data_base_model_auto_table_name", name);
    }

    /**
     * 测试自增主键
     */
    @Test
    public void autoPrimaryKey() {
        EasyConfiguration configuration = new EasyConfiguration();
        DefaultTableMetaAssistant assistant = new DefaultTableMetaAssistant(configuration);
        Field field = Reflection.getField(AutoPrimaryKey.class).stream()
                .findFirst().orElseThrow(() -> new RuntimeException("未找到合法的属性"));
        PrimaryKeyAttribute primaryKeyAttribute = assistant.primaryKeyAttribute(AutoPrimaryKey.class, field);
        Assert.assertEquals("id", primaryKeyAttribute.getColumn());
        Assert.assertEquals("id", primaryKeyAttribute.getField());
        Assert.assertEquals(IdType.AUTO, primaryKeyAttribute.getIdType());
        Assert.assertTrue(primaryKeyAttribute.isInsertIgnore());
        Assert.assertFalse(primaryKeyAttribute.isSelectIgnore());
        Assert.assertTrue(primaryKeyAttribute.isUpdateIgnore());

    }

    /**
     * 测试自增主键
     */
    @Test
    public void notAutoPrimaryKey() {
        EasyConfiguration configuration = new EasyConfiguration();
        DefaultTableMetaAssistant assistant = new DefaultTableMetaAssistant(configuration);
        Field field = Reflection.getField(NotAutoPrimaryKey.class).stream()
                .findFirst().orElseThrow(() -> new RuntimeException("未找到合法的属性"));
        PrimaryKeyAttribute primaryKeyAttribute = assistant.primaryKeyAttribute(NotAutoPrimaryKey.class, field);
        Assert.assertEquals(primaryKeyAttribute.getColumn(), "user_id");
        Assert.assertEquals(primaryKeyAttribute.getField(), "userId");
        Assert.assertEquals(primaryKeyAttribute.getIdType(), IdType.INPUT);
        Assert.assertFalse(primaryKeyAttribute.isInsertIgnore());
        Assert.assertFalse(primaryKeyAttribute.isSelectIgnore());
        Assert.assertTrue(primaryKeyAttribute.isUpdateIgnore());
    }

    /**
     * 测试全局配置的主键类型 没有配置的时候 而且也没有指定属性的时候 会不会报错
     */
    @Test
    public void globalPrimaryKey() {
        EasyConfiguration configuration = new EasyConfiguration();
        DefaultTableMetaAssistant assistant = new DefaultTableMetaAssistant(configuration);
        Field field = Reflection.getField(GlobalPrimaryKey.class).stream()
                .findFirst().orElseThrow(() -> new RuntimeException("未找到合法的属性"));
        try {
            assistant.primaryKeyAttribute(GlobalPrimaryKey.class, field);
        } catch (Exception e) {
            return;
        }
        Assert.fail();
    }

    /**
     * 测试全局属性配置了后 能否正常处理全局属性
     */
    @Test
    public void globalConfigurationPrimaryKey() {
        EasyConfiguration configuration = new EasyConfiguration();
        configuration.setGlobalIdType(IdType.AUTO);
        DefaultTableMetaAssistant assistant = new DefaultTableMetaAssistant(configuration);
        Field field = Reflection.getField(GlobalPrimaryKey.class).stream()
                .findFirst().orElseThrow(() -> new RuntimeException("未找到合法的属性"));
        PrimaryKeyAttribute primaryKeyAttribute = assistant.primaryKeyAttribute(NotAutoPrimaryKey.class, field);
        Assert.assertEquals(IdType.AUTO, primaryKeyAttribute.getIdType());
    }

    /**
     * 测试全局主键配置了后 最定义主键配置是否生效
     */
    @Test
    public void globalCustomConfigurationPrimaryKey() {
        EasyConfiguration configuration = new EasyConfiguration();
        configuration.setGlobalIdType(IdType.AUTO);
        DefaultTableMetaAssistant assistant = new DefaultTableMetaAssistant(configuration);
        Field field = Reflection.getField(GlobalCustomPrimaryKey.class).stream()
                .findFirst().orElseThrow(() -> new RuntimeException("未找到合法的属性"));
        PrimaryKeyAttribute primaryKeyAttribute = assistant.primaryKeyAttribute(GlobalCustomPrimaryKey.class, field);
        Assert.assertEquals(IdType.INPUT, primaryKeyAttribute.getIdType());
    }


    /**
     * 测试UUID ID 有 IdGenerateHandler
     */
    @Test
    public void uuiPrimaryKey() {
        EasyConfiguration configuration = new EasyConfiguration();
        DefaultTableMetaAssistant assistant = new DefaultTableMetaAssistant(configuration);
        Field field = Reflection.getField(UuidPrimaryKey.class).stream()
                .findFirst().orElseThrow(() -> new RuntimeException("未找到合法的属性"));
        PrimaryKeyAttribute primaryKeyAttribute = assistant.primaryKeyAttribute(UuidPrimaryKey.class, field);
        Assert.assertTrue(primaryKeyAttribute.getIdGenerateHandler().getClass().isAssignableFrom(DefaultUUIDHandler.class));
    }

    /**
     * 测试HANDLER 自定义ID 有 IdGenerateHandler
     */
    @Test
    public void customPrimaryKey() {
        EasyConfiguration configuration = new EasyConfiguration();
        DefaultTableMetaAssistant assistant = new DefaultTableMetaAssistant(configuration);
        Field field = Reflection.getField(HandlerPrimaryKey.class).stream()
                .findFirst().orElseThrow(() -> new RuntimeException("未找到合法的属性"));
        PrimaryKeyAttribute primaryKeyAttribute = assistant.primaryKeyAttribute(HandlerPrimaryKey.class, field);
        Assert.assertTrue(primaryKeyAttribute.getIdGenerateHandler().getClass().isAssignableFrom(NoneIdGenerateHandler.class));
    }

    /**
     * 测试logic是否工作正常
     */
    @Test
    public void logic() {
        EasyConfiguration configuration = new EasyConfiguration();
        DefaultTableMetaAssistant assistant = new DefaultTableMetaAssistant(configuration);
        Field field = Reflection.getField(LogicField.class).stream()
                .findFirst().orElseThrow(() -> new RuntimeException("未找到合法的属性"));
        LogicAttribute logicAttribute = assistant.logicAttribute(LogicField.class, field);
        Assert.assertNotNull(logicAttribute);
        Assert.assertEquals(100, logicAttribute.getInvalid());
        Assert.assertEquals(101, logicAttribute.getValid());
        Assert.assertEquals("delete_flag", logicAttribute.getColumn());
        Assert.assertTrue(logicAttribute.isSelectIgnore());
        Assert.assertTrue(logicAttribute.isUpdateIgnore());
        Assert.assertFalse(logicAttribute.isInsertIgnore());
    }

    /**
     * 正常填充注解测试
     */
    @Test
    public void fillField() {
        EasyConfiguration configuration = new EasyConfiguration();
        DefaultTableMetaAssistant assistant = new DefaultTableMetaAssistant(configuration);
        Field field = Reflection.getField(FillFieldModel.class).stream()
                .findFirst().orElseThrow(() -> new RuntimeException("未找到合法的属性"));
        FillAttribute fillAttribute = assistant.fillAttribute(FillFieldModel.class, field);
        Assert.assertEquals("createUserId", fillAttribute.getIdentification());
        Assert.assertEquals("create_user_id", fillAttribute.getColumn());
        Assert.assertEquals(FillType.INSERT, fillAttribute.getType());
        Assert.assertFalse(fillAttribute.isInsertIgnore());
        Assert.assertFalse(fillAttribute.isUpdateIgnore());
        Assert.assertTrue(fillAttribute.isSelectIgnore());
    }


    /**
     * 自定义属性填充注解测试
     */
    @Test
    public void customFillField() {
        EasyConfiguration configuration = new EasyConfiguration();
        DefaultTableMetaAssistant assistant = new DefaultTableMetaAssistant(configuration);
        Field field = Reflection.getField(CustomFillFieldModel.class).stream()
                .findFirst().orElseThrow(() -> new RuntimeException("未找到合法的属性"));
        FillAttribute fillAttribute = assistant.fillAttribute(CustomFillFieldModel.class, field);
        Assert.assertTrue(fillAttribute.isSelectIgnore());
        Assert.assertEquals("username", fillAttribute.getIdentification());
        Assert.assertEquals("create_user_id", fillAttribute.getColumn());
        Assert.assertEquals(FillType.INSERT_UPDATE, fillAttribute.getType());
    }

    /**
     * 没有Column普通属性的解析测试
     */
    @Test
    public void notColumn() {
        EasyConfiguration configuration = new EasyConfiguration();
        DefaultTableMetaAssistant assistant = new DefaultTableMetaAssistant(configuration);
        Field field = Reflection.getField(NotColumnModel.class).stream()
                .findFirst().orElseThrow(() -> new RuntimeException("未找到合法的属性"));
        ModelAttribute modelAttribute = assistant.modelAttribute(NotColumnModel.class, field);
        Assert.assertEquals("username", modelAttribute.getColumn());
    }

    /**
     * 有Column注解的解析测试
     */
    @Test
    public void column() {
        EasyConfiguration configuration = new EasyConfiguration();
        DefaultTableMetaAssistant assistant = new DefaultTableMetaAssistant(configuration);
        Field field = Reflection.getField(ColumnModel.class).stream()
                .findFirst().orElseThrow(() -> new RuntimeException("未找到合法的属性"));
        ModelAttribute modelAttribute = assistant.modelAttribute(ColumnModel.class, field);
        Assert.assertEquals("alias_name", modelAttribute.getColumn());
        Assert.assertTrue(modelAttribute.isInsertIgnore());
        Assert.assertTrue(modelAttribute.isUpdateIgnore());
        Assert.assertTrue(modelAttribute.isSelectIgnore());
    }

    /**
     * 忽略属性的测试
     */
    @Test
    public void ignoreColumn() {
        EasyConfiguration configuration = new EasyConfiguration();
        DefaultTableMetaAssistant assistant = new DefaultTableMetaAssistant(configuration);
        Field field = Reflection.getField(IgnoreColumnModel.class).stream()
                .findFirst().orElseThrow(() -> new RuntimeException("未找到合法的属性"));
        ModelAttribute modelAttribute = assistant.modelAttribute(IgnoreColumnModel.class, field);
        Assert.assertNull(modelAttribute);
    }

}
