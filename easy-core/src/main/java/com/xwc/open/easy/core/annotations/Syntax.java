package com.xwc.open.easy.core.annotations;

import java.lang.annotation.*;

/**
 * 类描述：语法注解  用于识别系统需要的注解,第二种是方便进行语法定位，根据顺序来构建系统中特定需要罗
 * 作者：徐卫超 (cc)
 * 时间 2022/11/25 23:40
 */

@Documented
@Target({ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface Syntax {

    /**
     * 语法所在的位置 必须指定 当系统识别为-1的时候认为该语法错误 语法位置必须是大于>0的数字
     *
     * @return
     */
    int value() default -1;

}
