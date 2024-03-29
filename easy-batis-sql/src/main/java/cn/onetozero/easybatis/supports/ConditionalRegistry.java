package cn.onetozero.easybatis.supports;

import cn.onetozero.easybatis.binding.BatisColumnAttribute;
import cn.onetozero.easybatis.snippet.conditional.ConditionalSnippet;

import java.lang.annotation.Annotation;

/**
 * 类描述：
 * 作者：徐卫超 (cc)
 * 时间 2023/1/17 13:48
 */
public interface ConditionalRegistry {

    /**
     * 注册一个条件处理器
     *
     * @param annotationClass    条件的注解
     * @param conditionalSnippet 条件的处理方式
     */
    void register(Class<? extends Annotation> annotationClass, ConditionalSnippet conditionalSnippet);

    /**
     * 通过参数 BatisColumnAttribute 获取执行片段
     *
     * @param columnAttribute 条件参数
     * @return 返回参数上的条件注解 注解可能为空
     */
    Annotation chooseAnnotation(BatisColumnAttribute columnAttribute);

    ConditionalSnippet chooseSnippet(Class<? extends Annotation> annotation);
}
