package com.xwc.open.easybatis.core.interfaces.snippet;

import com.xwc.open.easybatis.core.interfaces.MyBatisOrSqlTemplate;
import com.xwc.open.easybatis.core.support.MethodMeta;
import com.xwc.open.easybatis.core.support.ParamMeta;
import com.xwc.open.easybatis.core.support.TableMeta;

import java.util.List;

/**
 * 作者：徐卫超 cc
 * 时间：2020/12/24
 * 描述：字段sql构建语句
 */
public interface InsertValueSnippet extends MyBatisOrSqlTemplate {
    String apply(MethodMeta methodMeta);
}
