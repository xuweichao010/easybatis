package com.xwc.open.easy.core.annotations;


import com.xwc.open.easy.core.enums.IdType;
import com.xwc.open.easy.core.supports.IdGenerateHandler;
import com.xwc.open.easy.core.supports.impl.NoneIdGenerateHandler;

import java.lang.annotation.*;

/**
 * 创建人：徐卫超
 * 创建时间：2019/1/18  15:13
 * 业务：主键标识 用于描述对象的主键信息
 * 功能：
 */

@Documented
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface Id {
    /**
     * 属性和数据表之间的列关系
     */
    String value() default "";

    /**
     * 主键类型
     * GLOBAL: 使用配置的主键类型
     * AUTO: 自增长数据类型 交给数据库来决定
     * UUID: UUID来作为数据主键
     * INPUT: 以用户提供的主键来作为标准
     * HANDLER: 用于自定义策略
     */
    IdType type() default IdType.GLOBAL;

    /**
     * id生成器
     * 只有 属性type等于INPUT的时候 需要使用该属性
     *
     * @return
     */
    Class<? extends IdGenerateHandler> idGenerateHandler() default NoneIdGenerateHandler.class;


}
