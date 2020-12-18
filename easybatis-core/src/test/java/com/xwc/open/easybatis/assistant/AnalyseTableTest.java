package com.xwc.open.easybatis.assistant;


import com.xwc.open.easybatis.assistant.table.TableColumn;
import com.xwc.open.easybatis.assistant.table.TableNotValue;
import com.xwc.open.easybatis.assistant.table.TableValue;
import com.xwc.open.easybatis.core.EasybatisConfiguration;
import com.xwc.open.easybatis.core.AnnotationAssistant;
import com.xwc.open.easybatis.core.support.TableMeta;
import com.xwc.open.easybatis.core.support.table.ColumnMeta;
import org.apache.ibatis.session.Configuration;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;


/**
 * 作者：徐卫超 cc
 * 时间：2020/12/10
 * 描述：实体解析单元测试
 */
public class AnalyseTableTest {

    AnnotationAssistant annotationAssistant;
    EasybatisConfiguration configuration;


    @Before
    public void before() {
        Configuration configuration = new Configuration();
        configuration.setUseActualParamName(true);
        configuration.setMapUnderscoreToCamelCase(true);
        this.configuration = new EasybatisConfiguration(configuration);
        this.annotationAssistant = this.configuration.getAnnotationAssistant();
    }

    @Test
    public void parseEntityName() {
        TableMeta tableMetadata = annotationAssistant.parseEntityMate(TableValue.class);
        Assert.assertEquals(tableMetadata.getTableName(), "t_table");
    }

    @Test
    public void parseEntityGenderName() {
        configuration.setTablePrefix("t_");
        TableMeta tableMetadata = annotationAssistant.parseEntityMate(TableNotValue.class);
        Assert.assertEquals(tableMetadata.getTableName(), "t_table_not_value");
    }

    @Test
    public void parseEntityColumn() {
        TableMeta tableMetadata = annotationAssistant.parseEntityMate(TableColumn.class);
        for (ColumnMeta column : tableMetadata.getColumnMetaList()) {
            if (column.getField().equals("address")) {
                Assert.assertEquals(column.getColumn(), "address_column");
            }
            if (column.getField().equals("employeesNum")) {
                Assert.assertTrue(column.isSelectIgnore());
                Assert.assertTrue(column.isUpdateIgnore());
                Assert.assertTrue(column.isInsertIgnore());
            }
            if (column.getField().equals("ignore")) {
                Assert.fail();
            }
        }
    }

//    @Test
//    public void parseMethodParamTest() {
//        //查询测试
//        Method[] methods = AnnotationAssistanTest.parseMethodParamMapper.class.getMethods();
//        for (Method method : methods) {
//            Annotation annotation = annotationAssistan.chooseOperationAnnotationType(method);
//            if (annotation != null) {
//                MethodMeta metadata = annotationAssistan.parseMethodMate(method, null);
//                AtomicInteger count = new AtomicInteger();
//                metadata.getParamMetaData().forEach(paramMetaData -> {
//                    if (paramMetaData.getParamName().equals("name")) {
//                        Assert.assertEquals(ConditionType.EQUEL, paramMetaData.getCondition());
//                        Assert.assertEquals("name", paramMetaData.getParamName());
//                        count.incrementAndGet();
//                    }
//                    if (paramMetaData.getParamName().equals("age")) {
//                        Assert.assertEquals("age_column", paramMetaData.getColumnName());
//                        Assert.assertEquals(ConditionType.IS_NULL, paramMetaData.getCondition());
//                        count.incrementAndGet();
//                    }
//                    if (paramMetaData.getParamName().equals("paramQuery.condition1")) {
//                        Assert.assertEquals("condition1", paramMetaData.getColumnName());
//                        Assert.assertEquals(ConditionType.EQUEL, paramMetaData.getCondition());
//                        count.incrementAndGet();
//                    }
//                    if (paramMetaData.getParamName().equals("paramQuery.condition2")) {
//                        Assert.assertEquals("condition2_column", paramMetaData.getColumnName());
//                        Assert.assertEquals(ConditionType.IS_NULL, paramMetaData.getCondition());
//                        count.incrementAndGet();
//                    }
//                });
//                Assert.assertEquals(count.get(), 4);
//            }
//        }
//    }
//
//    // 支持 parseMethodParamTest 方法的测试
//    public interface parseMethodParamMapper {
//        // 支持 parseMethodParamTest 方法的测试
//        @SelectSql
//        Object find(String name, @IsNull("age_column") String age, AnnotationAssistanTest.ParseMethodParamQuery paramQuery);
//    }
//
//    // 支持 parseMethodParamTest 方法的测试
//    @Data
//    public class ParseMethodParamQuery {
//        private String condition1;
//
//        @IsNull("condition2_column")
//        private String condition2;
//    }
}
