package cn.onetozero.easy.parse.model;

import cn.onetozero.easy.parse.supports.ValueHandler;
import cn.onetozero.easy.parse.supports.impl.DefaultHandler;
import lombok.*;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.List;

/**
 * 类描述：模型属性映射
 *
 * @author 徐卫超 (cc)
 * @since 2022/11/24 14:19
 */
@Getter
@Setter
@With
@NoArgsConstructor
@AllArgsConstructor
public class ModelAttribute {
    /**
     * 属性的名字
     */
    private String field;
    /**
     * 字段名
     */
    private String column;

    /**
     * 属性的getter 方法
     */
    private Method getter;
    /**
     * 属性的setter 方法
     */
    private Method setter;
    /**
     * 查询忽略
     */
    private boolean selectIgnore;
    /**
     * 更新忽略
     */
    private boolean updateIgnore;
    /**
     * 插入忽略
     */
    private boolean insertIgnore;

    private List<Annotation> annotations;

    private ValueHandler<?> valueHandler = new DefaultHandler();
}
