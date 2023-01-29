package com.xwc.open.easybatis.snippet.where;

import com.xwc.open.easybatis.binding.BatisColumnAttribute;

import java.util.List;

/**
 * 类描述：
 * 作者：徐卫超 (cc)
 * 时间 2023/1/29 15:16
 */
public interface WhereSnippet {

    String where(List<BatisColumnAttribute> batisColumnAttributes);
}
