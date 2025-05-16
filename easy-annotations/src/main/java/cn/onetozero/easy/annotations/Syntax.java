package cn.onetozero.easy.annotations;

import java.lang.annotation.*;

/**
 * 类描述：语法注解  用于识别系统需要的注解,第二种是方便进行语法定位，根据顺序来构建系统中特定需要罗
 * @author  徐卫超 (cc)
 * @since 2022/11/25 23:40
 */

@Documented
@Target({ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface Syntax {

    /**
     * @return 语法所在的位置 必须指定 当系统识别为-1的时候认为该语法错误 语法位置必须是大于>0的数字
     */
    int value() default -1;

}
