package com.xwc.easy.core;

import cn.onetozero.easy.parse.model.parameter.*;
import com.xwc.easy.core.method.TestBaseMapper;
import cn.onetozero.easy.parse.DefaultTableMetaAssistant;
import cn.onetozero.easy.parse.DefaultOperateMethodAssistant;
import cn.onetozero.easy.parse.EasyConfiguration;
import cn.onetozero.easy.parse.model.OperateMethodMeta;
import cn.onetozero.easy.parse.model.ParameterAttribute;
import org.junit.Assert;
import org.junit.Test;

import java.lang.reflect.Method;

/**
 * 类描述：默认的属性类型
 * 作者：徐卫超 (cc)
 * 时间 2022/11/26 21:01
 */
public class DefaultOperateMethodAssistantTest {


    /**
     * 测试实体参数能否被解析出来
     */
    @Test
    public void customEntityParameterAttribute() {
        EasyConfiguration configuration = new EasyConfiguration();
        configuration.setTableMetaAssistant(new DefaultTableMetaAssistant(configuration));
        DefaultOperateMethodAssistant methodAssistant = new DefaultOperateMethodAssistant(configuration);
        OperateMethodMeta methodMeta = methodAssistant.getOperateMethodMeta(TestBaseMapper.class, method(TestBaseMapper.class,
                "customEntityParameterAttribute"));
        ParameterAttribute parameterAttribute = methodMeta.getParameterAttributes().stream().findFirst().orElseGet(null);
        Assert.assertEquals(EntityParameterAttribute.class, parameterAttribute.getClass());
    }

    /**
     * 测试 泛型实体是否能被解析出来
     */
    @Test
    public void genericsEntityParameterAttribute() {
        EasyConfiguration configuration = new EasyConfiguration();
        configuration.setTableMetaAssistant(new DefaultTableMetaAssistant(configuration));
        DefaultOperateMethodAssistant methodAssistant = new DefaultOperateMethodAssistant(configuration);
        OperateMethodMeta methodMeta = methodAssistant.getOperateMethodMeta(TestBaseMapper.class, method(TestBaseMapper.class,
                "genericsEntityParameterAttribute"));
        ParameterAttribute parameterAttribute = methodMeta.getParameterAttributes().stream().findFirst().orElseGet(null);
        Assert.assertEquals(EntityParameterAttribute.class, parameterAttribute.getClass());
    }

    /**
     * 实体集合参数解析 测试
     */
    @Test
    public void customCollectionEntityParameterAttribute() {
        EasyConfiguration configuration = new EasyConfiguration();
        configuration.setTableMetaAssistant(new DefaultTableMetaAssistant(configuration));
        DefaultOperateMethodAssistant methodAssistant = new DefaultOperateMethodAssistant(configuration);
        OperateMethodMeta methodMeta = methodAssistant.getOperateMethodMeta(TestBaseMapper.class, method(TestBaseMapper.class,
                "customCollectionEntityParameterAttribute"));
        ParameterAttribute parameterAttribute = methodMeta.getParameterAttributes().stream().findFirst().orElseGet(null);
        Assert.assertEquals(CollectionEntityParameterAttribute.class, parameterAttribute.getClass());
    }

    /**
     * 集合泛型参数解析 测试
     */
    @Test
    public void genericsCollectionEntityParameterAttribute() {
        EasyConfiguration configuration = new EasyConfiguration();
        configuration.setTableMetaAssistant(new DefaultTableMetaAssistant(configuration));
        DefaultOperateMethodAssistant methodAssistant = new DefaultOperateMethodAssistant(configuration);
        OperateMethodMeta methodMeta = methodAssistant.getOperateMethodMeta(TestBaseMapper.class, method(TestBaseMapper.class,
                "genericsCollectionEntityParameterAttribute"));
        ParameterAttribute parameterAttribute = methodMeta.getParameterAttributes().stream().findFirst().orElseGet(null);
        Assert.assertEquals(CollectionEntityParameterAttribute.class, parameterAttribute.getClass());
    }

    /**
     * 测试泛型主键是否能被解析出来
     */
    @Test
    public void genericsPrimaryKeyParameterAttribute() {
        EasyConfiguration configuration = new EasyConfiguration();
        configuration.setTableMetaAssistant(new DefaultTableMetaAssistant(configuration));
        DefaultOperateMethodAssistant methodAssistant = new DefaultOperateMethodAssistant(configuration);
        OperateMethodMeta methodMeta = methodAssistant.getOperateMethodMeta(TestBaseMapper.class, method(TestBaseMapper.class,
                "genericsPrimaryKeyParameterAttribute"));
        ParameterAttribute parameterAttribute = methodMeta.getParameterAttributes().stream().findFirst().orElseGet(null);
        Assert.assertEquals(PrimaryKeyParameterAttribute.class, parameterAttribute.getClass());
    }

    @Test
    public void genericsCollectionPrimaryKeyParameterAttribute() {
        EasyConfiguration configuration = new EasyConfiguration();
        configuration.setTableMetaAssistant(new DefaultTableMetaAssistant(configuration));
        DefaultOperateMethodAssistant methodAssistant = new DefaultOperateMethodAssistant(configuration);
        OperateMethodMeta methodMeta = methodAssistant.getOperateMethodMeta(TestBaseMapper.class, method(TestBaseMapper.class,
                "genericsCollectionPrimaryKeyParameterAttribute"));
        ParameterAttribute parameterAttribute = methodMeta.getParameterAttributes().stream().findFirst().orElseGet(null);
        Assert.assertEquals(CollectionPrimaryKeyParameterAttribute.class, parameterAttribute.getClass());
    }

    /**
     * 测试普通参数能否被解析出来
     */
    @Test
    public void baseParameterAttribute() {
        EasyConfiguration configuration = new EasyConfiguration();
        configuration.setTableMetaAssistant(new DefaultTableMetaAssistant(configuration));
        DefaultOperateMethodAssistant methodAssistant = new DefaultOperateMethodAssistant(configuration);
        OperateMethodMeta methodMeta = methodAssistant.getOperateMethodMeta(TestBaseMapper.class, method(TestBaseMapper.class,
                "baseParameterAttribute"));
        ParameterAttribute parameterAttribute = methodMeta.getParameterAttributes().stream().findFirst().orElseGet(null);
        Assert.assertEquals(BaseParameterAttribute.class, parameterAttribute.getClass());
    }

    @Test
    public void collectionParameterAttribute() {
        EasyConfiguration configuration = new EasyConfiguration();
        configuration.setTableMetaAssistant(new DefaultTableMetaAssistant(configuration));
        DefaultOperateMethodAssistant methodAssistant = new DefaultOperateMethodAssistant(configuration);
        OperateMethodMeta methodMeta = methodAssistant.getOperateMethodMeta(TestBaseMapper.class, method(TestBaseMapper.class,
                "collectionParameterAttribute"));
        ParameterAttribute parameterAttribute = methodMeta.getParameterAttributes().stream().findFirst().orElseGet(null);
        Assert.assertEquals(CollectionParameterAttribute.class, parameterAttribute.getClass());
    }


    @Test
    public void objectParameterAttribute() {
        EasyConfiguration configuration = new EasyConfiguration();
        configuration.setTableMetaAssistant(new DefaultTableMetaAssistant(configuration));
        DefaultOperateMethodAssistant methodAssistant = new DefaultOperateMethodAssistant(configuration);
        OperateMethodMeta methodMeta = methodAssistant.getOperateMethodMeta(TestBaseMapper.class, method(TestBaseMapper.class,
                "objectParameterAttribute"));
        ParameterAttribute parameterAttribute = methodMeta.getParameterAttributes().stream().findFirst().orElseGet(null);
        Assert.assertEquals(ObjectParameterAttribute.class, parameterAttribute.getClass());
    }

    /**
     * 测试map类型
     */
    @Test
    public void mapParameterAttribute() {
        EasyConfiguration configuration = new EasyConfiguration();
        configuration.setTableMetaAssistant(new DefaultTableMetaAssistant(configuration));
        DefaultOperateMethodAssistant methodAssistant = new DefaultOperateMethodAssistant(configuration);
        OperateMethodMeta methodMeta = methodAssistant.getOperateMethodMeta(TestBaseMapper.class, method(TestBaseMapper.class,
                "mapParameterAttribute"));
        ParameterAttribute parameterAttribute = methodMeta.getParameterAttributes().stream().findFirst().orElseGet(null);
        Assert.assertEquals(MapParameterAttribute.class, parameterAttribute.getClass());
    }


    public Method method(Class<?> clazz, String methodName) {
        Method[] methods = clazz.getMethods();
        for (Method method : methods) {
            if (method.getName().equals(methodName)) {
                return method;
            }
        }
        Assert.fail("无法找到对应的方法" + methodName);
        return null;
    }

}
