package com.xwc.open.easybatis.anno.condition.filter;

import com.xwc.open.easybatis.enums.ConditionType;
import org.springframework.core.annotation.AliasFor;

import java.lang.annotation.*;

/**
 * 创建人：徐卫超
 * 创建时间：2019/4/24  13:01
 * 业务：
 * 功能：
 */
@Documented
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Condition(type = ConditionType.ORDER_BY)
public @interface OrderBy {

    /**
     * 查询条件排序
     *
     * @return
     */
    int index() default 99;

    /**
     * 属性和数据表之间的列关系
     *
     * @return
     */
    @AliasFor("value")
    String column() default "";


    /**
     * 属性和数据表之间的列关系
     *
     * @return
     */
    @AliasFor("column")
    String value() default "";

    /**
     * 条件别名
     * 在JOIN条件中使用
     *
     * @return
     */
    String alias() default "";

    /**
     * 排序方式
     *
     * @return
     */
    OrderEnum order() default OrderEnum.ASC;

    /**
     * 自定义排序方式
     * @return
     */
    String by() default "";

    /**
     * 排序条件：当为对象查询时
     * true: 则采用属性中的值排序，
     * 当值为null时 排序属性不生效
     * false: 采用属性名排序或方法参数名排序
     * 只和属性和参数定义有关
     *
     * @return
     */
    boolean byValue() default false;

    enum OrderEnum {
        ASC("ASC "), DESC("DESC ");
        private String value;

        OrderEnum(String value) {
            this.value = value;
        }

        public String value() {
           return this.value;
        }
    }

}
