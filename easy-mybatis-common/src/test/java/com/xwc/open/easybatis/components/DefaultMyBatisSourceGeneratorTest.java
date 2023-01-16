package com.xwc.open.easybatis.components;

import com.xwc.open.easy.parse.exceptions.EasyException;
import com.xwc.open.easy.parse.model.OperateMethodMeta;
import com.xwc.open.easy.parse.model.ParameterAttribute;
import com.xwc.open.easy.parse.model.parameter.EntityParameterAttribute;
import com.xwc.open.easy.parse.supports.impl.CamelConverterUnderscore;
import com.xwc.open.easy.parse.supports.impl.NoneNameConverter;
import com.xwc.open.easy.parse.utils.Reflection;
import com.xwc.open.easybatis.EasyBatisConfiguration;
import com.xwc.open.easybatis.binding.MybatisParameterAttribute;
import com.xwc.open.easybatis.components.mapper.FillAndLogicSourceGeneratorMapper;
import com.xwc.open.easybatis.components.mapper.FillSourceGeneratorMapper;
import com.xwc.open.easybatis.components.mapper.LogicSourceGeneratorMapper;
import com.xwc.open.easybatis.components.mapper.SimpleSourceGeneratorMapper;
import com.xwc.open.easybatis.supports.DefaultEasyBatisSourceGenerator;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.mapping.SqlCommandType;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 类描述：
 * 作者：徐卫超 (cc)
 * 时间 2023/1/14 10:47
 */
public class DefaultMyBatisSourceGeneratorTest {

    SqlSessionFactory sqlSessionFactory;
    Configuration configuration;
    EasyBatisConfiguration easyBatisConfiguration;
    NoneNameConverter noneNameConverter = new NoneNameConverter();
    CamelConverterUnderscore camelConverterUnderscore = new CamelConverterUnderscore();

    @Before
    public void before() throws IOException {
        InputStream inputStream = Resources.getResourceAsStream("mybatis-config.xml");
        this.sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
        this.configuration = sqlSessionFactory.getConfiguration();
        this.configuration.setMapUnderscoreToCamelCase(true);
        this.easyBatisConfiguration = new EasyBatisConfiguration(configuration);
    }

    @Test
    public void InsertIsMultiTest() {
        Assert.assertFalse(isMultiTest(SimpleSourceGeneratorMapper.class, "simpleInsert", SqlCommandType.INSERT));
        Assert.assertTrue(isMultiTest(SimpleSourceGeneratorMapper.class, "insertIgnore", SqlCommandType.INSERT));
        Assert.assertFalse(isMultiTest(SimpleSourceGeneratorMapper.class, "insertBatch", SqlCommandType.INSERT));
        Assert.assertTrue(isMultiTest(SimpleSourceGeneratorMapper.class, "insertBatchIgnore", SqlCommandType.INSERT));
        try {
            isMultiTest(SimpleSourceGeneratorMapper.class, "insertObject", SqlCommandType.INSERT);
            Assert.fail();
        } catch (Exception e) {
        }
    }

    @Test
    public void flatEntityParameterAttributeTest() {
        List<MybatisParameterAttribute> simpleInsert = flatEntityParameterAttribute(SimpleSourceGeneratorMapper.class,
                "simpleInsert", SqlCommandType.INSERT);
        Assert.assertEquals(13, simpleInsert.size());

    }


    private List<MybatisParameterAttribute> flatEntityParameterAttribute(Class<?> interfaceClass, String methodName,
                                                                         SqlCommandType sqlCommandType) {
        Method method = Reflection.chooseMethod(interfaceClass, methodName);
        OperateMethodMeta operateMethodMeta =
                easyBatisConfiguration.getOperateMethodAssistant().getOperateMethodMeta(interfaceClass
                        , method);
        DefaultEasyBatisSourceGenerator defaultEasyBatisSourceGenerator =
                new DefaultEasyBatisSourceGenerator(easyBatisConfiguration);
        boolean multi = defaultEasyBatisSourceGenerator.isMulti(operateMethodMeta, sqlCommandType);
        List<MybatisParameterAttribute> operateParameterAttributes =
                operateMethodMeta.getParameterAttributes().stream().map(parameterAttribute -> {
                    if (parameterAttribute instanceof EntityParameterAttribute) {
                        return defaultEasyBatisSourceGenerator.flatEntityParameterAttribute(parameterAttribute,
                                operateMethodMeta.getDatabaseMeta(), multi, sqlCommandType);
                    } else {
                        throw new EasyException("错误的数据类型");
                    }
                }).flatMap(Collection::stream).collect(Collectors.toList());
        operateParameterAttributes.forEach(mybatisParameterAttribute -> {
            if (configuration.isMapUnderscoreToCamelCase()) {
                Assert.assertEquals(camelConverterUnderscore.convert(mybatisParameterAttribute.getParameterName()),
                        mybatisParameterAttribute.getColumn());
            } else {
                Assert.assertEquals(noneNameConverter.convert(mybatisParameterAttribute.getParameterName()),
                        mybatisParameterAttribute.getColumn());
            }
        });

        return operateParameterAttributes;

    }


    private boolean isMultiTest(Class<?> interfaceClass, String methodName, SqlCommandType sqlCommandType) {
        Method method = Reflection.chooseMethod(interfaceClass, methodName);
        OperateMethodMeta simpleInsert =
                easyBatisConfiguration.getOperateMethodAssistant().getOperateMethodMeta(interfaceClass
                        , method);
        DefaultEasyBatisSourceGenerator defaultEasyBatisSourceGenerator =
                new DefaultEasyBatisSourceGenerator(easyBatisConfiguration);
        return defaultEasyBatisSourceGenerator.isMulti(simpleInsert, sqlCommandType);
    }


}
