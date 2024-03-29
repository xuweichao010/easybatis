package cn.onetozero.easy.parse.annotations;


import java.lang.annotation.*;

/**
 * 创建人：徐卫超
 * 创建时间：2019/1/18  11:46
 * 业务：描述实体类和数据仓库存储的关系
 * 功能：
 */

@Documented
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface Table {
    /**
     * 指定实体对应的表名
     *
     * @return
     */
    String value() default "";

}
