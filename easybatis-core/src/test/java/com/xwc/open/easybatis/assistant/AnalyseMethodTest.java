package com.xwc.open.easybatis.assistant;


import com.xwc.open.easybatis.assistant.method.MethodTestUserMapper;
import com.xwc.open.easybatis.assistant.model.User;
import com.xwc.open.easybatis.core.AnnotationAssistant;
import com.xwc.open.easybatis.core.EasybatisConfiguration;
import com.xwc.open.easybatis.core.enums.ConditionType;
import com.xwc.open.easybatis.core.model.MethodMeta;
import com.xwc.open.easybatis.core.model.ParamMapping;
import com.xwc.open.easybatis.core.model.TableMeta;
import org.apache.ibatis.session.Configuration;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;


/**
 * 作者：徐卫超 cc
 * 时间：2020/12/10
 * 描述：方法参数解析
 */
public class AnalyseMethodTest {

    AnnotationAssistant annotationAssistant;
    EasybatisConfiguration configuration;
    TableMeta tableMeta;


    @Before
    public void before() {
        Configuration configuration = new Configuration();
        configuration.setUseActualParamName(true);
        configuration.setMapUnderscoreToCamelCase(true);
        this.configuration = new EasybatisConfiguration(configuration);
        this.annotationAssistant = new AnnotationAssistant(this.configuration);
        this.tableMeta = this.annotationAssistant.parseEntityMate(User.class);
    }

    @Test
    public void parseMethodParamTest1() {
        //查询测试
        Method[] methods = MethodTestUserMapper.class.getMethods();
        for (Method method : methods) {
            Annotation annotation = annotationAssistant.chooseOperationAnnotationType(method);
            if (annotation != null && "find".equals(method.getName())) {
                MethodMeta metadata = annotationAssistant.parseMethodMate(method, tableMeta);
                AtomicInteger count = new AtomicInteger();
                metadata.getParamMetaList().forEach(paramMetaData -> {
                    if (paramMetaData.getParamName().equals("name")) {
                        Assert.assertEquals(ConditionType.EQUAL, paramMetaData.getCondition());
                        Assert.assertEquals("name", paramMetaData.getParamName());
                        Assert.assertEquals("#{name}",paramMetaData.getPlaceholderName().getHolder());
                        count.incrementAndGet();
                    }
                    if (paramMetaData.getParamName().equals("age")) {
                        Assert.assertEquals("age_column", paramMetaData.getColumnName());
                        Assert.assertEquals(ConditionType.IS_NULL, paramMetaData.getCondition());
                        Assert.assertEquals("#{age}",paramMetaData.getPlaceholderName().getHolder());
                        count.incrementAndGet();
                    }
                    if (paramMetaData.getParamName().equals("condition1")) {
                        Assert.assertEquals("condition1", paramMetaData.getColumnName());
                        Assert.assertEquals(ConditionType.EQUAL, paramMetaData.getCondition());
                        Assert.assertEquals("#{paramQuery.condition1}",paramMetaData.getPlaceholderName().getHolder());
                        count.incrementAndGet();
                    }
                    if (paramMetaData.getParamName().equals("condition2")) {
                        Assert.assertEquals("condition2_column", paramMetaData.getColumnName());
                        Assert.assertEquals(ConditionType.IS_NULL, paramMetaData.getCondition());
                        Assert.assertEquals("#{paramQuery.condition2}",paramMetaData.getPlaceholderName().getHolder());
                        count.incrementAndGet();
                    }
                });
                Assert.assertEquals(count.get(), 4);
            } else if (annotation != null && "listIn".equals(method.getName())) {
                MethodMeta metadata = annotationAssistant.parseMethodMate(method, tableMeta);
                List<ParamMapping> paramMetaList = metadata.getParamMetaList();
                paramMetaList.forEach(paramMapping -> {
                    if (paramMapping.getParamName().equals("collection")) {
                        Assert.assertEquals("ids", paramMapping.getColumnName());
                        Assert.assertEquals(ConditionType.IN, paramMapping.getCondition());
                    } else {
                        Assert.fail("期望有一个单参数的IN查询");
                    }
                });
            } else if (annotation != null && "listMultiIn".equals(method.getName())) {
                MethodMeta metadata = annotationAssistant.parseMethodMate(method, tableMeta);
                List<ParamMapping> paramMetaList = metadata.getParamMetaList();
                AtomicInteger count = new AtomicInteger();
                paramMetaList.forEach(paramMapping -> {
                    if (paramMapping.getParamName().equals("ids")) {
                        Assert.assertEquals("ids", paramMapping.getColumnName());
                        Assert.assertEquals(ConditionType.IN, paramMapping.getCondition());
                        count.incrementAndGet();
                    } else if (paramMapping.getParamName().equals("ages")) {
                        Assert.assertEquals("ages", paramMapping.getColumnName());
                        Assert.assertEquals(ConditionType.IN, paramMapping.getCondition());
                        count.incrementAndGet();
                    }
                });
                Assert.assertEquals(count.get(), 2);
            }
        }
    }


    @Test
    public void parseMethodParamTest() {
        //查询测试
        Method[] methods = MethodTestUserMapper.class.getMethods();
        for (Method method : methods) {
            Annotation annotation = annotationAssistant.chooseOperationAnnotationType(method);
            if (annotation != null) {
                MethodMeta metadata = annotationAssistant.parseMethodMate(method, tableMeta);
                AtomicInteger count = new AtomicInteger();
                metadata.getParamMetaList().forEach(paramMetaData -> {
                    if (paramMetaData.getParamName().equals("name")) {
                        Assert.assertEquals(ConditionType.EQUAL, paramMetaData.getCondition());
                        Assert.assertEquals("name", paramMetaData.getParamName());
                        count.incrementAndGet();
                    }
                    if (paramMetaData.getParamName().equals("age")) {
                        Assert.assertEquals("age_column", paramMetaData.getColumnName());
                        Assert.assertEquals(ConditionType.IS_NULL, paramMetaData.getCondition());
                        count.incrementAndGet();
                    }
                    if (paramMetaData.getParamName().equals("paramQuery.condition1")) {
                        Assert.assertEquals("condition1", paramMetaData.getColumnName());
                        Assert.assertEquals(ConditionType.EQUAL, paramMetaData.getCondition());
                        count.incrementAndGet();
                    }
                    if (paramMetaData.getParamName().equals("paramQuery.condition2")) {
                        Assert.assertEquals("condition2_column", paramMetaData.getColumnName());
                        Assert.assertEquals(ConditionType.IS_NULL, paramMetaData.getCondition());
                        count.incrementAndGet();
                    }
                });

            }
        }
    }
}
