package com.xwc.open.easybatis.components;

import com.xwc.open.easy.parse.EasyConfiguration;
import com.xwc.open.easy.parse.exceptions.EasyException;
import com.xwc.open.easy.parse.model.OperateMethodMeta;
import com.xwc.open.easy.parse.model.parameter.EntityParameterAttribute;
import com.xwc.open.easy.parse.supports.impl.CamelConverterUnderscore;
import com.xwc.open.easy.parse.supports.impl.NoneNameConverter;
import com.xwc.open.easy.parse.utils.Reflection;
import com.xwc.open.easybatis.EasyBatisConfiguration;
import com.xwc.open.easybatis.binding.BatisColumnAttribute;
import com.xwc.open.easybatis.mapper.SimpleSourceGeneratorMapper;
import com.xwc.open.easybatis.supports.DefaultSqlSourceGenerator;
import com.xwc.open.easybatis.supports.SqlSourceGenerator;
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
public class DefaultMyBatisSourceGeneratorCommonMethodTest {

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
        this.easyBatisConfiguration = new EasyBatisConfiguration(new EasyConfiguration());
    }

    @Test
    public void InsertIsMultiTest() {
        Assert.assertFalse(isMultiTest(SimpleSourceGeneratorMapper.class, "insert", SqlCommandType.INSERT));
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
        List<BatisColumnAttribute> simpleInsert = flatEntityParameterAttribute(SimpleSourceGeneratorMapper.class,
                "insert", SqlCommandType.INSERT);
        Assert.assertEquals(14, simpleInsert.size());

    }


    private List<BatisColumnAttribute> flatEntityParameterAttribute(Class<?> interfaceClass, String methodName,
                                                                    SqlCommandType sqlCommandType) {
        Method method = Reflection.chooseMethod(interfaceClass, methodName);
        OperateMethodMeta operateMethodMeta =
                easyBatisConfiguration.getEasyConfiguration().getOperateMethodAssistant().getOperateMethodMeta(interfaceClass
                        , method);
        DefaultSqlSourceGenerator defaultEasyBatisSourceGenerator =
                new DefaultSqlSourceGenerator(easyBatisConfiguration);
        boolean multi = SqlSourceGenerator.isMulti(operateMethodMeta, sqlCommandType);
        List<BatisColumnAttribute> operateParameterAttributes =
                operateMethodMeta.getParameterAttributes().stream().map(parameterAttribute -> {
                    if (parameterAttribute instanceof EntityParameterAttribute) {
                        return defaultEasyBatisSourceGenerator.analysisEntityParameterAttribute((EntityParameterAttribute) parameterAttribute,
                                multi, false, sqlCommandType);
                    } else {
                        throw new EasyException("错误的数据类型");
                    }
                }).flatMap(Collection::stream).collect(Collectors.toList());
        operateParameterAttributes.forEach(BatisParameterAttribute -> {
            if (configuration.isMapUnderscoreToCamelCase()) {
                Assert.assertEquals(camelConverterUnderscore.convert(BatisParameterAttribute.getParameterName()),
                        BatisParameterAttribute.getColumn());
            } else {
                Assert.assertEquals(noneNameConverter.convert(BatisParameterAttribute.getParameterName()),
                        BatisParameterAttribute.getColumn());
            }
        });

        return operateParameterAttributes;

    }


    private boolean isMultiTest(Class<?> interfaceClass, String methodName, SqlCommandType sqlCommandType) {
        Method method = Reflection.chooseMethod(interfaceClass, methodName);
        OperateMethodMeta simpleInsert =
                easyBatisConfiguration.getEasyConfiguration().getOperateMethodAssistant().getOperateMethodMeta(interfaceClass
                        , method);
        return SqlSourceGenerator.isMulti(simpleInsert, sqlCommandType);
    }


}
