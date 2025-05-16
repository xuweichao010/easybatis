package cn.onetozero.easybatis.snippet.page;

import cn.onetozero.easybatis.binding.BatisColumnAttribute;

import java.util.List;

/**
 * 类描述：
 * @author  徐卫超 (cc)
 * @since 2023/1/31 16:13
 */
public interface PageSnippet {

    String page(List<BatisColumnAttribute> batisColumnAttributes);
}
