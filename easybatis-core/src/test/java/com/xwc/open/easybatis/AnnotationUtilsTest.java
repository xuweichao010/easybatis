package com.xwc.open.easybatis;

import com.xwc.open.easybatis.core.anno.auditor.Auditor;
import com.xwc.open.easybatis.core.anno.auditor.UpdateId;
import com.xwc.open.easybatis.core.anno.table.Column;
import com.xwc.open.easybatis.core.anno.table.Table;
import com.xwc.open.easybatis.core.commons.AnnotationUtils;
import org.junit.Assert;
import org.junit.Test;

import java.lang.reflect.Field;

/**
 * 作者：徐卫超 cc
 * 时间：2020/12/14
 * 描述：注解测试
 */
public class AnnotationUtilsTest {
    @Test
    public void findAnnotationFieldsTest() {
        Field[] fields = AnnotationUtilsObject.class.getDeclaredFields();
        for (Field field : fields) {
            if (field.getName().equals("name")) {
                Column column = AnnotationUtils.findAnnotation(field, Column.class);
                Assert.assertNotNull(column);
            } else if (field.getName().equals("updateId")) {
                UpdateId updateId = AnnotationUtils.findAnnotation(field, UpdateId.class);
                Assert.assertNotNull(updateId);
                Auditor auditor = AnnotationUtils.findAnnotation(field, Auditor.class);
                Assert.assertNotNull(auditor);
            }
        }
        Assert.assertEquals(fields.length, 2);
    }

    @Test
    public void findAnnotationTypeTest() {
        Table annotation = AnnotationUtils.findAnnotation(AnnotationUtilsObject.class, Table.class);
        Assert.assertNotNull(annotation);
    }

    @Test
    public void getValueTest() {
        Field[] fields = AnnotationUtilsObject.class.getDeclaredFields();
        for (Field field : fields) {
            if (field.getName().equals("name")) {
                Column column = AnnotationUtils.findAnnotation(field, Column.class);
                Object value = AnnotationUtils.getValue(column, "value");
                Assert.assertEquals(String.valueOf(value), "name");
            }
        }

    }


    @Table("t_test")
    public static class AnnotationUtilsObject {
        @Column("name")
        private String name;

        @UpdateId("update_user_id")
        private Long updateId;
    }
}
