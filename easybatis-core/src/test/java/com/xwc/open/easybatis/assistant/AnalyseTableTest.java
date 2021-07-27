package com.xwc.open.easybatis.assistant;


import com.xwc.open.easybatis.assistant.model.*;
import com.xwc.open.easybatis.core.AnnotationAssistant;
import com.xwc.open.easybatis.core.EasybatisConfiguration;
import com.xwc.open.easybatis.core.enums.AuditorType;
import com.xwc.open.easybatis.core.enums.IdType;
import com.xwc.open.easybatis.core.model.TableMeta;
import com.xwc.open.easybatis.core.model.table.AuditorMapping;
import com.xwc.open.easybatis.core.model.table.IdMapping;
import com.xwc.open.easybatis.core.model.table.LogicMapping;
import com.xwc.open.easybatis.core.model.table.Mapping;
import org.apache.ibatis.session.Configuration;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Map;


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
        if (tableMetadata.getColumnMetaList().isEmpty()) {
            Assert.fail();
        }
        if (tableMetadata.getId() == null) {
            Assert.fail();
        } else {
            IdMapping id = tableMetadata.getId();
            if (id.getIdType() == IdType.AUTO) {
                Assert.assertFalse(id.isSelectIgnore());
                Assert.assertFalse(id.isUpdateIgnore());
                Assert.assertTrue(id.isInsertIgnore());
            } else {
                Assert.assertFalse(id.isSelectIgnore());
                Assert.assertTrue(id.isUpdateIgnore());
                Assert.assertFalse(id.isInsertIgnore());
            }
        }
        for (Mapping column : tableMetadata.getColumnMetaList()) {
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


    @Test
    public void parseEntityLogicColumn() {
        TableMeta tableMetadata = annotationAssistant.parseEntityMate(LogicUser.class);
        if (tableMetadata.getColumnMetaList().isEmpty()) {
            Assert.fail();
        }
        LogicMapping logic = tableMetadata.getLogic();
        Assert.assertNotNull(logic);
    }

    @Test
    public void parseEntityAuditColumn() {
        TableMeta tableMetadata = annotationAssistant.parseEntityMate(AuditUser.class);
        if (tableMetadata.getColumnMetaList().isEmpty()) {
            Assert.fail();
        }
        Map<AuditorType, AuditorMapping> auditorMap = tableMetadata.getAuditorMap();
        Assert.assertNotNull(auditorMap);
        AuditorMapping auditorColumn = auditorMap.get(AuditorType.INSERT);
        Assert.assertNotNull(auditorColumn);
        Assert.assertEquals(auditorColumn.getColumn(), "update_name01");
        Assert.assertFalse(auditorColumn.isSelectIgnore());
    }

}
