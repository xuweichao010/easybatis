package cn.onetozero.easybatis.snippet.order;

import cn.onetozero.easy.parse.model.OperateMethodMeta;
import cn.onetozero.easybatis.binding.BatisColumnAttribute;

import java.util.List;

/**
 * 类描述：
 * @author  徐卫超 (cc)
 * @since 2023/1/31 11:28
 */
public interface OrderSnippet {

    String order(OperateMethodMeta operateMethodMeta, List<BatisColumnAttribute> batisColumnAttributes);
}
