package com.xwc.easy.core;

import com.xwc.easy.core.table.key.AutoPrimaryKey;
import com.xwc.easy.core.table.key.GlobalPrimaryKey;
import com.xwc.easy.core.table.key.NotAutoPrimaryKey;
import com.xwc.easy.core.table.name.DataBaseModelAutoTableName;
import com.xwc.easy.core.table.name.DataBaseModelTableName;
import com.xwc.open.easy.core.EasyConfiguration;
import com.xwc.open.easy.core.enums.IdType;
import com.xwc.open.easy.core.model.PrimaryKeyAttribute;
import com.xwc.open.easy.core.supports.impl.DefaultDataBaseModelAssistant;
import com.xwc.open.easy.core.supports.impl.NoneNameConverter;
import com.xwc.open.easy.core.utils.Reflection;
import org.junit.Assert;
import org.junit.Test;

import java.lang.reflect.Field;

/**
 * 类描述：测试 DefaultDataBaseModelAssistant 类是否解析数据正常
 * 作者：徐卫超 (cc)
 * 时间 2022/11/25 9:24
 */
public class DefaultDataBaseModelAssistantTest {


    /**
     * 测试是否能解析出来tableName
     */
    @Test
    public void tableName() {
        EasyConfiguration configuration = new EasyConfiguration();
        DefaultDataBaseModelAssistant assistant = new DefaultDataBaseModelAssistant(configuration);
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
        DefaultDataBaseModelAssistant assistant = new DefaultDataBaseModelAssistant(configuration);
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
        DefaultDataBaseModelAssistant assistant = new DefaultDataBaseModelAssistant(configuration);
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
        DefaultDataBaseModelAssistant assistant = new DefaultDataBaseModelAssistant(configuration);
        String name = assistant.tableName(DataBaseModelAutoTableName.class);
        Assert.assertEquals("t_data_base_model_auto_table_name", name);
    }

    /**
     * 测试自增主键
     */
    @Test
    public void autoPrimaryKey() {
        EasyConfiguration configuration = new EasyConfiguration();
        DefaultDataBaseModelAssistant assistant = new DefaultDataBaseModelAssistant(configuration);
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
        DefaultDataBaseModelAssistant assistant = new DefaultDataBaseModelAssistant(configuration);
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

    @Test
    public void globalPrimaryKey() {
        EasyConfiguration configuration = new EasyConfiguration();
        DefaultDataBaseModelAssistant assistant = new DefaultDataBaseModelAssistant(configuration);
        Field field = Reflection.getField(GlobalPrimaryKey.class).stream()
                .findFirst().orElseThrow(() -> new RuntimeException("未找到合法的属性"));
        try {
            assistant.primaryKeyAttribute(GlobalPrimaryKey.class, field);
        } catch (Exception e) {
            return;
        }
        Assert.fail();
    }

    @Test
    public void globalConfigurationPrimaryKey() {
        EasyConfiguration configuration = new EasyConfiguration();
        configuration.setGlobalIdType(IdType.AUTO);
        DefaultDataBaseModelAssistant assistant = new DefaultDataBaseModelAssistant(configuration);
        Field field = Reflection.getField(GlobalPrimaryKey.class).stream()
                .findFirst().orElseThrow(() -> new RuntimeException("未找到合法的属性"));
        PrimaryKeyAttribute primaryKeyAttribute = assistant.primaryKeyAttribute(NotAutoPrimaryKey.class, field);
        Assert.assertEquals(IdType.AUTO, primaryKeyAttribute.getIdType());
    }
}
