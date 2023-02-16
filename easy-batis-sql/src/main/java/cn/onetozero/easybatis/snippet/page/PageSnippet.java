package cn.onetozero.easybatis.snippet.page;

import cn.onetozero.easybatis.binding.BatisColumnAttribute;

import java.util.List;

/**
 * 类描述：
 * 作者：徐卫超 (cc)
 * 时间 2023/1/31 16:13
 */
public interface PageSnippet {

    String page(List<BatisColumnAttribute> batisColumnAttributes);
}
