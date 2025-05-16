package cn.onetozero.easybatis.components;

import cn.onetozero.easy.parse.EasyConfiguration;
import cn.onetozero.easy.parse.exceptions.EasyException;
import cn.onetozero.easy.parse.model.OperateMethodMeta;
import cn.onetozero.easy.parse.model.parameter.EntityParameterAttribute;
import cn.onetozero.easy.parse.supports.impl.CamelConverterUnderscore;
import cn.onetozero.easy.parse.supports.impl.NoneNameConverter;
import cn.onetozero.easy.parse.utils.Reflection;
import cn.onetozero.easybatis.binding.BatisColumnAttribute;
import cn.onetozero.easybatis.supports.DefaultSqlSourceGenerator;
import cn.onetozero.easybatis.supports.SqlSourceGenerator;
import cn.onetozero.easybatis.EasyBatisConfiguration;
import cn.onetozero.easybatis.mapper.SimpleSourceGeneratorMapper;
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
 * @author  徐卫超 (cc)
 * @since 2023/1/14 10:47
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
        this.easyBatisConfiguration.setMapUnderscoreToCamelCase(true);
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
