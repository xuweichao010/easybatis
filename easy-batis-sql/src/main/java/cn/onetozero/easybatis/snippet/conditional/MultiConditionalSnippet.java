package cn.onetozero.easybatis.snippet.conditional;

import cn.onetozero.easybatis.binding.BatisColumnAttribute;

/**
 * 类描述：多个属性的过滤语法
 * @author  徐卫超 (cc)
 * @since 2023/1/17 13:51
 */
public interface MultiConditionalSnippet extends ConditionalSnippet {

    String snippet(BatisColumnAttribute fromAttribute, BatisColumnAttribute toAttribute);
}
