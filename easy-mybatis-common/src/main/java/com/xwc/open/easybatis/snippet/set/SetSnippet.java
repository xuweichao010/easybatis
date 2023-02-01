package com.xwc.open.easybatis.snippet.set;

import com.xwc.open.easybatis.binding.BatisColumnAttribute;

import java.util.List;

/**
 * 类描述：
 * 作者：徐卫超 (cc)
 * 时间 2023/2/1 16:30
 */
public interface SetSnippet {

    String set(List<BatisColumnAttribute> attributes);
}
